package com.idisc.web.servlets.handlers;

import javax.servlet.http.HttpServletRequest;

public abstract interface RequestHandlerProvider
{
  public abstract String getRequestHandlerParamName(HttpServletRequest paramHttpServletRequest);
  
  public abstract String[] getRequestHandlerParamNames(HttpServletRequest paramHttpServletRequest);
  
  public abstract RequestHandler getRequestHandler(HttpServletRequest paramHttpServletRequest);
  
  public abstract RequestHandler getRequestHandler(String paramString);
  
  public abstract RequestHandler getRequestHandler(Class<RequestHandler> paramClass);
}
