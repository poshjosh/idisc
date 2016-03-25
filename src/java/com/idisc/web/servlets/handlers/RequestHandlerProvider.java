package com.idisc.web.servlets.handlers;

import javax.servlet.http.HttpServletRequest;


/**
 * @(#)RequestHandlerProvider.java   25-Feb-2015 08:35:20
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
public interface RequestHandlerProvider {

    String getRequestHandlerParamName(HttpServletRequest request);

    String [] getRequestHandlerParamNames(HttpServletRequest request);
    
    RequestHandler getRequestHandler(HttpServletRequest request);

    RequestHandler getRequestHandler(String handlerRequestParamName);

    RequestHandler getRequestHandler(Class<RequestHandler> aClass);
}
