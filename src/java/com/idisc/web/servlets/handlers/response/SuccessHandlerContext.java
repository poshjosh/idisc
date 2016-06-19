package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.bc.web.core.util.ServletUtil;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 * @param <V>
 */
public class SuccessHandlerContext<V> implements ResponseContext<V>, Serializable {
  
  private final String referer;
  
  public SuccessHandlerContext(HttpServletRequest request) {
    this.referer = request.getHeader("referer");
  }

  @Override
  public Object format(String name, V value) {
    if ((value instanceof Boolean)) {
      return ((Boolean)value) ? "Success" : "Error";
    }
    return value;
  }
  
  @Override
  public int getStatusCode(String name, V value) {
    return 200;
  }

  @Override
  public String getResponseMessage(String name, V value) {
    String message = null;
    try{
      Boolean success = (Boolean)value;
      if(success) {
        message = name + " successful";
      }else{
        message = name + " failed";
      }
    }catch(ClassCastException ignored) {}
    return message;
  }
  
  @Override
  public String getTargetPage(String name, Object message)
    throws ValidationException {
    String output;
    if(message instanceof Boolean) {
        // Back to sender
        output = this.getRefererRelativePath();
        if(output == null) {
            output = '/' + name + ".jsp";
        }
    }else{
        output = '/' + name + ".jsp";
    }
XLogger.getInstance().log(Level.FINE, "Request for {0} resulted in target page: {1}", this.getClass(), name, output);
    return output;
  }

  private String getRefererRelativePath() {
    String output = referer == null ? null : ServletUtil.getRelativePath(
            WebApp.getInstance().getServletContext(), referer, "baseURL");
    return output;
  }

  public final String getReferer() {
    return referer;
  }
}