package com.idisc.web.servlets;

import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.ServiceController;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class IdiscAsyncLargeOutputResponseServlet extends IdiscSelectiveAsyncServlet {

    public IdiscAsyncLargeOutputResponseServlet() { }

    public IdiscAsyncLargeOutputResponseServlet(ServiceController sc) {
        super(sc);
    }

    @Override
    public boolean isProcessRequestAsync(HttpServletRequest request, boolean defaultValue) {
        try{
            RequestHandler rh = this.getServiceController().getRequestHandler(request, null);
            return rh == null ? defaultValue : rh.isOutputLarge(request);
        }catch(ServletException e) {
            XLogger.getInstance().log(Level.WARNING, "Unexpected error", this.getClass(), e);
            return defaultValue;
        }
    }
}

