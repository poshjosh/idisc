package com.idisc.web.servlets.handlers.request;

import com.authsvc.client.AppAuthenticationSession;
import com.authsvc.client.parameters.Createuser;
import com.bc.util.MapBuilder;
import java.util.logging.Logger;
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
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Getuser extends AbstractRequestHandler<Map> {
    private transient static final Logger LOG = Logger.getLogger(Getuser.class.getName());
    
  @Override
  protected Map execute(HttpServletRequest request) throws ServletException, IOException {
      
    AppVersionCode versionCode = new AppVersionCode(request.getServletContext(), null);
    
    int version = versionCode.get(request, -1);
    
    if(version > 24) {
      this.getInstallationOrException(request);  
    }
      
      Map<String, String> params = new RequestParameters(request);
    
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "Request parameters: {0}", params);
}
    
    final boolean loggedIn = this.isLoggedIn(request);
    
    if(LOG.isLoggable(Level.FINE)){
      LOG.log(Level.FINE, "Logged in: {0}", loggedIn);
    }

    Map output = Collections.EMPTY_MAP;
    
    if (loggedIn) {
        
      User user = getUser(request);
      
      output = this.getDetails(request, user);
      
    }else {
       
      AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);        
      
      AppAuthenticationSession authSession = appContext.getAuthSvcSession();
      
      if (!authSession.isInitialized()) {
        throw new ServletException("Authentication Session not yet initialized");
      }
      if (!authSession.isServiceAvailable()) {
        throw new ServletException("Authentication Service Unavailable");
      }
      
      params.put(Createuser.ParamName.sendregistrationmail.name(), Boolean.toString(false));
      
      try {
          
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "Getuser parameters: {0}", params);
}

        final Map authuserdetails = authSession.getUser(params);
        
            LOG.log(Level.FINE, "User auth details: {0}", authuserdetails);
        
        if (authuserdetails != null) {
        
          if (authSession.isError(authuserdetails))       {

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
    
    if(LOG.isLoggable(Level.INFO)){
      LOG.log(Level.INFO, "x x x x x x x Output: {0}", output);
    }
    
    return output;
  }

  public Map getDetails(HttpServletRequest request, User user) {
    
    Map output = new HashMap(64, 0.9f);
    
    Map authDetails = user.getAuthdetails();
    output.putAll(authDetails);
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "Authentication details: {0}", authDetails);
    }
    
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
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "Installation details: {0}", instDetails);
    }
    
    final Feeduser feeduser = user.getDelegate();
    final Map userDetails = mapBuilder
            .sourceType(Feeduser.class)
            .source(feeduser)
            .transformer(transformerService.get(Feeduser.class))
            .build();
    output.putAll(userDetails);
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "Feeduser details: {0}", userDetails);
    }
    
    return output;
  }
  
  private void throwServletException(AppAuthenticationSession authSession) throws ServletException {
    
    final String responseMessage = authSession.getResponse().getMessage();

    if(responseMessage == null || responseMessage.isEmpty()) {
      throw new ServletException("Error processing request");  
    }else{
      throw new ServletException("Error processing request" + responseMessage);
    }
  }
}
