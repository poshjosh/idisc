package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Feed_;
import com.idisc.web.exceptions.ValidationException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public class Searchresults extends Selectfeeds {
    
  public Searchresults() { }

  @Override
  protected Map<String, Object> getSearchParams(HttpServletRequest request) throws ValidationException {
    final String name = Feed_.siteid.getName();
    final String sval = request.getParameter(name);
    if(sval == null) {
      return Collections.EMPTY_MAP;
    }else{
      return Collections.singletonMap(name, (Object)Integer.valueOf(sval));
    }
  }
  
//  @Override
//  protected String getSearchTerm(HttpServletRequest request) throws ValidationException {
      
//    String query =  super.getSearchTerm(request);
    
//    if(query == null || query.isEmpty()) {
        
//      String noquery = request.getParameter("noquery");
      
//      if(noquery == null || (!"1".equals(noquery) && !Boolean.parseBoolean(query))) {
          
//        throw new ValidationException("You did not enter any query");
//      }
//    }
    
//    this.setAttributeForAsync(request, "query", query);
    
//    return query;
//  }
}
