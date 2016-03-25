package com.idisc.web.servlets.handlers;

import com.bc.util.JsonFormat;
import com.bc.util.XLogger;
import com.idisc.web.AppProperties;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;


/**
 * @(#)AbstractHandler.java   25-Nov-2014 02:55:04
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public abstract class BaseRequestHandler<X> extends AbstractRequestHandler<X> {

    @Override
    public void respond(
            HttpServletRequest request,
            HttpServletResponse response, 
            int status, Object message) 
            throws IOException, ServletException {
        
        String format = this.getResponseFormat(request);
        
        if("text/html".equals(format)) {

            this.sendHtmlResponse(request, response, status, message);
            
        }else{ // default
            
            this.sendJsonResponse(request, response, status, message);
        }
    }
    
    public String getResponseFormat(HttpServletRequest request) {
        
        String format = request.getParameter(com.idisc.web.RequestParameters.FORMAT);
        
        if(format == null) {
            
            String requestUri = request.getRequestURI();
            
            int start = requestUri.lastIndexOf('/');
            
            if(start != -1) {
            
                int n = requestUri.indexOf('.', start);

                format = n == -1 ? null : "text/html";
            }
        }

        return format;
    }

    protected void sendJsonResponse(
            HttpServletRequest request,
            HttpServletResponse response, 
            int status, Object message) {
        
        PrintWriter out = null;
        try {

            response.setContentType("text/plain;charset=UTF-8");
            
            response.setStatus(status);
            
            out = response.getWriter();
            
            Configuration config = WebApp.getInstance().getConfiguration();
            
            int maxLimit = config.getInt(AppProperties.MAX_LIMIT, 200);
            int contentLen = config.getInt(AppProperties.DEFAULT_CONTENT_LENGTH, 1000);
            final int len = maxLimit * contentLen;
            
            StringBuilder output = new StringBuilder(len + len/3);
            
            final String key = this.getRequestHandlerProvider().getRequestHandlerParamName(request);
        
            JsonFormat jsonFmt = new JsonFormat();
            jsonFmt.setTidyOutput(true);
            
            jsonFmt.appendJSONString(key, message, output);
            
            out.println(output.toString());
            
        }catch(Throwable t) {
            
            XLogger.getInstance().log(Level.WARNING, 
                    "Failed to write response", this.getClass(), t);
            
        } finally { 
            if(out != null) {
                out.close();
            }
        }
    }

    protected void sendHtmlResponse(
            HttpServletRequest request,
            HttpServletResponse response, 
            int status, Object message) 
            throws ServletException, IOException {
        
        final String key = this.getRequestHandlerProvider().getRequestHandlerParamName(request);
        
        request.setAttribute(key, message);
        
        String targetPage = this.getTargetPage(request);
XLogger.getInstance().log(Level.FINER, "Page: {0}, key: {1}, message: {2}", this.getClass(), targetPage, key, message);

        response.setContentType("text/html;charset=UTF-8"); 
        
        response.setStatus(status);
        
        request.getRequestDispatcher(targetPage).forward(request, response);
    }
    
    /**
     * if request contains parameter <tt>req=newitem</tt>, then the target page 
     * will be <tt>/newitem.jsp</tt>
     * @param request
     * @return The target page
     * @throws ValidationException 
     */
    public String getTargetPage(HttpServletRequest request) throws ValidationException {
        
        final String key = this.getRequestHandlerProvider().getRequestHandlerParamName(request);
        
        if(key == null) {
            throw new ValidationException("Invalid request path");
        }
        
        return new StringBuilder().append('/').append(key).append(".jsp").toString();
    }

    public String getTargetPage(HttpServletRequest request, Exception e) {
        
        return "/oops.jsp";
    }
}
