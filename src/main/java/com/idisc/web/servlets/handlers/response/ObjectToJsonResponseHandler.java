package com.idisc.web.servlets.handlers.response;

import com.bc.util.JsonBuilder;
import java.util.logging.Logger;
import com.idisc.core.util.EntityJsonBuilder;
import com.idisc.pu.entities.Feed;
import com.idisc.web.AppContext;
import com.idisc.web.ConfigNames;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    private transient static final Logger LOG = Logger.getLogger(ObjectToJsonResponseHandler.class.getName());

  private final int bufferSize;
  
  private final JsonBuilder jsonBuilder;
  
  public ObjectToJsonResponseHandler(ResponseContext context, boolean tidyOutput, boolean plainTextOnly) {
    this(context, tidyOutput, plainTextOnly, 8192);
  }
  
  public ObjectToJsonResponseHandler(ResponseContext context, boolean tidyOutput, boolean plainTextOnly, int maxTextLengthPerItem) {
    this(context, new EntityJsonBuilder(tidyOutput, plainTextOnly, maxTextLengthPerItem), maxTextLengthPerItem);
  }

  public ObjectToJsonResponseHandler(ResponseContext context, JsonBuilder jsonBuilder) {
    this(context, jsonBuilder, 8192);
  }
  
  public ObjectToJsonResponseHandler(ResponseContext context, JsonBuilder jsonBuilder, int bufferSize) {
    super(context);
    this.bufferSize = bufferSize;
    this.jsonBuilder = jsonBuilder;
  }

  @Override
  public Map processResponse(
      HttpServletRequest request, HttpServletResponse response, String name, V message)
      throws ServletException, IOException {
    LOG.entering(this.getClass().getName(), 
            "#processResponse(HttpServletRequest, HttpServletRequest, String, V)", name);
    
//    try{
//      this.printFirstDateLastDateAndFeedIds(Level.FINE, "Json response for: "+name, (List)message);
//    }catch(Exception e) { }

    Object output = this.getContext().format(name, message);
    
    return Collections.singletonMap(name, output);
  }
  
  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, Map output)
      throws ServletException, IOException {

    LOG.entering(this.getClass().getName(), 
            "#sendResponse(HttpServletRequest, HttpServletRequest, String, Map)", name);

    this.logOutputSizes(Level.FINE, output);

    try(PrintWriter pw = response.getWriter();
            Writer appendTo = this.bufferSize < 1 ? pw : new BufferedWriter(pw, this.bufferSize)) {
        
      LOG.log(Level.FINER, "==================== Printing Output =====================\n{0}", output);

      final AppContext appContext = this.getAppContext(request);

      long tb4 = System.currentTimeMillis();
      long mb4 = appContext.getMemoryManager().getAvailableMemory();

      jsonBuilder.appendJSONString(output, appendTo); 

      this.logJsonOutput(Level.FINER, jsonBuilder, output);

      appendTo.flush();

      final Level level = this.isDebugTimeAndMemory(request) ? Level.INFO : Level.FINE;
      if(LOG.isLoggable(level)) {
        LOG.log(level, "Written to output, json response name: {0}. Consumed time: {1}, memory: {2}", 
        new Object[]{name, (System.currentTimeMillis()-tb4), (mb4-appContext.getMemoryManager().getAvailableMemory())});
      }
    }
  }
  
  private boolean isDebugTimeAndMemory(HttpServletRequest request) {
    AppContext appContext = this.getAppContext(request);
    final boolean debugTimeAndMemory = 
        appContext.getConfiguration().getBoolean(ConfigNames.DEBUG_TIME_AND_MEMORY, false);
    return debugTimeAndMemory;  
  }
  
  private void logJsonOutput(Level level, JsonBuilder jsonBuilder, Map output) {
if(LOG.isLoggable(level)) {   
    try{
      StringBuilder builder = new StringBuilder();
      jsonBuilder.appendJSONString(output, builder);
      Object oval = JSONValue.parse(builder.toString());
      LOG.log(level, "{0}\n{1}", new Object[]{builder, oval});
    }catch(Exception e) {
      if(LOG.isLoggable(Level.WARNING)){
         LOG.log(Level.WARNING, "Unexpected exception", e);
      }
    }
}
      
  }
  private void logOutputSizes(Level level, Map output) {
    if(output != null && LOG.isLoggable(level)) {
        StringBuilder builder = new StringBuilder();
        final Set keys = output.keySet();
        for(Object key:keys) {
            Object val = output.get(key);
            builder.append(key).append('=');
            if(val instanceof Collection) {
                builder.append(((Collection)val).size()).append(" elements in ").append(val.getClass().getName());
            }else if(val instanceof Map) {
                builder.append(((Map)val).size()).append(" elements in ").append(val.getClass().getName());
            }else{
                builder.append("[NO SIZE METHOD]");
            }
        }
        LOG.log(level, "==================== Printing Output Sizes =====================\n{0}", builder);
    }
  }

  private void printFirstDateLastDateAndFeedIds(Level level, String key, List response) {
    if (LOG.isLoggable(level) && response != null && !response.isEmpty()) {
      Feed first = (Feed)response.get(0);
      Feed last = (Feed)response.get(response.size() - 1);
      LOG.log(level, "{0}. First feed, date: {1}. Last feed, date: {2}. {3} items", 
          new Object[]{key, first.getFeeddate(), last.getFeeddate(), response.size()});
    }
  }
  
  public final int getBufferSize() {
    return bufferSize;
  }

  public final JsonBuilder getJsonBuilder() {
    return this.jsonBuilder;
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