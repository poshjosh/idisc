package com.idisc.web.servlets.handlers.request;

import com.idisc.core.User;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class GetUserPreferenceFeeds<K> extends Selectfeeds {
    
  private User user;
  
  public abstract Class<K> getPreferenceEntityClass();
  
  public abstract List<K> getPreferenceFeedList(Installation paramInstallation);
  
  public abstract Feed getFeed(K paramK);
  
  @Override
  public boolean isProtected()
  {
    return true;
  }
  
  @Override
  public List<Feed> execute(HttpServletRequest request)
    throws ServletException, IOException
  {
    this.user = findUser(request);
    
    return super.execute(request);
  }
  
  @Override
  protected List<Feed> select(HttpServletRequest request) throws ServletException {
      
    Installation installation = getInstallation(request, true);
    if(installation == null) {
        throw new ServletException("You are not authorized to perform the requested operation");
    }

    List<K> prefs = getPreferenceFeedList(installation);
    List<Feed> feeds = toFeedList(prefs);
    return feeds;
  }
  
  public List<Feed> toFeedList(List<K> prefs) {
    if (prefs == null) {
      return null;
    }
    List<Feed> feeds = new ArrayList(prefs.size());
    for (K pref : prefs) {
      Feed feed = getFeed(pref);
      feeds.add(feed);
    }
    return feeds;
  }
  

  protected List<Feed> ensureEquality(List<Feed> feeds, int outputSize)
  {
    List<Feed> output;
    
    if (feeds == null) {
      output = null; } else { 
      if (feeds.size() > outputSize) {
        output = feeds.subList(0, outputSize);
      } else
        output = feeds;
    }
    return output;
  }
}
