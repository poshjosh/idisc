package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.EntityJsonFormat;
import com.idisc.web.WebApp;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;

/**
 * @author poshjosh
 * @param <V>
 */
public class EntityJsonResponseHandler<V> extends JsonResponseHandler<V> {
    
  private EntityJsonFormat jsonFormat;

  protected EntityJsonFormat createJsonFormat() {
      
    return new EntityJsonFormat();
  }
  
  @Override
  public EntityJsonFormat getJsonFormat(HttpServletRequest request)
  {
    if (this.jsonFormat == null) {
        
      this.jsonFormat = this.createJsonFormat();
      
      String contentType = request.getParameter("content-type");
      
      boolean plainTextOnly = (contentType != null) && (contentType.contains("text/plain"));
      
      jsonFormat.setPlainTextOnly(plainTextOnly);

      XLogger.getInstance().log(Level.FINER, "Plain text only: {0}", getClass(), Boolean.valueOf(plainTextOnly));

      Configuration config = WebApp.getInstance().getConfiguration();
      
      int defaultLen = config == null ? 1000 : config.getInt("defaultContentLength", 1000);

      int maxLen = getInt(request, "maxlen", defaultLen);
      
      jsonFormat.setMaxTextLength(maxLen);
    }
    
    return this.jsonFormat;
  }

  private int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }
}
