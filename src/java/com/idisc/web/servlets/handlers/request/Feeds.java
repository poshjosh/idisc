package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.DefaultFeedCache;
import com.idisc.web.DefaultFeedComparator;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Feeds extends Selectfeeds {
    
  private Installation installation;

  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !this.isHtmlResponse(request);
  }

  @Override
  protected ResponseContext<List<Feed>> createSuccessResponseContext(HttpServletRequest request) {
    return new FeedsResponseContext(request, installation);
  }
  
  @Override
  public List<Feed> execute(HttpServletRequest request) throws ServletException, IOException {
      
    boolean create = true;
    
    this.installation = getInstallation(request, create);
    
    List<Feed> output = super.execute(request);
    
    return output;
  }
  
  @Override
  public synchronized List<Feed> select(HttpServletRequest request)
    throws ValidationException {
      
XLogger.getInstance().entering(this.getClass(), "#select(HttpServletRequest)", "");
      
    if (!DefaultFeedCache.isCachedFeedsAvailable()) {
        
      DefaultFeedCache fc = new DefaultFeedCache();
      
      fc.updateCache();
    }
    
    int userLimit = getLimit(request);
    
    List<Feed> output = DefaultFeedCache.getCachedFeeds(userLimit);
    
long tb4 = System.currentTimeMillis();
long mb4 = Runtime.getRuntime().freeMemory();
    try (DefaultFeedComparator autoCloseableFeedComparator = new DefaultFeedComparator(installation)) {
          
      Collections.sort(output, autoCloseableFeedComparator);
    }
XLogger.getInstance().log(Level.FINE, "Sorted {0} feeds. Consumed time: {1}, memory: {2}", 
this.getClass(), output.size(), (System.currentTimeMillis()-tb4), (mb4-Runtime.getRuntime().freeMemory()));

    return output;
  }
}
