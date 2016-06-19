package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.InstallationException;
import com.idisc.web.exceptions.LoginException;
import com.idisc.web.exceptions.ValidationException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class ErrorHandlerContext implements ResponseContext<Throwable>, Serializable {

  public ErrorHandlerContext(HttpServletRequest request) { }
    
  @Override
  public Object format(String name, Throwable value) {
    if ((value instanceof ServletException)) {
      return value.getLocalizedMessage();
    }
    return "Unexpected Error";
  }
  
  @Override
  public int getStatusCode(String name, Throwable value) {
    if ((value instanceof ValidationException))
      return 400;
    if ((value instanceof FileNotFoundException)) {
      return 404;
    }
    return 500;
  }

  @Override
  public String getResponseMessage(String name, Throwable value) { 
      
    Object oval =  this.format(name, value);
    
    return oval == null ? "Unexpected error" : oval.toString();
  }

  @Override
  public String getTargetPage(String name, Throwable t) {
    String output;
    if(t instanceof LoginException || t instanceof InstallationException ) {
      output = "/login.jsp";
    }else {
      output = "/oops.jsp";  
    }
XLogger.getInstance().log(Level.FINE, "{0} = {1}. Target page: {2}", this.getClass(), name, t, output);
    return output;
  }
}
