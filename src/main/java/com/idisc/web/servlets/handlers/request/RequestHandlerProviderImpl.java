package com.idisc.web.servlets.handlers.request;

import java.util.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class RequestHandlerProviderImpl implements RequestHandlerProvider{
    
  private transient static final Logger LOG = Logger.getLogger(RequestHandlerProviderImpl.class.getName());
    
  public RequestHandlerProviderImpl() { }
  
  @Override
  public String getRequestHandlerParamName() {
      return "req";
  }  
  
  @Override
  public String getRequestHandlerName(HttpServletRequest request) throws ServletException {
      
    final String paramName = this.getRequestHandlerParamName();

    String token = request.getParameter(paramName);
    
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "{0} = {1}", new Object[]{ paramName,  token});
}

    if (token == null) {
        
      final String servletPath = request.getServletPath();
      
      if (servletPath != null) {
          
        int n = servletPath.indexOf('/');
        
        if (n != -1) {
            
          token = servletPath.substring(n + 1);
        }
      }
                
      if(token == null) {
        throw new ServletException("Unexpected servlet path: "+servletPath);    
      }
    }
    
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "Request handler name = {0}", token);
}    

    if(token == null) {
      throw new ServletException("Required parameter '"+this.getRequestHandlerParamName()+
              "' is missing from request.\n"+this.toString(request));      
    }
    
    return token;
  }
  
  @Override
  public String [] getRequestHandlerNames(HttpServletRequest request) throws ServletException {
      
final Level level = Level.FINER;

    final String paramName = this.getRequestHandlerParamName();
    
    String[] tokens = request.getParameterValues(paramName);
    
if(LOG.isLoggable(level))    
LOG.log(level, "{0} = {1}", new Object[]{paramName, tokens==null?null:Arrays.toString(tokens)});    

    boolean parseServletPath = false;
    
    if (tokens == null || tokens.length == 0) {
        
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
      final String servletPath = request.getServletPath();
      if (servletPath != null) {
        int n = servletPath.indexOf('/');
        
        if (n != -1) {
          String token = servletPath.substring(n + 1);
          if (!token.isEmpty()) {
            tokens = token.split(",");
            
            if(tokens == null || tokens.length == 0) {
              throw new ServletException("Unexpected servlet path: "+servletPath);        
            }
          }
        }
      }
    }
    
if(LOG.isLoggable(level))    
LOG.log(level, "Request handler names: {0}", tokens==null?null:Arrays.toString(tokens));

    if(tokens == null || tokens.length == 0) {
      throw new ServletException("Required parameter '"+this.getRequestHandlerParamName()+
              "' is missing from request.\n"+this.toString(request));      
    }

    return tokens;
  }

  @Override
  public RequestHandler getRequestHandler(HttpServletRequest request, RequestHandler outputIfNone) throws ServletException{
      
    String paramName = getRequestHandlerName(request);
    
    RequestHandler handler = getRequestHandler(paramName, outputIfNone);
    
    return handler;
  }
  
  @Override
  public RequestHandler getRequestHandler(String name, RequestHandler outputIfNone) {
    final String className = toClassName(Feeds.class.getPackage().getName(), name);
    try {
      return getRequestHandler((Class<RequestHandler>)Class.forName(className), outputIfNone);
    } catch (ClassNotFoundException | ClassCastException e) {
      LOG.log(Level.WARNING, "Failed to create new instance of: " + className, e); 
      return outputIfNone;
    }
  }
  
  private RequestHandler getRequestHandler(Class<RequestHandler> aClass, RequestHandler outputIfNone){
    try {
      return (RequestHandler)aClass.getConstructor().newInstance();
    } catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
      LOG.log(Level.WARNING, "Failed to create new instance of " + aClass, e); 
      return outputIfNone;
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
    if(LOG.isLoggable(Level.FINER)) {
      LOG.log(Level.FINER, "Prefix: {0}, param: {1}, class name: {2}", 
              new Object[]{prefix, paramValue, builder});
    }
    return builder.toString();
  }

  protected StringBuilder toString(HttpServletRequest request) {
    final StringBuilder output = new StringBuilder();
    output.append("Referer: ").append(request.getHeader("referer"));
    output.append("\nURL: ").append(this.getFullURL(request));
    return output;
  }

  protected String getFullURL(HttpServletRequest request) {
    final StringBuffer requestURL = request.getRequestURL();
    final String queryString = request.getQueryString();
    if (queryString == null) {
        return requestURL.toString();
    } else {
        return requestURL.append('?').append(queryString).toString();
    }
  }
}
