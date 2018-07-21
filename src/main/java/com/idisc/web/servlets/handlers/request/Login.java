package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AppAuthenticationSession;
import java.util.logging.Logger;
import com.idisc.pu.User;
import com.idisc.web.AppContext;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Login extends AbstractRequestHandler<Boolean> {
    
  private transient static final Logger LOG = Logger.getLogger(Login.class.getName());
  
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    if (isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    final AppContext appCtx = this.getAppContext(request);
    
    AppAuthenticationSession authSession = appCtx.getAuthSvcSession();
    
    if (!authSession.isInitialized()) {
      throw new ServletException("Authentication Session not yet initialized");
    }
    if (!authSession.isServiceAvailable()) {
      throw new ServletException("Authentication Service Unavailable");
    }
      
    final Map<String, String> params = new RequestParameters(request);
    
    LOG.log(Level.FINE, "Request parameters: {0}", params.keySet());
    
    Boolean output;
    
    try {
        
      final Map authuserdetails = authSession.getUser(params);
      
      if (authuserdetails == null) {
        throw new ServletException("Error accessing user details from authenctiation service");
      }
      
      if(authSession.isError(authuserdetails)) {
          String msg;
          if(authuserdetails.isEmpty()) {
              msg = authSession.getResponse().getMessage();
          }else{
              msg = String.valueOf(authuserdetails.values().iterator().next());
          }
          throw new ServletException(msg);
      }
      
      User user = setLoggedIn(request, authuserdetails, false);
      
      output = user != null ? Boolean.TRUE : Boolean.FALSE;
      
    } catch (ParseException e) {
        
      throw new ServletException("Error processing request", e);
    }
    return output;
  }
}
