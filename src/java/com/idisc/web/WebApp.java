package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.SessionLoader;
import com.bc.util.XLogger;
import com.bc.util.concurrent.DirectExecutorService;
import com.idisc.core.FeedUpdateService;
import com.idisc.core.FeedUpdateTask;
import com.idisc.core.IdiscApp;
import com.idisc.core.IdiscAuthSvcSession;
import com.idisc.core.jpa.SearchHandlerFactory;
import com.idisc.core.jpa.SearchHandlerFactoryImpl;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class WebApp {
    
  private boolean debug;
    
  private long appSessionTimeoutMillis;
  
  private final boolean productionMode;
  
  private final long memoryAtStartup;
  
  private final String propertiesFileName;
  
  private final String defaultPropertiesFileName;
  
  private ExecutorService globalExecutor;
  
  private FeedUpdateService feedUpdateService;
  
  private Configuration config;
  
  private AuthSvcSession authSvcSession;
  
  private ServletContext servletContext;
  
  private static WebApp instance;
  
  private WebApp(boolean productionMode){
    this.productionMode = productionMode;
    this.memoryAtStartup = Runtime.getRuntime().freeMemory();
    XLogger.getInstance().log(Level.INFO, "Memory at startup: {0}, available processors: {1}", 
            this.getClass(), this.memoryAtStartup, Runtime.getRuntime().availableProcessors());
    this.defaultPropertiesFileName = "META-INF/properties/idiscwebdefaults.properties";
    this.propertiesFileName = this.productionMode ?
            "META-INF/properties/idiscweb.properties" :
            "META-INF/properties/idiscweb_devmode.properties";
  }
  
  public static WebApp getInstance() {
    return getInstance(false);
  }

  public static WebApp getInstance(boolean productionMode) {
    if (instance == null) {
      instance = new WebApp(productionMode);
    }
    return instance;
  }
  
  public ExecutorService getGlobalExecutorService(boolean createIfNeed) {
    if(this.globalExecutor == null && createIfNeed) {
        this.globalExecutor = this.createFixedThreadPoolExecutorService(1);
    } 
    return this.globalExecutor;
  }
  
  /**
   * A poolSize < 1 will result in a {@link com.bc.util.concurrent.DirectExecutorService}
   * @param poolSize The thread pool size for the executor service
   * @return An executor service
   */
  public ExecutorService createFixedThreadPoolExecutorService(int poolSize) {
    ExecutorService es;
    if(poolSize < 1) {
      es = new DirectExecutorService();
    }else{
      es = Executors.newFixedThreadPool(poolSize);
    }
    return es;
  }
  
  private SearchHandlerFactory _searchHandlerFactory;
  public SearchHandlerFactory getSearchHandlerFactory(boolean create) {
    if(_searchHandlerFactory == null && create) {
      _searchHandlerFactory = new SearchHandlerFactoryImpl();
    }
    return _searchHandlerFactory;
  }
  
  private WeakReference<Map> _appProperties_weakReference;
  public Map getAppProperties() {
    Map appProps;
    if(_appProperties_weakReference == null || _appProperties_weakReference.get() ==  null) {
      if(_appProperties_weakReference != null) {
        _appProperties_weakReference.clear();
      }
      Appproperties loader = new Appproperties();
      try{
        appProps = Collections.unmodifiableMap(loader.load());
      }catch(IOException e) {
        XLogger.getInstance().log(Level.WARNING, "Error loading: "+loader.getFilename(), this.getClass(), e);
        appProps = Collections.EMPTY_MAP;  
      }
      _appProperties_weakReference = new WeakReference<>(appProps);
    }else{
      appProps = _appProperties_weakReference.get();
    } 
    return appProps;
  }
  
  public void init(ServletContext context)
    throws ServletException, IOException, ConfigurationException, 
          IllegalAccessException, InterruptedException, InvocationTargetException
  {
    
    URL defaultFileLoc = context.getResource(this.defaultPropertiesFileName);
    
    URL fileLoc = context.getResource(this.propertiesFileName);
    
    init(context, defaultFileLoc, fileLoc);
  }
  
  public void init(ServletContext context, URL defaultFileLocation, URL fileLocation)
    throws ServletException, IOException, ConfigurationException, 
          IllegalAccessException, InterruptedException, InvocationTargetException
  {
    XLogger.getInstance().log(Level.INFO, "Initializing: {0}", getClass(), getClass().getName());
    this.servletContext = context;
    
    this.config = loadConfig(defaultFileLocation, fileLocation, ',');
    
    debug = this.config.getBoolean("debug", false);
    
    String authsvc_url = this.config.getString("authsvc.url");
    String app_name = this.getAppName();
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

    boolean startFeedUpdateService = config.getBoolean(ConfigNames.START_FEED_UPDATE_SERVICE, true);
    if (startFeedUpdateService) {
      this.feedUpdateService = new FeedUpdateService(){
        @Override
        public FeedUpdateTask newTask() {
          return new IdiscUpdateTask();
        }
      };

      final int delay = config.getInt(ConfigNames.FEED_CYCLE_DELAY);
      final int interval = config.getInt(ConfigNames.FEED_CYCLE_INTERVAL);
        
      this.feedUpdateService.start(delay, interval, TimeUnit.MINUTES);
    }
    
    this.initIdiscApp();
    
    this.servletContext.setAttribute(Attributes.APP, this);
    
    // We call this after initializing the IdiscApp so that its properties will
    // supercede any previously loaded logging configuration file
    //
    this.initLogging2();
    
    final Map appProps = WebApp.getInstance().getAppProperties();
    final long connectTime = Long.parseLong(appProps.get(Appproperties.CONNECT_TIMEOUT_MILLIS).toString());
    final long readTime = Long.parseLong(appProps.get(Appproperties.READ_TIMEOUT_MILLIS).toString());
    appSessionTimeoutMillis = (connectTime + readTime);
XLogger.getInstance().log(Level.INFO, "Session timeout for apps: {0} seconds", 
        this.getClass(), appSessionTimeoutMillis);
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
  
  private IdiscApp initIdiscApp()
    throws ConfigurationException, IOException, IllegalAccessException, 
          InterruptedException, InvocationTargetException
  {
    IdiscApp idiscApp = IdiscApp.getInstance();
    
    String scrapperPropsFile = this.config.getString("scrapperPropertiesFile");
    XLogger.getInstance().log(Level.INFO, "Scrapper properties file: {0}", getClass(), scrapperPropsFile);
    if (scrapperPropsFile != null) {
      idiscApp.setScrapperPropertiesFilename(scrapperPropsFile);
    }
    
    String persistenceFile = this.config.getString("persistenceFile");
    XLogger.getInstance().log(Level.INFO, "Persistence file: {0}", getClass(), persistenceFile);
    if (persistenceFile != null) {
      idiscApp.setPersistenceFilename(persistenceFile);
    }
    
    String corePropertiesFile = this.config.getString("idisccorePropertiesFile");
    XLogger.getInstance().log(Level.INFO, "Idisc core properties file: {0}", getClass(), corePropertiesFile);
    if (corePropertiesFile != null) {
      URL fileLoc = this.servletContext.getResource(corePropertiesFile);
      idiscApp.init(fileLoc);
    } else {
      idiscApp.init();
    }
    
    IdiscApp.setInstance(idiscApp);
    
    return idiscApp;
  }
  
  public boolean saveConfiguration() throws ConfigurationException
  {
    return saveConfiguration(this.config);
  }
  
  public boolean saveConfiguration(Configuration cfg) throws ConfigurationException {
    if ((cfg instanceof CompositeConfiguration)) {
      CompositeConfiguration cc = (CompositeConfiguration)cfg;
      Configuration imc = cc.getInMemoryConfiguration();
      if ((imc instanceof PropertiesConfiguration)) {
        ((PropertiesConfiguration)imc).save();
        return true;
      }
    } else { if ((cfg instanceof PropertiesConfiguration)) {
        ((PropertiesConfiguration)cfg).save();
        return true;
      }
      throw new UnsupportedOperationException("Unexpected configuration type: " + cfg.getClass().getName());
    }
    return false;
  }
  

  public Configuration loadConfig(URL defaultFileLocation, URL fileLocation, char listDelimiter)
    throws ConfigurationException
  {
    XLogger.getInstance().log(Level.INFO, 
            "Loading properties configuration. List delimiter: {0}\nDefault file: {1}\nFile: {2}", 
            this.getClass(), Character.valueOf(listDelimiter), defaultFileLocation, fileLocation);

    if (fileLocation == null) {
      throw new NullPointerException();
    }
    
    Configuration output;
    if (defaultFileLocation != null) {
        
      CompositeConfiguration composite = new CompositeConfiguration();
      
      PropertiesConfiguration cfg = loadConfig(fileLocation, listDelimiter);
      
      composite.addConfiguration(cfg, true);
      
      PropertiesConfiguration defaults = loadConfig(defaultFileLocation, listDelimiter);
      
      composite.addConfiguration(defaults);
      
      output = composite;
    }
    else
    {
      output = loadConfig(fileLocation, listDelimiter);
    }
    
    return output;
  }
  
  private PropertiesConfiguration loadConfig(URL fileLocation, char listDelimiter)
    throws ConfigurationException {
    PropertiesConfiguration cfg = new PropertiesConfiguration();
    cfg.setListDelimiter(listDelimiter);
    cfg.setURL(fileLocation);
    cfg.load();
    return cfg;
  }
  
  public AuthSvcSession getAuthSvcSession() {
    return this.authSvcSession;
  }
  
  public String getAppName() {
    return this.servletContext.getInitParameter("appName");
  }
  
  public String getAppUrl() {
    return this.servletContext.getInitParameter("appUrl");
  }
  
  public ServletContext getServletContext() {
    return this.servletContext;
  }
  
  public Configuration getConfiguration() {
    return this.config;
  }
  
  public String getPropertiesFileName() {
    return this.propertiesFileName;
  }
  
  public String getDefaultPropertiesFileName() {
    return this.defaultPropertiesFileName;
  }

  public boolean isProductionMode() {
    return this.productionMode;
  }
  
  public FeedUpdateService getFeedUpdateService() {
    return feedUpdateService;
  }

  public long getMemoryAtStartup() {
    return memoryAtStartup;
  }

  public boolean isDebug() {
    return debug;
  }

  public long getAppSessionTimeoutMillis() {
    return appSessionTimeoutMillis;
  }
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
