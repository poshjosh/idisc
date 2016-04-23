package com.idisc.web;

import com.bc.webapptest.HttpServletRequestImpl;
import com.bc.webapptest.HttpServletResponseImpl;
import com.bc.webapptest.HttpSessionImpl;
import com.bc.webapptest.TestServletContext;
import com.idisc.web.servlets.handlers.request.Login;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


/**
 * @(#)Bcwebapptest.java   08-May-2015 19:42:40
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
public class BaseTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
        //
        final ServletContext servletContext = new TestServletContext(
                "http://www.looseboxes.com",
                System.getProperty("user.home")+"/Documents/NetBeansProjects/idisc/web",
                "/idisc"){
                
                    @Override
                    public URL getResource(String path) throws MalformedURLException {
                        if(!path.startsWith("/")) {
                            path  = "/" + path;
                        }
                        return new URL("file://" + this.getLocalDir() + this.getContextPath() + path);
                    }
                };
        
        ServletContextListener contextListener = new com.idisc.web.listeners.ServletContextListener(){
            @Override
            public void contextInitialized(ServletContextEvent sce) { 
                
                super.contextInitialized(sce);
                
                HttpSessionImpl session = new HttpSessionImpl(servletContext);
                
                HttpServletRequestImpl request = new HttpServletRequestImpl(session);
                
// Referer: http://localhost:8080/idisc/feed/455167.jsp, Request URI: /idisc/login.jsp                
                request.from("/idisc/feed/455167.jsp").to("/idisc/login.jsp");
                
printRequest(request);                
                
// Referer: http://localhost:8080/idisc/login.jsp, Request URI: /idisc/login
                Map<String, String> params = new HashMap<>();
                params.put("username", "posh.bc@gmail.com");
                params.put("password", "1kjvdul-");
                request.from("/idisc/login.jsp").with("").to("/idisc/login");

// Use your login servlet to call the request
                try{
                    Boolean success = new Login().execute(request, new HttpServletResponseImpl());
System.out.println("Login Successful: "+success);                    
                }catch(ServletException | IOException e) {
                    e.printStackTrace();
                }
                
                this.contextDestroyed(new ServletContextEvent(servletContext));
            }
            @Override
            public void contextDestroyed(ServletContextEvent sce) { }
        };

        contextListener.contextInitialized(new ServletContextEvent(servletContext));
    }
    
    private static void printRequest(HttpServletRequest request) {
System.out.println("Request.getContextPath: "+request.getContextPath());        
System.out.println("Request..getPathInfo: "+request.getPathInfo());        
System.out.println("Request.getQueryString: "+request.getQueryString());        
System.out.println("Request.getRequestURI: "+request.getRequestURI());        
System.out.println("Request.getServletPath: "+request.getServletPath());        
    }
}
