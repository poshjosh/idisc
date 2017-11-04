package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.context.JpaContext;
import com.bc.jpa.dao.Criteria;
import com.bc.jpa.dao.SelectDao;
import com.bc.jpa.search.SearchResults;
import com.bc.util.XLogger;
import com.idisc.pu.SearchDao;
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
import com.bc.webdatex.converter.DateTimeConverter;
import com.idisc.core.util.TimeZones;
import com.idisc.pu.functions.GetColumnNamesOfType;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.TimeZone;

public class Select<T> extends AbstractRequestHandler<List<T>>{
  
  private final Class<T> entityClass;
  
  public Select(Class<T> entityClass){ 
    this.entityClass = entityClass;
  }

  @Override
  protected List<T> execute(HttpServletRequest request) throws ServletException, IOException{
      
XLogger.getInstance().entering(this.getClass(), "#execute(HttpServletRequest, HttpServletResponse)", "");

    List<T> entities = select(request);
    
    return entities;
  }
  
  protected List<T> select(HttpServletRequest request) throws ServletException{
      
    com.bc.jpa.dao.SelectDao<T> dao = this.createSelect(request);
    
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
  
  protected com.bc.jpa.dao.Select<T> createSelect(HttpServletRequest request) throws ValidationException {
      
    JpaContext jpaContext = this.getJpaContext(request);
    
    final int offset = this.isPaginate(request) ? -1 : getOffset(request);
    final int limit = this.isPaginate(request) ? -1 : getLimit(request);
    
    final String [] queries = this.getSearchTerms(request);
    final String query = getSearchTerm(request);

    final String dateColumn = this.getDateColumnName(null);
    final String [] columnsToSearch = this.getColumnsToSearch(jpaContext);

    final com.bc.jpa.dao.Select<T> select;
    
    if(queries != null && queries.length != 0) {
        select = new SearchDao(
            jpaContext, this.entityClass, offset, limit, Arrays.asList(queries), columnsToSearch);
    }else{
        select = new SearchDao(
            jpaContext, this.entityClass, offset, limit, query, columnsToSearch);
    }
            
    
    Date after = getAfter(request);
    
    if(after != null && dateColumn != null) {
      select.and().where(this.entityClass, dateColumn, Criteria.GT, after);
    }
    
    Map<String, Object> searchParams = getSearchParams(request);
    if(searchParams != null && !searchParams.isEmpty()){
      select.and().where(entityClass, searchParams);
    }
    
    Map<String, String> orderBy = getOrderBy(request);
    if(orderBy != null && !orderBy.isEmpty()){
      select.orderBy(entityClass, orderBy);
    }
    
    return select;
  }
  
  protected String [] getColumnsToSearch(JpaContext jpaContext) {
    final String [] columnsToSearch;
    if(this.entityClass == com.idisc.pu.entities.Feed.class) {
//      columnsToSearch = new String[]{"title", "keywords", "description", "author", "content"};
      columnsToSearch = new String[]{"author", "title", "keywords",  "categories", "description", "content"};
    }else if(this.entityClass == Comment.class) {
      columnsToSearch = new String[]{"commentSubject", "commentText"};
    }else{
      columnsToSearch = new GetColumnNamesOfType(jpaContext.getMetaData())
              .apply(entityClass, String.class).toArray(new String[0]);
    }
    return columnsToSearch;
  }
  
  protected String getDateColumnName(String outputIfNone) {
    final String dateColumn;
    if(this.entityClass == com.idisc.pu.entities.Feed.class) {
      dateColumn = Feed_.feeddate.getName();
    }else if(this.entityClass == Comment.class) {
      dateColumn = Comment_.datecreated.getName();
    }else{
      dateColumn = outputIfNone;
    }
    return dateColumn;
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
  
  protected String [] getSearchTerms(HttpServletRequest request) throws ValidationException {
    final int max = this.getSearchTermsMax(request);
    final String [] queries =  request.getParameterValues("queries");
    return queries == null || queries.length <= max ? queries : 
            Arrays.asList(queries).subList(0, max).toArray(new String[0]);
  }
  
  protected int getSearchTermsMax(HttpServletRequest request) throws ValidationException {
    return 5;
  }
  
  protected String getSearchTerm(HttpServletRequest request) throws ValidationException {
    String query =  request.getParameter("query");
    return query;
  }
  
  protected Date getAfter(HttpServletRequest request) throws ValidationException {
      
    final String sval = request.getParameter("after");
    
    final TimeZones timeZones = new TimeZones();
    final String databaseTimeZoneId = timeZones.getDatabaseTimeZoneId();
    final TimeZone databaseTimeZone = TimeZone.getTimeZone(databaseTimeZoneId);
    
    Date after;
    if(sval == null) {
        
      after = null;
      
    }else{
        
      try {
        
        after = new Date(Long.parseLong(sval));
        
        if(databaseTimeZone.getOffset(after.getTime()) != 0) {
            
          DateTimeConverter converter = new DateTimeConverter(
                  TimeZone.getTimeZone(TimeZones.UTC_ZONEID), databaseTimeZone);  
          
          after = converter.convert(after);
        }
      
      } catch (NumberFormatException ignored) {
        try{
          
          final String javaDatePattern = "E MMM dd HH:mm:ss z yyyy";  
          DateFormat dateFormat = new SimpleDateFormat(javaDatePattern);        
          dateFormat.setTimeZone(databaseTimeZone);
          
          after = dateFormat.parse(sval);
        
        }catch(java.text.ParseException pe) {
            
          after = null;
          
          throw new ValidationException("Invalid value for parameter 'after': " + sval);  
        }
      }
    }
    
    return after;
  }
  
  protected int getOffset(HttpServletRequest request) throws ValidationException {
    int offset = getIntRequestParam(request, "offset", 0);
    return offset;
  }
  
  protected int getLimit(HttpServletRequest request) throws ValidationException {
      
    final int requestedLimit = getIntRequestParam(request, "limit", this.getDefaultLimit(request));
    
    int outputLimit = requestedLimit;

    final AppContext appContext = this.getAppContext(request);
    
    if(appContext.getConfiguration().getBoolean(ConfigNames.ADJUST_LIMIT_BASED_ON_MEMORY_LEVEL, Boolean.FALSE)) {
        
      outputLimit = appContext.getMemoryManager().limit(outputLimit, 1);
    }
    
    final int minLimit = this.getMinLimit(request);
    
    if(outputLimit < minLimit) {
        
        outputLimit = minLimit;
    }
    
    final int maxLimit = this.getMaxLimit(request);
    
    if(outputLimit > maxLimit) {
        
        outputLimit = maxLimit;
    }
    
XLogger.getInstance().log(Level.FINE, "Requested limit: {0}, limit: {1}",
        this.getClass(), requestedLimit, outputLimit);
        
    return outputLimit;
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
