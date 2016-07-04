package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.search.SearchResults;
import com.idisc.web.exceptions.ValidationException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public class Searchresults extends Selectfeeds {
    
  private boolean htmlResponse;

  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !(htmlResponse = this.isHtmlResponse(request)); 
  }

  @Override
  public int getMinLimit(HttpServletRequest request) {
    return htmlResponse ? 10 : super.getMinLimit(request);
  }

  @Override
  public int getDefaultLimit(HttpServletRequest request) {
    return htmlResponse ? 20 : super.getDefaultLimit(request);
  }

  @Override
  public int getMaxLimit(HttpServletRequest request) {
    return htmlResponse ? 10 : super.getMaxLimit(request);
  }
  
  @Override
  protected int formatLimitBasedOnAvailableMemory(float memoryLevel, int limit) {
      if(htmlResponse) {
          return limit;
      }else{
          return super.formatLimitBasedOnAvailableMemory(memoryLevel, limit);
      }
  }  
  
  @Override
  public List<com.idisc.pu.entities.Feed> select(
          HttpServletRequest request, String toFind, Date after, int offset, int limit) {

    List<com.idisc.pu.entities.Feed> feeds;  
    
    if(htmlResponse = this.isHtmlResponse(request)) {
        
        SearchResults<com.idisc.pu.entities.Feed> searchresults = 
                this.getSearchResults(request.getSession(), 
                        com.idisc.pu.entities.Feed.class, toFind, after, limit);

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
    this.setAttributeForAsync(request, "query", query);
    return query;
  }
}
