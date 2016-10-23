package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.bc.jpa.JpaContext;
import com.bc.jpa.exceptions.EntityInstantiationException;
import com.bc.jpa.exceptions.PreexistingEntityException;
import com.bc.task.Task;
import com.bc.util.XLogger;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Site;
import com.idisc.pu.entities.Feed_;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
/**
 * @author poshjosh
 */
public class Newfeed extends NewEntityHandler<com.idisc.pu.entities.Feed> {

  @Override
  public Class<com.idisc.pu.entities.Feed> getEntityType() {
    return com.idisc.pu.entities.Feed.class;
  }

  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    final Class cls = this.getClass();
    final XLogger log = XLogger.getInstance();
    
    log.log(Level.FINER, "execute(HttpServletRequest)", cls);

    Installation installation = getInstallationOrException(request);
    
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
    
    EntityController<Site, Integer> siteEc = 
            this.getJpaContext(request).getEntityController(Site.class, Integer.class);
    
    Site newsminute = siteEc.find(28); // News Minute has site id of 28
    
    if(newsminute == null) {
        throw new NullPointerException();
    }
    
    params.put(Feed_.siteid.getName(), newsminute);
    
    EntityController<com.idisc.pu.entities.Feed, Integer> ec = 
            this.getJpaContext(request).getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
    
    Boolean output = Boolean.FALSE;
    try {
        
      com.idisc.pu.entities.Feed feed = new com.idisc.pu.entities.Feed();
      final int numberOfParamsUpdated = ec.updateEntity(feed, params, false);
      log.log(Level.FINER, "Number of parameters updated: {0}", cls, numberOfParamsUpdated);
      
      ec.create(feed);
      
      this.updateUrl(ec, log, cls, request.getServletContext(), feed);
        
      String hitcountStr = (String)params.remove("hitcount");
      final int hitcount = hitcountStr == null || hitcountStr.isEmpty() ? 0 : Integer.parseInt(hitcountStr);
      log.log(Level.FINER, "Hit count: {0}", cls, hitcountStr);
        
      final JpaContext jpaContext = this.getJpaContext(request);
      Task<Integer> feedhitGenerationTask = new FeedhitGenerator(jpaContext, installation, feed, hitcount);
      feedhitGenerationTask.call();
      
      output = Boolean.TRUE;
      
    } catch (EntityInstantiationException | PreexistingEntityException | NumberFormatException e) {
      throw new ServletException("An unexpected error occured posting the news item", e);
    } catch(Exception e) {
      throw new ServletException("An unexpected error occured posting the news item", e);
    }
    
    return output;
  }
  
  private boolean updateUrl(EntityController<com.idisc.pu.entities.Feed, Integer> ec, final XLogger log, 
          final Class cls, ServletContext sc, com.idisc.pu.entities.Feed feed) {
      
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
          
        ec.edit(feed);
        
        return true;
        
      }catch(Exception e) {
        log.log(Level.WARNING, "Unexpected exception", cls, e);
        
        return false;
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

