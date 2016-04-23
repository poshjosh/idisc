package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.InstallationException;
import com.idisc.web.exceptions.LoginException;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlResponseHandler<V>
  extends AbstractResponseHandler<V>
{
    
  protected String getResponseMessage(HttpServletRequest request, String name, V value)
  {
    return null;
  }
  
  protected String getResponseMessage(HttpServletRequest request, String name, Throwable value)
  { 
    Object oval =  this.getOutput(request, name, value);
    
    return oval == null ? "Unexpected error" : oval.toString();
  }

  @Override
  public String getContentType(HttpServletRequest request)
  {
    return "text/html;charset=" + getCharacterEncoding(request);
  }

  @Override
  public void processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V message)
    throws ServletException, IOException
  {

    super.processResponse(request, response, name, message);
    
    // Session attribute
    //
    Object output = getOutput(request, name, message);
    request.getSession().setAttribute(name, output);
XLogger.getInstance().log(Level.FINER, "{0} = {1}", getClass(), name, output);
    
    // @related userMessage
    //
    // Request attribute
    //
    String userMessage = this.getResponseMessage(request, name, message);
    request.setAttribute("userMessage", userMessage);
XLogger.getInstance().log(Level.FINE, "Request param name: {0}, {1} = {2}", 
        getClass(), name, "userMessage", userMessage);
  }
  
  @Override
  public void processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Throwable message)
    throws ServletException, IOException
  {
    
    super.processResponse(request, response, name, message);
    
    // Session attribute
    //
    Object output = getOutput(request, name, message);
    request.getSession().setAttribute(name, output);
XLogger.getInstance().log(Level.FINER, "{0} = {1}", getClass(), name, output);
    
    // @related userMessage
    //
    // Request attribute
    //
    String userMessage = this.getResponseMessage(request, name, message);
    request.setAttribute("userMessage", userMessage);
XLogger.getInstance().log(Level.FINE, "Request param name: {0}, {1} = {2}", 
        getClass(), name, "userMessage", userMessage);
  }

  @Override
  public void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V message)
    throws ValidationException, ServletException, IOException
  {
XLogger.getInstance().entering(this.getClass(), "#sendResponse(HttpServletRequest, HttpServletRequest, X)", null);
    
    String targetPage = getTargetPage(request, name, message);

XLogger.getInstance().log(Level.FINE, "Target Page: {0}", getClass(), targetPage);

    request.getRequestDispatcher(targetPage).forward(request, response);
  }
  
  @Override
  public void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Throwable message)
    throws ValidationException, ServletException, IOException
  {
XLogger.getInstance().entering(this.getClass(), "#sendResponse(HttpServletRequest, HttpServletRequest, Throwable)", null);
    
    String targetPage = getTargetPage(request, name, message);

XLogger.getInstance().log(Level.FINE, "Target Page: {0}", getClass(), targetPage);

    request.getRequestDispatcher(targetPage).forward(request, response);
  }
  
  public String getTargetPage(HttpServletRequest request, String name, V message)
    throws ValidationException
  {
    
    return '/' + name + ".jsp";
  }
  
  public String getTargetPage(HttpServletRequest request, String name, Throwable t)
  {
    String output;
    if(t instanceof LoginException || t instanceof InstallationException ) {
      output = "/login.jsp";
    }else {
      output = "/oops.jsp";  
    }
    return output;
  }
}
