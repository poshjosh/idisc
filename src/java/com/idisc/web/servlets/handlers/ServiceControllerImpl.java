package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.request.Getmultipleresults;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.request.RequestHandlerProviderImpl;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class ServiceControllerImpl extends RequestHandlerProviderImpl implements ServiceController {

  private static final Logger logger = Logger.getLogger(ServiceControllerImpl.class.getName());

  public ServiceControllerImpl() { }
  
  @Override
  public void process(
    final HttpServletRequest request, final HttpServletResponse response, boolean sendResponse) 
      throws ServletException, IOException {
      
    final RequestHandler requestHandler = this.getRequestHandler(request, null);
    
    if(requestHandler == null) {
        
        throw new ServletException("Failed to create request handler for request.\n"+this.toString(request));
    }
    
    final String firstParamName = this.getFirstParameterName(request);
    
    if(firstParamName == null) {
        
        throw new ServletException("Required parameter "+this.getRequestHandlerParamName()+" is missing");
    }
    
    process(requestHandler, request, response, firstParamName, sendResponse);
  }
  
  @Override
  public void processAsync(
    final AsyncContext asyncContext, final boolean sendResponse) 
      throws ServletException, IOException {
     
    final HttpServletRequest request = (HttpServletRequest)asyncContext.getRequest();
    final HttpServletResponse response = (HttpServletResponse)asyncContext.getResponse();
      
    final RequestHandler requestHandler = this.getRequestHandler(request, null);
    
    if(requestHandler == null) {
        
        throw new ServletException("Failed to create request handler for request.\n"+this.toString(request));
    }
    
    final String firstParamName = this.getFirstParameterName(request);
    
    processAsync(asyncContext, requestHandler, request, response, firstParamName, sendResponse);
  }
  
  public void process(
      final RequestHandler requestHandler,
      final HttpServletRequest request, 
      final HttpServletResponse response, 
      final String paramName,
      final boolean sendResponse) 
      throws ServletException, IOException {
      
      Object output;
      ResponseHandler responseHandler;
      
      try{
          
        XLogger.getInstance().log(Level.FINE, "Processing request for: {0}", getClass(), paramName);

        output = requestHandler.processRequest(request);
        
        responseHandler = requestHandler.getResponseHandler(request);

      }catch(ServletException | IOException e) {

        XLogger.getInstance().log(Level.WARNING, "Error processing request for: "+paramName, this.getClass(), e);
        
        output = e;
        
        responseHandler = requestHandler.getErrorResponseHandler(request);
      }
      
      this.processResponse(responseHandler, request, response, paramName, output, sendResponse);
  }
 
  public void processAsync(
      final AsyncContext asyncContext,
      final RequestHandler requestHandler,
      final HttpServletRequest request, 
      final HttpServletResponse response, 
      final String paramName,
      final boolean sendResponse) 
      throws ServletException, IOException {
      
      Object output;
      ResponseHandler responseHandler;
      
      try{
          
        XLogger.getInstance().log(Level.FINE, "Processing request for: {0}", getClass(), paramName);

        output = requestHandler.processRequest(request);
        
        responseHandler = requestHandler.getResponseHandler(request);

      }catch(ServletException | IOException e) {

        XLogger.getInstance().log(Level.WARNING, "Error processing request for: "+paramName, this.getClass(), e);
        
        output = e;
        
        responseHandler = requestHandler.getErrorResponseHandler(request);
      }
      
      this.processResponseAsync(asyncContext, responseHandler, request, response, paramName, output, sendResponse);
  }
  
  public void processResponse(
      final ResponseHandler responseHandler,
      final HttpServletRequest request, 
      final HttpServletResponse response, 
      final String paramName,
      final Object paramValue,
      final boolean sendResponse) throws ServletException, IOException {
      
    logger.log(Level.FINER, "Response handler: {0}", responseHandler);
    
    final Object output = Objects.requireNonNull(
                                responseHandler.processResponse(
                                    request, response, 
                                    Objects.requireNonNull(paramName), 
                                    Objects.requireNonNull(paramValue)
                                )
                            );
    
    if(sendResponse) {
        
      if(this.prepareResponse(responseHandler, request, response, paramName, paramValue)) {
            
        responseHandler.sendResponse(request, response, paramName, output);
      }
    }
  }
      
  public void processResponseAsync(
      final AsyncContext asyncContext,
      final ResponseHandler responseHandler,
      final HttpServletRequest request, 
      final HttpServletResponse response, 
      final String paramName,
      final Object paramValue,
      final boolean sendResponse) throws ServletException, IOException {
      
    logger.log(Level.FINER, "Response handler: {0}", responseHandler);
    
    final Object output = Objects.requireNonNull(
                                responseHandler.processResponse(
                                    request, response, 
                                    Objects.requireNonNull(paramName), 
                                    Objects.requireNonNull(paramValue)
                                )
                            );
   
    if(sendResponse) {
        
      if(this.prepareResponse(responseHandler, request, response, paramName, paramValue)) {
          
        final String requestURI = request.getRequestURI();
          
        final boolean htmlResponse = responseHandler.getContentType().toLowerCase().contains("html"); 
        
        logger.finer(() -> "HTML response: "+htmlResponse+", URI: " + requestURI);

        if(htmlResponse) {
            
          final String targetPage = responseHandler.getContext().getTargetPage(paramName, paramValue);

          asyncContext.dispatch(targetPage);
            
        }else{

          responseHandler.sendResponse(request, response, paramName, output);
          
          try{
            asyncContext.complete();
          }catch(IllegalStateException thrownIfAlreadyCompleted) {
            logger.log(Level.FINE, "{0}", thrownIfAlreadyCompleted.toString());
          }
        }
      }
    }
  }
  
  public boolean prepareResponse(
      final ResponseHandler responseHandler,
      final HttpServletRequest request, 
      final HttpServletResponse response, 
      final String paramName,
      final Object paramValue) throws ServletException, IOException {
      
    if(!response.isCommitted()) {

        ResponseContext context = responseHandler.getContext();

        response.setCharacterEncoding(responseHandler.getCharacterEncoding());
        response.setContentType(responseHandler.getContentType());
        response.setStatus(context.getStatusCode(paramName, paramValue));

        return true;

    }else{

        final String sessionId = String.valueOf(request.getSession() == null ? null : request.getSession().getId());

        logger.warning(() -> "Response already committed for session. ID: " + sessionId +
            ", requestURI: " + request.getRequestURI());

        return false;
    }
  }
  
  public String getFirstParameterName(final HttpServletRequest request) throws ServletException {
    
    String [] paramNames = this.getRequestHandlerNames(request);
    
    return paramNames[0];
  }
  
  @Override
  public RequestHandler getRequestHandler(final HttpServletRequest request, RequestHandler outputIfNone) throws ServletException {
      
    super.getRequestHandler(request, outputIfNone);
   
    final String [] paramNames = this.getRequestHandlerNames(request);
    
    assert paramNames.length != 0;

    RequestHandler output;

    if (paramNames.length == 1){
      output = this.getRequestHandler(paramNames[0], outputIfNone);
    }else if (paramNames.length > 1){
      output = new Getmultipleresults(this);
    }else{
      throw new ServletException("Failed to resolve request handler names for request.\n"+this.toString(request));  
    }
        
    logger.log(Level.FINER, "Request Handler: {0}", output);
    
    return output;
  }
}
