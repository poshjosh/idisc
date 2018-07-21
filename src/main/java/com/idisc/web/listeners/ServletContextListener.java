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
import com.looseboxes.cometd.chat.CometdChat;
import com.looseboxes.cometd.chat.PrivateMessageConsumerLocalDiscStore;
import com.looseboxes.cometd.chat.functions.GetChatFromMessage;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.apache.commons.configuration.Configuration;

public class ServletContextListener implements javax.servlet.ServletContextListener {

  private transient static final Logger LOG = Logger.getLogger(ServletContextListener.class.getName());

  public ServletContextListener() { }
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {

    final ServletContext sc = sce.getServletContext();

    try { 

      updateAttributesFromInitParameters(sc);

      final String sval = sc.getInitParameter("productionMode");

      LOG.log(Level.INFO, "Production mode: {0}", sval);

      final boolean productionMode = sval == null ? false : Boolean.parseBoolean(sval);

      ConfigurationLoader configLoader = productionMode ? new ConfigurationLoader(sc) : new ConfigurationLoaderDevMode(sc);

      Configuration config = configLoader.load();

      IdiscApp idiscApp = productionMode ? new IdiscAppProductionmode() : new IdiscAppDevmode();

      WebApp webApp = new WebApp(sc, config, idiscApp, productionMode);

      sc.setAttribute(Attributes.APP_CONTEXT, webApp);
      
      webApp.startFeedLoadService();

      final CometdChat cometdChat = new CometdChat(
          sc, new GetChatFromMessage(), new PrivateMessageConsumerLocalDiscStore(sc) 
      );

    }catch (Exception e) {
      final String msg = "This program has to exit due to the following problem:";
      LOG.log(Level.SEVERE, msg, e);
      throw new RuntimeException(e);
    }
  }
  
  private void updateAttributesFromInitParameters(ServletContext context) {
      
    Enumeration en = context.getInitParameterNames();
    
    while (en.hasMoreElements()) {
      String key = (String)en.nextElement();
      Object val = context.getInitParameter(key);
      LOG.log(Level.FINE, "Setting context attribute from init param: {0}={1}", new Object[] { key, val });
      context.setAttribute(key, val);
    }
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
      
    final ServletContext context = sce.getServletContext();
    
    try{
      final AppContext appContext = (AppContext)context.getAttribute(Attributes.APP_CONTEXT);
      if(appContext != null) {
        final IdiscApp app = appContext.getIdiscApp();
        if(app != null) {
          final JpaContext jpaContext = app.getJpaContext();
          if(jpaContext != null) {
            jpaContext.getEntityManagerFactory(com.idisc.pu.entities.Feed.class).getCache().evictAll();
            jpaContext.close();
          }
        }
      }
    }catch(Exception e) {
      LOG.log(Level.WARNING, "Exception closing JpaContext", e);
    }
    
    CloseAutoCloseable cac = new CloseAutoCloseable();
    
    Enumeration<String> en = context.getAttributeNames();
    
    while(en.hasMoreElements()) {
        
      String elemName = en.nextElement();
        
      Object elemValue = context.getAttribute(elemName);
      
      if(elemValue instanceof AutoCloseable) {
        try{
          cac.execute(elemName, (AutoCloseable)elemValue);
        }catch(Exception e) {
          LOG.warning(e.toString());
        }
      }else if(elemValue instanceof ExecutorService){
        try{
          Util.shutdownAndAwaitTermination((ExecutorService)elemValue, 500L, TimeUnit.MILLISECONDS);
        }catch(Exception e) {
          LOG.log(Level.WARNING, null, e);
        }
      }
    }
  }
}
