package com.idisc.web.servlets.handlers.request;

import com.idisc.web.exceptions.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.dao.BuilderForSelect;

/**
 * @author poshjosh
 * @param <T>
 */
public abstract class SearchHandler<T> extends com.idisc.web.servlets.handlers.request.Select<T> {

  public SearchHandler() { }

  @Override
  protected abstract Class<T> getEntityClass();
  
  protected abstract BuilderForSelect<T> getSelect(HttpServletRequest request, String query);

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

    try(BuilderForSelect<T> select = this.getSelect(request, toFind)) {
    
        if(after != null) {

            select.where(Feed.class, "feeddate", BuilderForSelect.GREATER_THAN, after);
        }

        final TypedQuery<T> tq = select.createQuery();

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
