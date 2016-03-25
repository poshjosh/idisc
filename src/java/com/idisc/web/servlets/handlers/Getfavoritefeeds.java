package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.List;



public class Getfavoritefeeds
  extends GetUserPreferenceFeeds<Favoritefeed>
{
  public Class<Favoritefeed> getPreferenceEntityClass()
  {
    return Favoritefeed.class;
  }
  
  public List<Favoritefeed> getPreferenceFeedList(Installation installation)
  {
    return installation.getFavoritefeedList();
  }
  
  public Feed getFeed(Favoritefeed pref)
  {
    return pref.getFeedid();
  }
}
