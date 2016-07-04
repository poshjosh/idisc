package com.idisc.web.servlets;

import com.idisc.web.servlets.handlers.ServiceController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class IdiscAsyncNonHtmlResponseServlet extends IdiscSelectiveAsyncServlet {

    public IdiscAsyncNonHtmlResponseServlet() { }

    public IdiscAsyncNonHtmlResponseServlet(ServiceController sc) {
        super(sc);
    }

    @Override
    public boolean isProcessRequestAsync(HttpServletRequest request) {
        
        return !this.getServiceController().getRequestHandler(request).isHtmlResponse(request);
    }
}
