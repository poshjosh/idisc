package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadLocalJson extends AbstractRequestHandler<Map> {
    
  private final String filename;
  
  public ReadLocalJson(String filename) {
    this.filename = filename;
  }
  
  public final String getFilename() {
    return filename;
  }
  
  @Override
  public boolean isProtected() {
    return false;
  }
  
  @Override
  public String getResponseFormat(HttpServletRequest request) {
    return "text/json";
  }
  
  @Override
  public Map execute(HttpServletRequest request) throws ServletException, IOException {
      
    Map output = load(request.getServletContext());
    
    return output == null ? Collections.emptyMap() : output;
  }
  

  public Map load(ServletContext context) throws IOException {
    
XLogger.getInstance().log(Level.FINE, "Filename: {0}", this.getClass(), filename);

    String realPath = context.getRealPath(filename);
    
XLogger.getInstance().log(Level.FINE, "File path: {0}", this.getClass(), realPath);    

    Map output;
    try { Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(realPath), "utf-8"));Throwable localThrowable2 = null;
      
      try {
          
        JSONParser parser = new JSONParser();
        
        output = (Map)parser.parse(r);
        
        XLogger.getInstance().log(Level.FINE, "Read json:\n{0}", getClass(), output);
      }
      catch (IOException | ParseException localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;




      } finally {

        if (r != null) if (localThrowable2 != null) try { r.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else r.close();
      }
    } catch (ParseException e) { output = null;
      
      XLogger.getInstance().log(Level.WARNING, "Error parsing: " + realPath, getClass(), e);
    }
    
    return output == null ? Collections.emptyMap() : output;
  }
}
