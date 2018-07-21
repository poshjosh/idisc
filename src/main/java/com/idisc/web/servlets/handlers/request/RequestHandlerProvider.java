package com.idisc.web.servlets.handlers.request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface RequestHandlerProvider{
    
    String getRequestHandlerName(HttpServletRequest request) throws ServletException;
  
    String[] getRequestHandlerNames(HttpServletRequest request) throws ServletException;
  
    RequestHandler getRequestHandler(HttpServletRequest request, RequestHandler outputIfNone) throws ServletException;
  
    RequestHandler getRequestHandler(String paramString, RequestHandler outputIfNone);
  
    String getRequestHandlerParamName();
}
