package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;











public class Addfeedhit
  extends AbstractRequestHandler<Long>
{
  private ControllerFactory _cf;
  private EntityController<Feedhit, Integer> _fhc;
  private EntityController<Feed, Integer> _fc;
  
  @Override
  public boolean isProtected()
  {
    return false;
  }
  


  @Override
  public Long execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
  {
    Installation installation = getInstallation(request, response, true);
    
    if(installation == null) {
      throw new ServletException("You are not authorized to perform the requested operation");
    }

    String param = null;
    Integer feedid;
    long hittime; try { param = "feedid";
      feedid = Integer.valueOf(request.getParameter(param));
      param = "hittime";
      hittime = Long.parseLong(request.getParameter(param));
    } catch (NumberFormatException e) {
      throw new ServletException("Invalid value for required parameter: " + param, e);
    } catch (NullPointerException e) {
      throw new ServletException("Required parameter: " + param + " is missing", e);
    }
    
    return execute(installation, feedid, hittime);
  }
  

  protected Long execute(Installation installation, Integer feedid, long hittime)
    throws ServletException
  {
    Feed feed = (Feed)getFeedController().find(feedid);
    
    if (feed == null) {
      throw new ServletException("News record not found in database, ID: " + feedid);
    }
    
    Feedhit feedhit = new Feedhit();
    
    feedhit.setFeedid(feed);
    
    feedhit.setInstallationid(installation);
    
    feedhit.setHittime(new Date(hittime));
    
    EntityController<Feedhit, Integer> feedhitCtrl = getFeedhitController();
    
    Long output;
    try
    {
      feedhitCtrl.create(feedhit);
      
      Map params = Collections.singletonMap("feedid", feedid);
      
      output = Long.valueOf(feedhitCtrl.count(params));
    }
    catch (Exception e) {
      throw new ServletException("Database Error updating hitcount");
    }
    
    return output;
  }
  
  private ControllerFactory getControllerFactory()
  {
    if (this._cf == null) {
      this._cf = IdiscApp.getInstance().getControllerFactory();
    }
    return this._cf;
  }
  
  private EntityController<Feedhit, Integer> getFeedhitController()
  {
    if (this._fhc == null) {
      ControllerFactory factory = getControllerFactory();
      this._fhc = factory.getEntityController(Feedhit.class, Integer.class);
    }
    return this._fhc;
  }
  
  private EntityController<Feed, Integer> getFeedController()
  {
    if (this._fc == null) {
      ControllerFactory factory = getControllerFactory();
      this._fc = factory.getEntityController(Feed.class, Integer.class);
    }
    return this._fc;
  }
}
