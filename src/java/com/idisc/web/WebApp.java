package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.SessionLoader;
import com.bc.util.XLogger;
import com.idisc.core.FeedUpdateService;
import com.idisc.core.FeedUpdateTask;
import com.idisc.core.IdiscApp;
import com.idisc.core.IdiscAuthSvcSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class WebApp
{
  private static final transient Logger logger = Logger.getLogger(WebApp.class.getName());
  
  private final boolean productionMode;
  
  private final long memoryAtStartup;
  
  private String propertiesFileName;
  
  private final String defaultPropertiesFileName;
  
  private FeedUpdateService feedUpdateService;
  
  private Configuration config;
  
  private AuthSvcSession authSvcSession;
  
  private ServletContext servletContext;
  
  private static WebApp instance;
  
  private WebApp(boolean productionMode)
  {
    this.productionMode = productionMode;
    this.memoryAtStartup = Runtime.getRuntime().freeMemory();
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
    
    String logLevelStr = this.config.getString("logLevel");
    if(logLevelStr != null) {
      try {
        Level logLevel = Level.parse(logLevelStr);
        String packageLoggerName = com.idisc.web.WebApp.class.getPackage().getName();
        if(!this.isProductionMode()) {
            XLogger.getInstance().transferConsoleHandler("", packageLoggerName, true);
            // Most home grown libraries start with com.bc
            // Only top level projects, that is projects which may not be used
            // as libraries for others should do this
            XLogger.getInstance().setLogLevel("com.bc", logLevel);
            
//            XLogger.getInstance().setLogLevel("com.bc.jpa.paging", logLevel);
//            XLogger.getInstance().setLogLevel("com.bc.jpa.search", logLevel);
        }
        XLogger.getInstance().setLogLevel(packageLoggerName, logLevel);
      } catch (Exception e) {
        XLogger.getInstance().log(Level.WARNING, "Error setting log level to: " + logLevelStr, getClass(), e);
      }
    }
    
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

    boolean startFeedUpdateService = config.getBoolean(AppProperties.START_FEED_UPDATE_SERVICE, true);
    if (startFeedUpdateService) {
      this.feedUpdateService = new FeedUpdateService(){
        @Override
        public FeedUpdateTask newTask() {
          return new IdiscUpdateTask();
        }
      };

      int delay = config.getInt(AppProperties.FEED_CYCLE_DELAY);
      int interval = config.getInt(AppProperties.FEED_CYCLE_INTERVAL);
        
      this.feedUpdateService.start(delay, interval, TimeUnit.MINUTES);
    }
    
    this.initIdiscApp();
    
    this.servletContext.setAttribute("App", this);
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
    logger.log(Level.INFO, "Loading properties configuration. List delimiter: {0}\nDefault file: {1}\nFile: {2}", new Object[] { Character.valueOf(listDelimiter), defaultFileLocation, fileLocation });

    if (fileLocation == null) {
      throw new NullPointerException();
    }
    
    Configuration output;
    if (defaultFileLocation != null)
    {
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
    throws ConfigurationException
  {
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
}
