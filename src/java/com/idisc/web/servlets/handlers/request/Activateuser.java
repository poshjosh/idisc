package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.idisc.web.WebApp;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Activateuser extends AbstractRequestHandler<Boolean> {
    
  @Override
  public boolean isProtected() {
      
    return false;
  }
  
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException
  {
    if (isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    Map app = WebApp.getInstance().getAuthSvcSession().getAppDetails();
    
    if (app == null) {
      throw new ServletException("Authentication Service Unavailable");
    }
    
    Map<String, String> params = new RequestParameters(request);
    
    AuthSvcSession authSession = WebApp.getInstance().getAuthSvcSession();
    
    Boolean output = Boolean.TRUE;
    
    return output;
  }
}
