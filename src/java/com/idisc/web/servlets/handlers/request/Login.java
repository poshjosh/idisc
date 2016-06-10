package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.HtmlBooleanResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.authsvc.client.AuthSvcSession;
import com.idisc.core.User;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Login extends AbstractRequestHandler<Boolean> {
  @Override
  public boolean isProtected() {
    return false;
  }
  
  @Override
  public ResponseHandler<Boolean, Object> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<Boolean, Object> responseHandler;
    if (this.isHtmlResponse(request)) {
      responseHandler = new HtmlBooleanResponseHandler();
    } else {
      responseHandler = super.createResponseHandler(request);
    }
    
    return responseHandler;
  }

  @Override
  public Boolean execute(HttpServletRequest request)
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
    } catch (ParseException e) {
      throw new ServletException("Error processing request", e);
    }
    return output;
  }
}
