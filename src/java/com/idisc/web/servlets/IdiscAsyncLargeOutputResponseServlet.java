package com.idisc.web.servlets;

import com.idisc.web.servlets.handlers.ServiceController;
import com.idisc.web.servlets.handlers.request.RequestHandler;
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
        RequestHandler rh = this.getServiceController().getRequestHandler(request);
        return rh == null ? defaultValue : rh.isOutputLarge(request);
    }
}

