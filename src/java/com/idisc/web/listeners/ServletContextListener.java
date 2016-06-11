package com.idisc.web.listeners;

import com.bc.util.XLogger;
import com.idisc.core.FeedUpdateService;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import com.idisc.web.beans.WebappContext;
import com.idisc.web.servlets.ServiceController;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    
    private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);
    
    private class RequestQueueThread extends Thread {
        private final ExecutorService executorService;
        private final Queue<AsyncContext> requestQueue;
        private RequestQueueThread(Queue<AsyncContext> requestQueue, int poolSize) {
            if(requestQueue == null) {
                throw new NullPointerException();
            }
            this.setDaemon(true);
            this.setName("Thread_requestQueue");
            this.setPriority(java.lang.Thread.MAX_PRIORITY-1);
            this.requestQueue = requestQueue;
            this.executorService = WebApp.getInstance().createExecutorService(poolSize);
        }
        @Override
        public void run() {
            while(!shutdownRequested.get()) {
              if(!requestQueue.isEmpty()) {
                final AsyncContext asyncContext = requestQueue.poll();
                executorService.execute(new Runnable(){
                    @Override
                    public void run() {
                        
                        HttpServletRequest request = (HttpServletRequest)asyncContext.getRequest();
                        HttpServletResponse response = (HttpServletResponse)asyncContext.getResponse();
                        try{
                            
                            ServiceController sc = (ServiceController)request.getAttribute(Attributes.SERVICE_CONTROLLER);
                            
                            RequestHandler rh = (RequestHandler)request.getAttribute(Attributes.REQUEST_HANDLER);

                            String paramName = sc.getRequestHandlerParamNames(request)[0];

                            sc.process(rh, request, response, paramName, true);
                            
//                            asyncContext.complete();
                            
                        }catch(RuntimeException re) {
                            final String unexpectedError = "Unexpected Error";
                            try{
                                XLogger.getInstance().log(Level.WARNING, unexpectedError, this.getClass(), re);
                                if(!response.isCommitted()) {
                                    response.sendError(500, unexpectedError);
                                }
                            }catch(Exception e) {
                                XLogger.getInstance().log(Level.WARNING, unexpectedError, this.getClass(), e);
                            }
                        }finally{
                            request.removeAttribute(Attributes.SERVICE_CONTROLLER);
                            request.removeAttribute(Attributes.REQUEST_HANDLER);
                        }
                    }                    
                });             
              }
            }
        }
    }
  
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
      
      Configuration config = webApp.getConfiguration();
      
      final boolean processRequestAsync = config.getBoolean(
              ConfigNames.PROCESS_REQUEST_ASYNC, true);
      
      if(processRequestAsync) {
          
        final int poolSize = config.getInt(ConfigNames.REQUEST_EXECUTOR_SERVICE_THREAD_COUNT, -1);
    
        this.initAsyncRequestQueue(sc, poolSize);
      }
    }catch (ServletException|IOException|ConfigurationException|IllegalAccessException|InterruptedException|InvocationTargetException e) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
      throw new RuntimeException("This program has to exit due to the following problem:", e);
    }
  }
  
  private void initAsyncRequestQueue(ServletContext ctx, int poolSize) {
      
    Queue<AsyncContext> requestQueue = new ConcurrentLinkedQueue<>();
    
    ctx.setAttribute(Attributes.REQUEST_QUEUE, requestQueue);
    
    RequestQueueThread looper = new RequestQueueThread(requestQueue, poolSize);
    
    looper.start();
  }
  
  private void updateAttributesFromInitParameters(ServletContext context) {
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
  public void contextDestroyed(ServletContextEvent sce) {
      
    ServletContext ctx = sce.getServletContext();
    
    this.shutdownRequested.set(true);
    
    RequestQueueThread looper = (RequestQueueThread)ctx.getAttribute(Attributes.REQUEST_QUEUE);
    
    if(looper.executorService != null) {
       
      com.bc.web.core.util.ServletUtil.shutdownAndAwaitTermination(looper.executorService, 500L, TimeUnit.MILLISECONDS);  
    }
    
    ExecutorService globalSvc = WebApp.getInstance().getGlobalExecutorService(false);
    
    if(globalSvc != null) {
        
      com.bc.web.core.util.ServletUtil.shutdownAndAwaitTermination(globalSvc, 500L, TimeUnit.MILLISECONDS);    
    }
    
    WebApp webApp = WebApp.getInstance();
      
    FeedUpdateService feedUpdateService = webApp.getFeedUpdateService();
    
    if(feedUpdateService != null) {
        
      feedUpdateService.shutdownAndAwaitTermination(500L, TimeUnit.MILLISECONDS);
    }
  }
}
