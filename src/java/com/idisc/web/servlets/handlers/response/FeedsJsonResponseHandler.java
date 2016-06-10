package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.CommentNotification;
import com.idisc.core.EntityJsonFormat;
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
public class FeedsJsonResponseHandler<E extends Feed> extends SelectfeedsJsonResponseHandler<E> {
    
  private final Installation installation;
  
  public FeedsJsonResponseHandler() {
    this(null);
  }

  public FeedsJsonResponseHandler(Installation installation) {
    this.installation = installation;
  }
  
  public List buildCompositeOutput(HttpServletRequest request, String name, List<E> feeds) {
      
    final int feedCount = feeds == null ? 0 : feeds.size();
    
    Collection<Map> notifications = getNotices(request, feeds);
    
    final int noticeCount = notifications == null ? 0 : notifications.size();
    
    Map commentNotices = installation == null ? null : 
            getCommentNotices(request, installation, this.getJsonFormat(request), feeds);
    
    final int commentNoticeCount = commentNotices == null ? 0 : 1;
    
    final int extraCount = noticeCount + commentNoticeCount;
    
    final List compositeOutput;
    
    if ((feedCount + extraCount) < 1) {
        
      compositeOutput = Collections.EMPTY_LIST;
      
    }else if(extraCount < 1) {
        
      compositeOutput = feeds;  
      
    }else{
        
        compositeOutput = new ArrayList(feedCount + extraCount);

        if ((feeds != null) && (!feeds.isEmpty())) {
          compositeOutput.addAll(feeds);
        }
        
        if ((notifications != null) && (!notifications.isEmpty())) {
          compositeOutput.addAll(notifications);
        }

        if ((commentNotices != null) && (!commentNotices.isEmpty())) {
          compositeOutput.add(commentNotices);
        }
    }
    
    return compositeOutput;
  }

  @Override
  public StringBuilder getOutput(HttpServletRequest request, String name, List<E> feeds) {
    
    final List compositeOutput = this.buildCompositeOutput(request, name, feeds);

    final int feedsSize = this.getEstimatedLengthChars(feeds, compositeOutput);
    final int extrasCount = compositeOutput.size() - (feeds==null?0:feeds.size());
    final int extraSize = extrasCount * 300;

    final int buffLen = feedsSize + extraSize;

    return super.getJsonOutput(request, name, compositeOutput, buffLen);
  }

  public Map getCommentNotices(
          HttpServletRequest request, Installation installation, 
          EntityJsonFormat jsonFormat, List<E> feeds) {
    if (installation == null) {
      return null;
    }
    try
    {
      int maxAgeDays = 30;boolean directReplies = false;
      List<Map<String, Object>> commentNotices = CommentNotification.getNotifications(installation, jsonFormat, directReplies, 30);
      
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
  public Collection<Map> getNotices(HttpServletRequest request, List<E> feeds) {
    try {
      if (_ns == null) {
        XLogger.getInstance().log(Level.INFO, "Creating tips cache", getClass());
        _ns = new Notices(request, true).values();
      }
      
      if ((_ns != null) && (!_ns.isEmpty()))
      {
        XLogger.getInstance().log(Level.FINER, "Adding {0} tips to output", getClass(), Integer.valueOf(_ns.size()));
        
        Date date = Util.getEarliestDate(feeds);
        
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