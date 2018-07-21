package com.idisc.web;

import com.bc.util.Util;
import java.util.logging.Logger;
import com.idisc.core.FeedUpdateTask;
import com.idisc.pu.FeedDao;
import com.idisc.pu.entities.Feed;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;

public class DefaultFeedUpdateTask extends FeedUpdateTask {
    private transient static final Logger LOG = Logger.getLogger(DefaultFeedUpdateTask.class.getName());

  private final ServletContext context;
  
  private final FeedDao feedService;
  
  public DefaultFeedUpdateTask(
          ServletContext context, FeedDao feedService) {
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
      
    final long tb4 = System.currentTimeMillis();
    final long mb4 = Util.availableMemory();
    try {
        
      List<Feed> feeds = feedService.getFeeds(offset, limit, spreadOutput);
      
      if(feeds != null && !feeds.isEmpty()) {
          
        context.setAttribute(Attributes.FEEDS, feeds);
      }
    } catch (RuntimeException e) {
        
      if(LOG.isLoggable(Level.WARNING)){
         LOG.log(Level.WARNING, "Unexpected exception while updating feed cache", e);
      } 
    }
    
    LOG.fine(() -> "DONE REFRESHING FEEDS. Consumed time: " + 
            (System.currentTimeMillis() - tb4) + ", memory: " + Util.usedMemory(mb4));
  }

    @Override
    public int archiveFeeds() {
        
        final AppContext appContext = Objects.requireNonNull((AppContext)context.getAttribute(Attributes.APP_CONTEXT));
        
final long tb4 = System.currentTimeMillis();
final long mb4 = appContext.getMemoryManager().getAvailableMemory();

        final Level level = this.getLogLevel(appContext);

        LOG.log(level, "Archiving feeds. Memory: {0}", mb4);
        
        final int output = super.archiveFeeds();

        if(LOG.isLoggable(level)) {
            LOG.log(level, "Done archiving feeds. Consumed time: {0}, memory: {1}", 
            new Object[]{(System.currentTimeMillis()-tb4), (mb4-appContext.getMemoryManager().getAvailableMemory())});
        }
        return output;
    }

    @Override
    public boolean downloadFeeds() {
        
        final AppContext appContext = Objects.requireNonNull((AppContext)context.getAttribute(Attributes.APP_CONTEXT));
        
final long tb4 = System.currentTimeMillis();
final long mb4 = appContext.getMemoryManager().getAvailableMemory();

        final Level level = this.getLogLevel(appContext);

        LOG.log(level, "Downloading feeds. Memory: {0}", mb4);
        
        final boolean output = super.downloadFeeds(); 

        if(LOG.isLoggable(level)) {
            LOG.log(level, "Done downloading feeds. Consumed time: {0}, memory: {1}", 
            new Object[]{(System.currentTimeMillis()-tb4), (mb4-appContext.getMemoryManager().getAvailableMemory())});
        }
        return output;
    }
    
    public Level getLogLevel(AppContext appContext) {
        final boolean debugTimeAndMemory = 
                appContext.getConfiguration().getBoolean(ConfigNames.DEBUG_TIME_AND_MEMORY, false);
        return debugTimeAndMemory ? Level.INFO : Level.FINE;
    }
  
  @Override
  public long getIntervalMillis() {
    return 60_000;
  }
}
