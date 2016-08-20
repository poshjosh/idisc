package com.idisc.web.beans;

import com.bc.util.XLogger;
import com.idisc.core.comparator.BaseFeedComparator;
import com.idisc.pu.SelectByDate;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feed_;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.dao.BuilderForSelect;

/**
 * @author Josh
 */
public class FeedSelectorBean implements Serializable {
    
    private boolean async;
    
    private int maxAgeDays;
    
    private int maxSpread;
    
    private int batchSize;
    
    private int maxOutputSize;

    private final long updateInterval;
    
    private long lastUpdateTime;
    
    private List<Feed> topfeeds;
    
    private boolean taskSubmitted;
    
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
        return taskSubmitted;
    }
    
    public boolean isNextUpdateDue() {
        return (System.currentTimeMillis() - lastUpdateTime) > this.updateInterval;
    }

    public List<Feed> getResultList() {
        return topfeeds;
    }
    
    protected Comparator<Feed> createComparator() {
        return new BaseFeedComparator(true);
    }
    
    public void setRequest(HttpServletRequest request) {

        if((topfeeds == null && !this.isTaskSubmitted()) || this.isNextUpdateDue()) {
            
XLogger.getInstance().log(Level.FINER, "Refreshing Topfeeds", this.getClass());        

            AppContext appCtx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
            
            SelectByDate feedDao = new SelectByDate(appCtx.getIdiscApp().getJpaContext(), Feed.class, Integer.class);
            
            FeedSelectionTask task = new FeedSelectionTask(feedDao);
            
            if(request.getSession() == null) {
                return;
            }
            
            task.sessionId = request.getSession().getId();
            
            taskSubmitted = true;
            
            if(async) {
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
        SelectByDate<Feed, Integer> feedDao;
        private FeedSelectionTask(SelectByDate<Feed, Integer> feedDao) {
          this.feedDao = feedDao;  
        }
        @Override
        public void run() {
            try{
                
                List<Feed> selected = feedDao.getResultList(Feed_.feeddate.getName(), BuilderForSelect.GT, maxAgeDays, 
                        TimeUnit.DAYS, maxSpread, batchSize);
                
                if(selected != null && !selected.isEmpty()) {
                    topfeeds = new ArrayList(feedDao.sort(selected, createComparator(), maxOutputSize));
                }
XLogger.getInstance().log(Level.FINER, "Session ID: {0}, Topfeeds: {1}", 
this.getClass(), sessionId, topfeeds == null ? null : topfeeds.size());

                lastUpdateTime = System.currentTimeMillis();
                
                taskSubmitted = false;
                
            }catch(RuntimeException e) {
                XLogger.getInstance().log(Level.WARNING, "Thread: "+Thread.currentThread().getName(), this.getClass(), e);
            }
        }
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
