package com.idisc.web.listeners;

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
        Enumeration<String> en = session.getAttributeNames();
        CloseAutoCloseable cac = new CloseAutoCloseable();
        while(en.hasMoreElements()) {
            String name = en.nextElement();
            Object value = session.getAttribute(name);
            cac.attempt(name, value);
        }
    }
}
