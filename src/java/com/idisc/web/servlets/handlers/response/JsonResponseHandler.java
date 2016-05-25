package com.idisc.web.servlets.handlers.response;

import com.bc.util.JsonFormat;
import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonResponseHandler<V> extends AbstractResponseHandler<V>
{
  private JsonFormat _jf;
  
  @Override
  public String getContentType(HttpServletRequest request)
  {
    return "text/plain;charset=" + getCharacterEncoding(request);
  }
  
  @Override
  public void sendResponse(HttpServletRequest request, HttpServletResponse response, String name, V message)
    throws ServletException, IOException
  {
    try
    {
      PrintWriter out = response.getWriter();Throwable localThrowable2 = null;
      try {
        Object output = getOutput(request, name, message);
        
        out.println(output.toString());
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;

      }
      finally
      {

        if (out != null) if (localThrowable2 != null) try { out.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else out.close();
      }
    } catch (Throwable t) { XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  @Override
  public void sendResponse(HttpServletRequest request, HttpServletResponse response, String name, Throwable message)
    throws ValidationException, ServletException, IOException
  {
    try
    {
      PrintWriter out = response.getWriter();Throwable localThrowable2 = null;
      try {
        Object output = getOutput(request, name, message);
        
        out.println(output.toString());
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;

      }
      finally
      {

        if (out != null) if (localThrowable2 != null) try { out.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else out.close();
      }
    } catch (Throwable t) { XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  @Override
  public Object getOutput(HttpServletRequest request, String name, V value)
  {
    
    Object msg = super.getOutput(request, name, value);
    
    XLogger.getInstance().log(Level.FINER, "{0}={1}", getClass(), name, msg);
    
    StringBuilder json = getJsonOutput(request, name, value, msg);
    
    return json.length() > 1000 ? new String(json) : json.toString();
  }
  
  @Override
  public Object getOutput(HttpServletRequest request, String name, Throwable value)
  {
      
    Object msg = super.getOutput(request, name, value);
    
    XLogger.getInstance().log(Level.FINER, "{0}={1}", getClass(), name, msg);
    
    StringBuilder json = getJsonOutput(request, name, value, msg);
    
    return json.length() > 1000 ? new String(json) : json.toString();
  }

  public StringBuilder getJsonOutput(HttpServletRequest request, 
          String key, V value, Object messageCreatedFromValue)
  {
      
    int buffLen = key.length() + this.getEstimatedBufferCapacity(value, messageCreatedFromValue);  
    
    return this.getJsonOutput(request, key, messageCreatedFromValue, buffLen);
  }
  
  public StringBuilder getJsonOutput(HttpServletRequest request, 
          String key, Throwable value, Object messageCreatedFromThrowable)
  {
      
    int buffLen = key.length() + this.getEstimatedBufferCapacity(value, messageCreatedFromThrowable);  
    
    return this.getJsonOutput(request, key, messageCreatedFromThrowable, buffLen);
  }

  public int getEstimatedBufferCapacity(V value, Object messageCreatedFromValue) {
      return 200;
  }

  public int getEstimatedBufferCapacity(Throwable value, Object messageCreatedFromValue) {
      return 100;
  }

  public StringBuilder getJsonOutput(HttpServletRequest request, String key, Object msg, int bufferLen)
  {
    
    if (key == null) {
      throw new NullPointerException();
    }

XLogger.getInstance().log(Level.FINE, "Output name: {0}, buffer length: {1}", this.getClass(), key, bufferLen);

    Map outputMap;
    if(msg == null) {
        
        outputMap = Collections.singletonMap(key, "");
    
    }else{
        
        outputMap = Collections.singletonMap(key, msg);
    }
    
    StringBuilder outputJson = this.getJsonOutputBuffer(request, key, msg, bufferLen);
    
    this.appendJsonOutput(request, outputMap, outputJson);
    
    return outputJson;
  }

  public StringBuilder getJsonOutputBuffer(HttpServletRequest request, String key, Object msg, int bufferLen) {
      
    if (key == null) {
      throw new NullPointerException();
    }
    
    StringBuilder outputJsonBuffer;
    if(msg == null) {
        
        outputJsonBuffer = new StringBuilder(key.length() + 16);
    
    }else{
        
        if(bufferLen < 1) {
            bufferLen = 16;
        }
        
        if(this.isTidyOutput(request)) {
            bufferLen += (bufferLen * 0.3);
        }

        outputJsonBuffer = new StringBuilder(bufferLen);
    }
    
    return outputJsonBuffer;
  }
  
  public void appendJsonOutput(
          HttpServletRequest request, Map outputMap, StringBuilder appendTo) {
      
    JsonFormat jsonFormat = getJsonFormat(request);

    XLogger.getInstance().log(Level.FINER, "JsonFormat type: {0}", getClass(), jsonFormat == null ? null : jsonFormat.getClass());
    
    if (jsonFormat == null) {
      throw new NullPointerException();
    }
    
    jsonFormat.setTidyOutput(this.isTidyOutput(request));
    
    jsonFormat.appendJSONString(outputMap, appendTo);
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
  
  public JsonFormat getJsonFormat(HttpServletRequest request)
  {
    if (this._jf == null) {
      this._jf = new JsonFormat();
    }
    return this._jf;
  }
}
