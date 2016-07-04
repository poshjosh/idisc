package com.idisc.web;

import com.idisc.core.FeedCache;
import com.idisc.core.FeedUpdateTask;
import org.apache.commons.configuration.Configuration;

public class IdiscUpdateTask extends FeedUpdateTask {

  private final Configuration config;
  
  public IdiscUpdateTask(Configuration config) { 
    this.config = config;
    if(config == null) {
      throw new NullPointerException();
    }
  }
    
  @Override
  public FeedCache createFeedCache() {
    return new DefaultFeedCache(config);
  }
  
  @Override
  public long getIntervalMillis() {
    return 60_000;
  }
}
