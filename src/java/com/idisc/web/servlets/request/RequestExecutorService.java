package com.idisc.web.servlets.request;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Josh
 */
public class RequestExecutorService extends ThreadPoolExecutor {

    public RequestExecutorService(
            int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity) {
        
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, 
                new LinkedBlockingQueue<Runnable>(queueCapacity), 
                new RequestThreadFactory(), 
                new RequestRejectionExceptionHandler());
    }
}
