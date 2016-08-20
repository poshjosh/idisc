package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Favoritefeed_;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;


public class Syncfavorites extends AbstractPreferenceSync<Favoritefeed> {
    
  @Override
  public String getKey() {
    return "syncfavorites";
  }
  
  @Override
  public Class<Favoritefeed> getEntityClass() {
    return Favoritefeed.class;
  }
  
  @Override
  public String getInstallationColumnName() {
    return Favoritefeed_.installationid.getName();
  }
  
  @Override
  public List<Favoritefeed> getPreferenceList(Installation installation){
    return installation.getFavoritefeedList();
  }
  
  @Override
  public Feed getFeed(Favoritefeed bookmark){
    return bookmark.getFeedid();
  }
  
  @Override
  public Favoritefeed createFor(Installation installation, Feed feed) {
    Favoritefeed bookmark = new Favoritefeed();
    bookmark.setDatecreated(new Date());
    bookmark.setInstallationid(installation);
    bookmark.setFeedid(feed);
    return bookmark;
  }
}
