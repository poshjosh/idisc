package com.idisc.web.servlets.handlers.response;

import com.idisc.core.util.CharIteratorReader;
import com.idisc.core.util.ListJsonCharIterator;
import com.idisc.core.util.SequenceReader;
import com.idisc.web.ConfigNames;
import com.idisc.web.WebApp;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONValue;

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
    return this.getOutputReader(request, name, value);
  }
  
  protected Reader getOutputReader(HttpServletRequest request, String name, List value) {
    // We build a reader that will serve a json Map of format (name = value)
    Reader prefixReader = new StringReader("{\""+name+"\":");
    Reader mainReader = new CharIteratorReader(new ListJsonCharIterator(value, this.getBufferSize(), this.getJsonFormat(request)));
    Reader suffixReader = new StringReader("}");
    return new SequenceReader(prefixReader, mainReader, suffixReader);
  }
  
  @Override
  public Reader getOutput(HttpServletRequest request, String name, Throwable value) {
    Map map = Collections.singletonMap(name, value);
    return new StringReader(JSONValue.toJSONString(map));
  }
}
