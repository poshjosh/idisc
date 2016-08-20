package com.idisc.web.servlets;

import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.servlets.handlers.ServiceController;
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
public abstract class IdiscSelectiveAsyncServlet extends AbstractIdiscServlet {

  public IdiscSelectiveAsyncServlet() { }

  public IdiscSelectiveAsyncServlet(ServiceController sc) {
    super(sc);
  }
  
  public abstract boolean isProcessRequestAsync(final HttpServletRequest request, boolean defaultValue);
    
  @Override
  public void process(
      final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
      
    final ServiceController serviceController = this.getServiceController();
    
    AppContext appCtx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
    if(appCtx.isAsyncProcessingEnabled() && this.isProcessRequestAsync(request, true)) {

        AsyncContext asyncContext = request.startAsync(request, response);

        ExecutorService svc = appCtx.getGlobalExecutorService(true);

        svc.submit(new AsyncRequestTask(asyncContext, serviceController));
        
    }else{
    
      serviceController.process(request, response, true);  
    }
  }
}
