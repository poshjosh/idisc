package com.idisc.web;

import com.authsvc.client.AppAuthenticationSession;
import com.idisc.core.IdiscApp;
import com.idisc.shared.SharedContext;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 */
public interface AppContext {
    
    IdiscApp getIdiscApp();
    
    SharedContext getSharedContext();

    String getAppName();
  
    Map getAppProperties();
    
    long getAppSessionTimeoutMillis();
    
    String getAppUrl();
  
    AppAuthenticationSession getAuthSvcSession();
  
    Configuration getConfiguration();

    /**
     * @return The ScheduledExecutorService or null if none had earlier been created
     * @see #getGlobalScheduledExecutorService(boolean) 
     */
    ScheduledExecutorService getGlobalScheduledExecutorService();
    
    ScheduledExecutorService getGlobalScheduledExecutorService(boolean createIfNone);

    /**
     * @return The Executor service or null if none had earlier been created
     * @see #getGlobalExecutorService(boolean) 
     */
    ExecutorService getGlobalExecutorService();
    
    /**
     * A poolSize < 1 will result in a {@link com.bc.util.concurrent.DirectExecutorService}
     * @param createIfNone
     * @return
     */
    ExecutorService getGlobalExecutorService(boolean createIfNone);

    ThreadPoolData getGlobalExecutorServiceThreadPoolData();
    
    MemoryManager getMemoryManager();
    
    boolean isProductionMode();
}
