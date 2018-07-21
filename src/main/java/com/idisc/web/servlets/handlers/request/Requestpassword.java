package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AppAuthenticationSession;
import java.util.logging.Logger;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Requestpassword extends AbstractRequestHandler<Boolean> {
    private transient static final Logger LOG = Logger.getLogger(Requestpassword.class.getName());
    
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    if (isLoggedIn(request)) {
      throw new ServletException("You are already logged in");
    }
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);

    AppAuthenticationSession authSession = appContext.getAuthSvcSession();
    
    if (!authSession.isInitialized()) {
      throw new ServletException("Authentication Session not yet initialized");
    }
    if (!authSession.isServiceAvailable()) {
      throw new ServletException("Authentication Service Unavailable");
    }
      
    //@todo remove eventually, change to default (i.e true)
    // We had to do this because of a bug in the earlier versions
    // of the newsminute android app... version deployed before
    // 4 Apr 2015
    //
    final Boolean caseSensitive = Boolean.FALSE;
    Map<String, String> params = new RequestParameters(request, caseSensitive);
    
    Boolean output;
    try {
      if(LOG.isLoggable(Level.FINE)){
         LOG.log(Level.FINE, "Parameters: {0}", params);
      }
      
      Map json = authSession.requestUserPassword(params);
      
      output = !authSession.isError(json) ? Boolean.TRUE : json == null ? Boolean.FALSE : Boolean.FALSE;
      
    } catch (ParseException e) {
      throw new ServletException("Invalid response from server", e);
    }
    
    return output;
  }
}
