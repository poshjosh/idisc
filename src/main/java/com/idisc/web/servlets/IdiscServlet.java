package com.idisc.web.servlets;

import java.util.logging.Logger;
import com.idisc.web.servlets.handlers.ServiceController;
import com.idisc.web.servlets.handlers.ServiceControllerImpl;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class IdiscServlet extends HttpServlet {
    
  private transient static final Logger LOG = Logger.getLogger(IdiscServlet.class.getName());
    
  private final ServiceController serviceController;

  public IdiscServlet() {
    this(new ServiceControllerImpl());
  }
  
  public IdiscServlet(ServiceController sc) { 
    this.serviceController = Objects.requireNonNull(sc);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if(LOG.isLoggable(Level.FINE)){
      LOG.log(Level.FINE, "Query string {0}", request.getQueryString());
    }
    process(request, response);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(LOG.isLoggable(Level.FINE)){
      LOG.log(Level.FINE, "Query string {0}", req.getQueryString());
    }
    process(req, resp);
  }
  
  public void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
      
    this.serviceController.process(request, response, true);
  }

  public final ServiceController getServiceController() {
    return serviceController;
  }
  
  @Override
  public String getServletInfo(){
    return "The single Controller Servlet of the WebApp, which routes requests to appropriate "+RequestHandler.class.getName()+"(s)";
  }
}
