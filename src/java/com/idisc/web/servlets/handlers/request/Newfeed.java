package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.bc.jpa.exceptions.EntityInstantiationException;
import com.bc.jpa.exceptions.PreexistingEntityException;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Site;
import com.idisc.pu.entities.one.Feed_;
import com.idisc.web.exceptions.InstallationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author poshjosh
 */
public class Newfeed extends NewEntityHandler<com.idisc.pu.entities.Feed> {

  @Override
  public Class<com.idisc.pu.entities.Feed> getEntityType() {
    return com.idisc.pu.entities.Feed.class;
  }

  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException
  {
      
    final Class cls = this.getClass();

    final XLogger log = XLogger.getInstance();
    
    log.log(Level.FINER, "execute(HttpServletRequest, HttpServletResponse)", cls);

    Installation installation = getInstallation(request, true);
    
    log.log(Level.FINER, "Installation: {0}", cls, installation);
    if(installation == null) {
      throw new InstallationException("You are not authorized to perform the requested operation");
    }

    Map params = this.getParameterMap(request);
    log.log(Level.FINER, "Parameters: {0}", cls, params);
        
    final Date datecreated = new Date();
    log.log(Level.FINE, "Date created: {0}", cls, datecreated);
    
// Feed_.datecreated.getName() This was observed to be null
        
    params.put("datecreated", datecreated);
    
    final String feeddate_col = "feeddate";
    
    // Note we removed
    //
    Object feeddateParam = params.remove(feeddate_col);
    
    log.log(Level.FINE, "Feeddate parameter: {0}", cls, feeddateParam);

    if(feeddateParam != null && !(feeddateParam instanceof Date) ) {
      String datestr = feeddateParam.toString();
      String [] datePatterns = {"yyyy MMMM dd HH:mm:ss", "yyyy MMM dd HH:mm:ss", "yy MM dd HH:mm:ss"};
      // parse to date with DateFormat and add parsed date
      SimpleDateFormat sdf = new SimpleDateFormat();
      for(String datePattern:datePatterns) {
        sdf.applyPattern(datePattern);
        try{

          Date feeddate = sdf.parse(datestr);
          log.log(Level.FINE, "Parsed: {0} to {1} using {2}", cls, datestr, feeddate, datePattern);

          if(feeddate != null) {
            params.put(feeddate_col, feeddate);
            break;
          }
        }catch(ParseException ingored) { }
      }
    }
    
    if(params.get(feeddate_col) == null) {
        params.put(feeddate_col, datecreated);
    }
    
    EntityController<Site, Object> siteEc = this.getEntityController(Site.class);
    
    Site newsminute = siteEc.find(28); // News Minute has site id of 28
    
    if(newsminute == null) {
        throw new NullPointerException();
    }
    
    params.put(Feed_.siteid.getName(), newsminute);
    
    EntityController<com.idisc.pu.entities.Feed, Object> ec = this.getEntityController();
    
    Boolean output = Boolean.FALSE;
    try {
        
      com.idisc.pu.entities.Feed feed = new com.idisc.pu.entities.Feed();
      final int numberOfParamsUpdated = ec.updateEntity(feed, params, false);
      log.log(Level.FINER, "Number of parameters updated: {0}", cls, numberOfParamsUpdated);
      
      ec.create(feed);
      
      this.updateUrl(log, cls, request.getServletContext(), feed);
        
      String hitcountStr = (String)params.remove("hitcount");
      final int hitcount = hitcountStr == null || hitcountStr.isEmpty() ? 0 : Integer.parseInt(hitcountStr);
      log.log(Level.FINER, "Hit count: {0}", cls, hitcountStr);
        
      this.generateFeedhits(log, cls, installation, feed, hitcount);
      
      output = Boolean.TRUE;
      
    } catch (EntityInstantiationException | PreexistingEntityException | NumberFormatException e) {
      throw new ServletException("An unexpected error occured posting the news item", e);
    } catch(Exception e) {
      throw new ServletException("An unexpected error occured posting the news item", e);
    }
    
    return output;
  }
  
  private boolean updateUrl(final XLogger log, final Class cls, ServletContext sc, com.idisc.pu.entities.Feed feed) {
      Integer feedid = feed.getFeedid();
      if(feedid == null) {
          throw new NullPointerException();
      }
      StringBuilder builder = new StringBuilder(256);
      String baseURL = (String)sc.getAttribute("baseURL");
      log.log(Level.FINE, "Base URL: {0}", cls, baseURL);
      if(baseURL == null) {
          throw new NullPointerException();
      }      
      builder.append(baseURL);
      builder.append(sc.getContextPath());
      builder.append("/feed/").append(feedid);
      String title = feed.getTitle();
      if(title != null && !title.isEmpty()) {
        try{
          String titlePart = URLEncoder.encode(title, "utf-8");
          builder.append('_').append(titlePart);
        }catch(UnsupportedEncodingException e) {
          log.log(Level.WARNING, "Unexpected exception", cls, e);
        }
      }
      builder.append(".jsp");
      
      log.log(Level.FINE, "Updating URL from {0} to {1}", cls, feed.getUrl(), builder);

      feed.setUrl(builder.toString());
      
      try{
          
        this.getEntityController().edit(feed);
        
        return true;
      }catch(Exception e) {
        log.log(Level.WARNING, "Unexpected exception", cls, e);
        
        return false;
      }
  }
  
  private int generateFeedhits(
          final XLogger log, final Class cls,
          Installation installation, com.idisc.pu.entities.Feed feed, int count) {
      if(count < 1) {
          return count;
      }
      
      EntityManager em = IdiscApp.getInstance().getJpaContext().getEntityManager(Feedhit.class);
      try{
          
        EntityTransaction t = em.getTransaction();
        try{
            
            t.begin();
            
            Date hittime = new Date();
            Feedhit feedhit = new Feedhit();

            for(int i=0; i<count; i++) {
                
              feedhit.setFeedid(feed);
              feedhit.setHittime(hittime);
              feedhit.setInstallationid(installation);
              
              em.persist(feedhit);
            }
            
            t.commit();
            
        }finally{
          if(t.isActive()) {
            t.rollback();
          }
        }
        return count;
      }catch(Exception e) {
          log.log(Level.WARNING, "Error generating hitcount for: "+feed, cls, e);
          return 0;
      }finally{
          em.close();
      }
  }
  
  private Map<String, String> getParameterMap(HttpServletRequest request) {
      Map<String, String> map = new HashMap<>(32, 0.75f);
      Enumeration en = request.getParameterNames();
      while(en.hasMoreElements()) {
          String key = en.nextElement().toString();
          String val = request.getParameter(key);
          map.put(key, val);
      }
      return map;
  }
}

