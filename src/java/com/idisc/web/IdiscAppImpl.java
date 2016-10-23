package com.idisc.web;

import com.bc.jpa.JpaContext;
import com.bc.jpa.JpaContextImpl;
import com.bc.sql.MySQLDateTimePatterns;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.References;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * @author Josh
 */
public class IdiscAppImpl extends IdiscApp {

    private final boolean productionMode;
    
    private final String persistenceFile;
    
    public IdiscAppImpl(ServletContext context, Configuration config, boolean productionMode) 
        throws ConfigurationException, IOException, IllegalAccessException, 
              InterruptedException, InvocationTargetException {
        
        String scrapperPropsFile = config.getString("scrapperPropertiesFile");
        XLogger.getInstance().log(Level.INFO, "Scrapper properties file: {0}", getClass(), scrapperPropsFile);
        if (scrapperPropsFile != null) {
          IdiscAppImpl.this.setScrapperPropertiesFilename(scrapperPropsFile);
        }

        final URL propertiesURL;
        String corePropertiesFile = config.getString("idisccorePropertiesFile");
        XLogger.getInstance().log(Level.INFO, "Idisc core properties file: {0}", getClass(), corePropertiesFile);
        if (corePropertiesFile != null) {
          propertiesURL = Paths.get(corePropertiesFile).toUri().toURL();
        } else {
          propertiesURL = null;
        }

        this.productionMode = productionMode;
        
        this.persistenceFile = config.getString(ConfigNames.PERSISTENCE_FILE, null);
        XLogger.getInstance().log(Level.INFO, "Persistence config file: {0}", getClass(), this.persistenceFile);
        
        if (propertiesURL != null) {
          IdiscAppImpl.this.init(propertiesURL);
        } else {
          IdiscAppImpl.this.init();
        }
        
        IdiscApp.setInstance(this);
    }

    @Override
    public JpaContext initJpaContext(String persistenceFilename) throws IOException {
      
        if(this.persistenceFile == null) {
            
            return super.initJpaContext(persistenceFilename);
            
        }else{
            try{
                return new JpaContextImpl(new URI(this.persistenceFile), 
                        new MySQLDateTimePatterns(), References.ENUM_TYPES);  
            }catch(java.net.URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final boolean isProductionMode() {
        return productionMode;
    }

    public final String getPersistenceFile() {
        return persistenceFile;
    }
}
