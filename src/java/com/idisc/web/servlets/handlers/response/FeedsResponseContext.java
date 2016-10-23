package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.CommentRepliesBuilder;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import com.idisc.web.tasks.UpdateFileFeeds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 * @param <E>
 */
public class FeedsResponseContext<E extends Feed> extends SuccessHandlerContext<List<E>> {

  private final Installation installation;
  
  public FeedsResponseContext(HttpServletRequest request, Installation installation) { 
    super(request);
    this.installation = installation;
  }

  @Override
  public Object format(String name, List<E> feeds) {
      
    final int feedCount = feeds == null ? 0 : feeds.size();
    
    Collection<Feed> notifications = getNotices(feeds, null);

XLogger.getInstance().log(Level.FINER, "Installation: {0}, number of notifications: {1}", 
        this.getClass(), installation, notifications==null?null:notifications.size());
    
    final int noticeCount = notifications == null ? 0 : notifications.size();
    
    Map commentNotices = installation == null ? null : getCommentNotices(installation, feeds);
    
XLogger.getInstance().log(Level.FINER, "Installation: {0}, number of commentNotices: {1}", 
        this.getClass(), installation, commentNotices==null?null:commentNotices.size());
    
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

  public Map getCommentNotices(Installation installation, List<E> feeds) {
      
XLogger.getInstance().log(Level.FINER, "Installation: {0}, number of feeds: {1}", 
        this.getClass(), installation, feeds==null?null:feeds.size());

    if (installation == null || feeds==null || feeds.isEmpty()) {
      return Collections.EMPTY_MAP;
    }
    
    try {
        
      Configuration config = this.getAppContext().getConfiguration();
      
      final boolean directRepliesOnly = config.getBoolean(ConfigNames.COMMENTS_NOTIFICATIONS_DIRECTREPLIESONLY, false);
      final int maxAgeDays = config.getInt(ConfigNames.COMMENTS_NOTIFICATIONS_MAXAGE_DAYS, 30);
      final int max = config.getInt(ConfigNames.COMMENTS_NOTIFICATIONS_MAX, 0);
      
      List<Map<String, Object>> commentNotices = 
              new CommentRepliesBuilder().build(installation, directRepliesOnly, maxAgeDays, max);
      
      if ((commentNotices != null) && (!commentNotices.isEmpty())) {
        return Collections.singletonMap("notices", commentNotices);
      }
      return Collections.EMPTY_MAP;
    }catch (Exception e) {
      XLogger.getInstance().log(Level.WARNING, null, getClass(), e); 
      return Collections.EMPTY_MAP;
    }
  }

  public Collection<Feed> getNotices(List<E> feeds, Collection<Feed> defaultValue) {
      
    Collection<Feed> notices = defaultValue;
    
    try {
        
      Map<String, Feed> noticesCache = this.getNoticesCache();
      
      notices = noticesCache == null ? null : new ArrayList(noticesCache.values());

      if (notices != null && !notices.isEmpty()) {
          
        XLogger.getInstance().log(Level.FINER, "Adding {0} notices to output", getClass(), notices.size());
        
        com.idisc.pu.FeedService feedService = new com.idisc.pu.FeedService(this.getAppContext().getIdiscApp().getJpaContext());
                
        Date date = feedService.getEarliestDate(feeds);
        
        for (Feed notice : notices) {
          notice.setDatecreated(date);
          notice.setFeeddate(date);
        }
      }
    } catch (Exception e) { 
      XLogger.getInstance().log(Level.WARNING, "Error loading notices", getClass(), e);
    }
    
    return notices;
  }
  
  public Map<String, Feed> getNoticesCache() {
      
    ServletContext context = this.getServletContext();
    
    Map<String, Feed> noticesCache = (Map<String, Feed>)context.getAttribute(Attributes.NOTICES_MAP);
    
    if(noticesCache == null) {
        
      noticesCache = new UpdateFileFeeds(context).call();
    }
    
    return noticesCache;
  }

  public final Installation getInstallation() {
    return installation;
  }
}
