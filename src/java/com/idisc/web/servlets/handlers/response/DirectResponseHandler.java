package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.EntityJsonFormat;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
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

    private boolean plainTextOnly;
    
    private int maxTextLengthPerItem;
    
    protected String toString(O output) {
        return String.valueOf(output);
    }
    
    @Override
    public void processResponse(HttpServletRequest request, HttpServletResponse response, String name, V message) throws ServletException, IOException {
        
      String contentType = request.getParameter("content-type");
      
      plainTextOnly = (contentType != null) && (contentType.contains("text/plain"));
      
      XLogger.getInstance().log(Level.FINER, "Plain text only: {0}", getClass(), Boolean.valueOf(plainTextOnly));

      Configuration config = WebApp.getInstance().getConfiguration();
      
      int defaultLen = config == null ? 1000 : config.getInt("defaultContentLength", 1000);

      this.maxTextLengthPerItem = getInt(request, "maxlen", defaultLen);
      
      super.processResponse(request, response, name, message); //To change body of generated methods, choose Tools | Templates.
    }
    
  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, V message)
      throws ServletException, IOException {
    try {
        
      PrintWriter out = response.getWriter();
      
      Throwable localThrowable2 = null;
      try {
          
        O output = getOutput(request, name, message);
        
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
    
  @Override
  public String getContentType(HttpServletRequest request)  {
     return "text/plain;charset=" + getCharacterEncoding(request);
  }
  
  public int getEstimatedLengthChars(V value, Object messageCreatedFromValue) {
      return 200;
  }

  public int getEstimatedLengthChars(Throwable value, Object messageCreatedFromValue) {
      return 100;
  }

  private EntityJsonFormat _jf;
  public EntityJsonFormat getJsonFormat(HttpServletRequest request) {
    if (this._jf == null) {
      this._jf = this.createJsonFormat(request);
    }
    return this._jf;
  }

  protected EntityJsonFormat createJsonFormat(HttpServletRequest request) {
      
    return new EntityJsonFormat(this.isPlainTextOnly(), this.getMaxTextLengthPerItem());
  }
  
  private int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }

  public boolean isPlainTextOnly() {
    return plainTextOnly;
  }

  public int getMaxTextLengthPerItem() {
    return maxTextLengthPerItem;
  }
}
