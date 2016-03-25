package com.idisc.web.servlets.handlers;

import com.authsvc.client.AuthSvcSession;
import com.idisc.core.User;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;















public class Login
  extends BaseRequestHandler<Boolean>
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
    
    Boolean output;
    
    try
    {
      JSONObject authuserdetails = authSession.getUser(params);
      
      if (authuserdetails == null) {
        throw new ServletException("Error accessing user details from authenctiation service");
      }
      
      User user = setLoggedIn(request, authuserdetails, false);
      
      output = Boolean.valueOf(user != null);
    }
    catch (ParseException e) {
      throw new ServletException("Error processing request", e);
    }
    return output;
  }
}
