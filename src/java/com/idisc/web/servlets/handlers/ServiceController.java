package com.idisc.web.servlets.handlers;

import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public interface ServiceController {
    
    void process(final HttpServletRequest request, final HttpServletResponse response, boolean sendResponse) throws ServletException, IOException;

    void process(final RequestHandler requestHandler, final HttpServletRequest request, final HttpServletResponse response, final String paramName, final boolean sendResponse) throws ServletException, IOException;

    void processAsync(final AsyncContext asyncContext, boolean sendResponse) throws ServletException, IOException;

    void processAsync(final AsyncContext asyncContext, final RequestHandler requestHandler, final HttpServletRequest request, final HttpServletResponse response, final String paramName, final boolean sendResponse) throws ServletException, IOException;
}