package com.idisc.web.servlets.handlers;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Archivedfeed;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Feeduser;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Site;
import com.idisc.pu.entities.Sitetype;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class Valueexists
  extends BaseRequestHandler<Boolean>
{
  public boolean isProtected()
  {
    return false;
  }
  



  public Boolean execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    RequestParameters params = new RequestParameters(request);
    
    String table = (String)params.remove("table");
    
    if (table == null) {
      throw new ServletException("Missing value for required parameter: 'table'");
    }
    





    EntityController ec = getEntityController(table);
    
    if (ec == null) {
      throw new ServletException("Invalid value for required parameter: 'table'");
    }
    
    String connector = (String)params.remove("connector");
    
    if ((connector == null) || (connector.isEmpty())) {
      connector = "AND";
    }
    
    List found = ec.select(params, null, connector, 1, 0);
    
    if ((found == null) || (found.isEmpty())) {
      return Boolean.FALSE;
    }
    
    return Boolean.TRUE;
  }
  
  public EntityController getEntityController(String table) {
    ControllerFactory cf = IdiscApp.getInstance().getControllerFactory();
    switch (table) {
    case "installation": 
      return cf.getEntityController(Installation.class);
    case "feed": 
      return cf.getEntityController(Feed.class);
    case "comment": 
      return cf.getEntityController(Comment.class);
    case "feeduser": 
      return cf.getEntityController(Feeduser.class);
    case "site": 
      return cf.getEntityController(Site.class);
    case "sitetype": 
      return cf.getEntityController(Sitetype.class);
    case "archivedfeed": 
      return cf.getEntityController(Archivedfeed.class);
    }
    return null;
  }
}
