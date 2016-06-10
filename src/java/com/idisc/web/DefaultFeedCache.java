package com.idisc.web;

import com.idisc.core.FeedCache;
import com.idisc.pu.entities.Feed;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;

public class DefaultFeedCache extends FeedCache
{
  
  public DefaultFeedCache() { }
  
  @Override
  public boolean isRearrangeOutput() {
    Configuration config = WebApp.getInstance().getConfiguration();
    return config.getBoolean("rearrangeOutput", super.isRearrangeOutput());
  }
  
  @Override
  public long getFeedCycleIntervalMillis() {
    Configuration config = WebApp.getInstance().getConfiguration();
    return TimeUnit.MINUTES.toMillis(config.getInt("feedCycleInterval"));
  }
  
  @Override
  public int getCacheLimit() {
    Configuration config = WebApp.getInstance().getConfiguration();
    return config.getInt("cacheLimit", super.getCacheLimit());
  }

  public static List<Feed> getCachedFeeds(HttpServletRequest request) {
    List<Feed> lastFeeds =  getCachedFeeds();
    request.getSession().getServletContext().setAttribute("cachedFeeds", lastFeeds);
    return lastFeeds;
  }
}