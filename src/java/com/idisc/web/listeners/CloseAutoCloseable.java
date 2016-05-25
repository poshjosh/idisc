package com.idisc.web.listeners;

import com.bc.util.XLogger;
import java.util.logging.Level;

/**
 * @author poshjosh
 */
public class CloseAutoCloseable {
    
    public boolean closeAutoCloseable(String name, Object val) {
        boolean output = false;
        try{
            AutoCloseable current = (AutoCloseable)val;
            try{
XLogger.getInstance().log(Level.FINE, "Closing {0}", this.getClass(), name);
                current.close();
                output = true;
            }catch(Exception e) {
                XLogger.getInstance().log(Level.WARNING, "Error closing attribute named: "+name, this.getClass(), e);
            }
        }catch(ClassCastException e) {}
        return output;
    }
}
