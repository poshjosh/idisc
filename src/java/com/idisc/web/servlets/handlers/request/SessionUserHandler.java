package com.idisc.web.servlets.handlers.request;

import com.idisc.core.User;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public interface SessionUserHandler {
    
  public abstract User findUser(HttpServletRequest request);
  
  public abstract User getUser(HttpServletRequest request);
  
  public abstract boolean isLoggedIn(HttpServletRequest request);
  
  public abstract void setLoggedIn(HttpServletRequest request, User paramUser);
  
  public abstract User setLoggedIn(HttpServletRequest request, Map paramMap, boolean paramBoolean)
    throws ServletException;
  
  public abstract void setLoggedout(HttpServletRequest request);
  
  public abstract User tryLogin(HttpServletRequest request);
}
