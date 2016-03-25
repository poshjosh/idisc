package com.idisc.web.servlets.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @(#)Logout.java   27-Mar-2015 11:19:53
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
public class Logout extends BaseRequestHandler<Boolean> {
    
    @Override
    public boolean isProtected() {
        // A user doesn't have to be logged in call this servlet
        return false;
    }
    
    @Override
    public Boolean execute(
            HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {

        if(!this.isLoggedIn(request)) {
            return Boolean.TRUE;
        }
            
        this.setLoggedout(request);
        
        return Boolean.TRUE;
    }
}

