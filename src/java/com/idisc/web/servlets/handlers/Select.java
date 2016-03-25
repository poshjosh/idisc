package com.idisc.web.servlets.handlers;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
















public abstract class Select<T>
  extends BaseRequestHandler<List<T>>
{
  private final long defaultLimit;
  private final long maxLimit;
  private transient EntityController ec_accessViaGetter;
  
  public Select()
  {
    Configuration config = WebApp.getInstance().getConfiguration();
    this.defaultLimit = (config == null ? 100L : config.getLong("defaultLimit", 100L));
    this.maxLimit = (config == null ? 200L : config.getLong("maxLimit", 200L));
  }
  
  protected abstract Class<T> getEntityClass();
  
  public EntityController<T, Object> getEntityController()
  {
    if (this.ec_accessViaGetter == null) {
      ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
      this.ec_accessViaGetter = factory.getEntityController(getEntityClass());
    }
    return this.ec_accessViaGetter;
  }
  


  public List<T> execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    List<T> entities = select(request);
    
    return entities;
  }
  
  protected List<T> select(HttpServletRequest request)
    throws ValidationException
  {
    return getEntityController().select(getSearchParams(request), getOrderBy(request), getOffset(request), getLimit(request));
  }
  


  protected Map<String, Object> getSearchParams(HttpServletRequest request)
    throws ValidationException
  {
    return null;
  }
  
  protected Map<String, String> getOrderBy(HttpServletRequest request) throws ValidationException {
    return null;
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
  
  protected int getOffset(HttpServletRequest request) throws ValidationException {
    int offset = getInt(request, "offset", 0);
    return offset;
  }
  
  protected int getLimit(HttpServletRequest request) throws ValidationException {
    int limit = getInt(request, "limit", (int)this.defaultLimit);
    if (limit > this.maxLimit) {
      limit = (int)this.maxLimit;
    }
    return limit;
  }
  
  protected int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }
}
