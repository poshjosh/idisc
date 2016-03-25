package com.idisc.web.servlets.handlers;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.web.FeedCache;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;












public class Feed
  extends BaseRequestHandler<com.idisc.pu.entities.Feed>
{
  public String getResponseFormat(HttpServletRequest request)
  {
    return "text/html";
  }
  
  public boolean isProtected()
  {
    return false;
  }
  


  public com.idisc.pu.entities.Feed execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    int feedid = getId(request).intValue();
    
    com.idisc.pu.entities.Feed feed = null;
    
    List<com.idisc.pu.entities.Feed> lastFeeds = FeedCache.getLastFeeds();
    
    if ((lastFeeds != null) && (!lastFeeds.isEmpty()))
    {
      synchronized (lastFeeds) {
        for (com.idisc.pu.entities.Feed lastFeed : lastFeeds) {
          if (lastFeed.getFeedid().intValue() == feedid) {
            feed = lastFeed;
            break;
          }
        }
      }
      
      request.getSession().getServletContext().setAttribute("lastFeeds", lastFeeds);
    }
    
    if (feed == null)
    {


      feed = select(feedid);
    }
    
    XLogger.getInstance().log(Level.FINER, "Selected feed: {0}", getClass(), feed);
    
    return feed;
  }
  
  public com.idisc.pu.entities.Feed select(int feedid)
  {
    ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
    
    EntityController<com.idisc.pu.entities.Feed, Integer> ec = factory.getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
    

    return (com.idisc.pu.entities.Feed)ec.find(Integer.valueOf(feedid));
  }
  
  public Integer getId(HttpServletRequest request) throws ValidationException
  {
    String sval = request.getParameter("feedid");
    
    return getId(sval);
  }
  
  public Integer getId(String sval) throws ValidationException
  {
    if ((sval == null) || (sval.isEmpty())) {
      throw new ValidationException("Required parameter 'feedid' not found");
    }
    int ival;
    try
    {
      ival = Integer.parseInt(sval);
    } catch (NumberFormatException e) {
      throw new ValidationException("Invalid value for required parameter: 'feedid'");
    }
    return Integer.valueOf(ival);
  }
}
