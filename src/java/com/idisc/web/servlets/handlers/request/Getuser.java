package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.parameters.Createuser;
import com.idisc.core.User;
import com.idisc.web.WebApp;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Getuser
  extends AbstractRequestHandler<Map>
{
  public boolean isProtected()
  {
    return false;
  }
  

  public Map execute(HttpServletRequest request)
    throws ServletException, IOException
  {
    Map output;
    
    if (isLoggedIn(request))
    {
      User user = getUser(request);
      output = user.getDetails();
    }
    else
    {
      Map app = WebApp.getInstance().getAuthSvcSession().getAppDetails();
      
      if (app == null) {
        throw new ServletException("Authentication Service Unavailable");
      }
      
      Map<String, String> params = new RequestParameters(request);
      params.put(Createuser.ParamName.sendregistrationmail.name(), Boolean.toString(false));
      
      try
      {
        AuthSvcSession authSession = WebApp.getInstance().getAuthSvcSession();
        


        JSONObject authuserdetails = authSession.getUser(params);
        if (authuserdetails != null) {
          if ((authSession.isError(authuserdetails)) || (authSession.getResponseCode() >= 300))
          {

            throw new ServletException(authSession.getResponseMessage());
          }
          
          Object oval = params.get(com.authsvc.client.parameters.Getuser.ParamName.create.name());
          boolean create = Boolean.parseBoolean(oval.toString());
          User user = setLoggedIn(request, authuserdetails, create);
          output = user.getDetails();
        }
        else {
          throw new ServletException("Error processing request");
        }
      } catch (ParseException e) {
        throw new ServletException("Invalid response from server", e);
      }
    }
    
    return output == null ? Collections.emptyMap() : output;
  }
}
