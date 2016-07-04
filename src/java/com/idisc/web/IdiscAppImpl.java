package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * @author Josh
 */
public class IdiscAppImpl extends IdiscApp {

    public IdiscAppImpl(ServletContext context, Configuration config) 
        throws ConfigurationException, IOException, IllegalAccessException, 
              InterruptedException, InvocationTargetException {
        
        String scrapperPropsFile = config.getString("scrapperPropertiesFile");
        XLogger.getInstance().log(Level.INFO, "Scrapper properties file: {0}", getClass(), scrapperPropsFile);
        if (scrapperPropsFile != null) {
          IdiscAppImpl.this.setScrapperPropertiesFilename(scrapperPropsFile);
        }

        String persistenceFile = config.getString("persistenceFile");
        XLogger.getInstance().log(Level.INFO, "Persistence file: {0}", getClass(), persistenceFile);
        if (persistenceFile != null) {
          IdiscAppImpl.this.setPersistenceFilename(persistenceFile);
        }

        final URL propertiesURL;
        String corePropertiesFile = config.getString("idisccorePropertiesFile");
        XLogger.getInstance().log(Level.INFO, "Idisc core properties file: {0}", getClass(), corePropertiesFile);
        if (corePropertiesFile != null) {
          propertiesURL = context.getResource(corePropertiesFile);
        } else {
          propertiesURL = null;
        }

        if (corePropertiesFile != null) {
          IdiscAppImpl.this.init(propertiesURL);
        } else {
          IdiscAppImpl.this.init();
        }
    }
}
