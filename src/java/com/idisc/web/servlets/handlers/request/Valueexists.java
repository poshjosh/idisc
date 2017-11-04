package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.context.JpaContext;
import com.bc.jpa.dao.Criteria;
import com.bc.util.XLogger;
import com.idisc.pu.DaoService;
import java.util.logging.Level;

public class Valueexists extends AbstractRequestHandler<Boolean> {
    
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException  {
      
    RequestParameters params = new RequestParameters(request);
    
    XLogger.getInstance().log(Level.FINE, "Request parameters: {0}", this.getClass(), params);
    
    String table = (String)params.remove("table");
    
    if (table == null) {
      throw new ServletException("Missing value for required parameter: 'table'");
    }
    
    final JpaContext jpaContext = this.getJpaContext(request);
    
    final Class entityType = jpaContext.getMetaData().findEntityClass(table);
    
    if (entityType == null) {
      throw new ServletException("Invalid value for required parameter: 'table'");
    }else{
      XLogger.getInstance().log(Level.FINE, "{0} = {1}", this.getClass(), table, entityType);
    }
    
    final String logicalOptrStr = (String)params.remove("connector");
    
    final Criteria.LogicalOperator logicalOptr;
    if(logicalOptrStr == null) {
        logicalOptr = Criteria.AND;
    }else{
        switch(logicalOptrStr) {
            case "OR": logicalOptr = Criteria.OR; break;
            default: logicalOptr = Criteria.AND; break;
        }
    }
    
    return new DaoService(jpaContext).isExisting(entityType, logicalOptr, params);
  }
}
