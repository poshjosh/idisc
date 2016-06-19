package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;


public class RequestHandlerProviderImpl implements RequestHandlerProvider{
    
  private static final Class cls = RequestHandlerProviderImpl.class;
  private static final XLogger logger = XLogger.getInstance();
    
  public RequestHandlerProviderImpl() { }
  
  @Override
  public String getRequestParamName(HttpServletRequest request) {
      return "req";
  }  
  
  @Override
  public String getRequestHandlerParamName(HttpServletRequest request) {
    String token = request.getParameter(this.getRequestParamName(request));
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
  
  @Override
  public String [] getRequestHandlerParamNames(HttpServletRequest request) {
      
    final String paramName = this.getRequestParamName(request);
    
    String[] tokens = request.getParameterValues(paramName);
    
if(logger.isLoggable(Level.FINER, cls))    
logger.log(Level.FINER, "Parameter names: {0}", cls, tokens==null?null:Arrays.toString(tokens));    

    boolean parseServletPath = false;
    
    if ((tokens == null) || (tokens.length == 0)) {
      parseServletPath = true;  
    }else{
        
      if(tokens.length == 1 && tokens[0] == null) {
          
        final String paramValue = request.getParameter(paramName);
          
        if(paramValue != null) {
          tokens = new String[]{paramValue};
        }else{
          parseServletPath = true;  
        }
      }
    }
    
    if(parseServletPath) {
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
if(logger.isLoggable(Level.FINER, cls))    
logger.log(Level.FINER, "Parameter names: {0}", cls, tokens==null?null:Arrays.toString(tokens));

    return tokens == null ? new String[0] : tokens;
  }

  @Override
  public RequestHandler getRequestHandler(HttpServletRequest request) {
      
    String paramName = getRequestHandlerParamName(request);
    
    RequestHandler handler = getRequestHandler(paramName);
    
    return handler;
  }
  
  @Override
  public RequestHandler getRequestHandler(String name) {
    String className = toClassName(Feeds.class.getPackage().getName(), name);
    try {
      return getRequestHandler((Class<RequestHandler>)Class.forName(className));
    } catch (ClassNotFoundException | ClassCastException e) {
      logger.log(Level.WARNING, "Failed to create new instance of: " + className, cls, e); 
    }
    return null;
  }
  
  @Override
  public RequestHandler getRequestHandler(Class<RequestHandler> aClass){
    try {
      return (RequestHandler)aClass.getConstructor().newInstance();
    } catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
      logger.log(Level.WARNING, "Failed to create new instance of: " + aClass.getName(), cls, e); 
      return null;
    }
  }

  private String toClassName(String prefix, String paramValue) {
    StringBuilder builder = new StringBuilder(prefix.length() + 1 + paramValue.length());
    builder.append(prefix).append('.');
    int indexOfFirst = 0;
    for (int i = 0; i < paramValue.length(); i++) {
      char ch = paramValue.charAt(i);
      if ((i == 0) && 
        (ch == '/')) {
        indexOfFirst = 1;
      }else {
        if (i == indexOfFirst) {
          ch = Character.toTitleCase(ch);
        } else {
          ch = Character.toLowerCase(ch);
        }
        builder.append(ch);
      } 
    }
logger.log(Level.FINER, "Prefix: {0}, param: {1}, class name: {2}", cls, prefix, paramValue, builder);
    
    return builder.toString();
  }
}
