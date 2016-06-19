package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Addfeedhits extends AbstractRequestHandler<Map<Integer, Long>>
{
  private ControllerFactory _cf;
  private EntityController<Feedhit, Integer> _fhc;
  private EntityController<Feed, Integer> _fc;
  
  public boolean isProtected()
  {
    return false;
  }
  


  @Override
  public Map<Integer, Long> execute(HttpServletRequest request)
    throws ServletException
  {
    Installation installation = getInstallation(request, true);
    
    if(installation == null) {
      throw new ServletException("You are not authorized to perform the requested operation");
    }

    String hits = request.getParameter("hits");
    
    if ((hits == null) || (hits.isEmpty())) {
      throw new ServletException("Required parameter: 'hits' is missing");
    }
    
    try
    {
      JSONParser parser = new JSONParser();
      
      List<String> list = (List)parser.parse(hits);
      
      return execute(installation, list);

    }
    catch (ParseException e)
    {
      throw new ServletException("Invalid value for parameter: hits", e);
    }
  }
  
  protected Map<Integer, Long> execute(Installation installation, List<String> hits)
    throws ServletException
  {
    Feedhit reusedFeedhit = new Feedhit();
    
    Date reusedDate = new Date();
    
    EntityManager em = getControllerFactory().getEntityManager(Feedhit.class);
    
    Integer[] feedids = new Integer[hits.size()];
    
    try
    {
      EntityTransaction t = em.getTransaction();
      
      int i;
      try
      {
        t.begin();
        
        i = 0;
        
        for (String hit : hits)
        {
          try{
            String[] parts = hit.split(",");
            if ((parts != null) && (parts.length >= 2))
            {

              final String part = parts[0];

              if(part == null || part.isEmpty()) {
                  continue;
              }

              Integer feedid = Integer.valueOf(part);

              feedids[(i++)] = feedid;

              long hittime = Long.parseLong(parts[1]);

              persistFeedhit(installation, feedid, hittime, em, reusedFeedhit, reusedDate);
            }
          }catch(NumberFormatException | ServletException e) {
              XLogger.getInstance().log(Level.WARNING, "{0}", this.getClass(), e.toString());
          }
        }
      }
      finally {
        if (t.isActive()) {
          t.rollback();
        }
      }
      
    }
    finally
    {
      em.close();
    }
    
    if ((feedids != null) && (feedids.length > 0))
    {
      long[] hitcounts = getHitcounts(feedids);
      
      Map<Integer, Long> output = new java.util.HashMap(hitcounts.length, 1.0F);
      
      for (int i = 0; i < hitcounts.length; i++)
      {
        output.put(feedids[i], Long.valueOf(hitcounts[i]));
      }
      
      return output;
    }
    

    return Collections.EMPTY_MAP;
  }
  

  private long[] getHitcounts(Integer[] feedids)
  {
    long[] hitcounts = new long[feedids.length];
    
    EntityController<Feedhit, Integer> ec = getFeedhitController();
    
    int i = 0;
    
    for (Integer feedid : feedids)
    {
      Map where = Collections.singletonMap("feedid", feedid);
      
      hitcounts[(i++)] = ec.count(where);
    }
    
    return hitcounts;
  }
  


  protected void persistFeedhit(Installation installation, Integer feedid, long hittime, EntityManager em, Feedhit reusedFeedhit, Date reusedDate)
    throws ServletException
  {
    Feed feed = (Feed)em.find(Feed.class, feedid);
    
    if (feed == null) {
      throw new ServletException("News record not found in database, id: " + feedid);
    }
    
    reusedFeedhit.setFeedhitid(null);
    
    reusedFeedhit.setFeedid(feed);
    
    reusedFeedhit.setInstallationid(installation);
    
    reusedDate.setTime(hittime);
    
    reusedFeedhit.setHittime(reusedDate);
    
    em.persist(reusedFeedhit);
  }
  
  private ControllerFactory getControllerFactory()
  {
    if (this._cf == null) {
      this._cf = IdiscApp.getInstance().getControllerFactory();
    }
    return this._cf;
  }
  
  private EntityController<Feedhit, Integer> getFeedhitController()
  {
    if (this._fhc == null) {
      ControllerFactory factory = getControllerFactory();
      this._fhc = factory.getEntityController(Feedhit.class, Integer.class);
    }
    return this._fhc;
  }
  
  private EntityController<Feed, Integer> getFeedController()
  {
    if (this._fc == null) {
      ControllerFactory factory = getControllerFactory();
      this._fc = factory.getEntityController(Feed.class, Integer.class);
    }
    return this._fc;
  }
}
