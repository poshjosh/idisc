package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.CommentNotification;
import com.idisc.core.Util;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.one.Feed_;
import com.idisc.web.Notices;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public class FeedsJsonResponseHandler extends SelectfeedsJsonResponseHandler {
    
  private final Installation installation;
  
  public FeedsJsonResponseHandler(Installation installation) {
    this.installation = installation;
  }

  @Override
  public Object getOutput(HttpServletRequest request, String name, List<Feed> feeds)
  {

    final int feedCount = feeds == null ? 0 : feeds.size();
    
    Collection<Map> notifications = getNotices(request, feeds);
    
    final int noticeCount = notifications == null ? 0 : notifications.size();
    
    Map commentNotices = getCommentNotices(request, feeds);
    
    final int commentNoticeCount = commentNotices == null ? 0 : 1;
    
    final int extraCount = noticeCount + commentNoticeCount;
    
    if ((feedCount + extraCount) < 1)
    {
      return this.getEmptyJsonOutput(request, name);
    }
    
    StringBuilder json;
    
    if(extraCount < 1) {
        
        json = super.getJsonOutput(request, name, feeds, feeds);
        
    }else{
        
        List combinedOutput = new ArrayList(feedCount + extraCount);

        if ((feeds != null) && (!feeds.isEmpty())) {
          combinedOutput.addAll(feeds);
        }
        
        if ((notifications != null) && (!notifications.isEmpty())) {
          combinedOutput.addAll(notifications);
        }

        if ((commentNotices != null) && (!commentNotices.isEmpty())) {
          combinedOutput.add(commentNotices);
        }
        
        final int feedsSize = this.getEstimatedBufferCapacity(feeds, feeds);
        final int extraSize = extraCount * 300;
        
        final int buffLen = feedsSize + extraSize;
        
        json = super.getJsonOutput(request, name, combinedOutput, buffLen);
    }
    
    return json.length() > 1000 ? new String(json) : json.toString();
  }

  public Map getCommentNotices(HttpServletRequest request, List<Feed> feeds)
  {
    if (this.installation == null) {
      return null;
    }
    try
    {
      int maxAgeDays = 30;boolean directReplies = false;
      List<Map<String, Object>> commentNotices = CommentNotification.getNotifications(this.installation, getJsonFormat(request), directReplies, 30);
      
      if ((commentNotices != null) && (!commentNotices.isEmpty())) {
        return Collections.singletonMap("notices", commentNotices);
      }
      return null;
    }
    catch (Exception e) {
      XLogger.getInstance().log(Level.WARNING, null, getClass(), e); }
    return null;
  }

  private static Collection<Map> _ns;
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
}
/**
 * 
  public Object getOutput_old(HttpServletRequest request, String name, List<Feed> feeds)
  {
      
    final int feedCount = feeds == null ? 0 : feeds.size();
    
    Collection<Map> notifications = getNotices(request, feeds);
    
    final int noticeCount = notifications == null ? 0 : notifications.size();
    
    Map commentNotices = getCommentNotices(request, feeds);
    
    final int commentNoticeCount = commentNotices == null ? 0 : 1;
    
    final int extraCount = noticeCount + commentNoticeCount;
    
    if ((feedCount + extraCount) < 1)
    {
      return this.getEmptyJsonOutput(request, name);
    }
    
    StringBuilder json;
    
    if(extraCount < 1) {
        
        json = super.getJsonOutput(request, name, feeds, feeds);
        
    }else{
        
        List combinedOutput = new ArrayList(feedCount + extraCount);

        if ((feeds != null) && (!feeds.isEmpty())) {
          combinedOutput.addAll(feeds);
        }

        if ((notifications != null) && (!notifications.isEmpty())) {
          combinedOutput.addAll(notifications);
        }

        if ((commentNotices != null) && (!commentNotices.isEmpty())) {
          combinedOutput.add(commentNotices);
        }

        json = getJsonOutput(request, name, feeds, combinedOutput);
    }
    
    return json.length() > 1000 ? new String(json) : json.toString();
  }
 * 
 */