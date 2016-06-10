package com.idisc.web.servlets;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.request.Getmultipleresults;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.request.RequestHandlerProviderImpl;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class ServiceController extends RequestHandlerProviderImpl {
    
  public ServiceController() { }
  
  public void process(
    final HttpServletRequest request, final HttpServletResponse response) {
      
    final RequestHandler requestHandler = this.getRequestHandler(request);
    
    final String [] paramNames = this.getRequestHandlerParamNames(request);
    
    final String firstParamName = paramNames[0];
    
    process(requestHandler, request, response, firstParamName, true);
  }
  
  public void process(
      final RequestHandler requestHandler,
      final HttpServletRequest request, 
      final HttpServletResponse response, 
      final String paramName,
      final boolean sendResponse) {
      
      ResponseHandler responseHandler = requestHandler.getResponseHandler(request);
      
      try{
          
        XLogger.getInstance().log(Level.FINE, "Processing request for: {0}", getClass(), paramName);

        Object output = requestHandler.processRequest(request);

        responseHandler.processResponse(request, response, paramName, output);

        if(sendResponse) {
            responseHandler.sendResponse(request, response, paramName, output);
        }
        
      }catch(ServletException | IOException e) {

        XLogger.getInstance().log(Level.WARNING, "Error processing request for: "+paramName, this.getClass(), e);
        
        try{ 

          responseHandler.processResponse(request, response, paramName, e);

          if(sendResponse) {
            responseHandler.sendResponse(request, response, paramName, e);
          }
          
        }catch(ServletException | IOException e1) {
            
          XLogger.getInstance().log(Level.WARNING, "Error sending response to request for: "+paramName, this.getClass(), e1);
        }
      }
  }
  
  @Override
  public RequestHandler getRequestHandler(final HttpServletRequest request) {
    
    String [] paramNames = this.getRequestHandlerParamNames(request);
    
    final String firstParamName = paramNames[0];

    RequestHandler output;
    if (paramNames.length == 1){
      output = this.getRequestHandler(firstParamName);
    }else{
      output = new Getmultipleresults(this);
    }
        
    XLogger.getInstance().log(Level.FINER, "Request Handler: {0}", getClass(), output);
    
    return output;
  }
}