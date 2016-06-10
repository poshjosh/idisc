package com.idisc.web.servlets.handlers.request;

import com.idisc.core.jpa.FeedSearchResults;
import com.idisc.web.exceptions.ValidationException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public class Searchresults extends Selectfeeds {

  public Searchresults() {
    super(20, 20, 10);
  }
    
    @Override
  public List<com.idisc.pu.entities.Feed> select(
          HttpServletRequest request, String toFind, Date after, int offset, int limit)
  {

    List<com.idisc.pu.entities.Feed> feeds;  
    
    if(this.isHtmlResponse(request)) {
        
        FeedSearchResults searchresults = new FeedSearchResults(toFind, after, limit);

        feeds = searchresults.getAllResults();
        
    }else{
        
        feeds = super.select(request, toFind, after, offset, limit);
    }

    return feeds;
  }

  @Override
  protected String getSearchTerm(HttpServletRequest request) throws ValidationException {
    String query =  super.getSearchTerm(request);
    if(query == null || query.isEmpty()) {
        throw new ValidationException("You did not enter any query");
    }
    request.getSession().setAttribute("query", query);
    return query;
  }
}
