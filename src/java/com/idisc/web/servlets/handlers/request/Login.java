package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.bc.util.XLogger;
import com.idisc.pu.User;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Login extends AbstractRequestHandler<Boolean> {
  
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
    if (isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    AppContext appCtx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    
    AuthSvcSession authSession = appCtx.getAuthSvcSession();
    
    Map app = authSession.getAppDetails();
    
    if (app == null) {
      throw new ServletException("Authentication Service Unavailable");
    }
    
    Map<String, String> params = new RequestParameters(request);
    
XLogger.getInstance().log(Level.FINE, "Request parameters: {0}", this.getClass(), params);
    
    Boolean output;
    
    try {
        
      JSONObject authuserdetails = authSession.getUser(params);
      
      if (authuserdetails == null) {
        throw new ServletException("Error accessing user details from authenctiation service");
      }
      
      User user = setLoggedIn(request, authuserdetails, false);
      
      output = user != null ? Boolean.TRUE : Boolean.FALSE;
      
    } catch (ParseException e) {
        
      throw new ServletException("Error processing request", e);
    }
    return output;
  }
}
