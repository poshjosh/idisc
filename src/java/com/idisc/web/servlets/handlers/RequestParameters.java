package com.idisc.web.servlets.handlers;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;


/**
 * @(#)RequestParameters.java   20-Jan-2015 00:54:34
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
public class RequestParameters extends HashMap<String, String> {
    
    public RequestParameters() { }

    public RequestParameters(HttpServletRequest request) { 
        RequestParameters.this.setRequest(request);
    }
    
    public void setRequest(HttpServletRequest request) {
        
        Enumeration<String> en = request.getParameterNames();
        while(en.hasMoreElements()) {
            String name = en.nextElement();
            String [] values = request.getParameterValues(name);
            if(values == null || values.length == 0) {
                continue;
            }
            
            this.put(name, values[0]);
            
            if(!this.isCaseSensitive()) {
                this.put(name.toLowerCase(), values[0]);
            }
        }
//        en = request.getAttributeNames();
//        while(en.hasMoreElements()) {
//            String name = en.nextElement();
//            Object value = request.getAttribute(name);
//            if(value == null) {
//                continue;
//            }
//            this.put(name, value);
//            if(!this.isCaseSensitive()) {
//                this.put(name.toLowerCase(), value);
//            }
//        }
    }

    public boolean isCaseSensitive() {
        return true;
    }
}
