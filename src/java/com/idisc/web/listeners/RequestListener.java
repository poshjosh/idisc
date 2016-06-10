/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idisc.web.listeners;

import com.bc.web.core.AttributesInitializer;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Web application lifecycle listener.
 *
 * @author USER
 */
public class RequestListener implements ServletRequestListener {

    private AttributesInitializer attributesInitializer;
    
    @Override
    public void requestDestroyed(ServletRequestEvent sre) { }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        
        if(request instanceof HttpServletRequest) {
            
            HttpServletRequest httpReq = (HttpServletRequest)request;

            if(attributesInitializer == null) {
                attributesInitializer = new AttributesInitializer();
            }
            
            attributesInitializer.init(httpReq);
        }
    }
}