package com.idisc.web.servlets;

import com.bc.util.XLogger;
import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.request.RequestExecutorService;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 */
public abstract class AbstractIdiscServlet extends HttpServlet {
    
  private final boolean processRequestAsync;  
  
  private final int threadPoolSize;
  
  private final int queueSize;
    
  public AbstractIdiscServlet() { 
    Configuration config = WebApp.getInstance().getConfiguration();  
    processRequestAsync = WebApp.getInstance().getConfiguration().getBoolean(ConfigNames.PROCESS_REQUEST_ASYNC, true);
    threadPoolSize = config.getInt(ConfigNames.REQUEST_EXECUTOR_SERVICE_THREAD_COUNT, 
            Runtime.getRuntime().availableProcessors());
    queueSize = threadPoolSize * 3;
XLogger.getInstance().log(Level.FINER, "Async processing: {0}, thread pool size: {1}, queue size: {2}", 
        this.getClass(), processRequestAsync, threadPoolSize, queueSize);
  }

  public abstract void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    process(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    process(req, resp);
  }
  
  protected ExecutorService getRequestExecutorService(
          ServletContext context, String attributeName, boolean createIfNeed) {
     ExecutorService es = (ExecutorService)context.getAttribute(attributeName);
     if(es == null && createIfNeed) {
         es = this.createExecutorService(threadPoolSize, threadPoolSize * 3);
         if(es != null) {
             context.setAttribute(attributeName, es);
         }
     }
     return es;
  }
  
  protected ExecutorService createExecutorService(int threadPoolSize, int queueCapacity) {
    ExecutorService es = new RequestExecutorService(threadPoolSize, threadPoolSize,
         0L, TimeUnit.MILLISECONDS, queueCapacity);
    return es;
  }

  public final boolean isProcessRequestAsync() {
    return processRequestAsync;
  }

  public final int getThreadPoolSize() {
    return threadPoolSize;
  }
  
  @Override
  public String getServletInfo(){
    return "The single Controller Servlet of the WebApp, which routes requests to appropriate "+RequestHandler.class.getName()+"(s)";
  }
}
/**
 * 
  protected void processAsynchronous_old(
      final ServiceController sc, final RequestHandler rh,
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
      
    request.setAttribute("serviceController", sc);
    request.setAttribute("requestHandler", rh);

    AsyncContext asyncContext = request.startAsync(request, response);

    AsyncRequestTaskWorker asyncTaskWorker = this.getAsyncRequestTaskWorker(request.getServletContext(), threadPoolSize, true);

    asyncTaskWorker.add(asyncContext);
  }
  
  protected AsyncRequestTaskWorker getAsyncRequestTaskWorker(
          ServletContext ctx, int poolSize, boolean createIfNone) {
      
    AsyncRequestTaskWorker asyncTaskWorker = (AsyncRequestTaskWorker)ctx.getAttribute(Attributes.ASYNCREQUEST_EXECUTOR_SERVICE);      

    if(asyncTaskWorker == null && createIfNone) {
      
      asyncTaskWorker = new AsyncRequestTaskWorker(
              Attributes.ASYNCREQUEST_EXECUTOR_SERVICE, poolSize);
      
      ctx.setAttribute(Attributes.ASYNCREQUEST_EXECUTOR_SERVICE, asyncTaskWorker);
    
      asyncTaskWorker.start();
    }
    
    return asyncTaskWorker;
  }
  
 * 
 */