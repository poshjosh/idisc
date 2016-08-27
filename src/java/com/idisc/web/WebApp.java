package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.SessionLoader;
import com.bc.util.XLogger;
import com.bc.util.concurrent.BoundedExecutorService;
import com.bc.util.concurrent.DirectExecutorService;
import com.bc.util.concurrent.NamedThreadFactory;
import com.idisc.core.FeedService;
import com.idisc.core.IdiscApp;
import com.idisc.core.IdiscAuthSvcSession;
import com.idisc.pu.SearchResultsHandlerFactoryImpl;
import com.idisc.pu.entities.Site;
import com.idisc.shared.SharedContext;
import com.idisc.shared.SharedContextImpl;
import com.idisc.shared.feedid.FeedidsImpl;
import com.idisc.shared.feedid.FeedidsServiceImpl;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
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
import com.idisc.pu.SearchResultsHandlerFactory;

public class WebApp implements AppContext, ThreadPoolData {
    
  private final boolean productionMode;
  
  private final boolean asyncProcessingEnabled;
  
  private final long appSessionTimeoutMillis;
  
  private final long memoryAtStartup;
  
  private List<Site> sites;
  
  private ThreadPoolExecutor threadPoolExecutor;
  
  private final Configuration config;
  
  private AuthSvcSession authSvcSession;
  
  private final ServletContext context;
  
  private final IdiscApp idiscApp;
  
  private final SharedContext sharedContext;
  
  public WebApp(ServletContext context, Configuration config, IdiscApp idiscApp, boolean productionMode) {
      
    this.memoryAtStartup = Runtime.getRuntime().freeMemory();
XLogger.getInstance().log(Level.INFO, "Memory at startup: {0}, available processors: {1}", 
        this.getClass(), this.memoryAtStartup, Runtime.getRuntime().availableProcessors());

    this.context = context;
    this.config = config;
    
    this.productionMode = productionMode;
    
    final Map appProps = WebApp.this.getAppProperties();
    final long connectTime = Long.parseLong(appProps.get(Appproperties.CONNECT_TIMEOUT_MILLIS).toString());
    final long readTime = Long.parseLong(appProps.get(Appproperties.READ_TIMEOUT_MILLIS).toString());
    appSessionTimeoutMillis = (connectTime + readTime);
XLogger.getInstance().log(Level.INFO, "Session timeout for apps: {0} seconds", 
        this.getClass(), appSessionTimeoutMillis);
    
    asyncProcessingEnabled = config.getBoolean(ConfigNames.PROCESS_REQUEST_ASYNC, true);
XLogger.getInstance().log(Level.INFO, "Async processing enabled: {0}", this.getClass(), asyncProcessingEnabled);
    
    String authsvc_url = this.config.getString("authsvc.url");
    String app_name = WebApp.this.getAppName();
    String app_email = this.config.getString("authsvc.emailaddress");
    String app_pass = this.config.getString("authsvc.password");
    
    SessionLoader sessLoader = new SessionLoader(){
      @Override
      public void onLoad(AuthSvcSession session) {
        WebApp.this.authSvcSession = session;
      }
      @Override
      protected AuthSvcSession getNewSession(String target, int maxRetrials, long retrialIntervals) {
        return new IdiscAuthSvcSession(target, maxRetrials, retrialIntervals);
      }
    };

    sessLoader.setMaxRetrials(1);
    sessLoader.setRetrialInterval(30000);

    sessLoader.loadAfter(30L, TimeUnit.SECONDS, authsvc_url, app_name, app_email, app_pass);

    // We call this after initializing the IdiscApp so that its properties will
    // supercede any previously loaded logging configuration file
    //
    this.initLogging2();
    
    this.idiscApp = idiscApp;
    
    this.sharedContext = new SharedContextImpl(new FeedidsServiceImpl(new FeedidsImpl()));
    
    final boolean startFeedUpdateService = config.getBoolean(ConfigNames.START_FEED_UPDATE_SERVICE, true);
    if (startFeedUpdateService) {
        
      final int initialDelay = config.getInt(ConfigNames.FEED_CYCLE_DELAY);
      final int interval = config.getInt(ConfigNames.FEED_CYCLE_INTERVAL);
      
      ScheduledExecutorService svc = WebApp.this.getGlobalScheduledExecutorService(true);
      
      FeedService feedService = new DefaultFeedService(idiscApp, config);      

      DefaultFeedUpdateTask task = new DefaultFeedUpdateTask(context, feedService);
      
      svc.scheduleWithFixedDelay(task, initialDelay, interval, TimeUnit.MINUTES);
    }
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
      XLogger.getInstance().log(Level.INFO, "Succesfully loaded {0} from: {1}", 
      this.getClass(), ConfigNames.LOGGING_PROPERTIES_FILE, loggingPropertiesFile);
    }catch (final IOException e) {
      XLogger.getInstance().log(Level.WARNING, "Failed to load "+ConfigNames.LOGGING_PROPERTIES_FILE+
              " from: "+loggingPropertiesFile, this.getClass(), e);
    }    
  }
  
  private void initLogging2() {
    String logLevelStr = this.config.getString("logLevel");
    if(logLevelStr != null) {
      try {
        Level logLevel = Level.parse(logLevelStr);
        XLogger logger = XLogger.getInstance();
        String packageLoggerName = com.idisc.web.WebApp.class.getPackage().getName();
        if(!this.isProductionMode()) {
            logger.transferConsoleHandler("", packageLoggerName, true);
            // Most home grown libraries start with com.bc
            // Only top level projects, that is projects which may not be used
            // as libraries for others should do this
            logger.setLogLevel("com.bc", logLevel);
        }
        XLogger.getInstance().setLogLevel(packageLoggerName, logLevel);
      } catch (Exception e) {
        XLogger.getInstance().log(Level.WARNING, "Error setting log level to: " + logLevelStr, getClass(), e);
      }
    }
  }  
  
  @Override
  public List<Site> getSites() {
    if(sites == null) {
      com.bc.jpa.dao.BuilderForSelect<Site> select = idiscApp.getJpaContext().getBuilderForSelect(Site.class);  
      sites = select.from(Site.class).getResultsAndClose();
    }
    return sites;
  }
  
  @Override
  public AuthSvcSession getAuthSvcSession() {
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
  public boolean isAsyncProcessingEnabled() {
    return this.asyncProcessingEnabled;
  }

  @Override
  public long getAppSessionTimeoutMillis() {
    return appSessionTimeoutMillis;
  }

  @Override
  public BigDecimal getMemoryLevel() {
    BigDecimal freeMemoryObj = new BigDecimal(Runtime.getRuntime().freeMemory());
    BigDecimal memoryAtStartupObj = new BigDecimal(this.memoryAtStartup);
    return freeMemoryObj.divide(memoryAtStartupObj, 2, RoundingMode.HALF_UP);
  }

  @Override
  public long getMemoryAtStartup() {
    return this.memoryAtStartup;
  }

  @Override
  public Map getAppProperties() {
    final String name = "AppProperties";
    Map appProps;
    WeakReference<Map> _apwr = (WeakReference<Map>)context.getAttribute(name);
    if(_apwr == null || _apwr.get() ==  null) {
      if(_apwr != null) {
        _apwr.clear();
      }
      Appproperties loader = new Appproperties();
      try{
        appProps = Collections.unmodifiableMap(loader.load(context));
        _apwr = new WeakReference<>(appProps);
        context.setAttribute(name, _apwr);
      }catch(IOException e) {
        XLogger.getInstance().log(Level.WARNING, "Error loading: "+loader.getFilename(), this.getClass(), e);
        appProps = Collections.EMPTY_MAP;  
      }
    }else{
      appProps = _apwr.get();
    } 
    return appProps;
  }
  
  @Override
  public SearchResultsHandlerFactory getSearchHandlerFactory() {
    return this.getSearchHandlerFactory(false);
  }
  
  @Override
  public SearchResultsHandlerFactory getSearchHandlerFactory(boolean createIfNone) {
    final String name = "SearchHandlerFactory";
    SearchResultsHandlerFactory shf = (SearchResultsHandlerFactory)context.getAttribute(name);
    if(shf == null && createIfNone) {
      shf = new SearchResultsHandlerFactoryImpl();
      context.setAttribute(name, shf);
    }
    return shf;
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
