package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.bc.jpa.JpaContext;
import com.bc.jpa.exceptions.PreexistingEntityException;
import com.bc.util.XLogger;
import com.bc.web.core.util.ServletUtil;
import com.idisc.pu.Installations;
import com.idisc.pu.User;
import com.idisc.pu.entities.Feeduser;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Installation_;
import com.idisc.web.AppContext;
import com.idisc.web.ConfigNames;
import com.idisc.web.servlets.handlers.BaseHandler;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class SessionUserHandlerImpl extends BaseHandler implements SessionUserHandler {

  public Installation getInstallationOrException(HttpServletRequest request) 
      throws ServletException {
      
    Installation installation = this.getInstallation(request, true);
      
    if(installation == null) {
      
      throw new ServletException("You are not authorized to perform the requested operation");
    }
      
    return installation;  
  }
    
  public Installation getInstallation(HttpServletRequest request, boolean createIfNone) {
      
XLogger.getInstance().log(Level.FINER, "getInstallation(HttpServletRequest, HttpServletResponse, boolean)", this.getClass());

    Installation installation;

    User user = null;
      
    String installationkey = null;
    String screenname = null;
    long firstinstall = 0;
    long lastinstall = 0;
    
    try {
        
      user = findUser(request);
      
      installationkey = this.getParameter(request, Installation_.installationkey.getName(), true);
      screenname = this.getParameter(request, Installation_.screenname.getName(), false); 
      firstinstall = this.getLongParameter(request, Installation_.firstinstallationdate.getName(), 0);
      lastinstall = this.getLongParameter(request, Installation_.lastinstallationdate.getName(), 0);
      JpaContext jpaContext = this.getAppContext(request).getIdiscApp().getJpaContext();
      
      installation = new Installations(jpaContext).from(
          user, installationkey, screenname, firstinstall, lastinstall, createIfNone);
      
    }catch (ServletException e) {
      installation = null;
      XLogger.getInstance().log(Level.WARNING, "{0}", this.getClass(), e.toString());
    }
    
    if(installation != null) {
        
      this.setAttributeForAsync(request, "installation", installation);
      
    }else{
        
      if(XLogger.getInstance().isLoggable(Level.FINEST, this.getClass())) {
          
        StringBuilder builder = new StringBuilder();
        if(createIfNone) {
            builder.append("Failed to create installation for user:\n");
        }else{
            builder.append("Could not find installation for user:\n");
        }
        this.appendDetails(builder, user, installationkey, screenname, firstinstall, lastinstall);
        builder.append('\n');
        builder.append(ServletUtil.getDetails(request, "\n", Level.FINEST))
                ;
        XLogger.getInstance().log(Level.WARNING, "{0}", this.getClass(), builder);
      }
    }
    return installation;
  }
  
  private void appendDetails(
      StringBuilder appendTo, User user, String installationkey, 
      String screenname, long firstinstall, long lastinstall) {
    if(user == null) {
      appendTo.append("User: null");
    }else{
      appendTo.append("User: ").append('(').append(user.getEmailAddress()).append(':').append(user.getUsername()).append(')');
    }
    appendTo.append(", installationkey: ").append(installationkey);
    appendTo.append(", screenname: ").append(screenname);
    appendTo.append(", firstinstall: ").append(firstinstall<1?firstinstall:new Date(firstinstall));
    appendTo.append(", lastinstall: ").append(firstinstall<1?firstinstall:new Date(lastinstall));
  }

  @Override
  public User tryLogin(HttpServletRequest request) {
    User user;
    try {
        
      Login login = new Login();
      
      login.execute(request);
      
      user = getUser(request);
    } catch (ServletException | IOException | RuntimeException e) {
      user = null;
    }
    return user;
  }
  
  @Override
  public void setLoggedout(HttpServletRequest request) {
    this.removeAttributeForAsync(request, "user");
  }
  
  @Override
  public void setLoggedIn(HttpServletRequest request, User user) {
    this.setAttributeForAsync(request, "user", user);
XLogger.getInstance().log(Level.FINER, "Updated session attribute user = {0}", this.getClass(), user);
  }
  
  @Override
  public User findUser(HttpServletRequest request) {
      
XLogger.getInstance().log(Level.FINER, "findUser(HttpServletRequest, HttpServletResponse)", this.getClass());

    User user;
    if (isLoggedIn(request)) {
      user = getUser(request);
    } else {
      user = tryLogin(request);
    }
    
    final Level logLevel = user != null && this.isDebug(request) ? Level.INFO : Level.FINE;
    
XLogger.getInstance().log(logLevel, "Found user: {0}", User.class, user);

    return user;
  }
  
  private boolean isDebug(HttpServletRequest request) {
      
    return this.getBooleanProperty(request, ConfigNames.DEBUG, Boolean.FALSE);
  }
  
  @Override
  public User getUser(HttpServletRequest request) {
    User user = (User)this.getAttributeForAsync(request, "user");
XLogger.getInstance().log(Level.FINER, "User: {0}", this.getClass(), user);
    return user;
  }
  
  @Override
  public boolean isLoggedIn(HttpServletRequest request) {
    return getUser(request) != null;
  }

  @Override
  public User setLoggedIn(HttpServletRequest request, Map authuserdetails, boolean create)
    throws ServletException {
    User user = null;
    try {
      AppContext appContext = this.getAppContext(request);
      user = createUser(appContext.getIdiscApp().getJpaContext(), authuserdetails, create);
XLogger.getInstance().log(Level.FINER, "User: {0}", this.getClass(), user);
      setLoggedIn(request, user);
    } catch (Exception e) {
      throw new ServletException("Unexpected error during Login", e);
    }
    return user;
  }
  
  protected User createUser(JpaContext factory, Map authdetails, boolean create)
    throws PreexistingEntityException, Exception {
    
    Feeduser feeduser = new Feeduser();
    
    User output = new User(feeduser, authdetails);
    
    EntityController<Feeduser, Integer> ec = factory.getEntityController(Feeduser.class, Integer.class);

    String email = output.getAuthEmailaddress();
    feeduser.setEmailAddress(email);
    
    Map where = ec.toMap(feeduser, false);
    
    List<Feeduser> found = ec.select(where, "AND");
    
    if (found.isEmpty()) {
        
      if (create) {
XLogger.getInstance().log(Level.FINER, "Creating user: {0}", User.class, feeduser);
        feeduser.setDatecreated(new Date());
        try {
          ec.create(feeduser);
XLogger.getInstance().log(Level.FINE, "Created user: {0}", User.class, feeduser);
        } catch (Exception e) {
          output = null;
          throw e;
        }
      } else {
        output = null;
      }
    } else if (found.size() == 1) {
      feeduser = (Feeduser)found.get(0);
      output = new User(feeduser, authdetails);
    } else {
      output = null;
      throw new UnsupportedOperationException("Found > 1 records where 1 or less was expected, entity: " + Feeduser.class.getName() + ", parameters: " + authdetails);
    }
XLogger.getInstance().log(Level.FINE, "User: {0}", User.class, output);

    return output;
  }
  
  boolean getBooleanProperty(HttpServletRequest request, String propertyName, Boolean defaultValue) {
    return this.getAppContext(request).getConfiguration().getBoolean(propertyName, defaultValue);
  }

  Integer getIntegerProperty(HttpServletRequest request, String propertyName, Integer defaultValue) {
    return this.getAppContext(request).getConfiguration().getInteger(propertyName, defaultValue);
  }
  
  long getLongParameter(HttpServletRequest request, String paramName, long defaultValue) 
      throws ServletException {
    
    String paramValue = this.getParameter(request, paramName, false);
    
    return paramValue == null ? defaultValue : Long.parseLong(paramValue);
  }
  
  String getParameter(HttpServletRequest request, String paramName, boolean exceptionIfNone) 
      throws ServletException {
    
    String paramValue = request.getParameter(paramName);
    
    if((paramValue == null || paramValue.isEmpty()) && exceptionIfNone) {
      throw new ServletException("Required parameter: "+paramName+" not found");  
    }
    
    return paramValue;
  }
}
