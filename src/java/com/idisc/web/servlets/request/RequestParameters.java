package com.idisc.web.servlets.request;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Feeduser_;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * <p><b>This class is a wrapper for a specified requests parameter.</b></p>
 * You should only use this class when you are sure each parameter in the request
 * has only one value. If any parameter might have more than one value, the value 
 * selected for that parameter is equal to the first value in the array returned 
 * by {@link javax.servlet.ServletRequest#getParameterValues(java.lang.String)}
 * @author Josh
 */
public class RequestParameters extends HashMap<String, String> {
  
  private final Boolean caseSensitive;

  public RequestParameters(HttpServletRequest request) {
    this(request, Boolean.TRUE);    
  }
  
  public RequestParameters(HttpServletRequest request, Boolean caseSensitive) {

    this.caseSensitive = caseSensitive;
    
    this.init(request);
  }
  
  private void init(HttpServletRequest request) {
    
    final Level level = Level.FINEST;
    final Class cls = this.getClass();
    final XLogger logger = XLogger.getInstance();

    Enumeration<String> en = request.getParameterNames(); 
    
    while (en.hasMoreElements()) {
        
      String name = en.nextElement();
      
      String value = request.getParameter(name);
      
if(logger.isLoggable(level, cls))     
logger.log(level, "{0} = {1}", cls, name, value);
      
      if(value != null) {
       
        this.add(name, value);
        
      }else{
      
        String [] values = request.getParameterValues(name);
        
if(logger.isLoggable(level, cls)) 
logger.log(level, "{0} = {1}", cls, name, values==null?null:Arrays.toString(values));

        if ((values != null) && (values.length != 0) && values[0] != null) {
        
          this.add(name, values[0]);
        }
      }
    }
    
    final String key0 = Feeduser_.emailAddress.getName();
    final String key1 = com.authsvc.client.parameters.Getuser.ParamName.emailaddress.name();
    String email = this.get(key0);
    if(email != null) {
        this.put(key1, email);
    }else{
        email = this.get(key1);
        if(email != null) {
            this.put(key0, email);
        }
    }
  }
  
  public void add(String name, String value) {
    put(name, value);
    if (!isCaseSensitive()) {
      put(name.toLowerCase(), value);
    }
  }
  
  public final Boolean isCaseSensitive() {
    return this.caseSensitive;
  }
}
