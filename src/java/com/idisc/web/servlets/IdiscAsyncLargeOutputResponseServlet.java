package com.idisc.web.servlets;

import com.idisc.web.servlets.handlers.ServiceController;
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
    public boolean isProcessRequestAsync(HttpServletRequest request) {
        
        return this.getServiceController().getRequestHandler(request).isOutputLarge(request);
    }
}

