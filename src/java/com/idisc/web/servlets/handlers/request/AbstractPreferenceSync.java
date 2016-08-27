package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.JpaContext;
import com.bc.util.XLogger;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feed_;
import com.idisc.pu.entities.Installation;
import com.idisc.web.exceptions.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.bc.jpa.dao.BuilderForSelect;

public abstract class AbstractPreferenceSync<E> 
        extends AbstractRequestHandler<Map<String, List>> {
    
  public abstract String getKey();
  
  public abstract String getInstallationColumnName();
  
  public abstract Class<E> getEntityClass();
  
  public abstract List<E> getPreferenceList(Installation installation);
  
  public abstract Feed getFeed(E e);
  
  public abstract E createFor(Installation installation, Feed feed);

  public final String[] getNames(String key) {
    return new String[] { "com.looseboxes.idisc.common." + key + ".feedids", "com.looseboxes.idisc.common." + getKey() + ".feedids_to_add", "com.looseboxes.idisc.common." + getKey() + ".feedids_to_remove" };
  }

  protected List getValues(String name, HttpServletRequest request) throws ValidationException {
   
    final List output;
    
    final String val = request.getParameter(name);
    
XLogger.getInstance().log(Level.FINER, "HttpServletRequest.getParameter('{0}') = {1}", this.getClass(), name, val);

    if (val == null || val.isEmpty()) {
      output = Collections.EMPTY_LIST;
    }else{
      JSONParser parser = new JSONParser();
      try {
        output = (JSONArray)parser.parse(val);
      }catch (ParseException e) {
        throw new ValidationException("Invalid format for required parameter: " + name, e);
      }
    }
XLogger.getInstance().log(Level.FINER, "{0} = {1}", this.getClass(), name, output);
    
    return output;
  }
  
  @Override
  public Map<String, List> execute(HttpServletRequest request) throws ServletException {
      
    Installation installation = getInstallationOrException(request);
    
    JpaContext jpaContext = getJpaContext(request);
      
    List<Feed> added = null;
    List<Feed> removed = null;
    
    final String key = getKey();
    
    final String [] names = getNames(key);
    
      for (String name : names) {
          
        List values = getValues(name, request);
        
        if ((values != null) && (!values.isEmpty())) {
            
          if (name.equals("com.looseboxes.idisc.common." + key + ".feedids_to_add")) {
              
            values = this.toIntegerList(values);
              
            added = add(jpaContext, values, installation);
            
          } else if (name.equals("com.looseboxes.idisc.common." + key + ".feedids_to_remove")) {
              
            values = this.toIntegerList(values);  
              
            removed = remove(jpaContext, values, installation);
            
          } else if (name.equals("com.looseboxes.idisc.common." + key + ".feedids")) {
              
//            syncAdd(jpaContext, values, installation);
            
//            syncRemove(jpaContext, values, installation);
            
          } else {
              
            throw new IllegalArgumentException("Expected any of: " + Arrays.toString(names) + ", found: " + name);
          }
        }
      }
    
    Map<String, List> output = new HashMap(3, 1.0f);

    if (added != null && !added.isEmpty()) {
        
      List<Feed> list = new ArrayList(added);
      
      output.put("com.looseboxes.idisc.common." + key + ".addedfeeds", list);
    }

    if (removed != null && !removed.isEmpty()) {
        
      List<Feed> list = new ArrayList(removed);
      
      output.put("com.looseboxes.idisc.common." + key + ".removedfeeds", list);
    }

    List<E> preferences = this.loadPreferenceList(jpaContext, installation);
    
    List<Integer> feedids = new LinkedList<>();
    
    if(preferences != null && !preferences.isEmpty()) {
      for (E preference : preferences) {
        feedids.add(getFeed(preference).getFeedid());
      }
    }
    
    if (!feedids.isEmpty()) {
      output.put("com.looseboxes.idisc.common." + key + ".feedids", feedids);
    }
    
XLogger.getInstance().log(Level.FINE, "Output: {0}", this.getClass(), output);    
    
XLogger.getInstance().log(Level.FINER, "Added: {0}\nRemoved: {1}\nAll feedids: {2}",
        this.getClass(), added, removed, feedids);
    
    return output;
  }
  
  protected List<Feed> add(JpaContext jpaContext, 
          List<Integer> feedidsToAdd, Installation installation) {
      
XLogger.getInstance().log(Level.FINE, "To add: {0}", this.getClass(), feedidsToAdd);

    this.requireNonNullOrEmpty(feedidsToAdd);
      
    List<E> preferences = this.loadPreferenceList(jpaContext, installation);
    
    List<Feed> feeds = this.selectByIds(jpaContext, feedidsToAdd);
    
    EntityManager em = 
            jpaContext.getEntityManager(getEntityClass());
    
    List<Feed> added = null;
    
    try {
        
      EntityTransaction t = em.getTransaction();
      
      try {
          
        t.begin();
        
        for (Feed feed : feeds) {
            
          if (!isRepresentedIn(preferences, feed)) {
              
            E pref = createFor(installation, feed);
            
            em.persist(pref);
            
XLogger.getInstance().log(Level.FINER, "Persisted: {0}", this.getClass(), pref);

            if (added == null) {
              added = new ArrayList(feeds.size());
            }
            
            added.add(feed);
          }
        }
        
        t.commit();
        
      } finally {
        if (t.isActive()) {
          t.rollback();
        }
      }
    } finally {
      em.close();
    }
    
    return added == null ? Collections.EMPTY_LIST : added;
  }
  
  protected List<Feed> remove(JpaContext jpaContext, List<Integer> feedidsToRemove, Installation installation) {

XLogger.getInstance().log(Level.FINE, "To remove: {0}", this.getClass(), feedidsToRemove);
      
    this.requireNonNullOrEmpty(feedidsToRemove);

    final List<Feed> feeds = this.selectByIds(jpaContext, feedidsToRemove);
    
    this.requireNonNullOrEmpty(feeds);
    
    final int updateCount = this.remove(jpaContext, feeds);
    
    List<Feed> output;
    
    if(updateCount == feeds.size()) {
        
      output = feeds;
        
    }else if(updateCount > 0) {
        
      output = new ArrayList<>(updateCount);
      
      List<E> preferences = this.loadPreferenceList(jpaContext, installation);
      
      if(preferences != null && !preferences.isEmpty()) {
          
        for(Feed feed:feeds) {
      
          if(this.isRepresentedIn(preferences, feed)) {
            
            output.add(feed);
          }
        }
      }
    }else{
      
      output = Collections.EMPTY_LIST;
    }
    
    return output;
  }
  
  protected int remove(JpaContext jpaContext, List<Feed> feeds) {
      
    this.requireNonNullOrEmpty(feeds);
    
    final Class<E> entityClass = this.getEntityClass();

    final EntityManager em = jpaContext.getEntityManager(entityClass);
    
    try {
        
      EntityTransaction t = em.getTransaction();
        
      try{
          
        t.begin();
        
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaDelete<E> cd = cb.createCriteriaDelete(entityClass);

        Root<E> entity = cd.from(entityClass);
        
        List<Predicate> restrictions = new ArrayList<>(feeds.size());
        
        for(Feed feed:feeds) {
            
            Predicate restriction = cb.equal(entity.get(Feed_.feedid.getName()), feed); 

            restrictions.add(restriction);
        }
        
        Predicate where = cb.or(restrictions.toArray(new Predicate[0]));
        
        cd = cd.where(where); 
        
        Query query = em.createQuery(cd); 

        final int updateCount = query.executeUpdate();
        
        t.commit();
        
        return updateCount;
        
      }finally{
        if(t.isActive()) {
          t.rollback();
        }  
      }  
    } finally {
      em.close();
    }
  }
  
  private List<E> loadPreferenceList(JpaContext jpaContext, Installation installation) {
      
      List<E> prefs;
      
      try(BuilderForSelect<E> qb = jpaContext.getBuilderForSelect(this.getEntityClass())) {
       
          prefs = qb.from(this.getEntityClass())
                  .where(this.getInstallationColumnName(), installation)
                  .createQuery().getResultList();
      }
      
      return prefs;
  }

  private List<Feed> selectByIds(JpaContext jpaContext, List<Integer> feedids) {
      
    List<Feed> feeds;
    
    try(BuilderForSelect<Feed> qb = jpaContext.getBuilderForSelect(Feed.class)) {
        
        feeds = qb.from(Feed.class)
                .where(Feed_.feedid.getName(), feedids.toArray())
                .createQuery().getResultList();
    }
    
    return feeds;
  }
  
  private boolean isRepresentedIn(List<E> prefs, Feed feed) {
    boolean output = false;
    for (E pref : prefs) {
      if (getFeed(pref).equals(feed)) {
        output = true;
        break;
      }
    }
XLogger.getInstance().log(Level.FINER, "Is already a preference: {0}, feed: {1}", this.getClass(), output, feed);
    return output;
  }
  
  private Feed getFeedContainedIn(List<E> prefs, Integer feedid) {
    Feed feed = null;
    for(E pref:prefs) {
      Feed f = getFeed(pref);
      if(f.getFeedid().equals(feedid)) {
        feed = f;
        break;
      }
    }
    return feed;
  }
  
  private boolean isRepresentedIn(List<E> prefs, Integer feedid){
    
    boolean output = this.getFeedContainedIn(prefs, feedid) != null;
    
XLogger.getInstance().log(Level.FINER, "Is already a preference feedid: {0}, feedid: {1}", this.getClass(), output, feedid);    
    return output;
  }
  
  private boolean contains(List<Integer> feedids, Integer feedid){
    boolean found = false;
    for (Object o : feedids) {
      try {
        found = ((Integer)o).equals(feedid);
      } catch (Exception ignored) {
        found = Integer.parseInt(o.toString()) == feedid;
      }
      if (found) {
        break;
      }
    }
XLogger.getInstance().log(Level.FINER, "Contains: {0}, feedid: {1}, list: {1}", this.getClass(), found, feedid, feedids);    
    return found;
  }
  
  private List<Integer> toIntegerList(List list) {
    this.requireNonNullOrEmpty(list);
    List<Integer> output = new ArrayList(list);
    for(Object o:list) {
      output.add(this.getInteger(o));
    }
    return Collections.unmodifiableList(output);  
  }

  private Integer getInteger(Object o) {
    try {
      return (Integer)o;
    } catch (Exception ignored) {}
    return Integer.valueOf(o.toString());
  }
  
  private void requireNonNullOrEmpty(Collection c) {
    Objects.requireNonNull(c);
    if(c.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }
}
/**
 * 
  protected List<Feed> syncAdd(JpaContext jpaContext, 
          JSONArray addedFeedIds, Installation installation) throws Exception {
      
    List<E> preferences = this.loadPreferenceList(jpaContext, installation);
    
    Iterator valuesIter = addedFeedIds.iterator();
    
    JSONArray feedIdsToAdd = new JSONArray();
    
    while (valuesIter.hasNext()) {
        
      Object feedid = valuesIter.next();
      
      if (!isRepresentedIn(preferences, getInteger(feedid))) {
        feedIdsToAdd.add(feedid);
      }
    }

    List<Feed> added;
    if (!feedIdsToAdd.isEmpty()) {
      added = add(jpaContext, feedIdsToAdd, installation);
    } else {
      added = Collections.emptyList();
    }
    
    return added;
  }
  
  protected List<Feed> syncRemove(JpaContext jpaContext, JSONArray removedFeedIds, Installation installation) throws Exception {
      
    List<E> preferences = this.loadPreferenceList(jpaContext, installation);
    
    Iterator<E> iter = preferences.iterator();
    
    JSONArray feedidsToRemove = new JSONArray();
    
    while (iter.hasNext()) {
        
      E pref = iter.next();
      
      Integer feedid = getFeed(pref).getFeedid();
      
      if (contains(removedFeedIds, feedid)) {
          
        feedidsToRemove.add(feedid);
      }
    }
    
    List<Feed> removed;
    if (!feedidsToRemove.isEmpty()){
      removed = remove(jpaContext, feedidsToRemove, installation);
    }else {
      removed = Collections.emptyList();
    }
    return removed;
  }

  protected List<Feed> executeRemove(JpaContext jpaContext, JSONArray feedids, Installation installation)
    throws Exception {
      
    List<E> preferences = this.loadPreferenceList(jpaContext, installation);
    
    EntityManager em = jpaContext.getEntityManager(getEntityClass());
    
    List<Feed> feeds = null;
    
    try {
        
      EntityTransaction t = em.getTransaction();
      
      try {
          
        t.begin();
        
        Iterator<E> iter = preferences.iterator();
        
        while (iter.hasNext()) {
            
          E pref = iter.next();
          
          Feed feed = getFeed(pref);
          
          if (contains(feedids, feed.getFeedid())) {
              
            iter.remove();
            
            if (feeds == null) {
              feeds = new ArrayList(feedids.size());
            }
            
            feeds.add(feed);
          }
        }
        
        em.merge(installation);
        
        t.commit();

      } finally {
        if (t.isActive()) {
          t.rollback();
        }
      }
    } finally {
      em.close();
    }
    
    return feeds == null ? Collections.EMPTY_LIST : feeds;
  }
 * 
 */