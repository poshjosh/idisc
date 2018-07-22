package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.context.JpaContext;
import com.bc.task.Task;
import java.util.logging.Logger;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Feed_;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
/**
 * @author poshjosh
 */
public class Generatefeedhit extends AbstractRequestHandler<Boolean> {
    private transient static final Logger LOG = Logger.getLogger(Generatefeedhit.class.getName());

  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "execute(HttpServletRequest)", this.getClass());
    }
    
    final int NO_VALUE = -1;

    final int feedid = (int)this.getLongParameter(request, Feed_.feedid.getName(), NO_VALUE);
    
    if(feedid == NO_VALUE) {
      throw new ValidationException();
    }
    
    final int hitcount = (int)this.getLongParameter(request, "hitcount", NO_VALUE);
    
    if(hitcount == NO_VALUE) {
      throw new ValidationException();
    }
    
    final Installation installation = getInstallationOrException(request);
    
    final JpaContext jpaContext = this.getJpaContext(request);
    
    final com.idisc.pu.entities.Feed feed;
    
    EntityManager em = jpaContext.getEntityManager(Feed.class);
    try{
      feed = em.find(com.idisc.pu.entities.Feed.class, feedid);
    }finally{
        em.close();
    }
    
    final Task<Integer> feedhitGenerationTask = new FeedhitGenerator(
        jpaContext, installation, feed, hitcount);
    
    try{
        
      Integer output = feedhitGenerationTask.call();
      
      return Boolean.TRUE;
      
    }catch(Exception e) {
        
      if(LOG.isLoggable(Level.WARNING)){
         LOG.log(Level.WARNING, "Unexpected exception", e);
      }
       
      return Boolean.FALSE;  
    }
  }
}
