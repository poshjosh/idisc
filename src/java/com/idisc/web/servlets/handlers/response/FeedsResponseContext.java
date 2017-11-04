package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.CommentRepliesBuilder;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import com.idisc.web.DefaultFeedService;
import com.idisc.web.tasks.UpdateFileFeeds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 * @param <E>
 */
public class FeedsResponseContext<E extends Feed> extends SuccessHandlerContext<List<E>> {

  private final Installation installation;
  
  private final List<Integer> hotnewsFeedids;

  public FeedsResponseContext(
          HttpServletRequest request, Installation installation) { 
      this(request, installation, Collections.EMPTY_LIST);
  }
  
  public FeedsResponseContext(
          HttpServletRequest request, Installation installation, List<Integer> hotnewsFeedids) { 
    super(request);
    this.installation = installation;
    this.hotnewsFeedids = Objects.requireNonNull(hotnewsFeedids);
  }

  @Override
  public Object format(String name, List<E> feeds) {
      
    final int feedCount = feeds == null ? 0 : feeds.size();
    
    final Collection<Feed> notifications = getNotices(feeds, Collections.EMPTY_LIST);

XLogger.getInstance().log(Level.FINER, "Installation: {0}, number of notifications: {1}", 
        this.getClass(), installation, notifications.size());
    
    final Map commentNotices = installation == null ? Collections.EMPTY_MAP : getCommentNotices(installation, feeds);
    
XLogger.getInstance().log(Level.FINER, "Number of commentNotices: {0}", 
        this.getClass(), installation, commentNotices.size());
    
XLogger.getInstance().log(Level.FINER, "Hot news feedids: {0}", 
        this.getClass(), installation, hotnewsFeedids);

    final List<Feed> hotnews = this.fetchHotnews(feeds, hotnewsFeedids);
    
    final int extraCount = notifications.size() + commentNotices.size() + hotnewsFeedids.size() + hotnews.size();
    
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
        
        if (!notifications.isEmpty()) {
          compositeOutput.addAll(notifications);
        }

        if (!commentNotices.isEmpty()) {
          compositeOutput.add(commentNotices);
        }
        
        if(!hotnewsFeedids.isEmpty()) {
          compositeOutput.add(Collections.singletonMap("newsminute-hotnews", hotnewsFeedids));
        }
        
        if(!hotnews.isEmpty()) {
          compositeOutput.addAll(hotnews);
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
        
        com.idisc.pu.FeedService feedService = new DefaultFeedService(this.getAppContext());
//                new com.idisc.pu.FeedService(this.getAppContext().getIdiscApp().getJpaContext());
                
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
  
  public List<Feed> fetchHotnews(List<E> feeds, List<Integer> hotnewsFeedids) {
    if(hotnewsFeedids.isEmpty()) {
      return Collections.EMPTY_LIST;      
    }else{
      final int limit = this.getAppContext().getMemoryManager().limit(hotnewsFeedids.size(), 1);
      if(limit < hotnewsFeedids.size()) {
          hotnewsFeedids = hotnewsFeedids.subList(0, limit);
      }
      final List<Feed> hotnews = new ArrayList<>(hotnewsFeedids.size());
      EntityManager em = this.getAppContext().getIdiscApp().getJpaContext().getEntityManager(Feed.class);
      for(Integer feedid : hotnewsFeedids) {
        if(this.contains(feeds, feedid)) {
          continue;    
        }
        Feed feed = em.find(Feed.class, feedid); 
        if(feed != null) {
          hotnews.add(feed);
        }
      }
      return hotnews;
    }
  }
  
  private boolean contains(List<E> feeds, Integer feedid) {
    for(E feed : feeds) {
      if(feedid.equals(feed.getFeedid())) {
        return true;
      }     
    }
    return false;
  }

  public final Installation getInstallation() {
    return installation;
  }
}
