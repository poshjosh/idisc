package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;


public class RequestHandlerProviderImpl
  implements RequestHandlerProvider
{
  public String getRequestHandlerParamName(HttpServletRequest request)
  {
    String token = request.getParameter("req");
    if (token == null) {
      String servletPath = request.getServletPath();
      if (servletPath != null) {
        int n = servletPath.indexOf('/');
        if (n != -1) {
          token = servletPath.substring(n + 1);
        }
      }
    }
    return token;
  }
  
  public String[] getRequestHandlerParamNames(HttpServletRequest request)
  {
    String[] tokens = request.getParameterValues("req");
    if ((tokens == null) || (tokens.length == 0)) {
      String servletPath = request.getServletPath();
      if (servletPath != null) {
        int n = servletPath.indexOf('/');
        
        if (n != -1) {
          String token = servletPath.substring(n + 1);
          if (!token.isEmpty()) {
            tokens = token.split(",");
          }
        }
      }
    }
    return tokens;
  }
  





  public RequestHandler getRequestHandler(HttpServletRequest request)
  {
    String key = getRequestHandlerParamName(request);
    
    RequestHandler handler = getRequestHandler(key);
    
    return handler;
  }
  
  @Override
  public RequestHandler getRequestHandler(String name)
  {
    String className = toClassName(Feeds.class.getPackage().getName(), name);
    try {
      return getRequestHandler((Class<RequestHandler>)Class.forName(className));
    } catch (ClassNotFoundException | ClassCastException e) {
      XLogger.getInstance().log(Level.WARNING, "Failed to create new instance of: " + className, getClass(), e); }
    return null;
  }
  
  @Override
  public RequestHandler getRequestHandler(Class<RequestHandler> aClass)
  {
    try
    {
      return (RequestHandler)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
      XLogger.getInstance().log(Level.WARNING, "Failed to create new instance of: " + aClass.getName(), getClass(), e); }
    return null;
  }
  







  private String toClassName(String prefix, String paramValue)
  {
    StringBuilder builder = new StringBuilder(prefix.length() + 1 + paramValue.length());
    builder.append(prefix).append('.');
    int indexOfFirst = 0;
    for (int i = 0; i < paramValue.length(); i++) {
      char ch = paramValue.charAt(i);
      if ((i == 0) && 
        (ch == '/')) {
        indexOfFirst = 1;
      }
      else
      {
        if (i == indexOfFirst) {
          ch = Character.toTitleCase(ch);
        } else {
          ch = Character.toLowerCase(ch);
        }
        builder.append(ch);
      } }
    XLogger.getInstance().log(Level.FINER, "Prefix: {0}, param: {1}, class name: {2}", getClass(), prefix, paramValue, builder);
    
    return builder.toString();
  }
}
