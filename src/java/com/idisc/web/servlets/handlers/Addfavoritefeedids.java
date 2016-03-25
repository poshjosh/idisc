package com.idisc.web.servlets.handlers;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;

/**
 * @author Josh
 */
public class Addfavoritefeedids extends UpdateUserPreferenceFeedids {
    
    @Override
    public String getRequestParameterName() {
        return "com.looseboxes.idisc.addfavoritefeedids.feedids";
    }

    @Override
    protected List execute(String name, JSONArray feedids, Installation installation) throws Exception {
                
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();

        EntityManager em = factory.getEntityManager(Favoritefeed.class);
        
        try{
            
            em.getTransaction().begin();

XLogger.getInstance().log(Level.FINE, "Adding {0} favorites for user: {1}", 
this.getClass(), feedids.size(), installation.getFeeduserid()==null?null:installation.getFeeduserid().getEmailAddress());

            List<Favoritefeed> valuesFromDatabase = installation.getFavoritefeedList();
            
            EntityController<com.idisc.pu.entities.Feed, Integer> feedCtrl = 
                    factory.getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
        
            outer:
            for(Object feedid:feedids) {

                if(feedid == null) {
                    continue;
                }
                
                for(Favoritefeed favorite:valuesFromDatabase) {
                    if(feedid.equals(favorite.getFeedid().getFeedid())) {
                    // bookmark alread exists
                        continue outer;
                    }
                }
                
                com.idisc.pu.entities.Feed feed = feedCtrl.find(Integer.valueOf(feedid.toString()));

                Favoritefeed favorite = new Favoritefeed();
                favorite.setDatecreated(new Date());
                favorite.setFeedid(feed);
                favorite.setInstallationid(installation);
                
                em.persist(favorite);
            }

            em.getTransaction().commit();

XLogger.getInstance().log(Level.FINE, "Added for user: {0}, favorite feedids: {1}", this.getClass(), 
installation.getFeeduserid()==null?null:installation.getFeeduserid().getEmailAddress(), feedids);
            
        }finally{
            
            if(em != null) {
                em.close();
            }
        }
        
        // Getting here means we are successful, We return an empty list as no failures occured
        return Collections.emptyList();
    }
}