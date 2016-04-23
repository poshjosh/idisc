package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;


public class Syncfavorites
  extends AbstractPreferenceSync<Favoritefeed>
{
  public String getKey()
  {
    return "syncfavorites";
  }
  
  public Class<Favoritefeed> getEntityClass()
  {
    return Favoritefeed.class;
  }
  
  public List<Favoritefeed> getFeedList(Installation installation)
  {
    return installation.getFavoritefeedList();
  }
  
  public Feed getFeed(Favoritefeed bookmark)
  {
    return bookmark.getFeedid();
  }
  
  public Favoritefeed createFor(Installation installation, Feed feed)
  {
    Favoritefeed bookmark = new Favoritefeed();
    bookmark.setDatecreated(new Date());
    bookmark.setInstallationid(installation);
    bookmark.setFeedid(feed);
    return bookmark;
  }
}
