package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.JpaContext;
import com.bc.jpa.dao.BuilderForSelect;
import com.bc.jpa.dao.SelectDao;
import com.bc.jpa.search.SearchResults;
import com.bc.util.XLogger;
import com.idisc.pu.CommentQuery;
import com.idisc.pu.FeedQuery;
import com.idisc.pu.Search;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Comment_;
import com.idisc.pu.entities.Feed_;
import com.idisc.web.AppContext;
import com.idisc.web.ConfigNames;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Select<T> extends AbstractRequestHandler<List<T>>{
  
  private final Class<T> entityClass;
  
  public Select(Class<T> entityClass){ 
    this.entityClass = entityClass;
  }

  @Override
  public List<T> execute(HttpServletRequest request) throws ServletException, IOException{
      
XLogger.getInstance().entering(this.getClass(), "#execute(HttpServletRequest, HttpServletResponse)", "");

    List<T> entities = select(request);
    
    return entities;
  }
  
  protected List<T> select(HttpServletRequest request) throws ServletException{
      
    SelectDao<T> dao = this.createSelect(request);
    
    List<T> selected;
    
    if(this.isPaginate(request)) {
      SearchResults<T> sr = this.getSearchResults(request, entityClass, dao);
      selected = sr.getPages();
    }else{
      selected = dao.getResultsAndClose();
    }
      
XLogger.getInstance().log(Level.FINE, "Selected: {0}", 
this.getClass(), selected==null?null:selected.size());

    return selected;
  }
  
  protected <X> SearchResults<X> getSearchResults(
          HttpServletRequest request, Class<X> enityType, SelectDao<X> dao) {
    AppContext appCtx = this.getAppContext(request);
    SearchResults<X> searchResults = appCtx.getSearchHandlerFactory(true)
            .get(request.getSession().getId(), dao, true);
    return searchResults;
  }
  
  protected <E> BuilderForSelect<E> createSelect(HttpServletRequest request) throws ValidationException {
      
    JpaContext jpaContext = this.getJpaContext(request);
    
    final int offset = this.isPaginate(request) ? -1 : getOffset(request);
    final int limit = this.isPaginate(request) ? -1 : getLimit(request);
    final String query = getSearchTerm(request);

    final BuilderForSelect select;
    
    final String dateColumn;
    if(this.entityClass == com.idisc.pu.entities.Feed.class) {
      select = new FeedQuery(jpaContext, offset, limit, query);
      dateColumn = Feed_.feeddate.getName();
    }else if(this.entityClass == Comment.class) {
      select = new CommentQuery(jpaContext, offset, limit, query);
      dateColumn = Comment_.datecreated.getName();
    }else{
      select = new Search(jpaContext, this.entityClass, offset, limit, query);
      dateColumn = null;
    }
    
    Date after = getAfter(request);
    if(after != null && dateColumn != null) {
      select.where(this.entityClass, dateColumn, BuilderForSelect.GREATER_THAN, after);
    }
    
    Map<String, Object> searchParams = getSearchParams(request);
    if(searchParams != null && !searchParams.isEmpty()){
      select.where(entityClass, searchParams);
    }
    
    Map<String, String> orderBy = getOrderBy(request);
    if(orderBy != null && !orderBy.isEmpty()){
      select.orderBy(entityClass, orderBy);
    }
    
    return select;
  }
  
  protected boolean isPaginate(HttpServletRequest request) {
    return false;
  }
  
  protected Map<String, Object> getSearchParams(HttpServletRequest request) throws ValidationException {
    return Collections.EMPTY_MAP;
  }
  
  protected Map<String, String> getOrderBy(HttpServletRequest request) throws ValidationException {
    return Collections.EMPTY_MAP;
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
  
  protected int getOffset(HttpServletRequest request) throws ValidationException {
    int offset = getIntRequestParam(request, "offset", 0);
    return offset;
  }
  
  protected int getLimit(HttpServletRequest request) throws ValidationException {
      
    int limit = getIntRequestParam(request, "limit", this.getDefaultLimit(request));

    AppContext appContext = this.getAppContext(request);
    
    if(appContext.getConfiguration().getBoolean(ConfigNames.ADJUST_LIMIT_BASED_ON_MEMORY_LEVEL, Boolean.FALSE)) {
        
      float memoryLevel = appContext.getMemoryLevel().floatValue();
      
      limit = this.formatLimitBasedOnAvailableMemory(memoryLevel, limit);  
    }
    
    final int minLimit = this.getMinLimit(request);
    
    if(limit < minLimit) {
        
        limit = minLimit;
    }
    
    final int maxLimit = this.getMaxLimit(request);
    
    if(limit > maxLimit) {
        
        limit = maxLimit;
    }
    return limit;
  }
  
  protected int formatLimitBasedOnAvailableMemory(float memoryLevel, int limit) {
    int result;
    if(memoryLevel >= 1.0f) {
      result = limit;
    }else{
      result = (int)(limit * memoryLevel);
    }
XLogger.getInstance().log(Level.FINE, 
        "Based on memory level: {0}, formatted limit from {1} to {2} ", 
        this.getClass(), memoryLevel, limit, result);
    return result;
  }
  
  private int getIntRequestParam(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }

  public Integer getCacheLimit(HttpServletRequest request) {
    return getIntegerProperty(request, ConfigNames.CACHE_LIMIT, 200);
  }
  public Integer getMaxLimit(HttpServletRequest request) {
    return getIntegerProperty(request, ConfigNames.MAX_LIMIT, 200);
  }
  public Integer getDefaultLimit(HttpServletRequest request) {
    return getIntegerProperty(request, ConfigNames.DEFAULT_LIMIT, 100);
  }
  public Integer getMinLimit(HttpServletRequest request) {
    return getIntegerProperty(request, ConfigNames.MIN_LIMIT, 5);
  }

  public final Class<T> getEntityClass() {
    return this.entityClass;
  }
}
