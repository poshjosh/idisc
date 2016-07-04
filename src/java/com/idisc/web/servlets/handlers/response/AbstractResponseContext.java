package com.idisc.web.servlets.handlers.response;

import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public abstract class AbstractResponseContext<V> implements ResponseContext<V>, Serializable {
    

  private final String referer;
  
  private final ServletContext servletContext;
  
  private final AppContext appContext;
  
  
  public AbstractResponseContext(HttpServletRequest request) {
    this.referer = request.getHeader("referer");
    this.servletContext = request.getServletContext();
    this.appContext = (AppContext)servletContext.getAttribute(Attributes.APP_CONTEXT);
  }
    
    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getReferer() {
        return referer;
    }

    public AppContext getAppContext() {
        return appContext;
    }
}
