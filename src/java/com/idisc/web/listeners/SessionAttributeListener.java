package com.idisc.web.listeners;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Web application lifecycle listener.
 *
 * @author poshjosh
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) { 
//XLogger.getInstance().log(Level.FINER, "Attribute added: {0} = {1}", this.getClass(), event.getName(), event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) { 
//XLogger.getInstance().log(Level.FINER, "Attribute removed: {0} = {1}", this.getClass(), event.getName(), event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String name = event.getName();
        Object replaced = event.getValue();
        if(replaced instanceof AutoCloseable) {
            CloseAutoCloseable cac = new CloseAutoCloseable();
            cac.execute(name, (AutoCloseable)replaced);
        }
    }
}
