package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.FeedCache;
import com.idisc.web.FeedComparator;
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
    if (!FeedCache.isCachedFeedsAvailable())
    {
      FeedCache fc = new FeedCache();
      
      fc.updateCache();
    }
    
    int userLimit = getLimit(request);
    
    List<Feed> lastFeeds = FeedCache.getLastFeeds();
    

    request.getSession().getServletContext().setAttribute("lastFeeds", lastFeeds);
    
    final int cacheSize = lastFeeds.size();
    
    List<Feed> output = new ArrayList(cacheSize <= userLimit ? lastFeeds : lastFeeds.subList(0, userLimit));
    
    FeedComparator comparator = new FeedComparator(){
      // Having an installation specified makes the sort user specific
      @Override
      public Installation getInstallation() {
        return installation;
      }
    };

    comparator.setInvertSort(true);
    
    Collections.sort(output, comparator);
    

    request.getSession().setAttribute("output", lastFeeds);
    
    return output;
  }
}
