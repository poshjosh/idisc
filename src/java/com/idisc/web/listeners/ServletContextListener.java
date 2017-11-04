package com.idisc.web.listeners;

import com.bc.jpa.context.JpaContext;
import com.bc.util.Util;
import com.idisc.core.IdiscApp;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigurationLoader;
import com.idisc.web.ConfigurationLoaderDevMode;
import com.idisc.web.IdiscAppDevmode;
import com.idisc.web.WebApp;
import com.idisc.web.IdiscAppProductionmode;
import com.idisc.web.servlets.handlers.CloseAutoCloseable;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.apache.commons.configuration.Configuration;

public class ServletContextListener implements javax.servlet.ServletContextListener {

  public ServletContextListener() { }
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {

    final ServletContext sc = sce.getServletContext();

    final Logger logger = Logger.getLogger(ServletContextListener.class.getName());
    try { 

      updateAttributesFromInitParameters(sc);

      final String sval = sc.getInitParameter("productionMode");

      logger.log(Level.INFO, "Production mode: {0}", sval);

      final boolean productionMode = sval == null ? false : Boolean.parseBoolean(sval);

      ConfigurationLoader configLoader = productionMode ? new ConfigurationLoader(sc) : new ConfigurationLoaderDevMode(sc);

      Configuration config = configLoader.load();

      IdiscApp idiscApp = productionMode ? new IdiscAppProductionmode() : new IdiscAppDevmode();

      WebApp webApp = new WebApp(sc, config, idiscApp, productionMode);

      sc.setAttribute(Attributes.APP_CONTEXT, webApp);

    }catch (Exception e) {
      final String msg = "This program has to exit due to the following problem:";
      logger.log(Level.SEVERE, msg, e);
      sc.removeAttribute(Attributes.APP_CONTEXT);
      throw new RuntimeException(e);
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
    
    try{
        final JpaContext jpaContext = ((AppContext)context.getAttribute(Attributes.APP_CONTEXT)).getIdiscApp().getJpaContext();
        jpaContext.getEntityManagerFactory(com.idisc.pu.entities.Feed.class).getCache().evictAll();
        jpaContext.close();
    }catch(Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Exception closing JpaContext", e);
    }
    
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
