package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;



public class Syncbookmarks
  extends AbstractPreferenceSync<Bookmarkfeed>
{
  public String getKey()
  {
    return "syncbookmarks";
  }
  
  public Class<Bookmarkfeed> getEntityClass()
  {
    return Bookmarkfeed.class;
  }
  
  public List<Bookmarkfeed> getFeedList(Installation installation)
  {
    return installation.getBookmarkfeedList();
  }
  
  public Feed getFeed(Bookmarkfeed bookmark)
  {
    return bookmark.getFeedid();
  }
  
  public Bookmarkfeed createFor(Installation installation, Feed feed)
  {
    Bookmarkfeed bookmark = new Bookmarkfeed();
    bookmark.setDatecreated(new Date());
    bookmark.setInstallationid(installation);
    bookmark.setFeedid(feed);
    return bookmark;
  }
}
