package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.tribes.util.Arrays;

public class Getmultipleresults extends AbstractRequestHandler<Map> {
    
  private final RequestHandlerProvider provider;
  
  public Getmultipleresults(RequestHandlerProvider provider) {
    this.provider = provider;
  }
  
  @Override
  public Map execute(HttpServletRequest request) {
    
    final String [] paramNames = provider.getRequestHandlerNames(request);
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    
    final Level level = appContext.getConfiguration().getBoolean(ConfigNames.DEBUG, false) 
            ? Level.INFO : Level.FINE;

XLogger.getInstance().log(level, "Parameter names: {0}", this.getClass(), paramNames==null?null:Arrays.toString(paramNames));

    final Map combinedResults = new HashMap(paramNames.length, 1.0f);
    
    for(String paramName:paramNames) {
          
      try{
          
        RequestHandler handler = provider.getRequestHandler(paramName);

        Object result = handler.processRequest(request); 

        if(result != null) {
            
            combinedResults.put(paramName, result);
        }
      }catch(ServletException | IOException | RuntimeException e) {
          
        XLogger.getInstance().log(Level.WARNING, "Error executing request", this.getClass(), e);
      }
    }  
    
    XLogger.getInstance().log(level, "Output count: {0}", this.getClass(), combinedResults.size());

    return combinedResults;
  }
}
