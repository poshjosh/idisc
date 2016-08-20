package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.parameters.Createuser;
import com.bc.util.XLogger;
import com.idisc.core.util.EntityMapBuilder;
import com.idisc.pu.User;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
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
  
  @Override
  public Map execute(HttpServletRequest request) throws ServletException, IOException {
      
    Map output;
    
    if (isLoggedIn(request)) {
      User user = getUser(request);
      output = this.getDetails(user);
    }else {
       
      AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);        
      
      AuthSvcSession authSession = appContext.getAuthSvcSession();
      
      Map app = authSession.getAppDetails();
      
      if (app == null) {
        throw new ServletException("Authentication Service Unavailable");
      }
      
      Map<String, String> params = new RequestParameters(request);
      params.put(Createuser.ParamName.sendregistrationmail.name(), Boolean.toString(false));
      
      try {
          
        JSONObject authuserdetails = authSession.getUser(params);
        if (authuserdetails != null) {
          if ((authSession.isError(authuserdetails)) || (authSession.getResponseCode() >= 300))
          {

            throw new ServletException(authSession.getResponseMessage());
          }
          
          Object oval = params.get(com.authsvc.client.parameters.Getuser.ParamName.create.name());
          boolean create = Boolean.parseBoolean(oval.toString());
          User user = setLoggedIn(request, authuserdetails, create);
          output = this.getDetails(user);
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

  public Map getDetails(User user) {
    XLogger.getInstance().log(Level.FINER, "Authentication details: {0}", getClass(), user.getAuthdetails());
    Map output = new HashMap(40, 0.75F);
    output.putAll(user.getAuthdetails());
    Map feeduserdetails = new EntityMapBuilder().toMap(user.getDelegate());
    output.putAll(feeduserdetails);
    return output;
  }
}
