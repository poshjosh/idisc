package com.idisc.web;

import com.idisc.core.FeedCache;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.Configuration;

public class DefaultFeedCache extends FeedCache {
  
  private final Configuration config;
  
  public DefaultFeedCache(Configuration config) { 
      this.config = Objects.requireNonNull(config);
  }

  @Override
  public boolean isRearrangeOutput() {
    return config.getBoolean("rearrangeOutput", super.isRearrangeOutput());
  }
  
  @Override
  public long getFeedCycleIntervalMillis() {
    return TimeUnit.MINUTES.toMillis(config.getInt("feedCycleInterval"));
  }
  
  @Override
  public int getCacheLimit() {
    return config.getInt("cacheLimit", super.getCacheLimit());
  }
}
