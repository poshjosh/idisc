package com.idisc.web.servlets.request;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.ServiceController;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class AsyncRequestTask implements Runnable {
    
    private final AsyncContext asyncContext;
    
    private final ServiceController serviceController;

    public AsyncRequestTask(AsyncContext asyncContext, ServiceController serviceController) {
        this.asyncContext = asyncContext;
        this.serviceController = serviceController;
    }

    @Override
    public void run() {

        try{

            serviceController.processAsync(asyncContext, true);
            
        }catch(ServletException | IOException | RuntimeException e) {
            final String unexpectedError = "Unexpected Error";
            try{
                XLogger.getInstance().log(Level.WARNING, unexpectedError, this.getClass(), e);
                ServletResponse response = asyncContext.getResponse();
                if(response != null && !response.isCommitted()) {
                    ((HttpServletResponse)response).sendError(500, unexpectedError);
                }
            }catch(Exception inner) {
                XLogger.getInstance().log(Level.WARNING, unexpectedError, this.getClass(), inner);
            }
        }
    }
}
