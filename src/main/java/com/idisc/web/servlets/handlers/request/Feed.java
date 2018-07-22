package com.idisc.web.servlets.handlers.request;

import java.util.logging.Logger;
import com.idisc.pu.entities.Comment;
import com.idisc.web.Attributes;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.context.JpaContext;
import com.bc.jpa.search.SearchResults;
import java.util.Collection;

public class Feed extends AbstractRequestHandler<com.idisc.pu.entities.Feed>{
    
  private transient static final Logger LOG = Logger.getLogger(Feed.class.getName());
  
  @Override
  protected com.idisc.pu.entities.Feed execute(HttpServletRequest request)
    throws ServletException, IOException {
    
    com.idisc.pu.entities.Feed feed = this.select(request);
    
    if(feed != null) {
        
      if(this.isHtmlResponse(request)) {
          
        final Comments commentsRequestHandler = new Comments();
        
        List<Comment> comments = commentsRequestHandler.processRequest(request);
          
        if(comments != null && !comments.isEmpty()) {
            
          this.setAttributeForAsync(request, Attributes.COMMENTS, comments);
        }
      }
    }
    
    return feed;
  }
  
  public com.idisc.pu.entities.Feed select(HttpServletRequest request)
    throws ServletException, IOException {
      
    final Integer feedid = getId(request);
    
    SearchResults<com.idisc.pu.entities.Feed> searchResults = 
            this.getSearchResults(request, Feed.class, SearchResults.EMPTY_INSTANCE);
    
    com.idisc.pu.entities.Feed feed = this.findByFeedid(searchResults.getPages(), feedid, null);
    
    if(feed == null) {
        
      List<com.idisc.pu.entities.Feed> feeds = (List<com.idisc.pu.entities.Feed>)
              request.getServletContext().getAttribute(Attributes.FEEDS);
      
      feed = this.findByFeedid(feeds, feedid, feed);
    }
    
    if (feed == null) {
        
      feed = select(request, feedid);
    }
    
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "Selected feed: {0}", feed);
    }
    
    return feed;
  }

  public com.idisc.pu.entities.Feed select(HttpServletRequest request, Integer feedid) {
      
    JpaContext jpaContext = this.getJpaContext(request);
    
    return jpaContext.getDaoForSelect(com.idisc.pu.entities.Feed.class).findAndClose(feedid);
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
    try {
        
      ival = Integer.parseInt(sval);
      
    } catch (NumberFormatException e) {
        
      throw new ValidationException("Invalid value for required parameter: 'feedid'");
    }
    return ival;
  }
  
  public com.idisc.pu.entities.Feed findByFeedid(
          Collection<com.idisc.pu.entities.Feed> feeds, Integer feedid, 
          com.idisc.pu.entities.Feed outputIfNone) {
      
    com.idisc.pu.entities.Feed output = outputIfNone;
    
    if(feeds != null && !feeds.isEmpty()) {
      for(com.idisc.pu.entities.Feed f:feeds) {
        if(f.getFeedid().equals(feedid)) {
          output = f;
          break;
        }
      }
    }
    return output;
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