package com.idisc.web.listeners;

import com.idisc.core.FeedUpdateService;
import com.idisc.core.FeedUpdateTask;
import com.idisc.web.AppProperties;
import com.idisc.web.IdiscUpdateTask;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

public class ServletContextListener
  implements javax.servlet.ServletContextListener
{
  private boolean productionMode;
  private boolean startFeedUpdateService;
  private FeedUpdateService feedUpdateService;
  
  public ServletContextListener()
  {
    this.productionMode = true;
    this.startFeedUpdateService = true;
    if (!this.productionMode) {
      com.idisc.core.FeedUpdateTask.LOG_LEVEL = Level.INFO;
    } else {
      com.idisc.core.FeedUpdateTask.LOG_LEVEL = Level.FINE;
    }
  }
  
  public void contextInitialized(ServletContextEvent sce)
  {
    try
    {
      
      ServletContext sc = sce.getServletContext();
      
      updateAttributesFromInitParameters(sc);
      
      WebApp webApp = WebApp.getInstance();
      
      String propertiesFileName = this.productionMode ? "META-INF/properties/idiscweb.properties" : "META-INF/properties/idiscweb_devmode.properties";
      
      webApp.setPropertiesFileName(propertiesFileName);
      
      webApp.init(sc);
      
      Configuration config = webApp.getConfiguration();
      
      sc.setAttribute(AppProperties.APP_NAME, config.getString(AppProperties.APP_NAME));
      
      if (this.startFeedUpdateService) {
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
    }
    catch (ServletException|IOException|ConfigurationException|IllegalAccessException|InterruptedException|InvocationTargetException e)
    {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
      throw new RuntimeException("This program has to exit due to the following problem:", e);
    }
  }
  
  private void updateAttributesFromInitParameters(ServletContext context)
  {
    Enumeration en = context.getInitParameterNames();
    
    while (en.hasMoreElements())
    {
      String key = (String)en.nextElement();
      Object val = context.getInitParameter(key);
      Logger.getLogger(getClass().getName()).log(Level.FINE, "Setting context attribute from init param: {0}={1}", new Object[] { key, val });
      
      context.setAttribute(key, val);
    }
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent sce)
  {
    if (this.feedUpdateService != null) {
      this.feedUpdateService.shutdownAndAwaitTermination(1500L, TimeUnit.MILLISECONDS);
    }
  }
  
  public boolean isProductionMode() {
    return this.productionMode;
  }
  
  public void setProductionMode(boolean productionMode) {
    this.productionMode = productionMode;
  }
  
  public boolean isStartFeedUpdateService() {
    return this.startFeedUpdateService;
  }
  
  public void setStartFeedUpdateService(boolean startFeedUpdateService) {
    this.startFeedUpdateService = startFeedUpdateService;
  }
}
