package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.JpaContext;
import com.bc.jpa.paging.ListPager;
import com.idisc.pu.entities.Commentreplynotice;
import com.idisc.pu.entities.Commentreplynotice_;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.bc.jpa.dao.BuilderForSelect;
import com.bc.util.XLogger;
import com.idisc.core.util.TimeZones;
import java.util.logging.Level;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 6, 2016 5:50:23 PM
 */
public class Updatereadcomments extends AbstractRequestHandler<Boolean> {
    
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException {
      
    Installation installation = getInstallationOrException(request);

    String sval = request.getParameter("commentids");
    
    if ((sval == null) || (sval.isEmpty())) {
      throw new ServletException("Required parameter: 'commentids' is missing");
    }
    
    List<Long> list;
    try {
        
      JSONParser parser = new JSONParser();
      
      list = (List<Long>)parser.parse(sval);
      
    }catch (ParseException e) {
        
      throw new ServletException("Invalid value for parameter: commentids", e);
    }
    
    return execute(request, installation, list);
  }
  
  protected Boolean execute(HttpServletRequest request, Installation installation, List<Long> commentids)
    throws ServletException {
      
    Boolean output;
    
    if(commentids == null || commentids.isEmpty()) {
    
      output = Boolean.FALSE;  
      
    }else{
        
      final int pageSize = 20;
        
      JpaContext jpaContext = this.getJpaContext(request);
      
      final Date NOW_DB_TIMEZONE = new TimeZones().getCurrentTimeInDatabaseTimeZone();
      
      int updateCount = 0;
      
      if(commentids.size() < pageSize) {
          
        updateCount = this.update(jpaContext, commentids, NOW_DB_TIMEZONE);
         
      }else{
          
        ListPager<Long> pages = new ListPager(commentids, pageSize);

        for(int pageNum=0; pageNum<pages.getPageCount(); pageNum++) {

          List<Long> page = pages.getPage(pageNum);
          
          updateCount += this.update(jpaContext, page, NOW_DB_TIMEZONE);
        }
      }
  
      output = updateCount > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    
    return output;
  }
  
  private int update(JpaContext jpaContext, List<Long> commentids, Date dateuserread) {
      
    int updateCount = 0;
    
    try(BuilderForSelect<Commentreplynotice> qb = jpaContext.getBuilderForSelect(Commentreplynotice.class)) {

      qb.where(Commentreplynotice.class, Commentreplynotice_.commentid.getName(), commentids);  
        
      List<Commentreplynotice> notices = qb.createQuery().getResultList();
        
      if(notices != null) {
          
        qb.begin();
        
        for(Commentreplynotice notice:notices) {
          
          notice = qb.merge(notice);  // Assignment important
          
          notice.setDateuserread(dateuserread);
        }
        
        qb.commit();
        
        XLogger.getInstance().log(Level.FINE, 
            "Updated dateuserread for {0}", this.getClass(), notices);
        
        updateCount = notices.size();
      }
    }
    
    return updateCount;
  }
}
