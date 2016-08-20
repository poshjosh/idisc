package com.idisc.web.servlets.handlers.response;

import com.bc.util.JsonBuilder;
import com.bc.util.XLogger;
import com.idisc.core.util.EntityJsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Writes the response output data directly to the response writer
 * @author Josh
 */
public abstract class DirectResponseHandler<V, O> extends AbstractResponseHandler<V, O> {

  private final JsonBuilder jsonBuilder;

  public DirectResponseHandler(ResponseContext context, boolean tidyOutput, boolean plainTextOnly, int maxTextLengthPerItem) {
    this(context, new EntityJsonBuilder(tidyOutput, plainTextOnly, maxTextLengthPerItem));
  }
    
  public DirectResponseHandler(ResponseContext context, JsonBuilder jsonBuilder) {
    super(context);
    this.jsonBuilder = jsonBuilder;
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

  public final JsonBuilder getJsonBuilder() {
    return this.jsonBuilder;
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