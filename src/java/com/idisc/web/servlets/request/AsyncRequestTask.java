package com.idisc.web.servlets.request;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.ServiceController;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;

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
            XLogger.getInstance().log(Level.WARNING, "Thread: "+Thread.currentThread().getName(), this.getClass(), e);
        }
    }
}
