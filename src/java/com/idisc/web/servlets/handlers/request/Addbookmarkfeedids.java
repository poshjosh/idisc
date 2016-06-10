package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;

public class Addbookmarkfeedids extends UpdateUserPreferenceFeedids
{
  @Override
  public String getRequestParameterName()
  {
    return "com.looseboxes.idisc.addbookmarkfeedids.feedids";
  }
  
  @Override
  protected List execute(String name, JSONArray feedids, Installation installation)
    throws Exception
  {
    ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
    
    EntityManager em = factory.getEntityManager(Bookmarkfeed.class);
    
    try
    {
      em.getTransaction().begin();
      
      XLogger.getInstance().log(Level.FINE, "Adding {0} bookmarks for user: {1}", getClass(), Integer.valueOf(feedids.size()), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress());
      

      List<Bookmarkfeed> valuesFromDatabase = installation.getBookmarkfeedList();
      
      EntityController<Feed, Integer> feedCtrl = factory.getEntityController(Feed.class, Integer.class);
      


      outer:
      for (Object feedid : feedids)
      {
        if (feedid != null)
        {

          for(Bookmarkfeed bookmark:valuesFromDatabase) {
            if(feedid.equals(bookmark.getFeedid().getFeedid())) {
              // bookmark alread exists
              continue outer;
            }
          }

          Feed feed = (Feed)feedCtrl.find(Integer.valueOf(feedid.toString()));
          
          Bookmarkfeed bookmark = new Bookmarkfeed();
          bookmark.setDatecreated(new Date());
          bookmark.setFeedid(feed);
          bookmark.setInstallationid(installation);
          
          em.persist(bookmark);
        } }
      label179:
      em.getTransaction().commit();
      
      XLogger.getInstance().log(Level.FINE, "Added for user: {0}, favorite feedids: {1}", getClass(), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress(), feedids);

    }
    finally
    {
      if (em != null) {
        em.close();
      }
    }
    

    return java.util.Collections.emptyList();
  }
}
