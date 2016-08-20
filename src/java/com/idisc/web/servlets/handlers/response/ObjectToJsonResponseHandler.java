package com.idisc.web.servlets.handlers.response;

import com.bc.util.JsonBuilder;
import com.bc.util.XLogger;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONValue;

/**
 * @author Josh
 * @param <V>
 */
public class ObjectToJsonResponseHandler<V> extends DirectResponseHandler<V, Map> {

  private final int bufferSize;
  
  public ObjectToJsonResponseHandler(ResponseContext context, boolean tidyOutput, boolean plainTextOnly) {
    this(context, tidyOutput, plainTextOnly, 8192);
  }
  
  public ObjectToJsonResponseHandler(ResponseContext context, boolean tidyOutput, boolean plainTextOnly, int maxTextLengthPerItem) {
    super(context, tidyOutput, plainTextOnly, maxTextLengthPerItem);
    this.bufferSize = maxTextLengthPerItem;
  }

  public ObjectToJsonResponseHandler(ResponseContext context, JsonBuilder jsonBuilder) {
    this(context, jsonBuilder, 8192);
  }
  
  public ObjectToJsonResponseHandler(ResponseContext context, JsonBuilder jsonBuilder, int bufferSize) {
    super(context, jsonBuilder);
    this.bufferSize = bufferSize;
  }

  @Override
  public Map processResponse(
      HttpServletRequest request, HttpServletResponse response, String name, V message)
      throws ServletException, IOException {
XLogger.getInstance().entering(this.getClass(), "#processResponse(HttpServletRequest, HttpServletRequest, String, V)", name);
    
    Object output = this.getContext().format(name, message);
    
    return Collections.singletonMap(name, output);
  }
  
  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, Map output)
      throws ServletException, IOException {

    final Class cls = this.getClass();
    final XLogger logger = XLogger.getInstance();
    final Level level = Level.FINER;
      
logger.entering(cls, "#sendResponse(HttpServletRequest, HttpServletRequest, String, Map)", name);

this.logOutput(logger, level, output);

    final PrintWriter pw = response.getWriter();
    
    final BufferedWriter bw = this.bufferSize < 1 ? new BufferedWriter(pw) : new BufferedWriter(pw, this.bufferSize);
     
    try {
        
logger.log(level, "==================== Printing Output =====================\n{0}", cls, output);

      JsonBuilder jsonBuilder = this.getJsonBuilder();

      jsonBuilder.appendJSONString(output, bw);
      
if(logger.isLoggable(level, cls)) {      
      StringBuilder builder = new StringBuilder();
      jsonBuilder.appendJSONString(output, builder);
      Object oval = JSONValue.parse(builder.toString());
logger.log(level, "{0}\n{1}", cls, builder, oval); 
}
      bw.flush();
         
    }finally{
      this.close(pw);
      this.close(bw);
    }
  }
  
  private void close(AutoCloseable c) {
    if(c == null) {
      return;
    }  
    try { c.close(); } catch (Exception e) { 
      XLogger.getInstance().log(Level.WARNING, "Failed to close", getClass(), e);
    }     
  }
  
  private void logOutput(XLogger logger, Level level, Map output) {
    final Class cls = this.getClass();
    if(output != null && logger.isLoggable(level, cls)) {
        StringBuilder builder = new StringBuilder();
        final Set keys = output.keySet();
        for(Object key:keys) {
            Object val = output.get(key);
            builder.append(key).append('=');
            if(val instanceof Collection) {
                builder.append(((Collection)val).size()).append(" elements");
            }else if(val instanceof Map) {
                builder.append(((Map)val).size()).append(" elements");
            }else{
                builder.append("[NO SIZE METHOD FOR THIS VALUE]");
            }
        }
        logger.log(level, "==================== Printing Output =====================\n{0}", cls, builder);
    }
  }

  public final int getBufferSize() {
    return bufferSize;
  }
}
/**
 * 
  @Override
  public Map getOutput(HttpServletRequest paramHttpServletRequest, String name, Throwable value) {
    return Collections.singletonMap(name, value);
  }

  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, Throwable message)
      throws ValidationException, ServletException, IOException {
      
    Map output = getOutput(request, name, message);
    
    this.sendResponse(request, response, output);
  }
  
 * 
 */