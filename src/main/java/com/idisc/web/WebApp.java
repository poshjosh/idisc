package com.idisc.web;

import com.authsvc.client.AppAuthenticationSession;
import com.authsvc.client.AppAuthenticationSessionImpl;
import com.authsvc.client.AuthDetailsLocalDiscStore;
import com.authsvc.client.AuthDetailsStore;
import com.authsvc.client.AuthenticationException;
import com.authsvc.client.JsonResponseIsErrorTestImpl;
import com.bc.net.impl.RequestBuilderImpl;
import com.bc.util.Retry;
import com.bc.util.concurrent.BoundedExecutorService;
import com.bc.util.concurrent.DirectExecutorService;
import com.bc.util.concurrent.NamedThreadFactory;
import com.idisc.core.IdiscApp;
import com.idisc.pu.FeedDao;
import com.idisc.shared.SharedContext;
import com.idisc.shared.SharedContextImpl;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;
import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class WebApp implements AppContext, ThreadPoolData {

  private transient static final Logger LOG = Logger.getLogger(WebApp.class.getName());
    
  private final boolean productionMode;
  
  private final long appSessionTimeoutMillis;
  
  private ThreadPoolExecutor threadPoolExecutor;
  
  private final Configuration config;
  
  private AppAuthenticationSession authSvcSession;
  
  private final ServletContext context;
  
  private final IdiscApp idiscApp;
  
  private final SharedContext sharedContext;
  
  private final MemoryManager memoryManager;
  
  public WebApp(ServletContext context, Configuration config, IdiscApp idiscApp, boolean productionMode) {
      
    this.memoryManager = new MemoryManagerImpl();

    this.context = context;
    this.config = config;
    
    this.productionMode = productionMode;
    
    final Map appProps = WebApp.this.getAppProperties();
    final long connectTime = Long.parseLong(appProps.get(Appproperties.CONNECT_TIMEOUT_MILLIS).toString());
    final long readTime = Long.parseLong(appProps.get(Appproperties.READ_TIMEOUT_MILLIS).toString());
    appSessionTimeoutMillis = (connectTime + readTime);
    
    LOG.log(Level.INFO, "Session timeout for apps: {0} seconds", appSessionTimeoutMillis);
    
    final AuthDetailsLocalDiscStore.GetPathForName pathContext = new AuthDetailsLocalDiscStore.GetPathForName() {
        @Override
        public String apply(String filename) {
            final String output = IdiscApp.getInstance().getAbsolutePath(filename);
            LOG.info(() -> filename + '=' + output);            
            return output;
        }
    };
    
    final AuthDetailsStore authDetailsStore = new AuthDetailsLocalDiscStore(
            pathContext, "com.idiscweb.authsvc.app.token", "com.idiscweb.authsvc.app.details"
    );
    
    final Retry retry = new Retry(2, (int)TimeUnit.MINUTES.toMillis(10));
    
    final Callable createAuthSessTask = () -> {
        try{
            this.authSvcSession = this.createAuthSession(authDetailsStore);
            return this.authSvcSession;
        }catch(IOException | ParseException | AuthenticationException e) {
            LOG.warning("Failed to create authentication service session");
            throw new RuntimeException(e);
        }catch(RuntimeException e) {
            LOG.warning("Failed to create authentication service session");
            throw e;
        }
    };
    
    retry.retryAsyncIf(createAuthSessTask, (t) -> true, 30_000);

    // We call this after initializing the IdiscApp so that its properties will
    // supercede any previously loaded logging configuration file
    //
    this.initLogging2();
    
    this.idiscApp = idiscApp;
    
    this.sharedContext = new SharedContextImpl();
  }
  
  public void startFeedLoadService() {
    
    final boolean startFeedUpdateService = config.getBoolean(ConfigNames.START_FEED_UPDATE_SERVICE, true);
    if (startFeedUpdateService) {
        
      final int initialDelay = config.getInt(ConfigNames.FEED_CYCLE_DELAY);
      final int interval = config.getInt(ConfigNames.FEED_CYCLE_INTERVAL);
      
      ScheduledExecutorService svc = WebApp.this.getGlobalScheduledExecutorService(true);
      
      final FeedDao feedService = new DefaultFeedService(this);      

      DefaultFeedUpdateTask task = new DefaultFeedUpdateTask(context, feedService);
      
      svc.scheduleWithFixedDelay(task, initialDelay, interval, TimeUnit.MINUTES);
    }
  }
  
    private AppAuthenticationSession createAuthSession(AuthDetailsStore authDetailsStore) 
            throws IOException, ParseException, AuthenticationException {
        
        Objects.requireNonNull(authDetailsStore);
        final String authsvc_url = Objects.requireNonNull(this.config.getString("authsvc.url"));
        final String app_name = WebApp.this.getAppName();
        final String app_email = Objects.requireNonNull(this.config.getString("authsvc.emailaddress"));
        final String app_pass = Objects.requireNonNull(this.config.getString("authsvc.password"));
        
        final AppAuthenticationSessionImpl authenticationSession = new AppAuthenticationSessionImpl(
                new RequestBuilderImpl(), authsvc_url,
                authDetailsStore, new JsonResponseIsErrorTestImpl()
        );

        authenticationSession.init(app_name, app_email, app_pass, true);
        
        return authenticationSession;
    }

  @Override
  public IdiscApp getIdiscApp() {
    return this.idiscApp;
  }

  @Override
  public final SharedContext getSharedContext() {
    return sharedContext;
  }
  
  private void initLogging() {
    final String loggingPropertiesFile = this.config.getString(ConfigNames.LOGGING_PROPERTIES_FILE, null);
    
    try{
      InputStream inputStream = new FileInputStream(loggingPropertiesFile);
      LogManager.getLogManager().readConfiguration(inputStream);
      LOG.info(() -> "Succesfully loaded "+ConfigNames.LOGGING_PROPERTIES_FILE+
              " from: " + loggingPropertiesFile);
    }catch (final IOException e) {
      LOG.log(Level.WARNING, "Failed to load "+ConfigNames.LOGGING_PROPERTIES_FILE+
              " from: "+loggingPropertiesFile, e);
    }    
  }
  
  private void initLogging2() {
    String logLevelStr = this.config.getString("logLevel");
    if(logLevelStr != null) {
      try {
        Level logLevel = Level.parse(logLevelStr);
        final com.bc.util.Log logger = com.bc.util.Log.getInstance();
        String packageLoggerName = com.idisc.web.WebApp.class.getPackage().getName();
        if(!this.isProductionMode()) {
            logger.transferConsoleHandler("", packageLoggerName, true);
            // Most home grown libraries start with com.bc
            // Only top level projects, that is projects which may not be used
            // as libraries for others should do this
            logger.setLogLevel("com.bc", logLevel);
        }
        com.bc.util.Log.getInstance().setLogLevel(packageLoggerName, logLevel);
      } catch (Exception e) {
        com.bc.util.Log.getInstance().log(Level.WARNING, "Error setting log level to: " + logLevelStr, getClass(), e);
      }
    }
  }  
  
  @Override
  public AppAuthenticationSession getAuthSvcSession() {
    return this.authSvcSession;
  }
  
  @Override
  public String getAppName() {
    return this.context.getInitParameter("appName");
  }
  
  @Override
  public String getAppUrl() {
    return this.context.getInitParameter("appUrl");
  }
  
  public ServletContext getContext() {
    return this.context;
  }
  
  @Override
  public Configuration getConfiguration() {
    return this.config;
  }
  
  @Override
  public boolean isProductionMode() {
    return this.productionMode;
  }
  
  @Override
  public long getAppSessionTimeoutMillis() {
    return appSessionTimeoutMillis;
  }

  private Map appProperties;
  private long lastLoadTime;
  @Override
  public Map getAppProperties() {
    if(appProperties == null || (System.currentTimeMillis() - lastLoadTime) > TimeUnit.HOURS.toMillis(1)) {
      try{  
        appProperties = new Appproperties().load(context);
        lastLoadTime = System.currentTimeMillis();
      }catch(IOException e) {
        LOG.log(Level.WARNING, null, e);
      }
    }
    LOG.log(Level.FINER, "App Properties: {0}", appProperties);
    return appProperties;
  }
  
  @Override
  public ExecutorService getGlobalExecutorService() {
   return this.getGlobalExecutorService(false);
  }

  /**
   * A poolSize < 1 will result in a {@link com.bc.util.concurrent.DirectExecutorService}
   * @param createIfNone
   * @return 
   */
  @Override
  public ExecutorService getGlobalExecutorService(boolean createIfNone) {
      
     final String executorServiceName = "GlobalExecutorService";
     
     ExecutorService es = (ExecutorService)context.getAttribute(executorServiceName);
     
     if(es == null && createIfNone) {
         
         final int avail = Runtime.getRuntime().availableProcessors();
         
         final int poolSize = this.config.getInt(ConfigNames.REQUEST_EXECUTOR_SERVICE_POOL_SIZE, avail);
         
         if(poolSize < 1) {
             
             es = new DirectExecutorService();
             
         }else{
             
            final int queueCapacity = this.config.getInt(ConfigNames.REQUEST_EXECUTOR_SERVICE_QUEUE_CAPACITY, avail);
            
            threadPoolExecutor = new BoundedExecutorService(
                    executorServiceName+"_ThreadPool", poolSize, queueCapacity, true);
            
            es = threadPoolExecutor;
            
            final int poolSizeAdjustmentIntervalMinutes = this.config.getInt(
                    ConfigNames.REQUEST_EXECUTOR_SERVICE_POOLSIZE_ADJUSTMENT_INTERVAL_MINUTES, 15);
            
            if(poolSizeAdjustmentIntervalMinutes > 0) {
                
              final String sizeAdjustingServiceName = "AdjustPoolSizeBasedOnMemoryLevelExecutorService";
              
              ScheduledExecutorService ses = this.getScheduledThreadPoolExecutor(
                      sizeAdjustingServiceName, 1, true, true);
              
              AppContext appContext = (AppContext)context.getAttribute(Attributes.APP_CONTEXT);
              
              ses.scheduleAtFixedRate(new AdjustPoolSizeBasedOnMemoryLevelTask(appContext, threadPoolExecutor), 
                      poolSizeAdjustmentIntervalMinutes, poolSizeAdjustmentIntervalMinutes, TimeUnit.MINUTES);
            }
         }

        context.setAttribute(executorServiceName, es);
     }
     return es;
  }

  @Override
  public ScheduledExecutorService getGlobalScheduledExecutorService() {
    return this.getGlobalScheduledExecutorService(false);
  }

  @Override
  public ScheduledExecutorService getGlobalScheduledExecutorService(boolean createIfNone) {
    final String name = "GlobalScheduledExecutorSevice";
    return this.getScheduledThreadPoolExecutor(name, 1, true, createIfNone);
  }
  
  protected ScheduledExecutorService getScheduledThreadPoolExecutor(
          String attributeName, int poolSize, boolean daemonThreads, boolean createIfNone) {
    ScheduledExecutorService ses = (ScheduledExecutorService)context.getAttribute(attributeName);
    if(ses == null && createIfNone) {
        ses = new ScheduledThreadPoolExecutor(
                poolSize, new NamedThreadFactory(attributeName+"_ThreadPool", daemonThreads));
        context.setAttribute(attributeName, ses);
    } 
    return ses;
  }
  
  @Override
  public ThreadPoolData getGlobalExecutorServiceThreadPoolData() {
    return this.threadPoolExecutor == null ? null : this;
  }

  @Override
  public MemoryManager getMemoryManager() {
    return this.memoryManager;
  }
  
//////////////////////////Begin ThreadPoolData methods ///////////////////////
  
  @Override
  public int getQueueSize() {
    return this.threadPoolExecutor == null || this.threadPoolExecutor.getQueue() == null ? 
            -1 : this.threadPoolExecutor.getQueue().size();
  }
  
  @Override
  public int getCorePoolSize() {
    return threadPoolExecutor.getCorePoolSize();
  }

  @Override
  public int getMaximumPoolSize() {
    return threadPoolExecutor.getMaximumPoolSize();
  }

  @Override
  public long getKeepAliveTime(TimeUnit unit) {
    return threadPoolExecutor.getKeepAliveTime(unit);
  }

  @Override
  public int getPoolSize() {
    return threadPoolExecutor.getPoolSize();
  }

  @Override
  public int getActiveCount() {
    return threadPoolExecutor.getActiveCount();
  }

  @Override
  public int getLargestPoolSize() {
    return threadPoolExecutor.getLargestPoolSize();
  }

  @Override
  public long getTaskCount() {
    return threadPoolExecutor.getTaskCount();
  }

  @Override
  public long getCompletedTaskCount() {
    return threadPoolExecutor.getCompletedTaskCount();
  }
////////////////////////// End ThreadPoolData methods ////////////////////////
}
/**
 * 
com.idisc.web.servlets.RequestHandlerProviderServlet.level = ALL
com.idisc.web.beans.FeedSelectorBean.level = ALL
com.idisc.web.servlets.handlers.request.Getmultipleresults.level = ALL
com.idisc.web.listeners.CloseAutoCloseable.level = ALL
com.idisc.web.servlets.handlers.request.Select.level = ALL
com.idisc.web.servlets.handlers.response.JsonResponseHandler.level = ALL
 * 
 */
