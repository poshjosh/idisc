package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.util.EntityJsonBuilder;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONValue;

/**
 * @author Josh
 */
public class ObjectToJsonResponseHandler<V> extends DirectResponseHandler<V, Map> {
    
  private final int bufferSize;
  
  public ObjectToJsonResponseHandler(HttpServletRequest request, ResponseContext context) {
    this(request, context, 8192);
  }
  
  public ObjectToJsonResponseHandler(HttpServletRequest request, ResponseContext context, int bufferSize) {
    super(request, context);
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
        
XLogger.getInstance().entering(this.getClass(), "#sendResponse(HttpServletRequest, HttpServletRequest, String, Map)", name);
      
    final PrintWriter pw = response.getWriter();
    
    final BufferedWriter bw = this.bufferSize < 1 ? new BufferedWriter(pw) : new BufferedWriter(pw, this.bufferSize);
     
    final Class cls = this.getClass();
    final XLogger logger = XLogger.getInstance();
    final Level level = Level.FINER;
    try {
        
logger.log(level, "==================== Printing Output =====================\n{0}", cls, output);

      this.getJsonBuilder(request).appendJSONString(output, bw);
      
if(logger.isLoggable(level, cls)) {      
      StringBuilder builder = new StringBuilder();
      new EntityJsonBuilder(1000).appendJSONString(output, builder);
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