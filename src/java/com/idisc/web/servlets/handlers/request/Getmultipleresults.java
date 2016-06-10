package com.idisc.web.servlets.handlers.request;

import com.bc.util.Util;
import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.configuration.Configuration;
import com.idisc.web.ConfigNames;

public class Getmultipleresults extends AbstractRequestHandler<Map> {
    
  private int defaultPoolSize;
  private int timeoutSeconds;
  
  private final RequestHandlerProvider provider;
  
  public Getmultipleresults(RequestHandlerProvider provider)
  {
    this.provider = provider;
    try
    {
      Configuration cfg = WebApp.getInstance().getConfiguration();
      this.defaultPoolSize = cfg.getInt(ConfigNames.MULTITASK_REQUEST_DEFAULT_POOL_SIZE);
      this.timeoutSeconds = cfg.getInt(ConfigNames.MULTITASK_REQUEST_TIMEOUT_SECONDS);
    } catch (Exception e) {
      XLogger.getInstance().log(Level.WARNING, "Using default values of properties", getClass(), e);
      this.defaultPoolSize = 3;
      this.timeoutSeconds = 180;
    }
  }
  
  @Override
  public boolean isProtected()
  {
    return false;
  }
  
  @Override
  public Map execute(HttpServletRequest request)
    throws ServletException, IOException {
    
    String[] paramNames = provider.getRequestHandlerParamNames(request);
    
XLogger.getInstance().log(Level.INFO, "Parameter names: {0}", this.getClass(), paramNames==null?null:Arrays.toString(paramNames));

    final int corePoolSize = paramNames.length >= this.defaultPoolSize ? this.defaultPoolSize : paramNames.length;
    
    final ExecutorService executorService = Executors.newFixedThreadPool(corePoolSize);
    
    Map output = new HashMap(paramNames.length, 1.0F);
    
    List<Getmultipleresults.RequestHandlerTask> tasks = getHandlerTasks(output, request);
    try {
      for(Getmultipleresults.RequestHandlerTask task:tasks) {
          executorService.submit(task);
      }  
    } catch (Exception e) { 
      XLogger.getInstance().log(Level.WARNING, "Error executing request", getClass(), e);
    } finally {
      Util.shutdownAndAwaitTermination(executorService, this.timeoutSeconds, TimeUnit.SECONDS);
    }
    
XLogger.getInstance().log(Level.INFO, "{0}", this.getClass(), output);

    return output;
  }
  

  public List<Getmultipleresults.RequestHandlerTask> getHandlerTasks(
          Map outputBuffer, HttpServletRequest request)
  {
    String[] paramNames = provider.getRequestHandlerParamNames(request);
    List<Getmultipleresults.RequestHandlerTask> output;
    if ((paramNames == null) || (paramNames.length == 0)) {
      output = null;
    } else {
      output = new ArrayList(paramNames.length);
      for (String paramName : paramNames) {
        Getmultipleresults.RequestHandlerTask callable = 
                getHandlerTask(paramName, outputBuffer, request);
        output.add(callable);
      }
    }
    
    return output;
  }
  

  public Getmultipleresults.RequestHandlerTask getHandlerTask(
          String handlerReqParamName, Map outputBuffer, HttpServletRequest request) {
      
    if ((handlerReqParamName == null) || (outputBuffer == null)) {
      throw new NullPointerException();
    }
    Getmultipleresults.RequestHandlerTask handlerTask = new Getmultipleresults.RequestHandlerTask();
    handlerTask.handlerReqParamName = handlerReqParamName;
    handlerTask.request = request;
    handlerTask.outputBuffer = outputBuffer;
    return handlerTask;
  }

  public int getDefaultPoolSize()
  {
    return this.defaultPoolSize;
  }
  
  public void setDefaultPoolSize(int defaultPoolSize) {
    this.defaultPoolSize = defaultPoolSize;
  }
  
  public int getTimeoutSeconds() {
    return this.timeoutSeconds;
  }
  
  public void setTimeoutSeconds(int timeoutSeconds) {
    this.timeoutSeconds = timeoutSeconds;
  }

    public final class RequestHandlerTask implements Callable<Object> {
        private String handlerReqParamName;
        private HttpServletRequest request;
        private Map outputBuffer;
        private RequestHandlerTask() { }
        public String getRequestParameterName() {
            return handlerReqParamName;
        }
        @Override
        public Object call() {

            Object output;
            try{
                RequestHandler handler = provider.getRequestHandler(handlerReqParamName);

                output = handler.processRequest(request);

                if(output != null) {
                    outputBuffer.put(handlerReqParamName, output);
                }
            }catch(ServletException | IOException | RuntimeException e) {
                output = null;
                XLogger.getInstance().log(Level.WARNING, "Error executing request", this.getClass(), e);
            }

            return output;
        }
    }
}
