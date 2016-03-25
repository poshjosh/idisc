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














public class FeedCache
  extends Selectfeeds
{
  private static List<Feed> lastFeeds;
  private static long lastTime;
  
  public static boolean isCachedFeedsAvailable()
  {
    return (lastFeeds != null) && (!lastFeeds.isEmpty());
  }
  
  public static boolean isNextUpdateDue() {
    return (lastTime == 0L) || (getTimeElapsedSinceLastUpdate() > getFeedCycleIntervalMillis());
  }
  
  public static long getTimeElapsedSinceLastUpdate() {
    return lastTime <= 0L ? 0L : System.currentTimeMillis() - lastTime;
  }
  
  public static long getFeedCycleIntervalMillis() {
    Configuration config = WebApp.getInstance().getConfiguration();
    return TimeUnit.MINUTES.toMillis(config.getInt("feedCycleInterval"));
  }
  
  public static int getCacheLimit() {
    Configuration config = WebApp.getInstance().getConfiguration();
    return config.getInt("cacheLimit", 500);
  }
  
  public boolean updateCache()
  {
    int cacheLimit = getCacheLimit();
    
    List<Feed> feeds = super.select(null, null, 0, cacheLimit * 2);
    
    if ((feeds == null) || (feeds.isEmpty())) {
      return false;
    }
    
    try
    {
      printFirstDateLastDateAndFeedIds("BEFORE SORT", feeds, Level.FINER);
      
      FeedComparator comparator = new FeedComparator();
      




      comparator.setInvertSort(true);
      
      Collections.sort(feeds, comparator);
      
      Configuration config = WebApp.getInstance().getConfiguration();
      boolean rearrange = config.getBoolean("rearrangeOutput", true);
      
      if (!rearrange)
      {
        lastFeeds = feeds.size() <= cacheLimit ? feeds : feeds.subList(0, cacheLimit);
      }
      else
      {
        lastFeeds = ensureEquality(feeds, cacheLimit);
      }
    } catch (Exception e) {
      XLogger.getInstance().log(Level.WARNING, "Error apply distribution logic to feeds", getClass(), e);
      lastFeeds = feeds.size() <= cacheLimit ? feeds : feeds.subList(0, cacheLimit);
    }
    







    XLogger.getInstance().log(Level.FINE, "Updated cache with {0} feeds", getClass(), lastFeeds == null ? null : Integer.valueOf(lastFeeds.size()));
    printFirstDateLastDateAndFeedIds("AFTER UPDATING CACHE", feeds, Level.FINER);
    
    lastTime = System.currentTimeMillis();
    
    return true;
  }
  
  public synchronized List<Feed> select(HttpServletRequest request)
    throws ValidationException
  {
    if (!isCachedFeedsAvailable())
    {
      updateCache();
    }
    
    return lastFeeds;
  }
  
  public static List<Feed> getLastFeeds() {
    return lastFeeds;
  }
}
