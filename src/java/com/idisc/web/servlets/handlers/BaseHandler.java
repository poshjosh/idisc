package com.idisc.web.servlets.handlers;

import com.bc.jpa.JpaContext;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Josh
 */
public class BaseHandler {
    
  private AppContext appContext;
    
  public Object getAttributeForAsync(HttpServletRequest request, String name) {
    Object value = request.getAttribute(name);
    HttpSession session;
    if(value == null && (session = request.getSession()) != null) {
      value = session.getAttribute(name);
    }  
    return value;
  }
  
  public void setAttributeForAsync(HttpServletRequest request, String name, Object value) {
    request.setAttribute(name, value);
    HttpSession session = request.getSession();
    if(session != null) {
      session.setAttribute(name, value);
    }
  }
  
  public void removeAttributeForAsync(HttpServletRequest request, String name) {
    request.removeAttribute(name);
    HttpSession session = request.getSession();
    if(session != null) {
      session.removeAttribute(name);
    }
  }

  public AppContext getAppContext(HttpServletRequest request) {
    if(appContext == null) {
      appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    }
    return appContext;
  }
  
  public JpaContext getJpaContext(HttpServletRequest request) {
    return this.getAppContext(request).getIdiscApp().getJpaContext();
  }
}
