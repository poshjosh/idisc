package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.JpaContext;

public class Valueexists extends AbstractRequestHandler<Boolean> {
    
  @Override
  public boolean isProtected() {
    return false;
  }
  
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException  {
      
    RequestParameters params = new RequestParameters(request);
    
    String table = (String)params.remove("table");
    
    if (table == null) {
      throw new ServletException("Missing value for required parameter: 'table'");
    }
    
    EntityController ec = getEntityController(request, table);
    
    if (ec == null) {
      throw new ServletException("Invalid value for required parameter: 'table'");
    }
    
    String connector = (String)params.remove("connector");
    
    if ((connector == null) || (connector.isEmpty())) {
      connector = "AND";
    }
    
    List found = ec.select(params, null, connector, 1, 0);
    
    if ((found == null) || (found.isEmpty())) {
      return Boolean.FALSE;
    }
    
    return Boolean.TRUE;
  }
  
  public EntityController getEntityController(HttpServletRequest request, String table) {
    JpaContext jpaContext = this.getJpaContext(request);
    Class entityClass = jpaContext.getMetaData().findEntityClass(table);
    return entityClass == null ? null : jpaContext.getEntityController(entityClass);
  }
}
