package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.web.Attributes;
import com.idisc.web.AppContext;
import com.idisc.web.ConfigNames;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.JpaContext;

public abstract class Select<T> extends AbstractRequestHandler<List<T>>{
    
  private transient EntityController ec_accessViaGetter;
  
  public Select(){ }

  protected abstract Class<T> getEntityClass();
  
  public EntityController<T, Object> getEntityController(){
    if (this.ec_accessViaGetter == null) {
      JpaContext factory = IdiscApp.getInstance().getJpaContext();
      this.ec_accessViaGetter = factory.getEntityController(getEntityClass());
    }
    return this.ec_accessViaGetter;
  }
  
  @Override
  public List<T> execute(HttpServletRequest request) throws ServletException, IOException{
      
XLogger.getInstance().entering(this.getClass(), "#execute(HttpServletRequest, HttpServletResponse)", "");

    List<T> entities = select(request);
    
    return entities;
  }
  
  protected List<T> select(HttpServletRequest request) throws ServletException{
      
    Map<String, Object> searchParams = getSearchParams(request);
    
    Map<String, String> orderBy = getOrderBy(request);

    int offset = getOffset(request);
    
    int limit = getLimit(request);
      
    List<T> selected = getEntityController().select(searchParams, orderBy, offset, limit);
    
XLogger.getInstance().log(Level.FINE, "Selected: {0}", this.getClass(), selected==null?null:selected.size());
    return selected;
  }
  
  protected Map<String, Object> getSearchParams(HttpServletRequest request)
    throws ValidationException
  {
    return null;
  }
  
  protected Map<String, String> getOrderBy(HttpServletRequest request) throws ValidationException {
    return null;
  }
  
  protected int getOffset(HttpServletRequest request) throws ValidationException {
    int offset = getIntRequestParam(request, "offset", 0);
    return offset;
  }
  
  public final int getLimit(HttpServletRequest request) throws ValidationException {
      
    int limit = getIntRequestParam(request, "limit", this.getDefaultLimit(request));
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    
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
XLogger.getInstance().log(Level.FINER, 
        "Based on memory level: {0}, formatted limit from {1} to {2} ", 
        this.getClass(), memoryLevel, limit, result);
    }
    return result;
  }
  
  private int getIntRequestParam(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }

  public int getMaxLimit(HttpServletRequest request) {
    return getIntProperty(request, "maxLimit", 200);
  }
  public int getDefaultLimit(HttpServletRequest request) {
    return getIntProperty(request, "defaultLimit", 100);
  }
  public int getMinLimit(HttpServletRequest request) {
    return getIntProperty(request, "minLimit", 5);
  }
  private int getIntProperty(HttpServletRequest request, String name, int defaultValue) {
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    return appContext.getConfiguration().getInt(name, defaultValue);
  }
}
