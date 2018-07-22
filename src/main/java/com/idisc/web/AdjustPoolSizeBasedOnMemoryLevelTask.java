package com.idisc.web;

import java.util.logging.Logger;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

/**
 * @author Josh
 */
public class AdjustPoolSizeBasedOnMemoryLevelTask implements Runnable {
    private transient static final Logger LOG = Logger.getLogger(AdjustPoolSizeBasedOnMemoryLevelTask.class.getName());
    
    private final AppContext appContext;

    private final ThreadPoolExecutor threadPoolExecutor;
    
    private final int originalPoolSize;
    
    public AdjustPoolSizeBasedOnMemoryLevelTask(
            AppContext appContext, ThreadPoolExecutor threadPoolExecutor) {
        if(appContext == null || threadPoolExecutor == null) {
            throw new NullPointerException();
        }
        this.appContext = appContext;
        this.threadPoolExecutor = threadPoolExecutor;
        this.originalPoolSize = threadPoolExecutor.getCorePoolSize();
    }

    @Override
    public void run() {
        try{
            double d = this.appContext.getMemoryManager().getMemoryLevel().doubleValue() * originalPoolSize;
            if(d < 1) {
                d = 1;
            }else if(d > this.threadPoolExecutor.getMaximumPoolSize()) {
                d = threadPoolExecutor.getMaximumPoolSize();
            }
            final int currentPoolSize = this.threadPoolExecutor.getCorePoolSize();
            final int targetPoolSize = (int)Math.rint(d);
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "Pool size:: original: {0}, current: {1}, target: {2}",
new Object[]{ this.originalPoolSize,  currentPoolSize,  targetPoolSize});
}
            if(targetPoolSize != currentPoolSize) {
                this.threadPoolExecutor.setCorePoolSize(targetPoolSize);
            }
        }catch(RuntimeException e) {
            if(LOG.isLoggable(Level.WARNING)){
                  LOG.log(Level.WARNING, "Thread: "+Thread.currentThread().getName(), e);
            }
        }
    }
}