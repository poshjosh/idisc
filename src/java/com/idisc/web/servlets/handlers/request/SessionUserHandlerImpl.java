package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class SessionUserHandlerImpl implements SessionUserHandler {
    
  public Installation getInstallation(HttpServletRequest request, boolean create) {
      
XLogger.getInstance().log(Level.FINER, "getInstallation(HttpServletRequest, HttpServletResponse, boolean)", this.getClass());
    Installation installation;
    
    try
    {
      User user = findUser(request);
      installation = AppInstallation.getEntity(request, user, create);
    }
    catch (Exception ignored) {
      installation = null;
    }
    if(installation != null) {
        
      // ////////////////////////
      //
      request.getSession().setAttribute("installation", installation);
    }
    return installation;
  }

  @Override
  public User tryLogin(HttpServletRequest request)
  {
    User user;
    try {
      Login login = new Login();
      login.execute(request);
      
      user = getUser(request);
    }
    catch (ServletException|IOException|RuntimeException e)
    {
      user = null;
    }
    return user;
  }
  
  @Override
  public void setLoggedout(HttpServletRequest request)
  {
    request.getSession().removeAttribute("user");
  }
  
  @Override
  public void setLoggedIn(HttpServletRequest request, User user)
  {
    request.getSession().setAttribute("user", user);
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
XLogger.getInstance().log(Level.FINE, "Found user: {0}", User.class, user);
    return user;
  }
  
  @Override
  public User getUser(HttpServletRequest request)
  {
    User user = (User)request.getSession().getAttribute("user");
XLogger.getInstance().log(Level.FINER, "User: {0}", this.getClass(), user);
    return user;
  }
  
  @Override
  public boolean isLoggedIn(HttpServletRequest request)
  {
    return getUser(request) != null;
  }

  @Override
  public User setLoggedIn(HttpServletRequest request, Map authuserdetails, boolean create)
    throws ServletException
  {
    User user = null;
    try {
      user = User.getInstance(authuserdetails, create);
XLogger.getInstance().log(Level.FINER, "User: {0}", this.getClass(), user);
      setLoggedIn(request, user);
    } catch (Exception e) {
      throw new ServletException("Unexpected Error", e);
    }
    return user;
  }
}
