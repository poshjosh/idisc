package com.idisc.web.servlets;

import com.idisc.web.Attributes;
import com.idisc.web.servlets.handlers.ServiceController;
import com.idisc.web.servlets.handlers.ServiceControllerImpl;
import com.idisc.web.servlets.request.AsyncRequestTask;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class IdiscAsyncServlet extends AbstractIdiscServlet {
    
  private final ServiceController serviceController;

  public IdiscAsyncServlet() {
      
    this(new ServiceControllerImpl());
  }
  
  public IdiscAsyncServlet(ServiceController sc) {
    this.serviceController = sc;
  }
  
  @Override
  public void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {

    if(!this.isProcessRequestAsync()) {

        serviceController.process(request, response, true);
        
    }else{
    
      AsyncContext asyncContext = request.startAsync(request, response);
    
      ExecutorService svc = getRequestExecutorService(
              request.getServletContext(), Attributes.ASYNCREQUEST_EXECUTOR_SERVICE, true);
    
      svc.submit(new AsyncRequestTask(asyncContext, serviceController));
    }
  }
}