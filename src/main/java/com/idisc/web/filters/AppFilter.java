package com.idisc.web.filters;

import com.bc.web.core.filters.BaseFilter;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.idisc.web.ThreadPoolData;
import com.idisc.web.servlets.request.AppVersionCode;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author poshjosh
 */
public class AppFilter extends BaseFilter {

    private transient static final Logger LOG = Logger.getLogger(AppFilter.class.getName());
  
    public AppFilter() { }    

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        final String appVersionCode = request.getParameter(AppVersionCode.PARAM_NAME);
        
        if(appVersionCode != null) { 
          
            AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
          
          final long sessionTimeoutSeconds = TimeUnit.MILLISECONDS.toSeconds(
                  appContext.getAppSessionTimeoutMillis());
          
          LOG.log(Level.FINE, "Setting session timeout to {0} seconds", sessionTimeoutSeconds);
          
          request.getSession().setMaxInactiveInterval((int)sessionTimeoutSeconds);
        }
        
        logMetrics(request);

        return super.doBeforeProcessing(request, response);
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
            LOG.info(builder.toString());
        }
    }
}
