package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Requestpassword
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
      throw new ServletException("You are already logged in");
    }
    
    Map app = WebApp.getInstance().getAuthSvcSession().getAppDetails();
    
    if (app == null) {
      throw new ServletException("Authentication Service Unavailable");
    }
    
    Map<String, String> params = new RequestParameters(request){
        @Override
        public boolean isCaseSensitive() {
            //@todo remove eventually
            // We had to do this because of a bug in the earlier versions
            // of the newsminute android app... version deployed before
            // 4 Apr 2015
            //
            return false;
        }
    };

    AuthSvcSession authSession = WebApp.getInstance().getAuthSvcSession();
    
    Boolean output;
    try
    {
      XLogger.getInstance().log(Level.FINE, "Parameters: {0}", getClass(), params);
      


      JSONObject json = authSession.requestUserPassword(app, params);
      
      output = Boolean.valueOf(!authSession.isError(json) ? true : json == null ? Boolean.FALSE.booleanValue() : false);
    }
    catch (ParseException e) {
      throw new ServletException("Invalid response from server", e);
    }
    
    return output;
  }
}
