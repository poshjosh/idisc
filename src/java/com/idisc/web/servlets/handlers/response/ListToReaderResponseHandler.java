package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.core.EntityJsonFormat;
import com.idisc.core.util.CharIteratorReader;
import com.idisc.core.util.ListJsonCharIterator;
import com.idisc.core.util.SequenceReader;
import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class ListToReaderResponseHandler<E> extends ReaderResponseHandler<List<E>>{

  public ListToReaderResponseHandler() { 
    super(WebApp.getInstance().getConfiguration().getInt(ConfigNames.DEFAULT_CONTENT_LENGTH, 1024));  
  }

  public ListToReaderResponseHandler(int bufferSize) {
    super(bufferSize);
  }

  @Override
  public Reader getOutput(HttpServletRequest request, String name, List<E> value) {
    return this.getJsonOutputReader(request, name, value);
  }
  
  protected Reader getJsonOutputReader(HttpServletRequest request, String name, List value) {
    Reader reader;
    if(value == null || value.isEmpty()) {
      reader = this.toJsonOutputReader(request, name, value);
    }else{
      Reader prefixReader = new StringReader("{\""+name+"\":");
      Reader mainReader = new CharIteratorReader(
          new ListJsonCharIterator(value, this.getBufferSize(), this.getJsonFormat(request)){
            @Override
            protected void appendChars(StringBuilder appendTo, Object listItem) {
              super.appendChars(appendTo, listItem);
//Level level = WebApp.getInstance().isDebug() ? Level.INFO : Level.FINER;
XLogger.getInstance().log(Level.FINER, "= = = = = = = In: {0} chars = {1}", 
this.getClass(), appendTo.length(), appendTo);
            }              
          }
      );
      Reader suffixReader = new StringReader("}");
      reader = new SequenceReader(prefixReader, mainReader, suffixReader);
    }
    return reader;
  }
  
  @Override
  public Reader getOutput(HttpServletRequest request, String name, Throwable value) {
    return this.toJsonOutputReader(request, name, value);
  }
  
  private Reader toJsonOutputReader(HttpServletRequest request, String name, Object value) {
    EntityJsonFormat jsonFormat = this.getJsonFormat(request);
    StringBuilder builder = new StringBuilder();
    jsonFormat.appendJSONString(Collections.singletonMap(name, value), builder);
    return new StringReader(builder.toString());
  }
}
