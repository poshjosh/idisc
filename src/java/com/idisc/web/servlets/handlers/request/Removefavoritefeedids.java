package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import com.bc.jpa.JpaContext;
import javax.servlet.http.HttpServletRequest;

public class Removefavoritefeedids extends UpdateUserPreferenceFeedids
{
  @Override
  public String getRequestParameterName()
  {
    return "com.looseboxes.idisc.removefavoritefeedids.feedids";
  }
  
  @Override
  protected List execute(HttpServletRequest request, String name, JSONArray feedids, Installation installation)
    throws Exception
  {
    JpaContext factory = this.getJpaContext(request);
    
    EntityManager em = factory.getEntityManager(Favoritefeed.class);
    
    try
    {
      em.getTransaction().begin();
      
      XLogger.getInstance().log(Level.FINE, "Removing {0} favorites for user: {1}", getClass(), Integer.valueOf(feedids.size()), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress());
      

      List<Favoritefeed> valuesFromDatabase = installation.getFavoritefeedList();
      
      Object feedid;

      for (Iterator i$ = feedids.iterator(); i$.hasNext();) { 
          
        feedid = i$.next();
        
        if (feedid != null)
        {


          for (Favoritefeed favorite : valuesFromDatabase)
          {
            if (feedid.equals(favorite.getFeedid().getFeedid()))
            {
              em.remove(favorite);
              
              break;
            } }
        }
      }
      em.getTransaction().commit();
      
      XLogger.getInstance().log(Level.FINE, "Added for user: {0}, bookmark feedids: {1}", getClass(), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress(), feedids);

    }
    finally
    {
      if (em != null) {
        em.close();
      }
    }
    
    return Collections.EMPTY_LIST;
  }
}
