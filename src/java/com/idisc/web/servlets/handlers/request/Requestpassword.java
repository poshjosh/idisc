package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.bc.util.XLogger;
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

public class Requestpassword extends AbstractRequestHandler<Boolean> {
    
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    if (isLoggedIn(request)) {
      throw new ServletException("You are already logged in");
    }
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);

    AuthSvcSession authSession = appContext.getAuthSvcSession();
    
    Map app = authSession.getAppDetails();
    
    if (app == null) {
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
      XLogger.getInstance().log(Level.FINE, "Parameters: {0}", getClass(), params);
      
      JSONObject json = authSession.requestUserPassword(app, params);
      
      output = !authSession.isError(json) ? Boolean.TRUE : json == null ? Boolean.FALSE : Boolean.FALSE;
      
    } catch (ParseException e) {
      throw new ServletException("Invalid response from server", e);
    }
    
    return output;
  }
}
