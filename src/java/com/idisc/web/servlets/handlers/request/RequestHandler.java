package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract interface RequestHandler<V, O> {
    
  public abstract ResponseHandler<V, O> getResponseHandler(HttpServletRequest request);
  
  public abstract ResponseHandler<Throwable, O> getErrorResponseHandler(HttpServletRequest request); 
  
  public abstract boolean isOutputLarge(HttpServletRequest request);

  public abstract boolean isProtected();
  
  public abstract V processRequest(HttpServletRequest request)
          throws ServletException, IOException;
}
