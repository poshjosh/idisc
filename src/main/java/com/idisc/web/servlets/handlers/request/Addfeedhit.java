package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.dao.Criteria;
import java.util.logging.Logger;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.idisc.pu.entities.Feed_;
import com.bc.jpa.dao.Select;

public class Addfeedhit extends AbstractRequestHandler<Long> {
    private transient static final Logger LOG = Logger.getLogger(Addfeedhit.class.getName());
    
  @Override
  protected Long execute(HttpServletRequest request) throws ServletException {
if(LOG.isLoggable(Level.FINER)){
LOG.log(Level.FINER, "#execute(HttpServletRequest)", this.getClass());
}     

    Installation installation = getInstallationOrException(request);
    
    String param = null;
    Integer feedid;
    long hittime; 
    try { 
      param = "feedid";
      feedid = Integer.valueOf(request.getParameter(param));
      param = "hittime";
      hittime = Long.parseLong(request.getParameter(param));
    } catch (NumberFormatException e) {
      throw new ServletException("Invalid value for required parameter: " + param, e);
    } catch (NullPointerException e) {
      throw new ServletException("Required parameter: " + param + " is missing", e);
    }
    
    Long hitcount = execute(request, installation, feedid, hittime);
    
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "{0} = {1}", new Object[]{ feedid,  hitcount});
}

    return hitcount;
  }
  

  protected Long execute(HttpServletRequest request, Installation installation, Integer feedid, long hittime)
    throws ServletException {
      
    final Select<Long> dao = this.getJpaContext(request).getDaoForSelect(Feedhit.class, Long.class);
    
    Feed feed = dao.getEntityManager().find(Feed.class, feedid);
    
    if (feed == null) {
      throw new ServletException("News record not found in database, ID: " + feedid);
    }
    
    Feedhit feedhit = new Feedhit();
    
    feedhit.setFeedid(feed);
    
    feedhit.setInstallationid(installation);
    
    feedhit.setHittime(new Date(hittime));
    
    Long output;
    try {

      dao.begin().persist(feedhit).commit();
      
      output = dao
              .where(Feed_.feedid.getName(), Criteria.EQ, feedid)
              .count().createQuery().getSingleResult();
     
    } catch (Exception e) {
        
      final String msg = "Database Error updating hitcount";
      LOG.log(Level.WARNING, msg, e);
      throw new ServletException(msg);
      
    }finally{
        
      dao.close();
    }
    
    return output== null ? 1 : output + 1;
  }
}
