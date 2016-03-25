package com.idisc.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class Tips extends FileCache {
    
    public Tips() {  }
    
    public Tips(HttpServletRequest request, HttpServletResponse response, boolean load) {
        super(request, response, load);
    }
    
    @Override
    public String getDirPath() {
        return WebApp.getInstance().getServletContext().getRealPath("/tips");
    }
}
