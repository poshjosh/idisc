package com.idisc.web.beans;

import com.bc.util.XLogger;
import com.idisc.core.FeedHitcountComparator;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.WebApp;
import com.idisc.web.servlets.handlers.request.SessionUserHandlerImpl;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class FeedSelectorBean extends com.idisc.core.FeedSelector implements Serializable {
    
    private int maxAgeDays;
    
    private int maxSpread;
    
    private int batchSize;
    
    private int maxOutputSize;

    private Installation installation;
    
    private final long updateInterval;
    
    private long lastUpdateTime;
    
    private List<Feed> topfeeds;
    
    private transient Future<List<Feed>> future;
    
    public FeedSelectorBean() { 
XLogger.getInstance().log(Level.FINE, "<init>", this.getClass());                
        this.updateInterval = TimeUnit.MINUTES.toMillis(5);
        this.maxAgeDays = 3;
        this.maxSpread = 2000;
        this.batchSize = this.maxSpread;
        this.maxOutputSize = 20;
    }
    
    public boolean isTaskSubmitted() {
        return future != null;
    }
    
    public boolean isNextUpdateDue() {
        return (System.currentTimeMillis() - lastUpdateTime) > this.updateInterval;
    }

    public List<Feed> getList() {
        return topfeeds;
    }
    
    protected Comparator<Feed> createComparator() {
        return new FeedHitcountComparator(true);
    }
    
    public void setRequest(HttpServletRequest request) {

        if(this.installation == null) {
            
            this.installation = new SessionUserHandlerImpl().getInstallation(request, false);
        }
        
        if((topfeeds == null && !this.isTaskSubmitted()) || this.isNextUpdateDue()) {
            
XLogger.getInstance().log(Level.FINER, "Refreshing Topfeeds", this.getClass());        

            FeedSelectionTask task = new FeedSelectionTask();
            
            task.sessionId = request.getSession().getId();
            
            future = WebApp.getInstance().getRequestExecutorService(true).submit(task);
        }
    }
    
    private final class FeedSelectionTask implements Callable<List<Feed>> {
        String sessionId;
        @Override
        public List<Feed> call() {
            List<Feed> selected = getList(maxAgeDays, maxSpread, batchSize);
            topfeeds = sort(selected, createComparator(), maxOutputSize);
XLogger.getInstance().log(Level.FINER, "Session ID: {1}, Topfeeds: {0}", 
this.getClass(), sessionId, topfeeds == null ? null : topfeeds.size());
            lastUpdateTime = System.currentTimeMillis();
            future = null;
            return topfeeds;
        }
    }

    public Installation getInstallation() {
        return installation;
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
