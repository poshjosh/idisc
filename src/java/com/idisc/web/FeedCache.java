package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Feed;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.Selectfeeds;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;


/**
 * @(#)FeedCache.java   22-Apr-2015 22:51:57
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class FeedCache extends Selectfeeds {
    
    private static List<Feed> lastFeeds;
    
//    private static List<Map> lastOutput;
    
    private static long lastTime;
    
    public static boolean isCachedFeedsAvailable() {
        return lastFeeds != null && !lastFeeds.isEmpty();
    }
    
    public static boolean isNextUpdateDue() {
        return lastTime == 0 || getTimeElapsedSinceLastUpdate() > getFeedCycleIntervalMillis();
    }
    
    public static long getTimeElapsedSinceLastUpdate() {
        return lastTime <= 0 ? 0 : System.currentTimeMillis() - lastTime;
    }
    
    public static long getFeedCycleIntervalMillis() {
        Configuration config = WebApp.getInstance().getConfiguration();
        return TimeUnit.MINUTES.toMillis(config.getInt(AppProperties.FEED_CYCLE_INTERVAL));
    }
    
    public static int getCacheLimit() {
        Configuration config = WebApp.getInstance().getConfiguration();
        return config.getInt(AppProperties.CACHE_LIMIT, 500);
    }
    
    public boolean updateCache() {
        
        int cacheLimit = FeedCache.getCacheLimit();
        
        List<Feed> feeds = super.select(null, null, 0, cacheLimit * 2);

        if(feeds == null || feeds.isEmpty()) {
            return false;
        }
        
        try{

this.printFirstDateLastDateAndFeedIds("BEFORE SORT", feeds, Level.FINER);

            FeedComparator comparator = new FeedComparator();

//    For input: 0, 1, 2, 3, 4       
//   Output was: 4, 3, 2, 1, 0
//   Hence we use invertSort=true to ensure DESCENING ORDER of input is maintained
//        
            comparator.setInvertSort(true);

            Collections.sort(feeds, comparator);

            Configuration config = WebApp.getInstance().getConfiguration();
            boolean rearrange = config.getBoolean(AppProperties.REARRANGE_OUTPUT, true);

            if(!rearrange) {

                lastFeeds = feeds.size() <= cacheLimit ? feeds : feeds.subList(0, cacheLimit);
                
            }else{
            
                lastFeeds = this.ensureEquality(feeds, cacheLimit);
            }
        }catch(Exception e) {
XLogger.getInstance().log(Level.WARNING, "Error apply distribution logic to feeds", this.getClass(), e);
            lastFeeds = feeds.size() <= cacheLimit ? feeds : feeds.subList(0, cacheLimit);
        }
        
//        List<Map> output = this.toMap(lastFeeds);
        
//        if(output != null && !output.isEmpty()) {
//            lastOutput.clear();
//            lastOutput = output;
//        }
        
XLogger.getInstance().log(Level.FINE, "Updated cache with {0} feeds", this.getClass(), lastFeeds==null?null:lastFeeds.size());
this.printFirstDateLastDateAndFeedIds("AFTER UPDATING CACHE", feeds, Level.FINER);
        
        lastTime = System.currentTimeMillis();
        
        return true;
    }
    
    @Override
    public synchronized List<Feed> select(HttpServletRequest request) throws ValidationException {
        
        if(!isCachedFeedsAvailable()) {
            
            this.updateCache();
        }

        return lastFeeds;
    }
    
    public static List<Feed> getLastFeeds() {
        return lastFeeds;
    }
}