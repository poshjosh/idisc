package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.query.QueryBuilder;
import com.idisc.web.exceptions.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public abstract class SearchHandler<T> extends Select<T> {

  public SearchHandler() { }

  @Override
  protected abstract Class<T> getEntityClass();
  
  protected abstract QueryBuilder getQueryBuilder(HttpServletRequest request, String query);

  @Override
  public boolean isProtected() {
    return false;
  }
  
  @Override
  protected List<T> select(HttpServletRequest request)
    throws ServletException {
      
    String toFind = getSearchTerm(request);

    Date after = getAfter(request);
    
    int offset = getOffset(request);
    
    int limit = getLimit(request);
    
    return select(request, toFind, after, offset, limit);
  }

  public List<T> select(HttpServletRequest request, String toFind, Date after, int offset, int limit) {

    try(QueryBuilder qb = this.getQueryBuilder(request, toFind)) {
    
        if(after != null) {

            qb.where(Feed.class, "feeddate", QueryBuilder.GREATER_THAN, after);
        }

        final TypedQuery<T> tq = qb.build();

        if(offset >= 0) {

            tq.setFirstResult(offset);
        }

        if(limit > 0) {

            tq.setMaxResults(limit);
        }

        return tq.getResultList();
    }
  }
  
  protected String getSearchTerm(HttpServletRequest request) throws ValidationException {
    String query =  request.getParameter("query");
    return query;
  }
  
  protected Date getAfter(HttpServletRequest request) throws ValidationException {
    String sval = request.getParameter("after");
    Date after;
    try {
      after = sval == null ? null : new Date(Long.parseLong(sval));
    } catch (NumberFormatException nfe) {
      try{
        final String javaDatePattern = "E MMM dd HH:mm:ss z yyyy";  
        after = sval == null ? null : new SimpleDateFormat(javaDatePattern).parse(sval);
      }catch(java.text.ParseException pe) {
        throw new ValidationException("Invalid value for parameter 'after': " + sval);  
      }
    }
    return after;
  }
}
