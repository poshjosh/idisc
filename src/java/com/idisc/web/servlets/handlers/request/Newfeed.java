package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.controller.EntityController;
import com.bc.jpa.context.JpaContext;
import com.bc.jpa.exceptions.EntityInstantiationException;
import com.bc.jpa.exceptions.PreexistingEntityException;
import com.bc.task.Task;
import com.bc.util.XLogger;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Site;
import com.idisc.pu.entities.Feed_;
import com.idisc.web.exceptions.LoginException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import javax.persistence.EntityManager;
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
    final JpaContext jpaContext = this.getJpaContext(request);
    
    log.log(Level.FINER, "execute(HttpServletRequest)", cls);
    
    if(!this.isLoggedIn(request)) {
        
        this.tryLogin(request); 
        
        if(!this.isLoggedIn(request)) {
        
            throw new LoginException();
        }
    }

    Installation installation = getInstallationOrException(request);
    
    Map<String, Object> params = this.getParameterMap(request);
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
    
    final Site newsminute = this.getNewsminuteSiteEntity(jpaContext);
    
    params.put(Feed_.siteid.getName(), newsminute);
    
    EntityController<com.idisc.pu.entities.Feed, Integer> ec = 
            this.getJpaContext(request).getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
    
    Boolean output = Boolean.FALSE;
    try {
        
      com.idisc.pu.entities.Feed feed = new com.idisc.pu.entities.Feed();
      final int numberOfParamsUpdated = ec.update(feed, params, false);
      log.log(Level.FINER, "Number of parameters updated: {0}", cls, numberOfParamsUpdated);
      
      ec.create(feed);
      
      try{
        final String URL = this.getUrl(log, cls, request.getServletContext(), feed);
        feed.setUrl(URL);
        
        ec.edit(feed);
        
        log.log(Level.FINE, "Updated URL from {0} to {1}", cls, feed.getUrl(), URL);
      }catch(Exception e) {
        log.log(Level.WARNING, "Unexpected exception", cls, e);
      }
      
      final int HITCOUNT = this.getHitcount(params);
      log.log(Level.FINER, "Hit count: {0}", cls, HITCOUNT);
        
      if(HITCOUNT > 0) {
        Task<Integer> feedhitGenerationTask = new FeedhitGenerator(jpaContext, installation, feed, HITCOUNT);
        feedhitGenerationTask.call();
      }
      
      output = Boolean.TRUE;
      
    } catch (EntityInstantiationException | PreexistingEntityException | NumberFormatException e) {
      throw new ServletException("An unexpected error occured posting the news item", e);
    } catch(Exception e) {
      throw new ServletException("An unexpected error occured posting the news item", e);
    }
    
    return output;
  }
  
  private Map<String, Object> getParameterMap(HttpServletRequest request) {
    Map<String, Object> parameterMap = new HashMap<>(32, 0.75f);
    Enumeration en = request.getParameterNames();
    while(en.hasMoreElements()) {
      String key = en.nextElement().toString();
      String val = request.getParameter(key);
      parameterMap.put(key, val);
    }
    return parameterMap;
  }

  private Site getNewsminuteSiteEntity(JpaContext jpaContext) {
    Site newsminute;
    EntityManager em = jpaContext.getEntityManager(Site.class);
    try{
        final Integer newsminuteSiteid = 28;
        newsminute = em.find(Site.class, newsminuteSiteid);
    }finally{
        em.close();
    }
    return Objects.requireNonNull(newsminute);
  }
  
  private String getUrl(final XLogger log, 
          final Class cls, ServletContext sc, com.idisc.pu.entities.Feed feed) {
      
      final Integer feedid = Objects.requireNonNull(feed.getFeedid());
      
      StringBuilder builder = new StringBuilder(512);
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
      
      final String url = builder.toString();
      
      log.log(Level.FINE, "Built URL {0} from {1}", cls, url, feed.getUrl());
      
      return url;
  }
  
  private int getHitcount(Map<String, Object> params) {
    String hitcountStr = (String)params.remove("hitcount");
    final int hitcount = hitcountStr == null || hitcountStr.isEmpty() ? 0 : Integer.parseInt(hitcountStr);
    return hitcount;
  }
}

