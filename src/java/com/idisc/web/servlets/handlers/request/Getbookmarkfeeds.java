package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.List;


public class Getbookmarkfeeds
  extends GetUserPreferenceFeeds<Bookmarkfeed>
{
  public Class<Bookmarkfeed> getPreferenceEntityClass()
  {
    return Bookmarkfeed.class;
  }
  
  public List<Bookmarkfeed> getPreferenceFeedList(Installation installation)
  {
    return installation.getBookmarkfeedList();
  }
  
  public Feed getFeed(Bookmarkfeed pref)
  {
    return pref.getFeedid();
  }
}
