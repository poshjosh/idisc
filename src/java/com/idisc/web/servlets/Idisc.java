package com.idisc.web.servlets;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.Getmultipleresults;
import com.idisc.web.servlets.handlers.RequestHandler;
import com.idisc.web.servlets.handlers.RequestHandlerProvider;
import com.idisc.web.servlets.handlers.RequestHandlerProviderImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


















public class Idisc
  extends HttpServlet
{
  private RequestHandlerProvider _rhp_accessViaGetter;
  
  public void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    XLogger.getInstance().log(Level.FINER, "Request URI: {0}", getClass(), request.getRequestURI());
    
    try
    {
      RequestHandlerProvider provider = getRequestHandlerProvider();
      
      String[] paramNames = provider.getRequestHandlerParamNames(request);
      
      if ((paramNames == null) || (paramNames.length == 0)) {
        throw new FileNotFoundException(request.getRequestURI());
      }
      
      RequestHandler handler;
      
      if (paramNames.length == 1)
      {
        handler = provider.getRequestHandler(paramNames[0]);
        
        XLogger.getInstance().log(Level.FINER, "Request Handler: {0}", getClass(), handler);
        
        if (handler == null) {
          throw new FileNotFoundException(request.getRequestURI());
        }
      }
      else
      {
        handler = new Getmultipleresults();
      }
      
      handler.processRequest(request, response);
    }
    catch (IOException|ServletException|RuntimeException e) {
      throw e;
    }
  }
  
  public RequestHandlerProvider getRequestHandlerProvider()
  {
    if (this._rhp_accessViaGetter == null) {
      this._rhp_accessViaGetter = new RequestHandlerProviderImpl();
    }
    return this._rhp_accessViaGetter;
  }
  









  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }
  







  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }
  




  public String getServletInfo()
  {
    return "Short description";
  }
}
