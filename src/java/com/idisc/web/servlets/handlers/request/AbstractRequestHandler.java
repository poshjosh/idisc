package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.search.SearchResults;
import com.bc.util.JsonBuilder;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.bc.util.XLogger;
import com.idisc.core.util.EntityJsonBuilder;
import com.idisc.pu.SearchResultsHandlerFactory;
import com.idisc.web.Attributes;
import com.idisc.web.AppContext;
import com.idisc.web.ConfigNames;
import com.idisc.web.servlets.handlers.response.ErrorHandlerContext;
import com.idisc.web.servlets.handlers.response.ObjectToJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.SuccessHandlerContext;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;

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
          HttpServletRequest request, Class<X> enityType, SearchResults<X> outputIfNone) {
    AppContext appCtx = this.getAppContext(request);
    SearchResultsHandlerFactory sf = appCtx.getSearchHandlerFactory(false);
    SearchResults<X> searchResults = sf == null ? null : 
            sf.get(request.getSession().getId(), enityType, outputIfNone);
    return searchResults == null ? outputIfNone : searchResults;
  }
  
  @Override
  public final V processRequest(HttpServletRequest request) 
      throws ServletException, IOException {
      
//      if (isProtected() && !isLoggedIn(request)) {
//        tryLogin(request);
//      }
      
//      if (isProtected() && !isLoggedIn(request)) {
//        throw new LoginException("Login required");
//      }
      
      XLogger.getInstance().log(Level.FINER, "Executing: {0}", getClass(), this.getClass().getName());
      
      V x = execute(request);
      
      XLogger.getInstance().log(Level.FINER, "Execution finished, output:\n{0}", getClass(), x);
      
      return x;
  }
  
  public <X> ResponseHandler<X, Object> createResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
    ResponseHandler<X, Object> output;
    if (this.isHtmlResponse(request)) {
      output = this.createHtmlResponseHandler(request, context);
    } else {
      output = this.createJsonResponseHandler(request, context);
    }
XLogger.getInstance().log(Level.FINER, "Response handler type: {0}", 
        this.getClass(), output.getClass().getName());
    return output;
  }
  
  protected <X> ResponseHandler<X, Object> createJsonResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
    final boolean tidyOutput = this.isTidyOutput(request);
    final boolean plainTextOnly = this.isPlainTextOnly(request);
    final int bufferSize = this.getMaxTextLengthPerItem(request);
    JsonBuilder jsonBuilder = 
            new EntityJsonBuilder(tidyOutput, plainTextOnly, bufferSize);
    ResponseHandler<X, Object> output = new ObjectToJsonResponseHandler(context, jsonBuilder, bufferSize);  
    return output;
  }
  
  protected <X> ResponseHandler<X, Object> createHtmlResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
    ResponseHandler<X, Object> output = new HtmlResponseHandler(context);
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

  public boolean isPlainTextOnly(HttpServletRequest request) {
    String contentType = request.getParameter("content-type");
    boolean b = (contentType != null) && (contentType.contains("text/plain"));
    XLogger.getInstance().log(Level.FINER, "Plain text only: {0}", getClass(), b);
    return b;
  }

  public boolean isTidyOutput(HttpServletRequest request) {
    boolean tidy;
    String tidyParam = request.getParameter("tidy");
    if(tidyParam != null) {
        tidy = "1".equals(tidyParam) || "true".equalsIgnoreCase(tidyParam);
    }else{
        tidy = !this.getAppContext(request).isProductionMode();
    }
    return tidy;
  }
  
  public int getMaxTextLengthPerItem(HttpServletRequest request) {
      Configuration config = this.getAppContext(request).getConfiguration();
      final int defaultLen = config.getInt(ConfigNames.DEFAULT_CONTENT_LENGTH, 1000);
      return getInt(request, "maxlen", defaultLen);
  }
  
  private int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }
}
