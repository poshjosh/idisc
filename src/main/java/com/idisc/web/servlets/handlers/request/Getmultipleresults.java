package com.idisc.web.servlets.handlers.request;

import java.util.logging.Logger;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Getmultipleresults extends AbstractRequestHandler<Map> {
    private transient static final Logger LOG = Logger.getLogger(Getmultipleresults.class.getName());
    
  private final RequestHandlerProvider provider;
  
  public Getmultipleresults(RequestHandlerProvider provider) {
    this.provider = provider;
  }
  
  @Override
  protected Map execute(HttpServletRequest request) throws ServletException {
    
    final String [] paramNames = provider.getRequestHandlerNames(request); 
    
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
    
    final Level level = appContext.getConfiguration().getBoolean(ConfigNames.DEBUG, false) 
            ? Level.INFO : Level.FINE;

    if(LOG.isLoggable(level)) {
      LOG.log(level, "Parameter names: {0}", Arrays.toString(paramNames));
    }
    
    final Map combinedResults = new HashMap(paramNames.length, 1.0f);
    
    for(String paramName:paramNames) {
          
      try{
          
        RequestHandler handler = provider.getRequestHandler(paramName, null);
        
        Object result = handler.processRequest(request); 

        if(result != null) {
            
            combinedResults.put(paramName, result);
        }
      }catch(ServletException | IOException | RuntimeException e) {
          
        if(LOG.isLoggable(Level.WARNING)){
            LOG.log(Level.WARNING, "Error executing request '"+paramName+'\'', e);
        }
      }
    }  
    
    LOG.log(level, "Output count: {0}", combinedResults.size());

    return combinedResults;
  }
}
