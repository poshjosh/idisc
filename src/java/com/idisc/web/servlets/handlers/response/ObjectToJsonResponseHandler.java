package com.idisc.web.servlets.handlers.response;

import com.bc.util.JsonFormat;
import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

public class ObjectToJsonResponseHandler<V> extends DirectResponseHandler<V, StringBuilder> {
    
  @Override
  protected String toString(StringBuilder jsonOutput) {
    return jsonOutput.length() > 1000 ? new String(jsonOutput) : jsonOutput.toString();      
  }
    
  @Override
  public StringBuilder getOutput(HttpServletRequest request, String name, V value) {
    
    Object msg = super.getDefaultOutput(request, name, value);
    
    XLogger.getInstance().log(Level.FINER, "{0}={1}", getClass(), name, msg);
    
    StringBuilder json = getJsonOutput(request, name, value, msg);
    
    return json;
  }
  
  @Override
  public StringBuilder getOutput(HttpServletRequest request, String name, Throwable value) {
      
    Object msg = super.getDefaultOutput(request, name, value);
    
    XLogger.getInstance().log(Level.FINER, "{0}={1}", getClass(), name, msg);
    
    StringBuilder json = getJsonOutput(request, name, value, msg);
    
    return json;
  }

  public StringBuilder getJsonOutput(HttpServletRequest request, 
          String key, V value, Object messageCreatedFromValue)
  {
      
    int buffLen = key.length() + this.getEstimatedLengthChars(value, messageCreatedFromValue);  
    
    return this.getJsonOutput(request, key, messageCreatedFromValue, buffLen);
  }
  
  public StringBuilder getJsonOutput(HttpServletRequest request, 
          String key, Throwable value, Object messageCreatedFromThrowable)
  {
      
    int buffLen = key.length() + this.getEstimatedLengthChars(value, messageCreatedFromThrowable);  
    
    return this.getJsonOutput(request, key, messageCreatedFromThrowable, buffLen);
  }

  public StringBuilder getJsonOutput(HttpServletRequest request, String key, Object msg, int bufferLen) {
    
    if (key == null) {
      throw new NullPointerException();
    }

XLogger.getInstance().log(Level.FINE, "Output name: {0}, buffer length: {1}", this.getClass(), key, bufferLen);

    Map outputMap = Collections.singletonMap(key, msg);
    
    StringBuilder outputJson = this.getJsonOutputBuffer(request, key, msg, bufferLen);
    
    this.appendJsonOutput(request, outputMap, outputJson);
    
    return outputJson;
  }

  public StringBuilder getJsonOutputBuffer(HttpServletRequest request, String key, Object msg, int bufferLen) {
      
    if (key == null) {
      throw new NullPointerException();
    }
    
    StringBuilder outputJsonBuffer;
    if(msg == null || msg.equals("")) {
        
        outputJsonBuffer = new StringBuilder(key.length() + 16);
    
    }else{
        
        if(bufferLen < 1) {
            bufferLen = 16;
        }
        
        if(this.isTidyOutput(request)) {
            bufferLen += (bufferLen * 0.3);
        }

XLogger.getInstance().log(Level.FINER, "Creating json output buffer of length: {0}", this.getClass(), bufferLen);

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
    
    XLogger.getInstance().log(Level.FINEST, "==================== PRINTING OUTPUT ==================\n{0}", this.getClass(), appendTo);
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
}
