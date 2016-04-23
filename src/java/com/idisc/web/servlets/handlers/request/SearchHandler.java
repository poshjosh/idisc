package com.idisc.web.servlets.handlers.request;

import com.idisc.core.jpa.Search;
import com.idisc.web.exceptions.ValidationException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author poshjosh
 */
public abstract class SearchHandler<T> extends Select<T>
{

  public SearchHandler() { }

  public SearchHandler(long maxLimit, long defaultLimit, long minLimit) {
    super(maxLimit, defaultLimit, minLimit);
  }
  
  @Override
  protected abstract Class<T> getEntityClass();
  
  protected abstract Search<T> getSearch();

  @Override
  public boolean isProtected()
  {
    return false;
  }
  
  @Override
  protected List<T> select(HttpServletRequest request)
    throws ServletException
  {
    String toFind = getSearchTerm(request);

    Date after = getAfter(request);
    
    int offset = getOffset(request);
    
    int limit = getLimit(request);
    
    return select(request, toFind, after, offset, limit);
  }

  public List<T> select(HttpServletRequest request, String toFind, Date after, int offset, int limit)
  {

    List<T> selected = this.getSearch().select(toFind, after, offset, limit);

    return selected;
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
    } catch (NumberFormatException e) {
      throw new ValidationException("Invalid value for parameter 'after': " + sval);
    }
    return after;
  }
}
