package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import com.bc.jpa.JpaContext;
import javax.servlet.http.HttpServletRequest;

public class Addbookmarkfeedids extends UpdateUserPreferenceFeedids {
    
  @Override
  public String getRequestParameterName(){
    return "com.looseboxes.idisc.addbookmarkfeedids.feedids";
  }
  
  @Override
  protected List execute(HttpServletRequest request, String name, JSONArray feedids, Installation installation)
    throws Exception {
      
    JpaContext jpaContext = getJpaContext(request);
    
    EntityManager em = jpaContext.getEntityManager(Bookmarkfeed.class);
    
    try {
        
      em.getTransaction().begin();
      
      final Object userId = installation.getFeeduserid() == null ? installation.getInstallationid() : installation.getFeeduserid().getEmailAddress();
      
      XLogger.getInstance().log(Level.FINE, "Adding for user: {0} bookmark feedids: {1}", getClass(), userId, feedids);
      
      List<Bookmarkfeed> valuesFromDatabase = installation.getBookmarkfeedList();
      
//      EntityController<Feed, Integer> feedCtrl = factory.getEntityController(Feed.class, Integer.class);
      com.bc.jpa.dao.BuilderForSelect<Feed> select = jpaContext.getBuilderForSelect(Feed.class);
      
      outer:
      for (Object feedid : feedids) {
          
        if (feedid != null){

          if(this.isExistingBookmarkfeed(valuesFromDatabase, feedid)) {
            continue;
          }

          Feed feed = select.findAndClose(Feed.class, feedid);
          
          Bookmarkfeed bookmark = new Bookmarkfeed();
          bookmark.setDatecreated(new Date());
          bookmark.setFeedid(feed);
          bookmark.setInstallationid(installation);
          
          em.persist(bookmark);
        } 
      }
      
      em.getTransaction().commit();
      
      XLogger.getInstance().log(Level.FINE, "Added for user: {0}, bookmark feedids: {1}", getClass(), userId, feedids);

    }finally{
      if (em != null) {
        em.close();
      }
    }
    
    return java.util.Collections.emptyList();
  }
  
  public boolean isExistingBookmarkfeed(List<Bookmarkfeed> valuesFromDatabase, Object feedid) {
    if(valuesFromDatabase != null) {
      for(Bookmarkfeed bookmark:valuesFromDatabase) {

        if(feedid.equals(bookmark.getFeedid().getFeedid())) {
          return true;
        }
      }
    }
    return false;
  }
}
