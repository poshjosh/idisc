package com.idisc.web.servlets;

import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import java.io.IOException;
import java.util.Queue;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class IdiscAsyncServlet extends HttpServlet {
    
  private final boolean processRequestAsync;  
    
  public IdiscAsyncServlet() { 
    processRequestAsync = WebApp.getInstance().getConfiguration().getBoolean(ConfigNames.PROCESS_REQUEST_ASYNC, true);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    process(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    process(req, resp);
  }
  
  public void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {

    ServiceController sc = new ServiceController();
    
    if(!this.processRequestAsync) {
        
        sc.process(request, response);
        
    }else{
    
      RequestHandler rh = sc.getRequestHandler(request);
    
//    if(rh instanceof SearchHandler) {
        
        request.setAttribute("serviceController", sc);
        request.setAttribute("requestHandler", rh);
    
        AsyncContext asyncContext = request.startAsync(request, response);

        Queue<AsyncContext> requestQueue = (Queue<AsyncContext>)request.getServletContext().getAttribute("requestQueue");

        requestQueue.add(asyncContext);

//    }else{
        
//        String [] paramNames = sc.getRequestHandlerParamNames(request);

//        sc.process(rh, request, response, paramNames[0], true);
//    }
    }
  }
  
  @Override
  public String getServletInfo(){
    return "The single Controller Servlet of the WebApp, which routes requests to appropriate "+RequestHandler.class.getName()+"(s)";
  }
}