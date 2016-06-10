package com.idisc.web.listeners;

import com.bc.util.XLogger;
import com.idisc.core.FeedUpdateService;
import com.idisc.web.WebApp;
import com.idisc.web.beans.WebappContext;
import com.idisc.web.servlets.ServiceController;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.ConfigurationException;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    
    private static class RequestQueueThread extends Thread {
        private final Queue<AsyncContext> requestQueue;
        private RequestQueueThread(Queue<AsyncContext> requestQueue) {
            if(requestQueue == null) {
                throw new NullPointerException();
            }
            this.requestQueue = requestQueue;
            this.setDaemon(true);
            this.setName("Thread_requestQueue");
            this.setPriority(java.lang.Thread.MAX_PRIORITY-1);
        }
        @Override
        public void run() {
            while(true) {
              if(!requestQueue.isEmpty()) {
                final AsyncContext asyncContext = requestQueue.poll();
                final Executor executor = WebApp.getInstance().getRequestExecutorService(true);
                executor.execute(new Runnable(){
                    @Override
                    public void run() {
                        HttpServletRequest request = (HttpServletRequest)asyncContext.getRequest();
                        HttpServletResponse response = (HttpServletResponse)asyncContext.getResponse();
                        try{
                            
                            ServiceController sc = (ServiceController)request.getAttribute("serviceController");
                            
                            RequestHandler rh = (RequestHandler)request.getAttribute("requestHandler");

                            String paramName = sc.getRequestHandlerParamNames(request)[0];

                            sc.process(rh, request, response, paramName, true);
                            
                            asyncContext.complete();
                            
                        }catch(RuntimeException re) {
                            final String unexpectedError = "Unexpected Error";
                            try{
                                XLogger.getInstance().log(Level.WARNING, unexpectedError, this.getClass(), re);
                                response.sendError(500, unexpectedError);
                            }catch(Exception e) {
                                XLogger.getInstance().log(Level.WARNING, unexpectedError, this.getClass(), e);
                            }
                        }finally{
                            request.removeAttribute("serviceController");
                            request.removeAttribute("requestHandler");
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
      
      sc.setAttribute("WebappContext", new WebappContext());
      
      this.initRequestQueue(sc);
      
    }catch (ServletException|IOException|ConfigurationException|IllegalAccessException|InterruptedException|InvocationTargetException e) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
      throw new RuntimeException("This program has to exit due to the following problem:", e);
    }
  }
  
  private void initRequestQueue(ServletContext ctx) {
      
    Queue<AsyncContext> requestQueue = new ConcurrentLinkedQueue<>();
    
    ctx.setAttribute("requestQueue", requestQueue);
    
    RequestQueueThread looper = new RequestQueueThread(requestQueue);
    
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
  public void contextDestroyed(ServletContextEvent sce)
  {
      
    WebApp webApp = WebApp.getInstance();
      
    ExecutorService svc = webApp.getRequestExecutorService(false);
    
    if(svc != null) {
       
      com.bc.web.core.util.ServletUtil.shutdownAndAwaitTermination(svc, 500L, TimeUnit.MILLISECONDS);  
    }
    
    FeedUpdateService feedUpdateService = webApp.getFeedUpdateService();
    
    if(feedUpdateService != null) {
        
      feedUpdateService.shutdownAndAwaitTermination(500L, TimeUnit.MILLISECONDS);
    }
  }
}
