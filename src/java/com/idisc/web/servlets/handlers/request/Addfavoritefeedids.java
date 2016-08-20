package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import com.bc.jpa.JpaContext;
import javax.servlet.http.HttpServletRequest;

public class Addfavoritefeedids extends UpdateUserPreferenceFeedids {
    
  @Override
  public String getRequestParameterName() {
    return "com.looseboxes.idisc.addfavoritefeedids.feedids";
  }
  
  @Override
  protected List execute(HttpServletRequest request, String name, JSONArray feedids, Installation installation)
    throws Exception {
      
    JpaContext factory = getJpaContext(request);
    
    EntityManager em = factory.getEntityManager(Favoritefeed.class);
    
    try {
        
      em.getTransaction().begin();
      
      final Object userId = installation.getFeeduserid() == null ? installation.getInstallationid() : installation.getFeeduserid().getEmailAddress();
      
      XLogger.getInstance().log(Level.FINE, "Adding for user: {0} favorite feedids: {1}", getClass(), userId, feedids);
      
      List<Favoritefeed> valuesFromDatabase = installation.getFavoritefeedList();
      
      for (Object feedid : feedids) {
          
        if (feedid != null) {

          if(this.isExistingFavoritefeed(valuesFromDatabase, feedid)) {
              continue;
          }

          Feed feed = (Feed)em.find(Feed.class, Integer.valueOf(feedid.toString()));
          
          Favoritefeed favorite = new Favoritefeed();
          favorite.setDatecreated(new Date());
          favorite.setFeedid(feed);
          favorite.setInstallationid(installation);
          
          em.persist(favorite);
        } 
      }
      
      em.getTransaction().commit();
      
      XLogger.getInstance().log(Level.FINE, "Added for user: {0} favorite feedids: {1}", getClass(), userId, feedids);
    }finally {
      if (em != null) {
        em.close();
      }
    }
    
    return java.util.Collections.emptyList();
  }
  
  public boolean isExistingFavoritefeed(List<Favoritefeed> valuesFromDatabase, Object feedid) {
    if(valuesFromDatabase != null) {
      for(Favoritefeed favorite:valuesFromDatabase) {
        if(feedid.equals(favorite.getFeedid().getFeedid())) {
          // favorite alread exists
          return true;
        }
      }
    }
    return false;
  }
}
