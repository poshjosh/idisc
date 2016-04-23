package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractResponseHandler<V>
  implements ResponseHandler<V>
{
  
  @Override
  public abstract void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException;
  
  @Override
  public abstract void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Throwable value)
    throws ServletException, IOException;
  
  @Override
  public String getCharacterEncoding(HttpServletRequest request)
  {
    return "UTF-8";
  }
  
  @Override
  public void processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V message)
    throws ServletException, IOException
  {
    int statusCode = getStatusCode(request, name, message);
    String contentType = getContentType(request);
    String charEncoding = getCharacterEncoding(request);
    
XLogger.getInstance().log(Level.FINE, "Response status code: {0}, contentType: {1}, character encoding: {2}", 
        getClass(), Integer.valueOf(statusCode), contentType, charEncoding);

    response.setStatus(statusCode);
    
    response.setContentType(contentType);
    
    response.setCharacterEncoding(charEncoding);
  }
  
  @Override
  public void processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Throwable message)
    throws ServletException, IOException
  {
    int statusCode = getStatusCode(request, name, message);
    String contentType = getContentType(request);
    String charEncoding = getCharacterEncoding(request);
    
XLogger.getInstance().log(Level.FINE, "Response status code: {0}, contentType: {1}, character encoding: {2}", 
        getClass(), Integer.valueOf(statusCode), contentType, charEncoding);

    response.setStatus(statusCode);
    
    response.setContentType(contentType);
    
    response.setCharacterEncoding(charEncoding);
  }
  
  @Override
  public int getStatusCode(HttpServletRequest request, String name, V value)
  {
    return 200;
  }
  
  @Override
  public int getStatusCode(HttpServletRequest request, String name, Throwable value)
  {
    if ((value instanceof ValidationException))
      return 400;
    if ((value instanceof FileNotFoundException)) {
      return 404;
    }
    return 500;
  }
  
  @Override
  public Object getOutput(HttpServletRequest request, String name, V value)
  {
    if ((value instanceof Boolean)) {
      return ((Boolean)value).booleanValue() ? "Success" : "Error";
    }
    return value;
  }
  
  @Override
  public Object getOutput(HttpServletRequest request, String name, Throwable value)
  {
    if ((value instanceof ServletException)) {
      return value.getLocalizedMessage();
    }
    return "Unexpected Error";
  }
}
