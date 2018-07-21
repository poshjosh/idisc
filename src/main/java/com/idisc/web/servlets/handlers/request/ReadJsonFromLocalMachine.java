package com.idisc.web.servlets.handlers.request;

import com.bc.util.JsonFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJsonFromLocalMachine extends AbstractRequestHandler<Map> {

  private transient static final Logger LOG = Logger.getLogger(ReadJsonFromLocalMachine.class.getName());
    
  private final String filename;
  
  public ReadJsonFromLocalMachine(String filename) {
    this.filename = filename;
  }
  
  public final String getFilename() {
    return filename;
  }
  
  @Override
  public String getResponseFormat(HttpServletRequest request) {
    return "application/json";
  }
  
  @Override
  protected Map execute(HttpServletRequest request) throws ServletException, IOException {
      
    Map output = load(request);
    
    return output == null ? Collections.emptyMap() : output;
  }
  

  public Map load(HttpServletRequest request) throws IOException {
    return load(request.getServletContext());
  }   
    
  public Map load(ServletContext context) throws IOException {
    
    LOG.log(Level.FINE, "Filename: {0}", filename);

    final String realPath = context.getRealPath(filename);
    
    File file = new File(realPath);
    LOG.log(Level.FINER, "new File(fname): {0}", file);
    if(!file.exists()) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
        if(url != null) {
            file = new File(url.getFile());
            LOG.log(Level.FINER, "new File(ContextClassLoader.getResource(fname).getFile()): {0}", file);
        }
    }
    Map output;
    if(file.exists()) {
      output = this.load(new FileInputStream(file), file.toString());
    }else{
      output = Collections.EMPTY_MAP;
    }
    return output;
  }   
  
  public Map load(String realPath) throws IOException {
    
    LOG.log(Level.FINE, "File path: {0}", realPath);
    
    return load(new FileInputStream(realPath), realPath);
  }

  public Map load(InputStream in, String name) throws IOException {
    
    Map output;
    try { 
        
      Reader r = new BufferedReader(new InputStreamReader(in, "utf-8"));
     
      Throwable localThrowable2 = null;
      
      try {
          
        JSONParser parser = new JSONParser();
        
        output = (Map)parser.parse(r);
        
        LOG.log(Level.FINE, "Read json:\n{0}", 
                output == null || output.isEmpty() ? null:  
                new JsonFormat(true, true, "  ").toJSONString(output));
        
      }catch (IOException | ParseException localThrowable1) {
          
        localThrowable2 = localThrowable1;
        
        throw localThrowable1;

      } finally {

        if (r != null) if (localThrowable2 != null) try { r.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else r.close();
      }
    } catch (ParseException e) { output = null;
      
      LOG.log(Level.WARNING, "Error parsing: " + name, e);
    }
    
    return output == null ? Collections.emptyMap() : output;
  }
}
