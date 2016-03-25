package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





public abstract class AbstractResponseHandler<X>
  implements ResponseHandler<X>
{
  private String parameterName;
  
  protected abstract void sendResponse(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, X paramX)
    throws ServletException, IOException;
  
  protected abstract void sendResponse(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Throwable paramThrowable)
    throws ServletException, IOException;
  
  public String getCharacterEncoding(HttpServletRequest request)
  {
    return "UTF-8";
  }
  


  public final void respond(HttpServletRequest request, HttpServletResponse response, X message)
    throws ServletException, IOException
  {
    int statusCode = getStatusCode(request, message);
    String contentType = getContentType(request);
    String charEncoding = getCharacterEncoding(request);
    
    XLogger.getInstance().log(Level.FINE, "Response status code: {0}, contentType: {1}, character encoding: {2}", getClass(), Integer.valueOf(statusCode), contentType, charEncoding);
    

    response.setStatus(statusCode);
    
    response.setContentType(contentType);
    
    response.setCharacterEncoding(charEncoding);
    
    sendResponse(request, response, message);
  }
  


  public final void respond(HttpServletRequest request, HttpServletResponse response, Throwable message)
    throws ServletException, IOException
  {
    int statusCode = getStatusCode(request, message);
    
    response.setStatus(statusCode);
    
    response.setContentType(getContentType(request));
    
    sendResponse(request, response, message);
  }
  
  public int getStatusCode(HttpServletRequest request, X x)
  {
    return 200;
  }
  
  public int getStatusCode(HttpServletRequest request, Throwable t)
  {
    if ((t instanceof ValidationException))
      return 400;
    if ((t instanceof FileNotFoundException)) {
      return 404;
    }
    return 500;
  }
  


  public Object getMessage(HttpServletRequest request, X x)
  {
    if ((x instanceof Boolean)) {
      return ((Boolean)x).booleanValue() ? "Success" : "Error";
    }
    return x;
  }
  

  public Object getMessage(HttpServletRequest request, Throwable t)
  {
    if ((t instanceof ServletException)) {
      return t.getLocalizedMessage();
    }
    return "Unexpected Error";
  }
  
  protected final String checkParameterName() throws ValidationException
  {
    String output;
    if ((output = getParameterName()) == null) {
      throw new ValidationException("Invalid request path");
    }
    return output;
  }
  
  public String getParameterName()
  {
    return this.parameterName;
  }
  
  public void setParameterName(String parameterName)
  {
    this.parameterName = parameterName;
  }
}
