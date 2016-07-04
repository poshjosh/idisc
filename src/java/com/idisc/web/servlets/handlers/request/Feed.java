package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Comment;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.JpaContext;

public class Feed extends AbstractRequestHandler<com.idisc.pu.entities.Feed>{

  private com.idisc.pu.entities.Feed feed;
  
  @Override
  public String getResponseFormat(HttpServletRequest request) {
    String resFmt = super.getResponseFormat(request);
    return resFmt == null ? "text/html" : resFmt;
  }
  
  @Override
  public boolean isProtected() {
    return false;
  }

  @Override
  public com.idisc.pu.entities.Feed execute(HttpServletRequest request)
    throws ServletException, IOException {
    
    this.feed = this.select(request);
    
    if(feed != null) {
        
      if(this.isHtmlResponse(request)) {
          
        final Comments commentsRequestHandler = new Comments();
        
        List<Comment> comments = commentsRequestHandler.execute(request); 
          
        if(comments != null && !comments.isEmpty()) {
            
          this.setAttributeForAsync(request, "comments", comments);
        }
      }
    }
    
    return feed;
  }
  
  public com.idisc.pu.entities.Feed select(HttpServletRequest request)
    throws ServletException, IOException {
      
    Integer feedid = getId(request);
    
    com.idisc.pu.entities.Feed feed = null;
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    
    List<com.idisc.pu.entities.Feed> lastFeeds = appContext.getCachedFeeds();
    
    if ((lastFeeds != null) && (!lastFeeds.isEmpty())){
        
      for (com.idisc.pu.entities.Feed lastFeed : lastFeeds) {
          
        if (lastFeed.getFeedid().intValue() == feedid) {
          feed = lastFeed;
          break;
        }
      }
    }
    
    if (feed == null) {

      feed = select(feedid);
    }
    
    XLogger.getInstance().log(Level.FINER, "Selected feed: {0}", getClass(), feed);
    
    return feed;
  }

  public com.idisc.pu.entities.Feed select(Integer feedid) {
      
    JpaContext factory = IdiscApp.getInstance().getJpaContext();
    
    EntityController<com.idisc.pu.entities.Feed, Integer> ec = factory.getEntityController(com.idisc.pu.entities.Feed.class, Integer.class);
    
    return (com.idisc.pu.entities.Feed)ec.find(feedid);
  }
  
  public Integer getId(HttpServletRequest request) throws ValidationException {
    String sval = request.getParameter("feedid");
    return getId(sval);
  }
  
  public Integer getId(String sval) throws ValidationException {
    if ((sval == null) || (sval.isEmpty())) {
      throw new ValidationException("Required parameter 'feedid' not found");
    }
    int ival;
    try
    {
      ival = Integer.parseInt(sval);
    } catch (NumberFormatException e) {
      throw new ValidationException("Invalid value for required parameter: 'feedid'");
    }
    return Integer.valueOf(ival);
  }
}
/**
 * 
  @Override
  public RequestHandler.RequestHandlerEntry getNextRequestHandler(HttpServletRequest request) {
      
    if(this.requestHandlerEntry == null && this.feed != null && this.isHtmlResponse(request)) {

      final Comments comments = new Comments();
      final String name = comments.getClass().getSimpleName().toLowerCase();
XLogger.getInstance().log(Level.FINE, "Created next request handler: {0}", this.getClass(), comments.getClass().getName());

      this.requestHandlerEntry = new RequestHandler.RequestHandlerEntry() {
        @Override
        public String getName() {
            return name;
        }
        @Override
        public RequestHandler getRequestHandler() {
            return comments;
        }
      };  
    }
    
    return this.requestHandlerEntry;
  }
  
 * 
 * 
 */