package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
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
import com.bc.jpa.JpaContext;

public class Addfeedhits extends AbstractRequestHandler<Map<Integer, Long>> {
  
  @Override
  public Map<Integer, Long> execute(HttpServletRequest request) throws ServletException {
      
    Installation installation = getInstallationOrException(request);
    
    String hits = request.getParameter("hits");
    
    if ((hits == null) || (hits.isEmpty())) {
      throw new ServletException("Required parameter: 'hits' is missing");
    }
    
    List<String> list;
    try {
        
      JSONParser parser = new JSONParser();
      
      list = (List)parser.parse(hits);
      
    }catch (ParseException e) {
        
      throw new ServletException("Invalid value for parameter: hits", e);
    }
    
    return execute(request, installation, list);
  }
  
  protected Map<Integer, Long> execute(
      HttpServletRequest request, Installation installation, List<String> hits)
      throws ServletException {
      
    Date reusedDate = new Date();
    
    JpaContext jpaContext = getJpaContext(request);
    
    EntityManager em = jpaContext.getEntityManager(Feedhit.class);
    
    Map<Integer, Long> output = new java.util.LinkedHashMap<>();
    
    try {
        
      final EntityTransaction t = em.getTransaction();
      
      try {
          
        t.begin();
        
        for (String hit : hits) {
            
          try{
              
            String[] parts = hit.split(",");
            
            if ((parts != null) && (parts.length >= 2)) {

              if(parts[0] == null || parts[0].isEmpty()) {
                  continue;
              }

              final Integer feedid = Integer.valueOf(parts[0]);

              final long hittime = Long.parseLong(parts[1]);

              Feed feed = (Feed)em.find(Feed.class, feedid);

              if (feed == null) {
          //      throw new ServletException("News record not found in database, id: " + feedid);
                // May have been archived
                continue;
              }
              
              Feedhit feedhit = getFeedhit(installation, feed, reusedDate, hittime);
              
              em.persist(feedhit);
              
              List<Feedhit> feedhits = feed.getFeedhitList();
              
              Long hitcount = feedhits == null ? 0 : (long)feedhits.size();
              
              output.put(feedid, hitcount + 1);
            }
          }catch(NumberFormatException e) {
              XLogger.getInstance().log(Level.WARNING, "{0}", this.getClass(), e.toString());
          }
        }
        
        t.commit();
        
      }finally {
        if (t.isActive()) {
          t.rollback();
        }
      }
    }finally {
      em.close();
    }
    
    return output;
  }
  
  protected Feedhit getFeedhit(
          Installation installation, Feed feed, Date reusedDate, long hittime) {
      
    Feedhit feedhit = new Feedhit();
    
    feedhit.setFeedhitid(null);
    
    feedhit.setFeedid(feed);
    
    feedhit.setInstallationid(installation);
    
    reusedDate.setTime(hittime);
    feedhit.setHittime(reusedDate);
    
    return feedhit;
  }
}
