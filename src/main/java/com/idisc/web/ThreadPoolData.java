package com.idisc.web;

import java.util.concurrent.TimeUnit;

/**
 * @author Josh
 */
public interface ThreadPoolData {
    
    int getQueueSize();
    
    int getCorePoolSize();

    int getMaximumPoolSize();

    long getKeepAliveTime(TimeUnit unit);

    int getPoolSize();

    int getActiveCount();

    int getLargestPoolSize();

    long getTaskCount();

    long getCompletedTaskCount();
}
