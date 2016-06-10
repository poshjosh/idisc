package com.idisc.web.listeners;

import com.bc.util.XLogger;
import java.util.logging.Level;

/**
 * @author poshjosh
 */
public class CloseAutoCloseable {
    
    public boolean execute(String name, AutoCloseable current) {
        boolean output = false;
        try{
            XLogger.getInstance().log(Level.INFO, "Closing atrribute named: {0}", this.getClass(), name);
            current.close();
            output = true;
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Error closing attribute named: "+name, this.getClass(), e);
        }
        return output;
    }
}
