package com.idisc.web.servlets.handlers.request;

import java.util.logging.Logger;
import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import com.bc.jpa.context.JpaContext;
import javax.servlet.http.HttpServletRequest;

public class Removefavoritefeedids extends UpdateUserPreferenceFeedids
{
    private transient static final Logger LOG = Logger.getLogger(Removefavoritefeedids.class.getName());
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
      
      if(LOG.isLoggable(Level.FINE)){
         LOG.log(Level.FINE, "Removing {0} favorites for user: {1}", new Object[]{ Integer.valueOf(feedids.size()),  installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress()});
      }
      

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
      
      if(LOG.isLoggable(Level.FINE)){
         LOG.log(Level.FINE, "Removed favorites for user: {0}, feedids: {1}", 
         new Object[]{ getClass(), installation.getFeeduserid() == null ? null : installation.getFeeduserid().getEmailAddress(), feedids});
      }

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
