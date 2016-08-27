package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.core.util.EntityMapBuilder;
import com.idisc.pu.entities.Installation;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Log extends AbstractRequestHandler<Boolean> {
    
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    RequestParameters params = new RequestParameters(request);
    
    String id = (String)params.remove("id");
    
    if (id == null) {
      throw new ServletException("Required parameter 'id' is missing");
    }
    
    return log(request, id);
  }
  
  private Boolean log(HttpServletRequest request, String logTypeId)
    throws ServletException
  {
    Installation installation = getInstallation(request, false);
    Map map = installation == null ? null : new EntityMapBuilder().toMap(installation);
    XLogger.getInstance().log(Level.WARNING, "x = x = x = " + getName(logTypeId) + " = x = x = x = on device with installation details::\n{0}", getClass(), map);
    return Boolean.TRUE;
  }
  
  private String getName(String id) throws ServletException {
    switch (id) {
    case "1": 
      return "INSTALLATION_ERROR";
    case "2": 
      return "SERVICE_UNAVAILABLE";
    }
    throw new ServletException("Invalid value for parameter. id=" + id);
  }
}
