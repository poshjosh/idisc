package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.bc.web.core.util.ServletUtil;
import com.idisc.web.exceptions.ValidationException;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 * @param <V>
 */
public class SuccessHandlerContext<V> extends AbstractResponseContext<V> {
  
  public SuccessHandlerContext(HttpServletRequest request) {
    super(request);
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
        message = name + " request successful";
      }else{
        message = name + " request failed";
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
    String referer = this.getReferer();
    String output = referer == null ? null : ServletUtil.getRelativePath(
            this.getServletContext(), referer, "baseURL");
    return output;
  }
}
