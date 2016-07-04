package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.search.SearchResults;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.bc.util.XLogger;
import com.idisc.web.Attributes;
import com.idisc.web.AppContext;
import com.idisc.web.exceptions.LoginException;
import com.idisc.web.servlets.handlers.response.ErrorHandlerContext;
import com.idisc.web.servlets.handlers.response.ObjectToJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.SuccessHandlerContext;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractRequestHandler<V> 
        extends SessionUserHandlerImpl 
        implements RequestHandler<V, Object> {
    
  private String responseFormat;
  
  private ResponseHandler<V, Object> responseHandler;
  
  private ResponseHandler<Throwable, Object> errorResponseHandler;
  
  public AbstractRequestHandler() { }
  
  public abstract V execute(HttpServletRequest request)
          throws ServletException, IOException;
  
  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
      return false;
  }
  
  public <X> SearchResults<X> getSearchResults(
          HttpSession session, Class<X> enityType, String query, Date after, int limit) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("query", query);
    parameters.put("after", after);
    parameters.put("limit", limit);
    AppContext appCtx = (AppContext)session.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    SearchResults<X> searchResults = appCtx.getSearchHandlerFactory(true).get(
            session.getId(), enityType, parameters, true);
    return searchResults;
  }
  
  @Override
  public final V processRequest(HttpServletRequest request) 
      throws ServletException, IOException {
      
      if ((isProtected()) && (!isLoggedIn(request))) {
        tryLogin(request);
      }
      
      if ((isProtected()) && (!isLoggedIn(request))) {
        throw new LoginException("Login required");
      }
      
      XLogger.getInstance().log(Level.FINER, "Executing: {0}", getClass(), this.getClass().getName());
      
      V x = execute(request);
      
      XLogger.getInstance().log(Level.FINER, "Execution finished, output:\n{0}", getClass(), x);
      
      return x;
  }
  
  @Override
  public boolean isProtected() {
    return true;
  }
  
  public <X> ResponseHandler<X, Object> createResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
    ResponseHandler<X, Object> output;
    if (this.isHtmlResponse(request)) {
      output = new HtmlResponseHandler(request, context);
    } else {
      output = new ObjectToJsonResponseHandler(request, context);  
    }
XLogger.getInstance().log(Level.FINER, "Response handler type: {0}", 
        this.getClass(), output.getClass().getName());
    return output;
  }
  
  protected ResponseContext<Throwable> createErrorResponseContext(HttpServletRequest request) {
    return new ErrorHandlerContext(request);  
  }
  
  protected ResponseContext<V> createSuccessResponseContext(HttpServletRequest request) {
    return new SuccessHandlerContext(request);  
  }
  
  @Override
  public boolean isHtmlResponse(HttpServletRequest request) {
    return this.isHtmlResponse(this.getResponseFormat(request));
  }
  
  public boolean isHtmlResponse(String responseFormat) {
    boolean output = (responseFormat != null) && (responseFormat.contains("text/html"));
    return output;
  }

  public String getResponseFormat(HttpServletRequest request) {
      
    if(responseFormat == null) {
        
      responseFormat = request.getParameter("format");

XLogger.getInstance().log(Level.FINER, "format = {0}", this.getClass(), responseFormat);       

      if (responseFormat == null) {
            
        Boolean flag = (Boolean)request.getAttribute(Attributes.REQUEST_FROM_OR_TO_WEBPAGE);
          
        if(flag != null && flag) {
              
          responseFormat = "text/html";
        }
      }
XLogger.getInstance().log(Level.FINER, "Response format: {0}", this.getClass(), responseFormat);
    }
    
    return responseFormat;
  }

  @Override
  public final ResponseHandler<V, Object> getResponseHandler(HttpServletRequest request) {
    if(responseHandler == null) {
        responseHandler = this.createResponseHandler(
                request, this.createSuccessResponseContext(request));
    }  
    return responseHandler;
  }
  
  @Override
  public final ResponseHandler<Throwable, Object> getErrorResponseHandler(HttpServletRequest request) {
    if(errorResponseHandler == null) {
        errorResponseHandler = this.createResponseHandler(
                request, this.createErrorResponseContext(request));
    }  
    return errorResponseHandler;
  }
}
