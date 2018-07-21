package com.idisc.web;

import java.util.logging.Logger;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Josh
 */
public class ConfigurationLoader implements Serializable {
    private transient static final Logger LOG = Logger.getLogger(ConfigurationLoader.class.getName());
    
  private final URL defaultFilename;
  
  private final URL filename;
  
  public ConfigurationLoader(ServletContext context) throws MalformedURLException {
    this(
        Thread.currentThread().getContextClassLoader().getResource("META-INF/properties/idiscwebdefaults.properties"), 
        Thread.currentThread().getContextClassLoader().getResource("META-INF/properties/idiscweb.properties")
    );
  }

  public ConfigurationLoader(URL defaultFilename, URL filename) {
    this.defaultFilename = defaultFilename;
    this.filename = filename;
  }
  
  public Configuration load() throws ConfigurationException{
    return this.load(this.defaultFilename, this.filename);
  }
  
  public Configuration load(URL defaultFileLocation, URL fileLocation)
    throws ConfigurationException{
      
      return this.load(defaultFileLocation, fileLocation, ',');
  }
    
  public boolean save(Configuration cfg) throws ConfigurationException {
    if ((cfg instanceof CompositeConfiguration)) {
      CompositeConfiguration cc = (CompositeConfiguration)cfg;
      Configuration imc = cc.getInMemoryConfiguration();
      if ((imc instanceof PropertiesConfiguration)) {
        ((PropertiesConfiguration)imc).save();
        return true;
      }
    } else { if ((cfg instanceof PropertiesConfiguration)) {
        ((PropertiesConfiguration)cfg).save();
        return true;
      }
      throw new UnsupportedOperationException("Unexpected configuration type: " + cfg.getClass().getName());
    }
    return false;
  }
  

  protected Configuration load(URL defaultFileLocation, URL fileLocation, char listDelimiter)
    throws ConfigurationException {
      
    if(LOG.isLoggable(Level.INFO)){
      LOG.log(Level.INFO, 
            "Loading properties configuration. List delimiter: {0}\nDefault file: {1}\nFile: {2}", 
new Object[]{ Character.toString(listDelimiter),  defaultFileLocation,  fileLocation});
    }

    if (fileLocation == null) {
      throw new NullPointerException();
    }
    
    Configuration output;
    if (defaultFileLocation != null) {
        
      CompositeConfiguration composite = new CompositeConfiguration();
      
      PropertiesConfiguration cfg = load(fileLocation, listDelimiter);

      composite.addConfiguration(cfg, true);
      
      PropertiesConfiguration defaults = load(defaultFileLocation, listDelimiter);
      
      composite.addConfiguration(defaults);
      
      output = composite;
    }else {
      output = load(fileLocation, listDelimiter);
    }
    
    return output;
  }
  
  private PropertiesConfiguration load(URL fileLocation, char listDelimiter)
    throws ConfigurationException {
    PropertiesConfiguration cfg = new PropertiesConfiguration();
    cfg.setListDelimiter(listDelimiter);
    cfg.setURL(fileLocation);
    cfg.load();
    return cfg;
  }
}
