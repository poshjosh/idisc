package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import com.bc.jpa.JpaContext;
import javax.servlet.http.HttpServletRequest;

public class Removebookmarkfeedids extends UpdateUserPreferenceFeedids
{
  @Override
  public String getRequestParameterName()
  {
    return "com.looseboxes.idisc.removebookmarkfeedids.feedids";
  }
  
  @Override
  protected List execute(HttpServletRequest request, String name, JSONArray feedids, Installation installation)
    throws Exception
  {
    JpaContext factory = this.getJpaContext(request);
    
    EntityManager em = factory.getEntityManager(Bookmarkfeed.class);
    
    try
    {
      em.getTransaction().begin();
      
      XLogger.getInstance().log(Level.FINE, "Removing {0} bookmarks for user: {1}", getClass(), Integer.valueOf(feedids.size()), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress());
      

      List<Bookmarkfeed> valuesFromDatabase = installation.getBookmarkfeedList();
      
      Object feedid;
      for (Iterator i$ = feedids.iterator(); i$.hasNext();) { 
          
        feedid = i$.next();
        
        if (feedid != null)
        {


          for (Bookmarkfeed bookmark : valuesFromDatabase)
          {
            if (feedid.equals(bookmark.getFeedid().getFeedid()))
            {
              em.remove(bookmark);
              
              break;
            } }
        }
      }
      em.getTransaction().commit();
      
      XLogger.getInstance().log(Level.FINE, "Removed for user: {0}, bookmark feedids: {1}", getClass(), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress(), feedids);

    }
    finally
    {
      if (em != null) {
        em.close();
      }
    }
    
    return Collections.emptyList();
  }
}
