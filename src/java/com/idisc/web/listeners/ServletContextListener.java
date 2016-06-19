package com.idisc.web.listeners;

import com.bc.util.XLogger;
import com.idisc.core.FeedUpdateService;
import com.idisc.core.jpa.SearchHandlerFactory;
import com.idisc.web.Attributes;
import com.idisc.web.WebApp;
import com.idisc.web.beans.WebappContext;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
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
      
      WebApp webApp = WebApp.getInstance(productionMode);
      
      webApp.init(sc);
      
      sc.setAttribute(Attributes.WEBAPP_CONTEXT, new WebappContext());
      
    }catch (ServletException|IOException|ConfigurationException|IllegalAccessException|InterruptedException|InvocationTargetException e) {
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
    
    final ExecutorService svc = (ExecutorService)context.getAttribute(Attributes.ASYNCREQUEST_EXECUTOR_SERVICE);
    
    if(svc != null) {
        
        com.bc.web.core.util.ServletUtil.shutdownAndAwaitTermination(svc, 500L, TimeUnit.MILLISECONDS);    
    }
      
//    if(this.asyncTaskWorker != null) {
        
//        this.asyncTaskWorker.shutdown(500L, TimeUnit.MILLISECONDS);
//    }
      
    ExecutorService globalSvc = WebApp.getInstance().getGlobalExecutorService(false);
    
    if(globalSvc != null) {
        
      com.bc.web.core.util.ServletUtil.shutdownAndAwaitTermination(globalSvc, 500L, TimeUnit.MILLISECONDS);    
    }
    
    WebApp webApp = WebApp.getInstance();
      
    FeedUpdateService feedUpdateService = webApp.getFeedUpdateService();
    
    if(feedUpdateService != null) {
        
      feedUpdateService.shutdownAndAwaitTermination(500L, TimeUnit.MILLISECONDS);
    }
    
    SearchHandlerFactory shf = WebApp.getInstance().getSearchHandlerFactory(false);
    
    if(shf != null) {
        
        shf.removeAll(true);
    }
  }
}
