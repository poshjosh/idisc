package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.idisc.core.User;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface RequestHandler<V>
{
    
  interface RequestHandlerEntry{
      String getName();
      RequestHandler getRequestHandler();
  }
    
  RequestHandlerEntry getNextRequestHandler(HttpServletRequest request);  
  
  ResponseHandler<V> getResponseHandler(HttpServletRequest request);

  public abstract User findUser(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
  
  public abstract User getUser(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isLoggedIn(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isProtected();
  
  public abstract V processRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException;
  
  public abstract void setLoggedIn(HttpServletRequest paramHttpServletRequest, User paramUser);
  
  public abstract User setLoggedIn(HttpServletRequest paramHttpServletRequest, Map paramMap, boolean paramBoolean)
    throws ServletException;
  
  public abstract void setLoggedout(HttpServletRequest paramHttpServletRequest);
  
  public abstract User tryLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}