package com.idisc.web.servlets.handlers;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.parameters.Createuser;
import com.idisc.core.User;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * @(#)Getuser.java   22-Jan-2015 15:26:47
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
public class Getuser extends BaseRequestHandler<Map> {

    @Override
    public boolean isProtected() {
        // A user doesn't have to be logged in call this servlet
        return false;
    }
    
    @Override
    public Map execute(
            HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {

        Map output;
        
        if(this.isLoggedIn(request)) {
            
            User user = this.getUser(request);
            output = user.getDetails();
            
        }else{
            
            Map app = WebApp.getInstance().getAuthSvcSession().getAppDetails();

            if(app == null) {
                throw new ServletException("Authentication Service Unavailable");
            }

            Map<String, String> params = new RequestParameters(request);
            params.put(Createuser.ParamName.sendregistrationmail.name(), Boolean.toString(false));

            try{

                AuthSvcSession authSession = WebApp.getInstance().getAuthSvcSession();

                // app id and other necessary parameters are added in this method
                //
                JSONObject authuserdetails = authSession.getUser(params);
                
                if(authuserdetails != null) {
                    if(authSession.isError(authuserdetails) ||
                            authSession.getResponseCode() >= 300) {

                        throw new ServletException(authSession.getResponseMessage());
                        
                    }else{
                        Object oval = params.get(com.authsvc.client.parameters.Getuser.ParamName.create.name());
                        boolean create = Boolean.parseBoolean(oval.toString());
                        User user = this.setLoggedIn(request, authuserdetails, create);
                        output = user.getDetails();
                    }
                }else{
                    throw new ServletException("Error processing request");
                }
            }catch(ParseException e) {
                throw new ServletException("Invalid response from server", e);
            }
        }
        
        return output == null ? Collections.emptyMap() : output;
    }
}

