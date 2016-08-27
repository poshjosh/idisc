package com.idisc.web.servlets.handlers.request;

import com.idisc.web.exceptions.ValidationException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public class Searchresults extends Selectfeeds {
    
  public static final String NO_QUERY = "noquery";
    
  public Searchresults() { }

  @Override
  protected String getSearchTerm(HttpServletRequest request) throws ValidationException {
      
    String query =  super.getSearchTerm(request);
    
    if(query == null || query.isEmpty()) {
        
      String noquery = request.getParameter(NO_QUERY);
      
      if(noquery == null || (!"1".equals(noquery) && !Boolean.parseBoolean(query))) {
          
        throw new ValidationException("You did not enter any query");
      }
    }
    
    this.setAttributeForAsync(request, "query", query);
    
    return query;
  }
}
