package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Signup extends AbstractRequestHandler<Boolean> {
    
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    if (isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);

    AuthSvcSession authSession = appContext.getAuthSvcSession();
    
    Map app = authSession.getAppDetails();
    
    if (app == null) {
      throw new ServletException("Authentication Service Unavailable");
    }
    
    Map<String, String> params = new RequestParameters(request);
    
    Boolean output;
    
    try{
        
      JSONObject authuserdetails = authSession.createUser(params);
      
      output = Boolean.valueOf(authuserdetails != null);
      
    } catch (ParseException e) {
      throw new ServletException("Invalid response from server", e);
    }
    return output;
  }
}
