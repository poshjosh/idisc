package com.idisc.web.servlets;

import com.bc.util.XLogger;
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
public abstract class AbstractIdiscServlet extends HttpServlet {
    
  private final ServiceController serviceController;

  public AbstractIdiscServlet() {
    this(new ServiceControllerImpl());
  }
  
  public AbstractIdiscServlet(ServiceController sc) { 
    this.serviceController = Objects.requireNonNull(sc);
  }
  
  public abstract void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    XLogger.getInstance().log(Level.FINE, "Query string {0}", this.getClass(), req.getQueryString());
    process(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    XLogger.getInstance().log(Level.FINE, "Query string {0}", this.getClass(), req.getQueryString());
    process(req, resp);
  }
  
  public final ServiceController getServiceController() {
    return serviceController;
  }
  
  @Override
  public String getServletInfo(){
    return "The single Controller Servlet of the WebApp, which routes requests to appropriate "+RequestHandler.class.getName()+"(s)";
  }
}
