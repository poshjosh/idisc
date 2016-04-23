package com.idisc.web.servlets.handlers.request;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandlerProvider
{
    String getRequestHandlerParamName(HttpServletRequest request);
  
    String[] getRequestHandlerParamNames(HttpServletRequest request);
  
    RequestHandler getRequestHandler(HttpServletRequest request);
  
    RequestHandler getRequestHandler(String paramString);
  
    RequestHandler getRequestHandler(Class<RequestHandler> paramClass);

    String getRequestParamName(HttpServletRequest request);
}
