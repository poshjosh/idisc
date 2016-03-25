package com.idisc.web.servlets.handlers;

import com.bc.util.Util;
import com.bc.util.XLogger;
import com.idisc.web.AppProperties;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 */
public class Getmultipleresults extends BaseRequestHandler<Map> {
    
    private int defaultPoolSize;
    
    private int timeoutSeconds;
    
    public Getmultipleresults() {
        try{
            Configuration cfg = WebApp.getInstance().getConfiguration();
            this.defaultPoolSize = cfg.getInt(AppProperties.MULTITASK_REQUEST_DEFAULT_POOL_SIZE);
            this.timeoutSeconds = cfg.getInt(AppProperties.MULTITASK_REQUEST_TIMEOUT_SECONDS);
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Using default values of properties", this.getClass(), e);
            this.defaultPoolSize = 3;
            this.timeoutSeconds = 180;
        }
    }

    @Override
    public boolean isProtected() {
        return false;
    }
    
    @Override
    public Map execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String [] paramNames = this.getRequestHandlerProvider().getRequestHandlerParamNames(request);
        
        final int corePoolSize = paramNames.length >= defaultPoolSize ? defaultPoolSize : paramNames.length;
        
        ExecutorService es = Executors.newFixedThreadPool(corePoolSize);
        
        Map output = new HashMap(paramNames.length, 1.0f);
        
        List<RequestHandlerTask> tasks = this.getHandlerTasks(output, request, response);
        
        try{
            List<Future<Object>> futures = es.invokeAll(tasks, this.timeoutSeconds, TimeUnit.SECONDS);
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Error executing request", this.getClass(), e);
        }finally{
            // At this stage no need to wait so 1 milliseconds
            Util.shutdownAndAwaitTermination(es, 1, TimeUnit.MILLISECONDS);
        }
        
        return output;
    }
    
    public List<RequestHandlerTask> getHandlerTasks(Map outputBuffer,
            HttpServletRequest request, HttpServletResponse response) {
        
        String [] paramNames = this.getRequestHandlerProvider().getRequestHandlerParamNames(request);
        
        List<RequestHandlerTask> output;
        if(paramNames == null || paramNames.length == 0) {
            output = null;
        }else {
            output = new ArrayList<>(paramNames.length);
            for(String paramName:paramNames) {
                RequestHandlerTask callable = this.getHandlerTask(paramName, outputBuffer, request, response);
                output.add(callable);
            }    
        }
            
        return output;    
    }
    
    public RequestHandlerTask getHandlerTask(
            String handlerReqParamName, Map outputBuffer, 
            HttpServletRequest request, HttpServletResponse response) {
        if(handlerReqParamName == null || outputBuffer == null) {
            throw new NullPointerException();
        }    
        RequestHandlerTask handlerTask = new RequestHandlerTask();
        handlerTask.handlerReqParamName = handlerReqParamName;
        handlerTask.provider = this.getRequestHandlerProvider();
        handlerTask.request = request;
        handlerTask.response = response;
        handlerTask.outputBuffer = outputBuffer;
        return handlerTask;
    }
    
    public static final class RequestHandlerTask implements Callable<Object> {
        private String handlerReqParamName;
        private HttpServletRequest request;
        private HttpServletResponse response;
        private RequestHandlerProvider provider;
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
                
                output = handler.execute(request, response);
                
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

    public int getDefaultPoolSize() {
        return defaultPoolSize;
    }

    public void setDefaultPoolSize(int defaultPoolSize) {
        this.defaultPoolSize = defaultPoolSize;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }
}
