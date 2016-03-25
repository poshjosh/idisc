package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.authsvc.client.SessionLoader;
import com.bc.util.XLogger;
import com.idisc.core.IdiscAuthSvcSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @(#)WebApp.java   16-Oct-2014 10:08:15
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
public class WebApp {
    
    private transient static final Logger logger =
            Logger.getLogger(WebApp.class.getName());
    
    private Configuration config;
    
    private AuthSvcSession authSvcSession;
    
    private ServletContext servletContext;
    
    private static WebApp instance;
    
    protected WebApp() { }
    
    public static WebApp getInstance() {
        if(instance == null) {
            instance = new WebApp();
        }
        return instance;
    }
    
    public void init(ServletContext context) 
            throws ServletException, IOException, ConfigurationException {
        
        try{
            
            URL defaultFileLoc = context.getResource("META-INF/properties/idiscwebdefaults.properties");
        
            URL fileLoc = context.getResource("META-INF/properties/idiscweb.properties");
        
            this.init(context, defaultFileLoc, fileLoc);
            
        }catch(MalformedURLException e) {
            
            throw new ConfigurationException(e);
        }
    }
    
    public void init(ServletContext context, 
            URL defaultFileLocation, URL fileLocation) 
            throws ServletException, IOException, ConfigurationException {
    
XLogger.getInstance().log(Level.INFO, "Initializing: {0}", this.getClass(), this.getClass().getName());
        this.servletContext = context;
            
        // Enable list delimiter handling using a comma as delimiter character
        config = this.loadConfig(defaultFileLocation, fileLocation, ',');
        
        String authsvc_url = config.getString(com.idisc.web.AppProperties.AUTHSVC_URL);
        if(WebApp.getInstance().isNetbeansDevelopmentMode()) {
            authsvc_url = "http://localhost:8080/authsvc";
        }
  
// Setting this might conflict with all the values in the database        
//        String timeZoneString = config.getString(com.idisc.web.AppProperties.TIME_ZONE);
//        if(timeZoneString != null) {
//            try{
//                TimeZone.setDefault(TimeZone.getTimeZone(timeZoneString));
//            }catch(Exception e) {
//                XLogger.getInstance().log(Level.WARNING, "Error setting timeZone to: "+timeZoneString, this.getClass(), e);
//            }
//        }
        
        String app_name = WebApp.getInstance().getAppName();
        String app_email = config.getString(com.idisc.web.AppProperties.AUTHSVC_EMAIL);
        String app_pass = config.getString(com.idisc.web.AppProperties.AUTHSVC_PASSWORD);
        
        SessionLoader sessLoader = new SessionLoader(){
            @Override
            public void onLoad(AuthSvcSession session) {
                WebApp.this.authSvcSession = session;
            }
            @Override
            protected AuthSvcSession getNewSession(String target, int maxRetrials, long retrialIntervals) {
                return new IdiscAuthSvcSession(target, maxRetrials, retrialIntervals);
            }
        };
        
        sessLoader.setMaxRetrials(1);
        sessLoader.setRetrialInterval(30000);
        
        // We use 120 seconds so that in the event the authentication service is on
        // the same server and we are just starting, then the authentication service
        // should be already up and running before we call this.
        sessLoader.loadAfter(120, TimeUnit.SECONDS, authsvc_url, app_name, app_email, app_pass);
    }

    public boolean isNetbeansDevelopmentMode() {
        if(this.getServletContext() == null) {
            return true;
        }else{
            String s = this.getServletContext().getRealPath("META-INF");
            return s.contains("/build/") || s.contains("\\build\\");
        }
    }
    
    public boolean saveConfiguration() throws ConfigurationException {
        return this.saveConfiguration(config);
    }
    
    public boolean saveConfiguration(Configuration cfg) throws ConfigurationException {
        if(cfg instanceof CompositeConfiguration) {
            CompositeConfiguration cc = (CompositeConfiguration)cfg;
            Configuration imc = cc.getInMemoryConfiguration();
            if(imc instanceof PropertiesConfiguration) {
                ((PropertiesConfiguration)imc).save();
                return true;
            }
        }else if (cfg instanceof PropertiesConfiguration){
            ((PropertiesConfiguration)cfg).save();
            return true;
        }else{
            throw new UnsupportedOperationException("Unexpected configuration type: "+cfg.getClass().getName());
        }
        return false;
    }
    
    public Configuration loadConfig(
            URL defaultFileLocation, URL fileLocation, char listDelimiter) 
            throws ConfigurationException {
        
logger.log(Level.INFO, 
"Loading properties configuration. List delimiter: {0}\nDefault file: {1}\nFile: {2}", 
new Object[]{listDelimiter, defaultFileLocation, fileLocation});

        if(fileLocation == null) {
            throw new NullPointerException();
        }
        
        Configuration output; 
        
        if(defaultFileLocation != null) {
            
            CompositeConfiguration composite = new CompositeConfiguration();

            PropertiesConfiguration cfg = this.loadConfig(
                    fileLocation, listDelimiter);
            composite.addConfiguration(cfg, true);
            
            PropertiesConfiguration defaults = this.loadConfig(
                    defaultFileLocation, listDelimiter);
            composite.addConfiguration(defaults);
            
            output = composite;
            
        }else{
            
            output = this.loadConfig(fileLocation, listDelimiter);
        }
        
        return output;
    }
    
    private PropertiesConfiguration loadConfig(
            URL fileLocation, char listDelimiter) 
            throws ConfigurationException {
        PropertiesConfiguration cfg = new PropertiesConfiguration();
        cfg.setListDelimiter(listDelimiter);
        cfg.setURL(fileLocation);
        cfg.load();
        return cfg;
    }
    
    public AuthSvcSession getAuthSvcSession() {
        return authSvcSession;
    }
    
    public String getAppName() {
        return this.getConfiguration().getString(AppProperties.APP_NAME);
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
    
    public Configuration getConfiguration() {
        return config;
    }
}
