package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;

public abstract class Select<T> extends AbstractRequestHandler<List<T>>{
    
  private final long maxLimit;
  private final long defaultLimit;
  private final long minLimit;
  private transient EntityController ec_accessViaGetter;
  
  public Select(){
    Configuration config = WebApp.getInstance().getConfiguration();
    this.maxLimit = (config == null ? 200L : config.getLong("maxLimit", 200L));
    this.defaultLimit = (config == null ? 100L : config.getLong("defaultLimit", 100L));
    this.minLimit = (config == null ? 5L : config.getLong("maxLimit", 5L));
  }
  
  public Select(long maxLimit, long defaultLimit, long minLimit) {
    this.maxLimit = maxLimit;
    this.defaultLimit = defaultLimit;
    this.minLimit = minLimit;
  }

  protected abstract Class<T> getEntityClass();
  
  public EntityController<T, Object> getEntityController(){
    if (this.ec_accessViaGetter == null) {
      ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
      this.ec_accessViaGetter = factory.getEntityController(getEntityClass());
    }
    return this.ec_accessViaGetter;
  }
  
  @Override
  public List<T> execute(HttpServletRequest request)
    throws ServletException, IOException{
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
    int offset = getInt(request, "offset", 0);
    return offset;
  }
  
  public final int getLimit(HttpServletRequest request) throws ValidationException {
    int limit = this.limit(request);
    limit = this.formatLimitBasedOnAvailableMemory(limit);
    if(limit < this.minLimit) {
        limit = (int)this.minLimit;
    }
    if(limit > this.maxLimit) {
        limit = (int)this.maxLimit;
    }
    return limit;
  }
  
  protected int limit(HttpServletRequest request) throws ValidationException {
    int limit = getInt(request, "limit", (int)this.defaultLimit);
    return limit;
  }
  
  protected int formatLimitBasedOnAvailableMemory(int limit) {
    long fm = Runtime.getRuntime().freeMemory();
    long mas = WebApp.getInstance().getMemoryAtStartup();
    int result;
    if(fm >= mas) {
      result = limit;
    }else{
      BigDecimal freeMemory = new BigDecimal(fm);
      BigDecimal memoryAtStartup = new BigDecimal(mas);
      float memoryLevel = freeMemory.divide(memoryAtStartup, 2, RoundingMode.HALF_UP).floatValue();
      // We do this twice to make our memory saving pro-active
      // 100 x 0.5 = 50. However 100 x 0.5 x 0.5 = 25
      result = (int)(limit * memoryLevel * memoryLevel);
XLogger.getInstance().log(Level.FINER, 
        "Based on memory level: {0}, formatted limit from {1} to {2} ", 
        this.getClass(), memoryLevel, limit, result);
    }
    return result;
  }
  
  private int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }
}
