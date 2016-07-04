package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.idisc.pu.SearchHandlerFactory;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Site;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 */
public interface AppContext {

    String getAppName();
  
    Map getAppProperties();
    
    long getAppSessionTimeoutMillis();
    
    String getAppUrl();
  
    AuthSvcSession getAuthSvcSession();
  
    List<Feed> getCachedFeeds();
    
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

    long getMemoryAtStartup();

    /**
     * @return {@link java.lang.Runtime#getRuntime()#freeMemory()} divided by {@link #getMemoryAtStartup()}
     */
    BigDecimal getMemoryLevel();

    /**
     * @return The SearchHandlerFactory or null if none had earlier been created
     * @see #getSearchHandlerFactory(boolean) 
     */
    SearchHandlerFactory getSearchHandlerFactory();
    
    SearchHandlerFactory getSearchHandlerFactory(boolean createIfNone);
    
    List<Site> getSites();
    
    boolean isAsyncProcessingEnabled();
    
    boolean isDebug();
    
    boolean isProductionMode();
}
