package com.idisc.web.servlets.handlers.response;

import com.bc.util.JsonBuilder;
import com.bc.util.XLogger;
import com.idisc.core.util.EntityJsonBuilder;
import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;

/**
 * Writes the response output data directly to the response writer
 * @author Josh
 */
public abstract class DirectResponseHandler<V, O> extends AbstractResponseHandler<V, O> {

  private final boolean tidyOutput;
    
  private final boolean plainTextOnly;
    
  private final int maxTextLengthPerItem;

  public DirectResponseHandler(HttpServletRequest request, ResponseContext context) {
    super(context);
    this.tidyOutput = DirectResponseHandler.this.isTidyOutput(request);
    this.plainTextOnly = DirectResponseHandler.this.isPlainTextOnly(request);
    this.maxTextLengthPerItem = DirectResponseHandler.this.getMaxTextLengthPerItem(request);
  }
    
  protected String toString(O output) {
    return String.valueOf(output);
  }
    
  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, O output)
      throws ServletException, IOException {
    try {
        
      PrintWriter out = response.getWriter();
      
      Throwable localThrowable2 = null;
      try {
          
        out.println(toString(output));
        
      }catch (Throwable localThrowable1) {
          
        localThrowable2 = localThrowable1;
        
        throw localThrowable1;
      }finally{
        if (out != null) {
          if (localThrowable2 != null) { 
            try { out.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } 
          }else{
            out.close();
          }
        }    
      }
    } catch (Throwable t) { 
      XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  @Override
  public String getContentType()  {
     return "text/plain;charset=" + this.getCharacterEncoding();
  }
  
  public int getEstimatedLengthChars(V value, Object messageCreatedFromValue) {
      return 200;
  }

  private JsonBuilder _jf;
  public JsonBuilder getJsonBuilder(HttpServletRequest request) {
    if (this._jf == null) {
      this._jf = this.createJsonBuilder(request);
    }
    return this._jf;
  }

  protected JsonBuilder createJsonBuilder(HttpServletRequest request) {
      
    EntityJsonBuilder jsonBuilder = new EntityJsonBuilder(
            this.isTidyOutput(), this.isPlainTextOnly(), this.getMaxTextLengthPerItem());
            
    return jsonBuilder;
  }
  
  private int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }
  
  public boolean isPlainTextOnly(HttpServletRequest request) {
    String contentType = request.getParameter("content-type");
    boolean b = (contentType != null) && (contentType.contains("text/plain"));
    XLogger.getInstance().log(Level.FINER, "Plain text only: {0}", getClass(), b);
    return b;
  }

  public boolean isTidyOutput(HttpServletRequest request) {
    boolean tidy;
    String tidyParam = request.getParameter("tidy");
    if(tidyParam != null) {
        tidy = "1".equals(tidyParam) || "true".equalsIgnoreCase(tidyParam);
    }else{
        tidy = !WebApp.getInstance().isProductionMode();
    }
    return tidy;
  }
  
  public int getMaxTextLengthPerItem(HttpServletRequest request) {
      Configuration config = WebApp.getInstance().getConfiguration();
      int defaultLen = config == null ? 1000 : config.getInt(ConfigNames.DEFAULT_CONTENT_LENGTH, 1000);
      return getInt(request, "maxlen", defaultLen);
  }
  
  public boolean isTidyOutput() {
    return tidyOutput;
  }
  
  public boolean isPlainTextOnly() {
    return plainTextOnly;
  }

  public int getMaxTextLengthPerItem() {
    return maxTextLengthPerItem;
  }
}
/**
 * 
  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, Throwable message)
      throws ValidationException, ServletException, IOException {
    try {
        
      PrintWriter out = response.getWriter();
      
      Throwable localThrowable2 = null;
      
      try {
          
        O output = getOutput(request, name, message);
        
        out.println(toString(output));
        
      }catch (Throwable localThrowable1) {
          
        localThrowable2 = localThrowable1;
        
        throw localThrowable1;
      } finally {
        if (out != null) {
          if (localThrowable2 != null) { 
            try { out.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } 
          }else{
            out.close();
          }
        }    
      }
    } catch (Throwable t) { 
      XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
    
  public int getEstimatedLengthChars(Throwable value, Object messageCreatedFromValue) {
      return 100;
  }

 * 
 */