package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.request.Getmultipleresults;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.request.RequestHandlerProviderImpl;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class ServiceControllerImpl extends RequestHandlerProviderImpl implements ServiceController {

  public ServiceControllerImpl() { }
  
  @Override
  public void process(
    final HttpServletRequest request, final HttpServletResponse response, boolean sendResponse) 
      throws ServletException, IOException {
      
    final RequestHandler requestHandler = this.getRequestHandler(request);
    
    if(requestHandler == null) {
        
        throw new ServletException("Required parameter "+this.getRequestParamName(request)+" is missing");
    }
    
    final String firstParamName = this.getFirstParameterName(request);
    
    if(firstParamName == null) {
        
        throw new ServletException("Required parameter "+this.getRequestParamName(request)+" is missing");
    }
    
    process(requestHandler, request, response, firstParamName, sendResponse);
  }
  
  @Override
  public void processAsync(
    final AsyncContext asyncContext, final boolean sendResponse) 
      throws ServletException, IOException {
     
    final HttpServletRequest request = (HttpServletRequest)asyncContext.getRequest();
    final HttpServletResponse response = (HttpServletResponse)asyncContext.getResponse();
      
    final RequestHandler requestHandler = this.getRequestHandler(request);
    
    if(requestHandler == null) {
        
        throw new ServletException("Required parameter "+this.getRequestParamName(request)+" is missing");
    }
    
    final String firstParamName = this.getFirstParameterName(request);
    
    if(firstParamName == null) {
        
        throw new ServletException("Required parameter "+this.getRequestParamName(request)+" is missing");
    }
    
    processAsync(asyncContext, requestHandler, request, response, firstParamName, sendResponse);
  }
  
  @Override
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
 
  @Override
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
      
    XLogger.getInstance().log(Level.FINER, "Response handler: {0}", getClass(), responseHandler);
    
    final Object output = responseHandler.processResponse(request, response, paramName, paramValue);
    
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
      
    XLogger.getInstance().log(Level.FINER, "Response handler: {0}", getClass(), responseHandler);

    final Object output = responseHandler.processResponse(request, response, paramName, paramValue);
    
    if(sendResponse) {
        
      if(this.prepareResponse(responseHandler, request, response, paramName, paramValue)) {
          
        final String requestURI = request.getRequestURI();
          
        final boolean htmlResponse = responseHandler.getContentType().toLowerCase().contains("html"); 
        
XLogger.getInstance().log(Level.FINER, "HTML response: {0}, URI: {1}", getClass(), htmlResponse, requestURI);

        if(htmlResponse) {
            
          final String targetPage = responseHandler.getContext().getTargetPage(paramName, paramValue);

          asyncContext.dispatch(targetPage);
            
        }else{

          responseHandler.sendResponse(request, response, paramName, output);
          
          asyncContext.complete();
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

XLogger.getInstance().log(Level.FINER, "Response already committed for session ID: {0}, requestURI: {1}", 
    this.getClass(), sessionId, request.getRequestURI());

        return false;
    }
  }
  
  public String getFirstParameterName(final HttpServletRequest request) {
    
    String [] paramNames = this.getRequestHandlerParamNames(request);
    
    return paramNames == null || paramNames.length == 0 ? null : paramNames[0];
  }
  
  @Override
  public RequestHandler getRequestHandler(final HttpServletRequest request) {
      
    String [] paramNames = this.getRequestHandlerParamNames(request);

    RequestHandler output;
    if(paramNames == null || paramNames.length == 0) {
      
      output = null;
      
    }else{
        
      final String firstParamName = paramNames[0];

      if (paramNames.length == 1){
        output = this.getRequestHandler(firstParamName);
      }else{
        output = new Getmultipleresults(this);
      }
    }
        
XLogger.getInstance().log(Level.FINER, "Request Handler: {0}", getClass(), output);
    
    return output;
  }
}
