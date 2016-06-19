package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.CommentNotificationsBuilder;
import com.idisc.core.util.Util;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.one.Feed_;
import com.idisc.web.ConfigNames;
import com.idisc.web.Notices;
import com.idisc.web.WebApp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
    
    Collection<Map> notifications = getNotices(feeds);
    
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
        this.getClass(), feeds==null?null:feeds.size());

    if (installation == null || feeds==null || feeds.isEmpty()) {
      return Collections.EMPTY_MAP;
    }
    
    try {
        
      Configuration config = WebApp.getInstance().getConfiguration();
      
      final boolean directRepliesOnly = config.getBoolean(ConfigNames.COMMENTS_NOTIFICATIONS_DIRECTREPLIESONLY, false);
      final int maxAgeDays = config.getInt(ConfigNames.COMMENTS_NOTIFICATIONS_MAXAGE_DAYS, 30);
      final boolean repeat = config.getBoolean(ConfigNames.COMMENTS_NOTIFICATIONS_REPEAT, false);
      
      List<Map<String, Object>> commentNotices = 
              new CommentNotificationsBuilder().build(installation, directRepliesOnly, maxAgeDays, repeat);
      
      if ((commentNotices != null) && (!commentNotices.isEmpty())) {
        return Collections.singletonMap("notices", commentNotices);
      }
      return Collections.EMPTY_MAP;
    }catch (Exception e) {
      XLogger.getInstance().log(Level.WARNING, null, getClass(), e); 
      return Collections.EMPTY_MAP;
    }
  }

  private static Collection<Map> _ns;
  public Collection<Map> getNotices(List<E> feeds) {
    try {
      if (_ns == null) {
        XLogger.getInstance().log(Level.INFO, "Creating tips cache", getClass());
        _ns = new Notices(true).values();
      }
      
      if ((_ns != null) && (!_ns.isEmpty())) {
          
        XLogger.getInstance().log(Level.FINER, "Adding {0} tips to output", getClass(), _ns.size());
        
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

  public final Installation getInstallation() {
    return installation;
  }
}
