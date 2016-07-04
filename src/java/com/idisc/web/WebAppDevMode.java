package com.idisc.web;

import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;

/**
 * @author Josh
 */
public class WebAppDevMode extends WebApp {

    public WebAppDevMode(ServletContext context, Configuration config) {
        super(context, config);
    }
    
    @Override
    public boolean isProductionMode() {
        return false;
    }
}
