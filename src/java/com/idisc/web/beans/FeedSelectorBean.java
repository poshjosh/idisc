package com.idisc.web.beans;

import com.bc.util.XLogger;
import com.idisc.core.comparator.BaseFeedComparator;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.handlers.request.SessionUserHandlerImpl;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class FeedSelectorBean extends com.idisc.core.FeedSelector implements Serializable {
    
    private boolean async;
    
    private int maxAgeDays;
    
    private int maxSpread;
    
    private int batchSize;
    
    private int maxOutputSize;

    private Installation installation;
    
    private final long updateInterval;
    
    private long lastUpdateTime;
    
    private List<Feed> topfeeds;
    
    private transient Runnable currentTask;
    
    public FeedSelectorBean() { 
XLogger.getInstance().log(Level.FINE, "<init>", this.getClass());  
        this.async = true;
        this.updateInterval = TimeUnit.MINUTES.toMillis(5);
        this.maxAgeDays = 3;
        this.maxSpread = 2000;
        this.batchSize = this.maxSpread;
        this.maxOutputSize = 20;
    }
    
    public boolean isTaskSubmitted() {
        return currentTask != null;
    }
    
    public boolean isNextUpdateDue() {
        return (System.currentTimeMillis() - lastUpdateTime) > this.updateInterval;
    }

    public List<Feed> getList() {
        return topfeeds;
    }
    
    protected Comparator<Feed> createComparator() {
        return new BaseFeedComparator(true);
    }
    
    public void setRequest(HttpServletRequest request) {

        if(this.installation == null) {
            
            this.installation = new SessionUserHandlerImpl().getInstallation(request, false);
        }
        
        if((topfeeds == null && !this.isTaskSubmitted()) || this.isNextUpdateDue()) {
            
XLogger.getInstance().log(Level.FINER, "Refreshing Topfeeds", this.getClass());        

            FeedSelectionTask task = new FeedSelectionTask();
            
            if(request.getSession() == null) {
                return;
            }
            
            task.sessionId = request.getSession().getId();
            
            currentTask = task;
            
            if(async) {
                AppContext appCtx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
                ExecutorService es = appCtx.getGlobalExecutorService(false);
                if(es != null) {
                    es.submit(task);
                }
            }else{
                task.run();
            }
        }
    }
    
    private final class FeedSelectionTask implements Runnable {
        String sessionId;
        @Override
        public void run() {
            try{
                List<Feed> selected = getList(maxAgeDays, maxSpread, batchSize);
                topfeeds = sort(selected, createComparator(), maxOutputSize);
XLogger.getInstance().log(Level.FINER, "Session ID: {0}, Topfeeds: {1}", 
this.getClass(), sessionId, topfeeds == null ? null : topfeeds.size());
                lastUpdateTime = System.currentTimeMillis();
                currentTask = null;
            }catch(RuntimeException e) {
                XLogger.getInstance().log(Level.WARNING, "Thread: "+Thread.currentThread().getName(), this.getClass(), e);
            }
        }
    }

    public Installation getInstallation() {
        return installation;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public int getMaxAgeDays() {
        return maxAgeDays;
    }

    public void setMaxAgeDays(int maxAgeDays) {
        this.maxAgeDays = maxAgeDays;
    }

    public int getMaxSpread() {
        return maxSpread;
    }

    public void setMaxSpread(int maxSpread) {
        this.maxSpread = maxSpread;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getMaxOutputSize() {
        return maxOutputSize;
    }

    public void setMaxOutputSize(int maxOutputSize) {
        this.maxOutputSize = maxOutputSize;
    }
}
