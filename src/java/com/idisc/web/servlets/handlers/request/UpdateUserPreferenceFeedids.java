package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Installation;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class UpdateUserPreferenceFeedids extends AbstractRequestHandler<List> {
    
  public abstract String getRequestParameterName();
  
  protected abstract List execute(HttpServletRequest request, String paramString, 
          JSONArray paramJSONArray, Installation paramInstallation) throws Exception;
  
  protected JSONArray getValues(String name, HttpServletRequest request) throws ServletException {
    String str = request.getParameter(name);
    if ((str == null) || (str.isEmpty())) {
      throw new ServletException("Required parameter " + name + " is missing");
    }
    JSONParser parser = new JSONParser();
    try {
      return (JSONArray)parser.parse(str);
    }
    catch (ParseException e) {
      throw new ServletException("Invalid format for required parameter: " + name, e);
    }
  }
  
  @Override
  protected List execute(HttpServletRequest request) throws ServletException {
      
    Installation installation = getInstallationOrException(request);
    
    String name = getRequestParameterName();
    
    List output;
    try {
        
      JSONArray values = getValues(name, request);
      
      output = execute(request, name, values, installation);
    }catch (Exception e) {
        
      output = null;
      
      throw new ServletException("Error updating user preference feedids", e);
    }
    
    return output;
  }
}
