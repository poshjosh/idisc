package com.idisc.web.servlets.handlers;

import com.idisc.core.User;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @(#)RequestHandler.java   16-Oct-2014 10:04:24
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
public interface RequestHandler<X> {

    X execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    User findUser(HttpServletRequest request, HttpServletResponse response);

    int getErrorCode(Throwable t);

    String getErrorMessage(Throwable t);

    RequestHandlerProvider getRequestHandlerProvider();

    int getSuccessCode(X x);

    Object getSuccessMessage(X x);

    User getUser(HttpServletRequest request);

    boolean isLoggedIn(HttpServletRequest request);

    /**
     * If true a use needs to be logged in to call this request handler.
     * The default is true.
     * @return
     */
    boolean isProtected();

    void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void respond(HttpServletRequest request, HttpServletResponse response, int status, Object message)
            throws ServletException, IOException;
    
    void setLoggedIn(HttpServletRequest request, User user);

    User setLoggedIn(HttpServletRequest request, Map authuserdetails, boolean create) throws ServletException;

    void setLoggedout(HttpServletRequest request);

    User tryLogin(HttpServletRequest request, HttpServletResponse response);
}
