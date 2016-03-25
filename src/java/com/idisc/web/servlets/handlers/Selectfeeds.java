package com.idisc.web.servlets.handlers;

import com.bc.jpa.EntityController;
import com.bc.util.IntegerArray;
import com.bc.util.XLogger;
import com.idisc.core.EntityJsonFormat;
import com.idisc.core.FeedFrequency;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Site;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
import org.eclipse.persistence.annotations.BatchFetchType;














public class Selectfeeds
  extends Select<Feed>
{
  public boolean isProtected()
  {
    return false;
  }
  
  protected Class<Feed> getEntityClass()
  {
    return Feed.class;
  }
  


  protected Predicate[] buildPredicates(CriteriaBuilder cb, Root<Feed> root, Date after, String[] columnNames, String toFind)
  {
    List<Predicate> predicates = new ArrayList();
    
    if (toFind == null)
    {
      XLogger.getInstance().log(Level.FINER, "Select feeds after: {0}", getClass(), after);
      if (after != null)
      {
        predicates.add(cb.greaterThan(root.<Date>get("feeddate"), after));
      }
    }
    else {
      toFind = "%" + toFind + "%";
      
      for (String key : columnNames)
      {
        Predicate p;
        if (after == null)
        {
          p = cb.like(root.<String>get(key), toFind);
        }
        else
        {
          p = cb.and(cb.greaterThan(root.<Date>get("feeddate"), after), cb.like(root.<String>get(key), toFind));
        }
        


        predicates.add(p);
      }
    }
    
    return (Predicate[])predicates.toArray(new Predicate[0]);
  }
  
  protected List<Feed> select(HttpServletRequest request)
    throws ValidationException
  {
    String toFind = getSearchTerm(request);
    
    Date after = getAfter(request);
    
    int offset = getOffset(request);
    
    int limit = getLimit(request);
    
    return select(toFind, after, offset, limit);
  }
  




  public List<Feed> select(String toFind, Date after, int offset, int limit)
  {
    long mb4 = Runtime.getRuntime().freeMemory();
    long tb4 = System.currentTimeMillis();
    


    EntityManager em = getEntityController().getEntityManager();
    List<Feed> feeds;
    try
    {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Feed> query = cb.createQuery(Feed.class);
      Root<Feed> feed = query.from(Feed.class);
      
      String[] columnNames = { "title", "keywords", "description", "content" };
      
      Predicate[] predicates = buildPredicates(cb, feed, after, columnNames, toFind);
      
      if ((predicates != null) && (predicates.length != 0))
      {
        if (predicates.length == 1) {
          query.where(predicates[0]);
        } else {
          query.where(cb.or(predicates));
        }
      }
      


      query.orderBy(new Order[] { cb.desc(feed.get("feeddate")) });
      
      TypedQuery<Feed> typedQuery = em.createQuery(query);
      typedQuery.setFirstResult(offset);
      typedQuery.setMaxResults(limit);
      



      typedQuery.setHint("eclipselink.read-only", "true");
      


      typedQuery.setHint("eclipselink.batch", "f.commentList");
      typedQuery.setHint("eclipselink.batch", "f.feedhitList");
      typedQuery.setHint("eclipselink.batch", "f.bookmarkfeedList");
      typedQuery.setHint("eclipselink.batch", "f.favoritefeedList");
      typedQuery.setHint("eclipselink.batch.type", BatchFetchType.IN);
      
      feeds = typedQuery.getResultList();
      
      XLogger.getInstance().log(Level.INFO, "Expected: {0}, retreived {1} feeds from database. Consumed memory: {2}, time: {3}", getClass(), Integer.valueOf(limit), feeds == null ? null : Integer.valueOf(feeds.size()), Long.valueOf(mb4 - Runtime.getRuntime().freeMemory()), Long.valueOf(System.currentTimeMillis() - tb4));

    }
    finally
    {
      em.close();
    }
    
    return feeds;
  }
  
  protected String getSearchTerm(HttpServletRequest request) {
    return request.getParameter("query");
  }
  
  protected Map<String, String> getOrderBy(HttpServletRequest request)
    throws ValidationException
  {
    Map<String, String> orderBy = Collections.singletonMap("feeddate", "DESC");
    return orderBy;
  }
  


  public List<Feed> execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    EntityJsonFormat jsonFormat = getJsonFormat();
    
    String contentType = request.getParameter("content-type");
    boolean plainTextOnly = (contentType != null) && (contentType.contains("text/plain"));
    jsonFormat.setPlainTextOnly(plainTextOnly);
    
    XLogger.getInstance().log(Level.FINER, "Plain text only: {0}", getClass(), Boolean.valueOf(plainTextOnly));
    
    Configuration config = WebApp.getInstance().getConfiguration();
    int defaultLen = config == null ? 1000 : config.getInt("defaultContentLength", 1000);
    
    int maxLen = getInt(request, "maxlen", defaultLen);
    jsonFormat.setMaxTextLength(maxLen);
    
    return super.execute(request, response);
  }
  
  protected List<Feed> ensureEquality(List<Feed> feeds, int outputSize)
  {
    printFirstDateLastDateAndFeedIds("BEFORE REARRANGE", feeds, Level.FINER);
    









    FeedFrequency ff = new FeedFrequency(feeds);
    int numOfSites = ff.getSiteCount();
    






    int multiple = 2;
    
    if ((outputSize > numOfSites) && (feeds.size() > numOfSites * 2))
    {
      int ave = outputSize / numOfSites;
      if (ave < 1) {
        ave = 1;
      }
      
      int max = ave * 2;
      
      IntegerArray siteIds = new IntegerArray(numOfSites);
      IntegerArray siteCounts = new IntegerArray(numOfSites);
      
      Iterator<Feed> iter = feeds.iterator();
      
      List<Feed> appendAtEnd = null;
      
      int index = 0;
      
      while (iter.hasNext())
      {
        index++;
        
        Feed feed = (Feed)iter.next();
        
        Site site = feed.getSiteid();
        
        if (site == null) {
          XLogger.getInstance().log(Level.WARNING, "No site found for Feed:: ID: {0}, title: {1}", getClass(), feed.getFeedid(), feed.getTitle());

        }
        else
        {

          int siteid = site.getSiteid().intValue();
          
          int siteIndex = siteIds.indexOf(siteid);
          
          int siteCount;
          
          if (siteIndex == -1) {
            siteCount = 0;
            siteIds.add(siteid);
            siteCounts.add(++siteCount);
          } else {
            siteCount = siteCounts.get(siteIndex);
            assert (siteCount > 0) : ("Expected count > 0 found: " + siteCount);
            siteCounts.set(siteIndex, ++siteCount);
          }
          
          if (siteCount >= max)
          {
            XLogger.getInstance().log(Level.FINER, "Index: {0}. Site: {1}, has appeared {2} times", getClass(), Integer.valueOf(index), feed.getSiteid() == null ? null : feed.getSiteid().getSite(), Integer.valueOf(siteCount));
            

            iter.remove();
            
            if (appendAtEnd == null) {
              appendAtEnd = new ArrayList(ave * 2);
            }
            
            XLogger.getInstance().log(Level.FINEST, "Relocating Feed. ID: {0}, Date: {1}", getClass(), feed.getFeedid(), feed.getFeeddate());
            

            appendAtEnd.add(feed);
          }
        }
      }
      if ((appendAtEnd != null) && (!appendAtEnd.isEmpty()))
      {
        feeds.addAll(appendAtEnd);
      }
      
      printFirstDateLastDateAndFeedIds("AFTER REARRANGE", feeds, Level.FINER);
    }
    
    return feeds.size() <= outputSize ? feeds : feeds.subList(0, outputSize);
  }
  
  protected void printFirstDateLastDateAndFeedIds(String key, List<Feed> feeds, Level level) {
    if ((feeds != null) && (feeds.size() > 1)) {
      Feed first = (Feed)feeds.get(0);
      Feed last = (Feed)feeds.get(feeds.size() - 1);
      XLogger.getInstance().log(level, "{0}. First feed, date: {1}. Last feed, date: {2}\n{3}", getClass(), key, first.getFeeddate(), last.getFeeddate(), toString(feeds));
    }
  }
  
  protected String toString(List<Feed> feeds)
  {
    StringBuilder ids = new StringBuilder();
    for (Feed feed : feeds) {
      ids.append(feed.getFeedid()).append(',');
    }
    return ids.toString();
  }
}
