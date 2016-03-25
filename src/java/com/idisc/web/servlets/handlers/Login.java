package com.idisc.web.servlets.handlers;

import com.authsvc.client.AuthSvcSession;
import com.idisc.core.User;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * @(#)Login.java   20-Jan-2015 18:17:01
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
public class Login extends BaseRequestHandler<Boolean> {
    
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

        if(this.isLoggedIn(request)) {
            return Boolean.TRUE;
        }
            
        Map app = WebApp.getInstance().getAuthSvcSession().getAppDetails();
        
        if(app == null) {
            throw new ServletException("Authentication Service Unavailable");
        }
        
        Map<String, String> params = new RequestParameters(request);
        
        AuthSvcSession authSession = WebApp.getInstance().getAuthSvcSession();
        
        Boolean output;
        try{
            // app id and other necessary parameters are added in this method
            //
            JSONObject authuserdetails = authSession.getUser(params);
            
            if(authuserdetails == null) {
                throw new ServletException("Error accessing user details from authenctiation service");
            }
            
            User user = this.setLoggedIn(request, authuserdetails, false);
            
            output = user != null;
            
        }catch(ParseException e) {
            throw new ServletException("Error processing request", e);
        }
        return output;
    }
}
