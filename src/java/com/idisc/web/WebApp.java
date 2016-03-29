package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.SessionLoader;
import com.bc.util.XLogger;
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
  
  private String propertiesFileName;
  
  private String defaultPropertiesFileName;
  
  private Configuration config;
  
  private AuthSvcSession authSvcSession;
  
  private ServletContext servletContext;
  
  private static WebApp instance;
  
  protected WebApp()
  {
    this.defaultPropertiesFileName = "META-INF/properties/idiscwebdefaults.properties";
    this.propertiesFileName = "META-INF/properties/idiscweb.properties";
  }
  
  public static WebApp getInstance() {
    if (instance == null) {
      instance = new WebApp();
    }
    return instance;
  }
  
  private IdiscApp initIdiscApp()
    throws ConfigurationException, IOException, IllegalAccessException, InterruptedException, InvocationTargetException
  {
    IdiscApp idiscApp = IdiscApp.getInstance();
    
    String scrapperPropsFile = this.config.getString("scrapperPropertiesFile");
    XLogger.getInstance().log(Level.FINE, "Scrapper properties file: {0}", getClass(), scrapperPropsFile);
    if (scrapperPropsFile != null) {
      idiscApp.setScrapperPropertiesFilename(scrapperPropsFile);
    }
    
    String persistenceFile = this.config.getString("persistenceFile");
    XLogger.getInstance().log(Level.FINE, "Persistence file: {0}", getClass(), persistenceFile);
    if (persistenceFile != null) {
      idiscApp.setPersistenceFilename(persistenceFile);
    }
    
    String corePropertiesFile = this.config.getString("idisccorePropertiesFile");
    if (corePropertiesFile != null) {
      URL fileLoc = this.servletContext.getResource(corePropertiesFile);
      idiscApp.init(fileLoc);
    } else {
      idiscApp.init();
    }
    
    IdiscApp.setInstance(idiscApp);
    
    return idiscApp;
  }
  

  public void init(ServletContext context)
    throws ServletException, IOException, ConfigurationException, IllegalAccessException, InterruptedException, InvocationTargetException
  {
    URL defaultFileLoc = context.getResource(this.defaultPropertiesFileName);
    
    URL fileLoc = context.getResource(this.propertiesFileName);
    
    init(context, defaultFileLoc, fileLoc);
    
    initIdiscApp();
  }
  

  public void init(ServletContext context, URL defaultFileLocation, URL fileLocation)
    throws ServletException, IOException, ConfigurationException
  {
    XLogger.getInstance().log(Level.INFO, "Initializing: {0}", getClass(), getClass().getName());
    this.servletContext = context;
    

    this.config = loadConfig(defaultFileLocation, fileLocation, ',');
    
    String logLevelStr = this.config.getString("logLevel");
    if(logLevelStr != null) {
      try {
        Level logLevel = Level.parse(logLevelStr);
        XLogger.getInstance().setLogLevel(com.idisc.web.WebApp.class.getPackage().getName(), logLevel);
        XLogger.getInstance().setLogLevelForConsoleHandlers(logLevel);
      } catch (Exception e) {
        XLogger.getInstance().log(Level.WARNING, "Error setting log level to: " + logLevelStr, getClass(), e);
      }
    }
    
    String authsvc_url = this.config.getString("authsvc.url");
    if (getInstance().isNetbeansDevelopmentMode()) {
      authsvc_url = "http://localhost:8080/authsvc";
    }

    String app_name = getInstance().getAppName();
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
  }
  
  public boolean isNetbeansDevelopmentMode() {
    if (getServletContext() == null) {
      return true;
    }
    String s = getServletContext().getRealPath("META-INF");
    return (s.contains("/build/")) || (s.contains("\\build\\"));
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
    return getConfiguration().getString("appName");
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
  
  public void setPropertiesFileName(String propertiesFileName) {
    this.propertiesFileName = propertiesFileName;
  }
  
  public String getDefaultPropertiesFileName() {
    return this.defaultPropertiesFileName;
  }
  
  public void setDefaultPropertiesFileName(String defaultPropertiesFileName) {
    this.defaultPropertiesFileName = defaultPropertiesFileName;
  }
}
