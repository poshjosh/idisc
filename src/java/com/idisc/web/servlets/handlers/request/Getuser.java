package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.parameters.Createuser;
import com.bc.util.MapBuilder;
import com.bc.util.XLogger;
import com.idisc.core.util.DefaultEntityMapBuilder;
import com.idisc.core.util.mapbuildertransformers.TransformerService;
import com.idisc.core.util.mapbuildertransformers.TransformerServiceImpl;
import com.idisc.pu.User;
import com.idisc.pu.entities.Feeduser;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.request.AppVersionCode;
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

public class Getuser extends AbstractRequestHandler<Map> {
    
  @Override
  protected Map execute(HttpServletRequest request) throws ServletException, IOException {
      
    AppVersionCode versionCode = new AppVersionCode(request.getServletContext(), null);
    
    int version = versionCode.get(request, -1);
    
    if(version > 24) {
      this.getInstallationOrException(request);  
    }
      
      Map<String, String> params = new RequestParameters(request);
    
XLogger.getInstance().log(Level.FINE, "Request parameters: {0}", this.getClass(), params);
    
    final boolean loggedIn = this.isLoggedIn(request);
    
    XLogger.getInstance().log(Level.FINE, "Logged in: {0}", this.getClass(), loggedIn);

    Map output = Collections.EMPTY_MAP;
    
    if (loggedIn) {
        
      User user = getUser(request);
      
      output = this.getDetails(request, user);
      
    }else {
       
      AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);        
      
      AuthSvcSession authSession = appContext.getAuthSvcSession();
      
      Map app = authSession.getAppDetails();
      
      if (app == null) {
        throw new ServletException("Authentication Service Unavailable");
      }
      
      params.put(Createuser.ParamName.sendregistrationmail.name(), Boolean.toString(false));
      
      try {
          
XLogger.getInstance().log(Level.FINE, "Getuser parameters: {0}", this.getClass(), params);

        JSONObject authuserdetails = authSession.getUser(params);
        
//        XLogger.getInstance().log(Level.FINE, "User auth details: {0}", this.getClass(), authuserdetails);
        
        if (authuserdetails != null) {
            
          if (authSession.isError(authuserdetails) || !authSession.isPositiveCompletion())       {

            this.throwServletException(authSession);
          }
          
          Object oval = params.get(com.authsvc.client.parameters.Getuser.ParamName.create.name());
          
          boolean create = Boolean.parseBoolean(oval.toString());
          
          User user = setLoggedIn(request, authuserdetails, create);
          
          output = this.getDetails(request, user);
          
        }else {
            
          this.throwServletException(authSession);
        }
      } catch (ParseException e) {
          
        throw new ServletException("Invalid response from server", e);
      }
    }
    
    XLogger.getInstance().log(Level.INFO, "x x x x x x x Output: {0}", this.getClass(), output);
    
    return output;
  }

  public Map getDetails(HttpServletRequest request, User user) {
    
    Map output = new HashMap(64, 0.9f);
    
    Map authDetails = user.getAuthdetails();
    output.putAll(authDetails);
    XLogger.getInstance().log(Level.FINER, "Authentication details: {0}", getClass(), authDetails);
    
    final MapBuilder mapBuilder = new DefaultEntityMapBuilder();
    mapBuilder.methodFilter(MapBuilder.MethodFilter.ACCEPT_ALL);
    final TransformerService transformerService = new TransformerServiceImpl(false, 1000);
    
    final Installation installation = this.getInstallation(request, false);
    Map instDetails = mapBuilder
            .sourceType(Installation.class)
            .source(installation)
            .transformer(transformerService.get(Installation.class))
            .build();
    
    if(instDetails != null) {
      output.putAll(instDetails);
    }
    XLogger.getInstance().log(Level.FINER, "Installation details: {0}", getClass(), instDetails);
    
    final Feeduser feeduser = user.getDelegate();
    final Map userDetails = mapBuilder
            .sourceType(Feeduser.class)
            .source(feeduser)
            .transformer(transformerService.get(Feeduser.class))
            .build();
    output.putAll(userDetails);
    XLogger.getInstance().log(Level.FINER, "Feeduser details: {0}", getClass(), userDetails);
    
    return output;
  }
  
  private void throwServletException(AuthSvcSession authSession) throws ServletException {
      
    final String responseMessage = authSession.getResponseMessage();  

    if(responseMessage == null || responseMessage.isEmpty()) {
      throw new ServletException("Error processing request");  
    }else{
      throw new ServletException("Error processing request" + responseMessage);
    }
  }
}
