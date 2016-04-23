package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















public class Activateuser
  extends AbstractRequestHandler<Boolean>
{
  public boolean isProtected()
  {
    return false;
  }
  



  public Boolean execute(HttpServletRequest request, HttpServletResponse response)
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
