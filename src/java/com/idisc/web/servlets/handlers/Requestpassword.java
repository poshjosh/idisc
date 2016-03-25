package com.idisc.web.servlets.handlers;

import com.authsvc.client.AuthSvcSession;
import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * @(#)Requestpassword.java   27-Mar-2015 10:15:13
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
public class Requestpassword extends BaseRequestHandler<Boolean> {
    
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
            throw new ServletException("You are already logged in");
        }
            
        Map app = WebApp.getInstance().getAuthSvcSession().getAppDetails();
        
        if(app == null) {
            throw new ServletException("Authentication Service Unavailable");
        }
        
        Map<String, String> params = new RequestParameters(request){
            @Override
            public boolean isCaseSensitive() {
                //@todo remove eventually
                // We had to do this because of a bug in the earlier versions
                // of the newsminute android app... version deployed before
                // 4 Apr 2015
                //
                return false;
            }
        };
        
        AuthSvcSession authSession = WebApp.getInstance().getAuthSvcSession();
        
        Boolean output;
        try{

XLogger.getInstance().log(Level.FINE, "Parameters: {0}", this.getClass(), params);
        
            // Sends a mail to the requesting user and returns {"SuccessMessage", "Success"} if successful
            //
            JSONObject json = authSession.requestUserPassword(app, params);
            
            output = json == null ? Boolean.FALSE : !authSession.isError(json);
            
        }catch(ParseException e) {
            throw new ServletException("Invalid response from server", e);
        }
        
        return output;
    }
}

