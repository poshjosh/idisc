package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.FeedUpdateTask;
import com.idisc.pu.FeedService;
import com.idisc.pu.entities.Feed;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;

public class DefaultFeedUpdateTask extends FeedUpdateTask {

  private final ServletContext context;
  
  private final FeedService feedService;
  
  public DefaultFeedUpdateTask(
          ServletContext context, FeedService feedService) {
    this.context = Objects.requireNonNull(context);
    this.feedService = feedService;
  }
    
  @Override
  public void run() {
     
    try{
        
      super.run();
      
      Configuration config = ((AppContext)this.context.getAttribute(Attributes.APP_CONTEXT)).getConfiguration();
      
      if(config.getBoolean(ConfigNames.CACHE_FEEDS, Boolean.FALSE)) {
        
        final int limit = config.getInt(ConfigNames.CACHE_LIMIT, 200);
        final boolean spreadOutput = config.getBoolean(ConfigNames.REARRANGE_OUTPUT, true);
          
        this.refreshFeedsAttribute(0, limit, spreadOutput);
      }
    }catch(RuntimeException e) {
     
      this.handleRuntimeException(e);
    }
  }
  
  public void refreshFeedsAttribute(int offset, int limit, boolean spreadOutput) {
      
    try {
        
      List<Feed> feeds = feedService.getFeeds(offset, limit, spreadOutput);
      
      if(feeds != null && !feeds.isEmpty()) {
          
        context.setAttribute(Attributes.FEEDS, feeds);
      }
    } catch (RuntimeException e) {
        
      XLogger.getInstance().log(Level.WARNING, "Unexpected exception while updating feed cache", getClass(), e); 
    }
  }
  
  @Override
  public long getIntervalMillis() {
    return 60_000;
  }
}
