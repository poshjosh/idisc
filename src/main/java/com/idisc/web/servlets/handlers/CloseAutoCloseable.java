package com.idisc.web.servlets.handlers;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author poshjosh
 */
public class CloseAutoCloseable {
    private transient static final Logger LOG = Logger.getLogger(CloseAutoCloseable.class.getName());
    
    public CloseAutoCloseable() { }
    
    public boolean execute(String name, AutoCloseable current) {
        boolean output = false;
        try{
            if(LOG.isLoggable(Level.FINE)){
                  LOG.log(Level.FINE, "Closing atrribute named: {0}", name);
            }
            current.close();
            output = true;
        }catch(Exception e) {
            if(LOG.isLoggable(Level.WARNING)){
                  LOG.log(Level.WARNING, "Error closing attribute named: "+name, e);
            }
        }
        return output;
    }
}
