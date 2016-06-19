package com.idisc.web.servlets.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Josh
 */
public class BaseHandler {
    
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
}
