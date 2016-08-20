package com.idisc.web.listeners;

import com.bc.util.Util;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigurationLoader;
import com.idisc.web.ConfigurationLoaderDevMode;
import com.idisc.web.WebApp;
import com.idisc.web.IdiscAppImpl;
import com.idisc.web.servlets.handlers.CloseAutoCloseable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    
  public ServletContextListener() { }
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try { 
      
      ServletContext sc = sce.getServletContext();
      
      updateAttributesFromInitParameters(sc);
      
      String sval = sc.getInitParameter("productionMode");
      
XLogger.getInstance().log(Level.INFO, "Production mode: {0}", this.getClass(), sval);

      final boolean productionMode = sval == null ? false : Boolean.parseBoolean(sval);
      
      ConfigurationLoader configLoader = productionMode ? new ConfigurationLoader(sc) : new ConfigurationLoaderDevMode(sc);
      
      Configuration config = configLoader.load();
      
      IdiscApp idiscApp = new IdiscAppImpl(sc, config, productionMode);

      WebApp webApp = new WebApp(sc, config, idiscApp, productionMode);
      
      sc.setAttribute(Attributes.APP_CONTEXT, webApp);
      
    }catch (ConfigurationException | IOException | IllegalAccessException | InterruptedException | InvocationTargetException e) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
      throw new RuntimeException("This program has to exit due to the following problem:", e);
    }
  }
  
  private void updateAttributesFromInitParameters(ServletContext context) {
      
    Enumeration en = context.getInitParameterNames();
    
    while (en.hasMoreElements()) {
      String key = (String)en.nextElement();
      Object val = context.getInitParameter(key);
      Logger.getLogger(getClass().getName()).log(Level.FINE, "Setting context attribute from init param: {0}={1}", new Object[] { key, val });
      
      context.setAttribute(key, val);
    }
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
      
    final ServletContext context = sce.getServletContext();
    
    CloseAutoCloseable cac = new CloseAutoCloseable();
    
    Enumeration<String> en = context.getAttributeNames();
    
    while(en.hasMoreElements()) {
        
        String elemName = en.nextElement();
        
        Object elemValue = context.getAttribute(elemName);
        
        try{
            cac.execute(elemName, (AutoCloseable)elemValue);
        }catch(Exception ignored) {}
        
        try{
            Util.shutdownAndAwaitTermination((ExecutorService)elemValue, 500L, TimeUnit.MILLISECONDS);
        }catch(Exception ignored) {}
    }
  }
}
