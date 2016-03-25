package com.idisc.web.servlets.handlers;

import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import com.idisc.web.exceptions.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Josh
 */
public abstract class AbstractPreferenceSync<E> extends BaseRequestHandler<Map> {

    public abstract String getKey();
    
    public abstract Class<E> getEntityClass();
    
    public abstract List<E> getFeedList(Installation installation);
    
    public abstract com.idisc.pu.entities.Feed getFeed(E e);
    
    public abstract E createFor(Installation installation, com.idisc.pu.entities.Feed feed);
    
    public String[] getNames() {
        return new String[]{
                "com.looseboxes.idisc.common."+this.getKey()+".feedids",
                "com.looseboxes.idisc.common."+this.getKey()+".feedids_to_add",
                "com.looseboxes.idisc.common."+this.getKey()+".feedids_to_remove"};
    }

    @Override
    public boolean isProtected() {
        return true;
    }
    
    protected JSONArray getValues(String name, HttpServletRequest request) throws ValidationException {
        String str = request.getParameter(name);
        if(str == null || str.isEmpty()) {
// In our case values not mandatory            
            return null;
//            throw new ValidationException("Required parameter "+name+" is missing");
        }
        JSONParser parser = new JSONParser();
        try{
            JSONArray json = (JSONArray)parser.parse(str); 
            return json;
        }catch(ParseException e) {
            throw new ValidationException("Invalid format for required parameter: "+name, e);
        }
    }

    @Override
    public Map execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException {
        
        User user = this.getUser(request);
        
        // Create the installation record if it doesn't exist
        boolean create = true;
        Installation installation = AppInstallation.getEntity(request, user, create);

        String [] names = this.getNames();
        
        List<com.idisc.pu.entities.Feed> added = null;
        List<com.idisc.pu.entities.Feed> removed = null;
        
        try{
            for(String name:names) {

                JSONArray values = this.getValues(name, request);
                
                if(values == null || values.isEmpty()) {
                    continue;
                }
                
                if(name.equals("com.looseboxes.idisc.common."+this.getKey()+".feedids_to_add")) {
                    this.executeAdd(values, installation); 
                }else if(name.equals("com.looseboxes.idisc.common."+this.getKey()+".feedids_to_remove")) {
                    this.executeRemove(values, installation); 
                }else if(name.equals("com.looseboxes.idisc.common."+this.getKey()+".feedids")) {
                    added = this.syncAdd(values, installation); 
                    removed = this.syncRemove(values, installation);
                }else{
                    throw new IllegalArgumentException("Expected any of: "+Arrays.toString(this.getNames())+", found: "+name);
                }
            }
        }catch(ValidationException e) {
            
            throw e;
            
        }catch(Exception e) {
            
            throw new ServletException("Error updating values", e);
        }

        Map output = new HashMap(3, 1.0f);
        
        EntityController<com.idisc.pu.entities.Feed, Integer> ecFeed = 
                this.getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
        
        if(added != null && !added.isEmpty()) {
            List list = new ArrayList(added.size());
            // Add the list not the 'added'
            output.put("com.looseboxes.idisc.common."+this.getKey()+".addedfeeds", list);
            for(com.idisc.pu.entities.Feed feed:added) {
                Map map = ecFeed.toMap(feed, false);
                list.add(map);
            }
        }
        if(removed != null && !removed.isEmpty()) {
            List list = new ArrayList(removed.size());
            // Add the list not the 'removed'
            output.put("com.looseboxes.idisc.common."+this.getKey()+".removedfeeds", list);
            for(com.idisc.pu.entities.Feed feed:removed) {
                Map map = ecFeed.toMap(feed, false);
                list.add(map);
            }
        }
        
        List<E> preferences = this.getFeedList(installation);
        
        List<Integer> feedids = new ArrayList<>();
        for(E preference:preferences) {
            feedids.add(this.getFeed(preference).getFeedid());
        }
        
        if(!feedids.isEmpty()) {
            output.put("com.looseboxes.idisc.common."+this.getKey()+".feedids", feedids);
        }
        
        return output;
    }
    
    protected List<com.idisc.pu.entities.Feed> executeAdd(JSONArray feedidsToAdd, Installation installation) throws Exception {

        Object [] feedids = feedidsToAdd.toArray(); 
        
        List<E> prefs = this.getFeedList(installation);
        
        EntityController<com.idisc.pu.entities.Feed, Integer> ecFeed = 
                this.getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
        
        List<com.idisc.pu.entities.Feed> feeds = ecFeed.select("feedid", feedids);
        
//        EntityController<Bookmarkfeed, Integer> ecBookmark = this.getEntityController(Bookmarkfeed.class, Integer.class);
        
        EntityManager em = this.getEntityManager(this.getEntityClass());
        
        List<com.idisc.pu.entities.Feed> added = null;
        
        try{
            
            EntityTransaction t = em.getTransaction();
            
            try{

                t.begin();
                
                for(com.idisc.pu.entities.Feed feed:feeds) {

                    if(!this.isRepresentedIn(prefs, feed)) {
                        
                        E pref = this.createFor(installation, feed);
                    
                        em.persist(pref);
                        
                        if(added == null) {
                            added = new ArrayList<>(feeds.size());
                        }
                        
                        added.add(feed);
                    }
                }
                
                t.commit();
                
            }finally{
                if(t.isActive()) {
                    t.rollback();
                }
            }
        }finally{
            em.close();
        }

        return added == null ? Collections.EMPTY_LIST : added;
    }

    protected List<com.idisc.pu.entities.Feed> executeRemove(JSONArray feedidsToRemove, Installation installation) throws Exception {
        
//        Object [] feedids = values.toArray();
        
//        EntityController<Feed, Integer> ecFeed = this.getEntityController(Feed.class, Integer.class);
        
//        List<Feed> feeds = ecFeed.select("feedid", feedids);
        
//        EntityController<Bookmarkfeed, Integer> ecBookmark = this.getEntityController(Bookmarkfeed.class, Integer.class);
        
        List<E> prefs = this.getFeedList(installation);
        
        EntityManager em = this.getEntityManager(this.getEntityClass());
        
        List<com.idisc.pu.entities.Feed> removed = null;
        
        try{
            
            EntityTransaction t = em.getTransaction();
            
            try{

                t.begin();
                
                Iterator<E> iter = prefs.iterator();

                while(iter.hasNext()) {
                    
                    E pref = iter.next();
                    
                    com.idisc.pu.entities.Feed feed = this.getFeed(pref);
                    
                    if(this.contains(feedidsToRemove, feed.getFeedid())) {
                    
                        iter.remove();
                        
                        if(removed == null) {
                            removed = new ArrayList<>(feedidsToRemove.size());
                        }
                        
                        removed.add(feed);
                    }
                }

                em.merge(installation);
                
                t.commit();
                
            }finally{
                if(t.isActive()) {
                    t.rollback();
                }
            }
        }finally{
            em.close();
        }

        // Getting here means success
        return removed == null ? Collections.EMPTY_LIST : removed;
    }

    protected List<com.idisc.pu.entities.Feed> syncAdd(JSONArray addedFeedIds, Installation installation) throws Exception {
        
        List<E> prefs = this.getFeedList(installation);
        
        Iterator valuesIter = addedFeedIds.iterator();

        JSONArray feedIdsToAdd = new JSONArray();
        
        while(valuesIter.hasNext()) {

            Object feedid = valuesIter.next();
            
            if(!this.isRepresentedIn(prefs, this.getInteger(feedid))) {
                feedIdsToAdd.add(feedid);
            }
        }

        List<com.idisc.pu.entities.Feed> added;
        if(!feedIdsToAdd.isEmpty()) {
            added = this.executeAdd(feedIdsToAdd, installation);
        }else{
            added = Collections.emptyList();
        }

        // Getting here means success
        return added;
    }

    protected List<com.idisc.pu.entities.Feed> syncRemove(JSONArray removedFeedIds, Installation installation) throws Exception {
        
        List<E> prefs = this.getFeedList(installation);
        
        Iterator<E> iter = prefs.iterator();

        JSONArray feedidsToRemove = new JSONArray();
        
        while(iter.hasNext()) {

            E pref = iter.next();

            Integer feedid = this.getFeed(pref).getFeedid();

            if(this.contains(removedFeedIds, feedid)) {
                feedidsToRemove.add(feedid);
            }
        }

        List<com.idisc.pu.entities.Feed> removed;
        
        if(!feedidsToRemove.isEmpty()) {

            removed = this.executeRemove(feedidsToRemove, installation);
            
        }else{
            
            removed = Collections.emptyList();
        }

        // Getting here means success
        return removed;
    }

    private boolean contains(List<E> prefs, E pref) {
        
        return this.isRepresentedIn(prefs, this.getFeed(pref));
    }

    private boolean isRepresentedIn(List<E> prefs, com.idisc.pu.entities.Feed feed) {
        
        for(E e:prefs) {
            if(this.getFeed(e).equals(feed)) {
                return true;
            }
        }
        
        return false;
    }

    private boolean isRepresentedIn(List<E> prefs, Integer feedid) {
        
        for(E e:prefs) {
            if(this.getFeed(e).getFeedid().equals(feedid)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean contains(JSONArray feedids, Integer feedid) {

        for(Object o:feedids) {
            boolean equals;
            try{
                equals = ((Integer)o).equals(feedid);
            }catch(Exception e) {
                equals = Integer.parseInt(o.toString()) == feedid; 
            }
            if(equals) {
                return true;
            }
        }
        
        return false;
    }
    
    private Integer getInteger(Object o) {
        try{
            return ((Integer)o);
        }catch(Exception e) {
            return Integer.valueOf(o.toString());
        }
    }

    private int getInt(Object o) {
        try{
            return ((Integer)o);
        }catch(Exception e) {
            return Integer.parseInt(o.toString());
        }
    }
    
    public EntityManager getEntityManager(Class entityClass) {
        return IdiscApp.getInstance().getControllerFactory().getEntityManager(entityClass);
    }
    
    public <X, I> EntityController<X, I> getEntityController(Class<X> ec, Class<I> ic) {
        return IdiscApp.getInstance().getControllerFactory().getEntityController(ec, ic);
    }
}

