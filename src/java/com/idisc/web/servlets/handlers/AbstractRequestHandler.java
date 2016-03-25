package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.User;
import com.idisc.web.exceptions.ValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @(#)AbstractRequestHandler.java   14-Feb-2015 10:38:22
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
public abstract class AbstractRequestHandler<X> implements RequestHandler<X>{

    /**
     * If true a user needs to be logged in to call this request handler.
     * The default is true.
     * @return 
     */
    @Override
    public boolean isProtected() {
        return true;
    }
    
    @Override
    public User tryLogin(HttpServletRequest request, 
            HttpServletResponse response){
        User user;
        try{
            Login login = new Login();
            login.execute(request, response);
            // Getting here means login was successful
            user = this.getUser(request);
//XLogger.getInstance().log(Level.FINE, "Login SUCCESS, User: {0}", this.getClass(), user==null?null:user.getDetails());
        }catch(ServletException | IOException | RuntimeException e) {
//XLogger.getInstance().log(Level.INFO, "Login ERROR, Message: {0}", this.getClass(), e.toString());
            // This login attempt is a gamble and may through a variety of
            // exceptions. For example NullPointerException is thrown if the
            // requeired parameter emailaddress does not have a value.
            // Hence the exception here is not logged.
            user = null;
        }
        return user;
    }
        
    @Override
    public void processRequest(
            HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {

        int statusCode;
        Object msg;
        
        try{

            // Try to login the user if not logged in
            //
            if(this.isProtected() && !this.isLoggedIn(request)) {
                
                this.tryLogin(request, response);
            }
            
            if(this.isProtected() && !this.isLoggedIn(request)) {
                throw new ServletException("Login required");
            }
            
            X x = this.execute(request, response);
            
            statusCode = this.getSuccessCode(x);
            
            msg = this.getSuccessMessage(x);
            
        }catch(ServletException | IOException | RuntimeException t) { 
            // We have to also catch RuntimeException
            
            statusCode = this.getErrorCode(t);
            
            msg = this.getErrorMessage(t);

            XLogger.getInstance().log(Level.WARNING, 
                    "Error processing request", this.getClass(), t);
        }
            
        this.respond(request, response, statusCode, msg);
    } 

    @Override
    public void setLoggedout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }
    
    @Override
    public void setLoggedIn(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    @Override
    public User findUser(HttpServletRequest request, HttpServletResponse response) {
        User user;
        if(this.isLoggedIn(request)) {
            user = this.getUser(request);
        }else{
            user = this.tryLogin(request, response);
        }
        return user;
    }
    
    @Override
    public User getUser(HttpServletRequest request) {
        return (User)request.getSession().getAttribute("user");
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest request) {
        return this.getUser(request) != null;
    }
    
    @Override
    public User setLoggedIn(
            HttpServletRequest request, 
            Map authuserdetails, 
            boolean create) throws ServletException {
        User user = null;
        try{
            user = User.getInstance(authuserdetails, create);
            this.setLoggedIn(request, user);
        }catch(Exception e) {
            throw new ServletException("Unexpected Error", e);
        }
        return user;
    }
    
    @Override
    public int getSuccessCode(X x) {
        return HttpServletResponse.SC_OK;
    }

    @Override
    public Object getSuccessMessage(X x) {
        if(x instanceof Boolean) {
            return ((Boolean)x) ? "Success" : "Error";
        }else{
            return x;
        }
    }
    
    @Override
    public int getErrorCode(Throwable t) {
        if(t instanceof ValidationException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }else if(t instanceof FileNotFoundException) {
            return HttpServletResponse.SC_NOT_FOUND;
        }else{
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public String getErrorMessage(Throwable t) {
        if(t instanceof ServletException) {
            return t.getLocalizedMessage();
        }else{
            return "Error";
        }
    }

    private RequestHandlerProvider _rhp_accessViaGetter;
    @Override
    public RequestHandlerProvider getRequestHandlerProvider() {
        if(_rhp_accessViaGetter == null) {
            _rhp_accessViaGetter = new RequestHandlerProviderImpl();
        }
        return _rhp_accessViaGetter;
            
    }
}

