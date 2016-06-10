package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.bc.util.XLogger;
import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.LoginException;
import com.idisc.web.servlets.handlers.response.ObjectToJsonResponseHandler;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractRequestHandler<V> 
        extends SessionUserHandlerImpl 
        implements RequestHandler<V, Object> {
  
  private final boolean streamLargeResponses;

  public AbstractRequestHandler() {
    this(WebApp.getInstance().getConfiguration().getBoolean(ConfigNames.STREAM_LARGE_RESPONSES, true));
  }
  
  public AbstractRequestHandler(boolean streamLargeResponses) {
    this.streamLargeResponses = streamLargeResponses;
  }
  
  public abstract V execute(HttpServletRequest request)
          throws ServletException, IOException;
  
  @Override
  public boolean isOutputLarge() {
    return false;
  }
  
  @Override
  public V processRequest(HttpServletRequest request) 
      throws ServletException, IOException {
      
    XLogger.getInstance().entering(getClass(), "processRequest(HttpServletRequest)", null);

      if ((isProtected()) && (!isLoggedIn(request))) {
        tryLogin(request);
      }
      
      if ((isProtected()) && (!isLoggedIn(request))) {
        throw new LoginException("Login required");
      }
      
      XLogger.getInstance().log(Level.FINER, "Executing", getClass());
      
      V x = execute(request);
      
      XLogger.getInstance().log(Level.FINER, "Execution finished, output:\n{0}", getClass(), x);
      
      return x;
  }
  
  @Override
  public boolean isProtected() {
    return true;
  }
  
  public final boolean isHtmlResponse(HttpServletRequest request) {
    String responseFormat = getResponseFormat(request);
    boolean output = (responseFormat != null) && (responseFormat.contains("text/html"));
    return output;
  }

  private ResponseHandler<V, Object> responseHandler;
  @Override
  public final ResponseHandler<V, Object> getResponseHandler(HttpServletRequest request)
  {
      if(responseHandler == null) {
          responseHandler = this.createResponseHandler(request);
      }
      return responseHandler;
  }
  
  public ResponseHandler<V, Object> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<V, Object> output;
    if (this.isHtmlResponse(request)) {
      output = new HtmlResponseHandler();
    } else {
      output = new ObjectToJsonResponseHandler();  
    }
    
    return output;
  }
  
  public String getResponseFormat(HttpServletRequest request)
  {
    String format = request.getParameter("format");
XLogger.getInstance().log(Level.FINER, "Request.getParameter(format): {0}", this.getClass(), format);
    
    if (format == null)
    {
      String requestUri = request.getRequestURI();
      
      int start = requestUri.lastIndexOf('/');
      
      if (start != -1)
      {
        int n = requestUri.indexOf('.', start);
        
        format = n == -1 ? null : "text/html";
      }
    }
XLogger.getInstance().log(Level.FINER, "Response format: {0}", this.getClass(), format);
    
    return format;
  }

  public boolean isStreamLargeResponses() {
    return streamLargeResponses;
  }
}
