package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Installation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class GetUserPreferenceFeedids extends AbstractRequestHandler<List> {
    
  public abstract List getFeedids(Installation paramInstallation);
  
  @Override
  public boolean isProtected(){
    return false;
  }
  
  @Override
  public List execute(HttpServletRequest request) throws ServletException, IOException {
      
    Installation installation = getInstallationOrException(request);
    
    List output = getFeedids(installation);
    
    return output == null ? Collections.EMPTY_LIST : output;
  }
}
