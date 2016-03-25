package com.idisc.web.servlets.handlers;

import com.bc.jpa.ControllerFactory;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;

/**
 * @author Josh
 */
public class Removebookmarkfeedids extends UpdateUserPreferenceFeedids {
    
    @Override
    public String getRequestParameterName() {
        return "com.looseboxes.idisc.removebookmarkfeedids.feedids";
    }

    @Override
    protected List execute(String name, JSONArray feedids, Installation installation) throws Exception {
                
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
        
        EntityManager em = factory.getEntityManager(Bookmarkfeed.class);
        
        try{
            
            em.getTransaction().begin();

XLogger.getInstance().log(Level.FINE, "Removing {0} bookmarks for user: {1}", 
this.getClass(), feedids.size(), installation.getFeeduserid()==null?null:installation.getFeeduserid().getEmailAddress());

            List<Bookmarkfeed> valuesFromDatabase = installation.getBookmarkfeedList();

            for(Object feedid:feedids) {

                if(feedid == null) {
                    continue;
                }
                
                for(Bookmarkfeed bookmark:valuesFromDatabase) {
                    
                    if(feedid.equals(bookmark.getFeedid().getFeedid())) {

                        em.remove(bookmark);
                        
                        break; 
                    }
                }
            }

            em.getTransaction().commit();

XLogger.getInstance().log(Level.FINE, "Removed for user: {0}, bookmark feedids: {1}", this.getClass(), 
installation.getFeeduserid()==null?null:installation.getFeeduserid().getEmailAddress(), feedids);
            
        }finally{
            
            if(em != null) {
                em.close();
            }
        }
        
        return Collections.emptyList();
    }
}
