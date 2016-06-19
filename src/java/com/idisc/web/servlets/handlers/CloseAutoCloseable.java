package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import java.util.logging.Level;

/**
 * @author poshjosh
 */
public class CloseAutoCloseable {
    
    public CloseAutoCloseable() { }
    
    public boolean execute(String name, AutoCloseable current) {
        boolean output = false;
        try{
            final Level level = WebApp.getInstance().isDebug() ? Level.INFO : Level.FINE;
            XLogger.getInstance().log(level, "Closing atrribute named: {0}", this.getClass(), name);
            current.close();
            output = true;
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Error closing attribute named: "+name, this.getClass(), e);
        }
        return output;
    }
}
