package com.idisc.web.servlets.handlers;

import com.idisc.core.User;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface RequestHandler<X>
{
  public abstract X execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException;
  
  public abstract ResponseHandler<X> getResponseHandler(HttpServletRequest paramHttpServletRequest);
  
  public abstract User findUser(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
  
  public abstract RequestHandlerProvider getRequestHandlerProvider();
  
  public abstract User getUser(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isLoggedIn(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isProtected();
  
  public abstract void processRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException;
  
  public abstract void setLoggedIn(HttpServletRequest paramHttpServletRequest, User paramUser);
  
  public abstract User setLoggedIn(HttpServletRequest paramHttpServletRequest, Map paramMap, boolean paramBoolean)
    throws ServletException;
  
  public abstract void setLoggedout(HttpServletRequest paramHttpServletRequest);
  
  public abstract User tryLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}
