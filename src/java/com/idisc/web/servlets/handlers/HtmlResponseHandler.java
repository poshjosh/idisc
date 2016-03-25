package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class HtmlResponseHandler<X>
  extends AbstractResponseHandler<X>
{
  public String getContentType(HttpServletRequest request)
  {
    return "text/html;charset=" + getCharacterEncoding(request);
  }
  


  protected void sendResponse(HttpServletRequest request, HttpServletResponse response, X message)
    throws ValidationException, ServletException, IOException
  {
    String parameterName = checkParameterName();
    
    request.setAttribute(parameterName, getMessage(request, message));
    
    String targetPage = getTargetPage(request);
    XLogger.getInstance().log(Level.FINER, "Page: {0}, {1} = {2}", getClass(), targetPage, parameterName, message);
    
    request.getRequestDispatcher(targetPage).forward(request, response);
  }
  


  protected void sendResponse(HttpServletRequest request, HttpServletResponse response, Throwable message)
    throws ValidationException, ServletException, IOException
  {
    String parameterName = checkParameterName();
    
    request.setAttribute(parameterName, getMessage(request, message));
    
    String targetPage = getTargetPage(request);
    XLogger.getInstance().log(Level.FINER, "Page: {0}, {1} = {2}", getClass(), targetPage, parameterName, message);
    
    request.getRequestDispatcher(targetPage).forward(request, response);
  }
  






  public String getTargetPage(HttpServletRequest request)
    throws ValidationException
  {
    String parameterName = checkParameterName();
    
    return '/' + parameterName + ".jsp";
  }
  
  public String getTargetPage(HttpServletRequest request, Exception e)
  {
    return "/oops.jsp";
  }
}
