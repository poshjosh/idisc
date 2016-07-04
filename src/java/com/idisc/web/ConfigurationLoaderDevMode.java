package com.idisc.web;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;

/**
 * @author Josh
 */
public class ConfigurationLoaderDevMode extends ConfigurationLoader {

    public ConfigurationLoaderDevMode(ServletContext context) throws MalformedURLException {
        this(
            context.getResource("META-INF/properties/idiscwebdefaults.properties"), 
            context.getResource("META-INF/properties/idiscweb_devmode.properties")
        );
    }

    public ConfigurationLoaderDevMode(URL defaultFilename, URL filename) {
        super(defaultFilename, filename);
    }
}
