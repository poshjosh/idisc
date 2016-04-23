package com.idisc.web.servlets;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.request.Getmultipleresults;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.request.RequestHandlerProvider;
import com.idisc.web.servlets.handlers.request.RequestHandlerProviderImpl;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.tribes.util.Arrays;

public class Idisc extends HttpServlet
{
  private final RequestHandlerProvider provider;
  
  public Idisc() {
      this.provider = new RequestHandlerProviderImpl();
  }

  public void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    this.processRequest_old(request, response);
  } 
  
  public void processRequest_old(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String[] paramNames = provider.getRequestHandlerParamNames(request);

    if ((paramNames == null) || (paramNames.length == 0)) {
      throw new FileNotFoundException(request.getRequestURI());
    }

    XLogger.getInstance().log(Level.FINE, "Parameter names: {0}", getClass(), Arrays.toString(paramNames));
    
    final String firstParamName = paramNames[0];

    RequestHandler requestHandler;
    if (paramNames.length == 1)
    {
      requestHandler = provider.getRequestHandler(firstParamName);
        
      XLogger.getInstance().log(Level.FINER, "Request Handler: {0}", getClass(), requestHandler);
    }
    else
    {
      requestHandler = new Getmultipleresults(provider);
    }
        
    if (requestHandler == null) {
      throw new FileNotFoundException(request.getRequestURI());
    }
    ResponseHandler responseHandler = requestHandler.getResponseHandler(request);
      
    try
    {
      
      XLogger.getInstance().log(Level.FINE, "Processing request for: {0}", getClass(), firstParamName);
      
      Object output = requestHandler.processRequest(request, response);
      
      responseHandler.processResponse(request, response, firstParamName, output);
      
      // VERY IMPORTANT ASSIGNMENT to prevent un-ending loop of death
      //
      RequestHandler.RequestHandlerEntry next = new Idisc.HandlerEntry(firstParamName, requestHandler);
      
      while((next = next.getRequestHandler().getNextRequestHandler(request)) != null) {
          
          final String nextName = next.getName();
          final RequestHandler nextReqHandler = next.getRequestHandler();
          
          ResponseHandler nextResHandler = nextReqHandler.getResponseHandler(request);
          
          try{

              XLogger.getInstance().log(Level.FINE, "Processing request for: {0}", getClass(), nextName);
      
              Object nextOutput = nextReqHandler.processRequest(request, response);
          
              nextResHandler.processResponse(request, response, nextName, nextOutput);
              
          }catch(ServletException | IOException e) {
              
              XLogger.getInstance().log(Level.WARNING, "Error processing request for: "+nextName, getClass(), e);
      
              nextResHandler.processResponse(request, response, nextName, e);
          }
      }
      
      responseHandler.sendResponse(request, response, firstParamName, output);
    }
    catch (IOException|ServletException e) {

      XLogger.getInstance().log(Level.WARNING, "Error processing request for: "+firstParamName, getClass(), e);
      
      responseHandler.processResponse(request, response, firstParamName, e);
      
      responseHandler.sendResponse(request, response, firstParamName, e);
    }
  }
  
  private static final class HandlerEntry implements RequestHandler.RequestHandlerEntry {
    private final String name;
    private final RequestHandler requestHandler;
    public HandlerEntry(String name, RequestHandler requestHandler) {
      this.name = name;
      this.requestHandler = requestHandler;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public RequestHandler getRequestHandler() {
      return this.requestHandler;
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  @Override
  public String getServletInfo()
  {
    return "The single Controller Servlet of the WebApp, which routes requests to appropriate "+RequestHandler.class.getName()+"s";
  }
}
