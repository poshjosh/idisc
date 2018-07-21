package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.context.JpaContext;
import com.bc.jpa.util.MapBuilderForEntity;
import com.bc.util.MapBuilder;
import com.idisc.pu.entities.Applaunchlog;
import com.idisc.pu.entities.Installation;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Log extends AbstractRequestHandler<Boolean> {

  private transient static final Logger logger = Logger.getLogger(Log.class.getName());
    
  public static final int INSTALLATION_ERROR = 1;
  public static final int SERVICE_UNAVAILABLE = 2;
  public static final int APP_LAUNCH = 3;
    
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    RequestParameters params = new RequestParameters(request);
    
    String id = (String)params.remove("id");
    
    if (id == null) {
      throw new ServletException("Required parameter 'id' is missing");
    }
    
    return log(request, Integer.parseInt(id));
  }
  
  private Boolean log(HttpServletRequest request, final int logTypeId) throws ServletException {
      
    Installation installation = getInstallation(request, false);
    
    if(installation == null) {
        
        throw new ServletException("You are not authorized to perform the requested operation");
    }
    
    final String logTypeName = this.getName(logTypeId);

    logger.fine(() -> "x = x = x = " + 
            logTypeName + " = x = x = x = on device with installation details::\n" +
            new MapBuilderForEntity()
            .methodFilter(MapBuilder.MethodFilter.ACCEPT_ALL)
            .nullsAllowed(false)
            .maxDepth(1)
            .maxCollectionSize(10)
            .sourceType(Installation.class)
            .source(installation)
            .build()
    );
    
    switch(logTypeId) {
        case APP_LAUNCH:
            final long launchtime = this.getLongParameter(request, "launchtime", System.currentTimeMillis());
            this.logAppLaunch(this.getJpaContext(request), installation, launchtime);
            break;
    }
    
    return Boolean.TRUE;
  }
  
  private void logAppLaunch(JpaContext jpaContext, Installation installation, long launchtime) {
    Applaunchlog log = new Applaunchlog();
    log.setInstallationid(installation);
    log.setLaunchtime(new Date(launchtime));
    jpaContext.getDao(Applaunchlog.class).begin().persistAndClose(log);
  }
  
  private String getName(int id) throws ServletException {
    switch (id) {
      case INSTALLATION_ERROR: 
        return "INSTALLATION_ERROR";
      case SERVICE_UNAVAILABLE: 
        return "SERVICE_UNAVAILABLE";
      case APP_LAUNCH:
        return "APP_LAUNCH";
    }
    throw new ServletException("Invalid value for parameter: id=" + id);
  }
}
