package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Installation;
import com.idisc.web.exceptions.ValidationException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public abstract class AbstractUpdateValues
  extends BaseRequestHandler<Map>
{
  public abstract String[] getNames();
  
  protected abstract int execute(String paramString, Object paramObject, Installation paramInstallation)
    throws Exception;
  
  public boolean isProtected()
  {
    return false;
  }
  
  protected Object getValues(String name, HttpServletRequest request) throws ValidationException {
    String str = request.getParameter(name);
    if ((str == null) || (str.isEmpty())) {
      throw new ValidationException("Required parameter " + name + " is missing");
    }
    JSONParser parser = new JSONParser();
    try {
      return parser.parse(str);
    }
    catch (ParseException e) {
      throw new ValidationException("Invalid format for required parameter: " + name, e);
    }
  }
  



  public Map execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
  {
    boolean create = true;
    Installation installation = getInstallation(request, response, create);
    
    String[] names = getNames();
    
    Map output = new HashMap(3, 1.0F);
    try {
      for (String name : names)
      {
        Object values = getValues(name, request);
        
        int updated = execute(name, values, installation);
        
        output.put(name, Integer.valueOf(updated));
      }
    }
    catch (ValidationException e) {
      throw e;
    }
    catch (Exception e)
    {
      throw new ServletException("Error updating values", e);
    }
    
    return output;
  }
}
