package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AppAuthenticationSession;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Signup extends AbstractRequestHandler<Boolean> {
    
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    if (isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);

    AppAuthenticationSession authSession = appContext.getAuthSvcSession();
    
    if (!authSession.isInitialized()) {
      throw new ServletException("Authentication Session not yet initialized");
    }
    if (!authSession.isServiceAvailable()) {
      throw new ServletException("Authentication Service Unavailable");
    }
    
    Map<String, String> params = new RequestParameters(request);
    
    Boolean output;
    
    try{
        
      Map authuserdetails = authSession.createUser(params);
      
      output = authuserdetails != null;
      
    } catch (ParseException e) {
      throw new ServletException("Invalid response from server", e);
    }
    return output;
  }
}