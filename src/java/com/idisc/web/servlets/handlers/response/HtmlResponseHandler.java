package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlResponseHandler<V> extends AbstractResponseHandler<V, Object> {

  private String targetPage;  
  
  public HtmlResponseHandler(ResponseContext context) {
    super(context);
  }

  @Override
  public String getContentType() {
    return "text/html;charset=" + this.getCharacterEncoding();
  }

  @Override
  public Object processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V message)
    throws ServletException, IOException {
      
//    try{
//      this.printFirstDateLastDateAndFeedIds(Level.INFO, "Html response for: " + name, (List<com.idisc.pu.entities.Feed>)message);
//    }catch(Exception e) { }
    
XLogger.getInstance().entering(this.getClass(), "#processResponse(HttpServletRequest, HttpServletRequest, String, V)", name);
      
    targetPage = this.getContext().getTargetPage(name, message); 
    
    // Session attribute
    //
    Object output = this.getContext().format(name, message);
    
    Objects.requireNonNull(output);
    
    this.setAttributeForAsync(request, name, output);
    
XLogger.getInstance().log(Level.FINER, "{0} = {1}", getClass(), name, output);
    
    // @related userMessage
    //
    // Request attribute
    //
    String userMessage = this.getContext().getResponseMessage(name, message);
    
    if(userMessage != null) {
      this.setAttributeForAsync(request, "userMessage", userMessage);
    }
    
XLogger.getInstance().log(Level.FINE, "Request param name: {0}, {1} = {2}", 
        getClass(), name, "userMessage", userMessage);

    return output;    
  }
  
  @Override
  public void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Object message)
    throws ValidationException, ServletException, IOException {
      
XLogger.getInstance().entering(this.getClass(), "#sendResponse(HttpServletRequest, HttpServletRequest, String, Object)", name);
    
XLogger.getInstance().log(Level.FINE, "Target Page: {0}", getClass(), targetPage);

    request.getRequestDispatcher(targetPage).forward(request, response);
  }
}
