package com.idisc.web.servlets;

import com.idisc.web.servlets.handlers.ServiceController;
import com.idisc.web.servlets.handlers.ServiceControllerImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class IdiscSelectiveAsyncServlet extends AbstractIdiscServlet {
    
  private final ServiceController serviceController;

  public IdiscSelectiveAsyncServlet() {
      
    this(new ServiceControllerImpl());
  }
  
  public IdiscSelectiveAsyncServlet(ServiceController sc) {
    this.serviceController = sc;
  }
  
  @Override
  public void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
    
    if(!this.isProcessRequestAsync()) {

        serviceController.process(request, response, true);
        
    }else{
    
//      RequestHandler rh = serviceController.getRequestHandler(request);
    
//      if(rh.isOutputLarge(request)) {
      
//        AsyncContext asyncContext = request.startAsync(request, response);

//        ExecutorService svc = getRequestExecutorService(
//                request.getServletContext(), Attributes.ASYNCREQUEST_EXECUTOR_SERVICE, true);

//        svc.submit(new AsyncRequestTask(asyncContext, serviceController));
        
//      }else{
         
//        serviceController.process(request, response, true);
//      }
    }
  }
}
