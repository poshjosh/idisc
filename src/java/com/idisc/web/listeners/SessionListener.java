package com.idisc.web.listeners;

import com.idisc.web.servlets.handlers.CloseAutoCloseable;
import com.idisc.core.jpa.SearchHandlerFactory;
import com.idisc.web.WebApp;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author poshjosh
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) { }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) { 
        HttpSession session = se.getSession();
//final Level level = WebApp.getInstance().isDebug() ? Level.INFO : Level.FINE;
//XLogger.getInstance().log(level, "Session destroyed: {0}", this.getClass(), session.getId());
        Enumeration<String> en = session.getAttributeNames();
        CloseAutoCloseable cac = new CloseAutoCloseable();
        while(en.hasMoreElements()) {
            String name = en.nextElement();
            Object value = session.getAttribute(name);
            if(value instanceof AutoCloseable) {
                cac.execute(name, (AutoCloseable)value);
            }
        }
        
        SearchHandlerFactory shf = WebApp.getInstance().getSearchHandlerFactory(false);
        
        if(shf != null) {
            
            shf.removeAll(session.getId(), true);
        }
    }
}
