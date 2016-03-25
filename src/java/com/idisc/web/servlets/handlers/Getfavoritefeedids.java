package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.ArrayList;
import java.util.List;


public class Getfavoritefeedids
  extends GetUserPreferenceFeedids
{
  public List getFeedids(Installation installation)
  {
    List favoriteFeedids = null;
    List<Favoritefeed> favorites = installation.getFavoritefeedList();
    if ((favorites != null) && (!favorites.isEmpty())) {
      favoriteFeedids = new ArrayList(favorites.size());
      for (Favoritefeed favorite : favorites) {
        favoriteFeedids.add(favorite.getFeedid().getFeedid());
      }
    }
    return favoriteFeedids;
  }
}
