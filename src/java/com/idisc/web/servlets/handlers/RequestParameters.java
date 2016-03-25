package com.idisc.web.servlets.handlers;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;













public class RequestParameters
  extends HashMap<String, String>
{
  public RequestParameters() {}
  
  public RequestParameters(HttpServletRequest request)
  {
    setRequest(request);
  }
  
  public void setRequest(HttpServletRequest request)
  {
    Enumeration<String> en = request.getParameterNames();
    while (en.hasMoreElements()) {
      String name = (String)en.nextElement();
      String[] values = request.getParameterValues(name);
      if ((values != null) && (values.length != 0))
      {


        put(name, values[0]);
        
        if (!isCaseSensitive()) {
          put(name.toLowerCase(), values[0]);
        }
      }
    }
  }
  










  public boolean isCaseSensitive()
  {
    return true;
  }
}
