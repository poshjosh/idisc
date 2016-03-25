package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Installation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class GetUserPreferenceFeedids
  extends BaseRequestHandler<List>
{
  public abstract List getFeedids(Installation paramInstallation);
  
  public boolean isProtected()
  {
    return true;
  }
  



  public List execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    boolean create = true;
    Installation installation = getInstallation(request, response, create);
    
    List output = getFeedids(installation);
    
    return output == null ? Collections.EMPTY_LIST : output;
  }
}
