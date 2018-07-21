package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Bookmarkfeed_;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;

public class Syncbookmarks extends AbstractPreferenceSync<Bookmarkfeed> {
    
  @Override
  public String getKey() {
    return "syncbookmarks";
  }
  
  @Override
  public String getInstallationColumnName() {
    return Bookmarkfeed_.installationid.getName();
  }
  
  @Override
  public Class<Bookmarkfeed> getEntityClass() {
    return Bookmarkfeed.class;
  }
  
  @Override
  public List<Bookmarkfeed> getPreferenceList(Installation installation) {
    return installation.getBookmarkfeedList();
  }
  
  @Override
  public Feed getFeed(Bookmarkfeed bookmark) {
    return bookmark.getFeedid();
  }
  
  @Override
  public Bookmarkfeed createFor(Installation installation, Feed feed) {
    Bookmarkfeed bookmark = new Bookmarkfeed();
    bookmark.setDatecreated(new Date());
    bookmark.setInstallationid(installation);
    bookmark.setFeedid(feed);
    return bookmark;
  }
}
