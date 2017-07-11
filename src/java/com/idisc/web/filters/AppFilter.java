package com.idisc.web.filters;

import com.bc.util.XLogger;
import com.bc.web.core.filters.BaseFilter;
import com.bc.web.core.util.ServletUtil;
import com.bc.web.core.util.FileExtensions;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.idisc.web.ThreadPoolData;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author poshjosh
 */
public class AppFilter extends BaseFilter {
  
    public AppFilter() { }    

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        final long sessionTimeoutSeconds;

        if(this.isRequestFromOrToAWebPage(request)) { 

          sessionTimeoutSeconds = TimeUnit.MINUTES.toSeconds(30);

          request.setAttribute(Attributes.REQUEST_FROM_OR_TO_WEBPAGE, Boolean.TRUE);

        }else{

          AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
          sessionTimeoutSeconds = TimeUnit.MILLISECONDS.toSeconds(
                  appContext.getAppSessionTimeoutMillis());
        }

        request.getSession().setMaxInactiveInterval((int)sessionTimeoutSeconds);
        
        logMetrics(request);

        return super.doBeforeProcessing(request, response);
    }

  public boolean isRequestFromOrToAWebPage(HttpServletRequest request) {
    String referer = request.getHeader("referer");
    String uri = request.getRequestURI();
    boolean output = this.isRequestFromOrToAWebPage(referer, uri);
XLogger.getInstance().log(Level.FINER, "Request is from or to web page: {0}, Referer: {1}, Request URI: {2}", 
        this.getClass(), output, referer, uri);
    return output;
  }  
    
  public boolean isRequestFromOrToAWebPage(String referer, String uri) {
    referer = this.getExtensionOrEmptyString(referer);
    uri = this.getExtensionOrEmptyString(uri);
    boolean output = (isJspPage(referer) || isJspPage(uri) || isStaticPage(referer) || isStaticPage(uri));
    return output;
  }

  public boolean isStaticResource(String str) {
      boolean output = str != null && FileExtensions.isStaticResource(str);
//System.out.println(this.getClass().getName()+". Static resource: "+output+", value: "+str);      
      return output;
  }
  
  public boolean isStaticPage(String str) {
      boolean output = str != null && FileExtensions.isStaticPage(str);
//System.out.println(this.getClass().getName()+". Static page: "+output+", value: "+str);      
      return output;
  }
  
  public boolean isJspPage(String str) {
      boolean output = str != null && FileExtensions.isJspPage(str);
//System.out.println(this.getClass().getName()+". Jsp page: "+output+", value: "+str);      
      return output;
  }
  
  private String getExtensionOrEmptyString(String s) {
      String output;
      if(s == null || s.isEmpty()) {
          output = null;
      }else {
          output = ServletUtil.getExtension(s);
      }
      return output;
  }
  
  
   private static final Lock lock = new ReentrantLock();
    private void logMetrics(HttpServletRequest request) {
      try{
          lock.lock();
          $lm(request);
      }catch(Exception ignored) {} 
      finally{
          lock.unlock();
      }
    }
  
    private static final int BATCH_SIZE = 100;
    private static int $posInBatch;
    private static int $numBatches;
    private void $lm(HttpServletRequest request) {
        if(++$posInBatch >= BATCH_SIZE) {
            ++$numBatches;
            $posInBatch = 0;
            AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
            final int total = $numBatches * BATCH_SIZE + $posInBatch;
            StringBuilder builder = new StringBuilder();
            builder.append("\n===============================     METRICS     ==============================");
            builder.append("\nAfter ").append(total).append(
            " requests, memory level: ").append(appContext.getMemoryManager().getMemoryLevel());
            builder.append(", async enabled: ").append(appContext.isAsyncProcessingEnabled());
            ThreadPoolData tpd = appContext.getGlobalExecutorServiceThreadPoolData();
            if(tpd != null) {
                builder.append("\n----------------------------- Thread Pool Data ----------------------------");
                builder.append("\nTasks:: ").append(tpd.getTaskCount());
                builder.append(", completed: ").append(tpd.getCompletedTaskCount());
                builder.append(", active: ").append(tpd.getActiveCount());
                builder.append(", queued: ").append(tpd.getQueueSize());
//          builder.append(", ").append(tpd.getKeepAliveTime(TimeUnit.SECONDS));
                builder.append("\nPool size:: max: ").append(tpd.getMaximumPoolSize());
                builder.append(", largest: ").append(tpd.getLargestPoolSize());
                builder.append(", core: ").append(tpd.getCorePoolSize());
                builder.append(", current: ").append(tpd.getPoolSize());
            }
            builder.append("\n==============================================================================");
            XLogger.getInstance().log(Level.INFO, "{0}", this.getClass(), builder);
        }
    }
}
