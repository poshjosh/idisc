package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.BaseHandler;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractResponseHandler<V, O> extends BaseHandler implements ResponseHandler<V, O> {
    
  private final ResponseContext<V> context;
  
  public AbstractResponseHandler(ResponseContext context) {
    this.context = context;
  }
  
  @Override
  public void processAndSendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException {
      
    O output = this.processResponse(request, response, name, value);
    
    this.prepareResponse(request, response, name, value);
    
    this.sendResponse(request, response, name, output);
  }
  
  protected void prepareResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException {
      
    int statusCode = context.getStatusCode(name, value);
    String contentType = this.getContentType();
    String charEncoding = this.getCharacterEncoding();
    
XLogger.getInstance().log(Level.FINE, "Response status code: {0}, contentType: {1}, character encoding: {2}", 
        getClass(), statusCode, contentType, charEncoding);

    if(!response.isCommitted()) {

      response.setStatus(statusCode);

      response.setContentType(contentType);

      response.setCharacterEncoding(charEncoding);
        
    }else{
      
XLogger.getInstance().log(Level.WARNING, 
"#prepareResponse. Response is already committed. Cannot prepare response for: {0}", 
this.getClass(), name);
    }
  }
  
  @Override
  public String getCharacterEncoding() {
    return "UTF-8";
  }

  @Override
  public final ResponseContext<V> getContext() {
    return context;
  }
}
