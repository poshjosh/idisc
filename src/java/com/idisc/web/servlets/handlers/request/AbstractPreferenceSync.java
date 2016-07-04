package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
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
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class AbstractPreferenceSync<E> extends AbstractRequestHandler<Map<String, List>>
{
  public abstract String getKey();
  
  public abstract Class<E> getEntityClass();
  
  public abstract List<E> getFeedList(Installation paramInstallation);
  
  public abstract Feed getFeed(E paramE);
  
  public abstract E createFor(Installation paramInstallation, Feed paramFeed);
  
  public String[] getNames()
  {
    return new String[] { "com.looseboxes.idisc.common." + getKey() + ".feedids", "com.looseboxes.idisc.common." + getKey() + ".feedids_to_add", "com.looseboxes.idisc.common." + getKey() + ".feedids_to_remove" };
  }

  @Override
  public boolean isProtected()
  {
    return true;
  }
  
  protected JSONArray getValues(String name, HttpServletRequest request) throws ValidationException {
    String str = request.getParameter(name);
    if ((str == null) || (str.isEmpty()))
    {
      return null;
    }
    
    JSONParser parser = new JSONParser();
    try {
      return (JSONArray)parser.parse(str);
    }
    catch (ParseException e) {
      throw new ValidationException("Invalid format for required parameter: " + name, e);
    }
  }
  
  @Override
  public Map<String, List> execute(HttpServletRequest request) throws ServletException
  {
    boolean create = true;
    Installation installation = getInstallation(request, create);
    if(installation == null) {
      throw new ServletException("You are not authorized to perform the requested operation");
    }
    String[] names = getNames();
    
    List<Feed> added = null;
    List<Feed> removed = null;
    
    String key = getKey();
    try
    {
      for (String name : names)
      {
        JSONArray values = getValues(name, request);
        
        if ((values != null) && (!values.isEmpty()))
        {
          if (name.equals("com.looseboxes.idisc.common." + key + ".feedids_to_add")) {
            executeAdd(values, installation);
          } else if (name.equals("com.looseboxes.idisc.common." + key + ".feedids_to_remove")) {
            executeRemove(values, installation);
          } else if (name.equals("com.looseboxes.idisc.common." + key + ".feedids")) {
            added = syncAdd(values, installation);
            removed = syncRemove(values, installation);
          } else {
            throw new IllegalArgumentException("Expected any of: " + Arrays.toString(getNames()) + ", found: " + name);
          }
        }
      }
    } catch (ValidationException e) {
      throw e;
    }
    catch (Exception e)
    {
      throw new ServletException("Error updating values", e);
    }
    
    Map<String, List> output = new HashMap(3, 1.0F);
    List list;
    if ((added != null) && (!added.isEmpty()))
    {
      list = new ArrayList(added.size());
      
      output.put("com.looseboxes.idisc.common." + key + ".addedfeeds", list);
      
      for (Feed feed : added)
      {
        list.add(feed);
      }
    }

    if ((removed != null) && (!removed.isEmpty()))
    {
      list = new ArrayList(removed.size());
      
      output.put("com.looseboxes.idisc.common." + key + ".removedfeeds", list);
      
      for (Feed feed : removed)
      {
        list.add(feed);
      }
    }
    
    List<E> preferences = getFeedList(installation);
    
    List<Integer> feedids = new ArrayList();
    
    for (E preference : preferences)
    {
      feedids.add(getFeed(preference).getFeedid());
    }
    
    if (!feedids.isEmpty())
    {
      output.put("com.looseboxes.idisc.common." + key + ".feedids", feedids);
    }
    
    return output;
  }
  
  protected List<Feed> executeAdd(JSONArray feedidsToAdd, Installation installation) throws Exception
  {
    Object[] feedids = feedidsToAdd.toArray();
    
    List<E> prefs = getFeedList(installation);
    
    EntityController<Feed, Integer> ecFeed = getEntityController(Feed.class, Integer.class);
    
    List<Feed> feeds = ecFeed.select("feedid", feedids);
    
    EntityManager em = getEntityManager(getEntityClass());
    
    List<Feed> added = null;
    
    try
    {
      EntityTransaction t = em.getTransaction();
      
      try
      {
        t.begin();
        
        for (Feed feed : feeds)
        {
          if (!isRepresentedIn(prefs, feed))
          {
            E pref = createFor(installation, feed);
            
            em.persist(pref);
            
            if (added == null) {
              added = new ArrayList(feeds.size());
            }
            
            added.add(feed);
          }
          
        }
      }
      finally
      {
        if (t.isActive()) {
          t.rollback();
        }
      }
    }
    finally
    {
      em.close();
    }
    
    return added == null ? Collections.EMPTY_LIST : added;
  }
  







  protected List<Feed> executeRemove(JSONArray feedidsToRemove, Installation installation)
    throws Exception
  {
    List<E> prefs = getFeedList(installation);
    
    EntityManager em = getEntityManager(getEntityClass());
    
    List<Feed> removed = null;
    
    try
    {
      EntityTransaction t = em.getTransaction();
      
      try
      {
        t.begin();
        
        Iterator<E> iter = prefs.iterator();
        
        while (iter.hasNext())
        {
          E pref = iter.next();
          
          Feed feed = getFeed(pref);
          
          if (contains(feedidsToRemove, feed.getFeedid()))
          {
            iter.remove();
            
            if (removed == null) {
              removed = new ArrayList(feedidsToRemove.size());
            }
            
            removed.add(feed);
          }
        }
        
        em.merge(installation);

      }
      finally
      {
        if (t.isActive()) {
          t.rollback();
        }
      }
    }
    finally
    {
      em.close();
    }
    

    return removed == null ? Collections.EMPTY_LIST : removed;
  }
  
  protected List<Feed> syncAdd(JSONArray addedFeedIds, Installation installation) throws Exception
  {
    List<E> prefs = getFeedList(installation);
    
    Iterator valuesIter = addedFeedIds.iterator();
    
    JSONArray feedIdsToAdd = new JSONArray();
    
    while (valuesIter.hasNext())
    {
      Object feedid = valuesIter.next();
      
      if (!isRepresentedIn(prefs, getInteger(feedid))) {
        feedIdsToAdd.add(feedid);
      }
    }

    List<Feed> added;
    if (!feedIdsToAdd.isEmpty()) {
      added = executeAdd(feedIdsToAdd, installation);
    } else {
      added = Collections.emptyList();
    }
    

    return added;
  }
  
  protected List<Feed> syncRemove(JSONArray removedFeedIds, Installation installation) throws Exception
  {
    List<E> prefs = getFeedList(installation);
    
    Iterator<E> iter = prefs.iterator();
    
    JSONArray feedidsToRemove = new JSONArray();
    
    while (iter.hasNext())
    {
      E pref = iter.next();
      
      Integer feedid = getFeed(pref).getFeedid();
      
      if (contains(removedFeedIds, feedid)) {
        feedidsToRemove.add(feedid);
      }
    }
    
    List<Feed> removed;
    if (!feedidsToRemove.isEmpty())
    {
      removed = executeRemove(feedidsToRemove, installation);
    }
    else
    {
      removed = Collections.emptyList();
    }
    

    return removed;
  }
  
  private boolean contains(List<E> prefs, E pref)
  {
    return isRepresentedIn(prefs, getFeed(pref));
  }
  
  private boolean isRepresentedIn(List<E> prefs, Feed feed)
  {
    for (E e : prefs) {
      if (getFeed(e).equals(feed)) {
        return true;
      }
    }
    
    return false;
  }
  
  private boolean isRepresentedIn(List<E> prefs, Integer feedid)
  {
    for (E e : prefs) {
      if (getFeed(e).getFeedid().equals(feedid)) {
        return true;
      }
    }
    
    return false;
  }
  
  private boolean contains(JSONArray feedids, Integer feedid)
  {
    for (Object o : feedids) {
      boolean equals;
      try {
        equals = ((Integer)o).equals(feedid);
      } catch (Exception e) {
        equals = Integer.parseInt(o.toString()) == feedid.intValue();
      }
      if (equals) {
        return true;
      }
    }
    
    return false;
  }
  
  private Integer getInteger(Object o) {
    try {
      return (Integer)o;
    } catch (Exception e) {}
    return Integer.valueOf(o.toString());
  }
  
  private int getInt(Object o)
  {
    try {
      return ((Integer)o).intValue();
    } catch (Exception e) {}
    return Integer.parseInt(o.toString());
  }
  
  public EntityManager getEntityManager(Class entityClass)
  {
    return IdiscApp.getInstance().getJpaContext().getEntityManager(entityClass);
  }
  
  public <X, I> EntityController<X, I> getEntityController(Class<X> ec, Class<I> ic) {
    return IdiscApp.getInstance().getJpaContext().getEntityController(ec, ic);
  }
}
