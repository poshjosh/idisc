package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.DefaultFeedCache;
import com.idisc.web.DefaultFeedComparator;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.FeedsJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Feeds extends Selectfeeds
{
  private Installation installation;

  @Override
  public ResponseHandler<List<Feed>> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<List<Feed>> responseHandler;
    if (this.isHtmlResponse(request))
    {
      responseHandler = new HtmlResponseHandler();
    }
    else
    {
      responseHandler = new FeedsJsonResponseHandler(installation);
    }
    
    return responseHandler;
  }
  
  @Override
  public List<Feed> execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    boolean create = true;
    this.installation = getInstallation(request, response, create);
    
    List<Feed> output = super.execute(request, response);
    
    return output;
  }
  
  @Override
  public synchronized List<Feed> select(HttpServletRequest request)
    throws ValidationException
  {
    if (!DefaultFeedCache.isCachedFeedsAvailable())
    {
      DefaultFeedCache fc = new DefaultFeedCache();
      
      fc.updateCache();
    }
    
    int userLimit = getLimit(request);
    
    List<Feed> cachedFeeds = DefaultFeedCache.getCachedFeeds(request);
    
    final int cacheSize = cachedFeeds.size();
    
    List<Feed> output = new ArrayList(cacheSize <= userLimit ? cachedFeeds : cachedFeeds.subList(0, userLimit));
    
//long tb4 = System.currentTimeMillis();
//long mb4 = Runtime.getRuntime().freeMemory();
    try (DefaultFeedComparator autoCloseableFeedComparator = new DefaultFeedComparator(installation)) {
          
      Collections.sort(output, autoCloseableFeedComparator);
    }
//XLogger.getInstance().log(Level.INFO, "Sorted {0} feeds. Consumed time: {1}, memory: {1}", 
//this.getClass(), output.size(), (System.currentTimeMillis()-tb4), (mb4-Runtime.getRuntime().freeMemory()));

    request.getSession().setAttribute("output", cachedFeeds);
    
    return output;
  }
}
