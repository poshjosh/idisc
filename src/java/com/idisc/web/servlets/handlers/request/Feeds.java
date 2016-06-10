package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.DefaultFeedCache;
import com.idisc.web.DefaultFeedComparator;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.FeedsJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.FeedsToReaderResponseHandler;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Feeds extends Selectfeeds {
    
  private Installation installation;

  @Override
  public boolean isOutputLarge() {
    return true;
  }
 
  @Override
  public ResponseHandler<List<Feed>, Object> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<List<Feed>, Object> responseHandler;
    if (this.isHtmlResponse(request)) {
      responseHandler = new HtmlResponseHandler();
    } else {
      if(this.isOutputLarge() && this.isStreamLargeResponses()) {  
        responseHandler = new FeedsToReaderResponseHandler(installation);
      }else{
        responseHandler = new FeedsJsonResponseHandler(installation);
      }
    }
    
    return responseHandler;
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
