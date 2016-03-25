package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.CommentNotification;
import com.idisc.core.Util;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.one.Feed_;
import com.idisc.web.FeedCache;
import com.idisc.web.FeedComparator;
import com.idisc.web.Notices;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;








public class Feeds
  extends Selectfeeds
{
  private Installation installation;
  private static Collection<Map> _ns;
  
  public Object getMessage(HttpServletRequest request, List<Feed> feeds)
  {
    int feedsLen = feeds == null ? 0 : feeds.size();
    
    Collection<Map> notifications = getNotices(request, feeds);
    
    int noticesLen = notifications == null ? 0 : notifications.size();
    
    Map commentNotices = getCommentNotices(request, feeds);
    
    int commentNoticesLen = commentNotices == null ? 0 : 1;
    
    int totalLen = feedsLen + noticesLen + commentNoticesLen;
    
    if (totalLen < 1)
    {
      return null;
    }
    

    List output = new ArrayList(feedsLen + noticesLen + commentNoticesLen);
    
    if ((feeds != null) && (!feeds.isEmpty())) {
      output.addAll(feeds);
    }
    
    if ((notifications != null) && (!notifications.isEmpty())) {
      output.addAll(notifications);
    }
    
    if ((commentNotices != null) && (!commentNotices.isEmpty())) {
      output.add(commentNotices);
    }
    
    return getJsonMessage(request, getParameterName(), output);
  }
  
  public Map getCommentNotices(HttpServletRequest request, List<Feed> feeds)
  {
    if (this.installation == null) {
      return null;
    }
    try
    {
      int maxAgeDays = 30;boolean directReplies = false;
      List<Map<String, Object>> commentNotices = CommentNotification.getNotifications(this.installation, getJsonFormat(), directReplies, 30);
      
      if ((commentNotices != null) && (!commentNotices.isEmpty())) {
        return Collections.singletonMap("notices", commentNotices);
      }
      return null;
    }
    catch (Exception e) {
      XLogger.getInstance().log(Level.WARNING, null, getClass(), e); }
    return null;
  }
  


  public Collection<Map> getNotices(HttpServletRequest request, List<Feed> output)
  {
    try
    {
      if (_ns == null) {
        XLogger.getInstance().log(Level.INFO, "Creating tips cache", getClass());
        _ns = new Notices(request, true).values();
      }
      
      if ((_ns != null) && (!_ns.isEmpty()))
      {
        XLogger.getInstance().log(Level.FINER, "Adding {0} tips to output", getClass(), Integer.valueOf(_ns.size()));
        
        Date date = Util.getEarliestDate(output);
        
        for (Map notice : _ns) {
          notice.put(Feed_.datecreated.getName(), date);
          notice.put(Feed_.feeddate.getName(), date);
        }
      }
    } catch (Exception e) { 
      XLogger.getInstance().log(Level.WARNING, "Error loading tips", getClass(), e);
    }
    
    return _ns;
  }
  



  public List<Feed> execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    boolean create = true;
    this.installation = getInstallation(request, response, create);
    
    List<Feed> output = super.execute(request, response);
    
    return output;
  }
  
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
    
    int cacheSize = lastFeeds.size();
    
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
