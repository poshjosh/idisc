package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.context.JpaContext;
import com.bc.jpa.controller.EntityController;
import com.bc.jpa.exceptions.PreexistingEntityException;
import java.util.logging.Logger;
import com.bc.web.core.util.ServletUtil;
import com.idisc.pu.InstallationDao;
import com.idisc.pu.User;
import com.idisc.pu.entities.Country;
import com.idisc.pu.entities.Feeduser;
import com.idisc.pu.entities.Installation;
import com.idisc.pu.entities.Installation_;
import com.idisc.web.AppContext;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.BaseHandler;
import com.idisc.web.servlets.request.AppVersionCode;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class SessionUserHandlerImpl extends BaseHandler implements SessionUserHandler {
    
  private transient static final Logger LOG = Logger.getLogger(SessionUserHandlerImpl.class.getName());
    
  private Installation installation;

  public Installation getInstallationOrException(HttpServletRequest request) 
      throws ServletException {
      
    installation = this.getInstallation(request, true);
      
    if(installation == null) {
      if(LOG.isLoggable(Level.FINE)) {
        LOG.log(Level.FINE, "getInstallationOrException(HttpServletRequest)\nCOULD NOT FIND INSTALLATION FOR REQUEST\n{0}\n{1}", 
                new Object[]{ServletUtil.getDetails(request.getSession(), "\n", Level.FINEST),
                ServletUtil.getDetails(request, "\n", Level.FINEST)}); 
      }
      throw new ServletException("You are not authorized to perform the requested operation");
    }
      
    return installation;  
  }

  public Installation getInstallation(HttpServletRequest request, boolean createIfNone) {
      
    User user = getUser(request);
     
    installation = this.getInstallation(request, user, createIfNone);
    
    return installation;
  }
  
  public Installation getInstallation(HttpServletRequest request, User user, boolean createIfNone) {
      
    if(installation != null) {
        
        return installation;
    }

    Integer installationid;
    String installationkey = null;
    String screenname = null;
    long firstinstall = 0;
    long lastinstall = 0;
    
    try {
        
      final boolean exceptionIfNone = user == null;
      
      installationid = (int)this.getLongParameter(request, Installation_.installationid.getName(), -1L);
      
      installationkey = this.getParameter(request, Installation_.installationkey.getName(), exceptionIfNone);
      
      screenname = this.getParameter(request, Installation_.screenname.getName(), false); 
      
      firstinstall = this.getLongParameter(request, Installation_.firstinstallationdate.getName(), -1L);
      
      lastinstall = this.getLongParameter(request, Installation_.lastinstallationdate.getName(), -1L);
      
      JpaContext jpaContext = this.getAppContext(request).getIdiscApp().getJpaContext();
      
      Map<String, String> params = new RequestParameters(request);
      
      InstallationDao is = new InstallationDao(jpaContext);
      
      Country country;
      try{
        params = is.parseJsonParameters(Country.class, params);
        country = is.getEntity(Country.class, params, null);
      }catch(RuntimeException e) {
        country = null;
        LOG.log(Level.WARNING, "Error creating country from: "+params, e);
      }
      
      installation = is.from(
          user, installationid, installationkey, screenname, 
          country, firstinstall, lastinstall, createIfNone);
      
    }catch (ServletException e) {
      installation = null;
      if(LOG.isLoggable(Level.FINE)){
         LOG.log(Level.FINE, "{0}", e.toString());
      }
    }
    
    if(installation != null) {
        
        if(LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "Installation. id: {0}, country: {1}, screenname: {2}, first install date: {3}", 
            new Object[]{installation.getInstallationid(), 
            installation.getCountryid()==null?null:installation.getCountryid().getCountry(), 
            installation.getScreenname(), installation.getFirstinstallationdate()});
        }
      this.setAttributeForAsync(request, "installation", installation);
      
    }else{
        
      if(LOG.isLoggable(Level.FINER)) {
          
        StringBuilder builder = new StringBuilder();
        if(createIfNone) {
            builder.append("Failed to create installation for user:\n");
        }else{
            builder.append("Could not find installation for user:\n");
        }
        this.appendDetails(builder, user, installationkey, screenname, firstinstall, lastinstall);
        builder.append('\n');
        builder.append(ServletUtil.getDetails(request, "\n", Level.FINEST));
        LOG.log(Level.WARNING, "{0}", builder);
      }
    }
    return installation;
  }
  
  private void appendDetails(
      StringBuilder appendTo, User user, String installationkey, 
      String screenname, long firstinstall, long lastinstall) {
    if(user == null) {
      appendTo.append("User: null");
    }else{
      appendTo.append("User: ").append('(').append(user.getEmailAddress()).append(':').append(user.getUsername()).append(')');
    }
    appendTo.append(", installationkey: ").append(installationkey);
    appendTo.append(", screenname: ").append(screenname);
    appendTo.append(", firstinstall: ").append(firstinstall<1?firstinstall:new Date(firstinstall));
    appendTo.append(", lastinstall: ").append(firstinstall<1?firstinstall:new Date(lastinstall));
  }

  @Override
  public User tryLogin(HttpServletRequest request) {
    User user;
    try {
        
      Login login = new Login();
      
      login.processRequest(request);
      
      user = this.getUser(request);
      
    } catch (ServletException | IOException | RuntimeException e) {
      user = null;
    }
    return user;
  }
  
  @Override
  public void setLoggedout(HttpServletRequest request) {
    this.removeAttributeForAsync(request, "user");
  }
  
  @Override
  public void setLoggedIn(HttpServletRequest request, User user) {
    this.setAttributeForAsync(request, "user", user);
    LOG.log(Level.FINER, "Updated session attribute user = {0}", user);
  }
  
  @Override
  public User findUser(HttpServletRequest request) {
      
    User user;
    if (isLoggedIn(request)) {
      user = this.getUser(request);
    } else {
      user = this.tryLogin(request);
    }
    
    LOG.log(Level.FINE, "Found user: {0}", user);
    return user;
  }
  
  @Override
  public User getUser(HttpServletRequest request) {
    User user = (User)this.getAttributeForAsync(request, "user");
    LOG.log(Level.FINER, "User: {0}", user);
    return user;
  }
  
  @Override
  public boolean isLoggedIn(HttpServletRequest request) {
    return this.getUser(request) != null;
  }

  @Override
  public User setLoggedIn(HttpServletRequest request, Map authuserdetails, boolean create)
    throws ServletException {

    AppVersionCode versionCodeManager = new AppVersionCode(request.getServletContext(), null);
    
    Installation mInstallation;
    if(versionCodeManager.isLessOrEquals(request, 24, true)) {
      mInstallation = null;
    }else{
      mInstallation = this.getInstallation(request, null, true);  
    }
    
    if(mInstallation != null) {
        if(LOG.isLoggable(Level.FINE)){
            LOG.log(Level.FINE, "Installation details: screenname: {0}, installationid: {1}", 
                    new Object[]{ mInstallation.getScreenname(),  mInstallation.getInstallationid()});
        }  
    }
    
    User user = null;
    try {
        
      AppContext appContext = this.getAppContext(request);
      
      user = getUser(appContext.getIdiscApp().getJpaContext(), 
              mInstallation, authuserdetails, create);
      LOG.log(Level.FINER, "User: {0}", user);

      setLoggedIn(request, user);
      
    } catch (Exception e) {
      throw new ServletException("Unexpected error during Login", e);
    }
    return user;
  }
  
  protected User getUser(
      JpaContext factory, Installation installation, Map authdetails, boolean create)
      throws PreexistingEntityException, Exception {
    
    Feeduser feeduser = installation == null ? null : installation.getFeeduserid();
    
    if(feeduser == null) {
      feeduser = new Feeduser();  
    }
    
    User output = new User(feeduser, authdetails);
    
    String email = output.getAuthEmailaddress();
    feeduser.setEmailAddress(email);
    
    final boolean existing = installation == null ? false : installation.getFeeduserid() != null;
    
    if(!existing) {
        
        EntityController<Feeduser, Integer> ec = factory.getEntityController(Feeduser.class, Integer.class);

        Map where = ec.toMap(feeduser, false);

        List<Feeduser> found = ec.select(where, "AND");

        if (found.isEmpty()) {

          if (create) {

            LOG.log(Level.FINER, "Creating user: {0}", feeduser);

            feeduser.setDatecreated(new Date());

            if(installation != null) {
              feeduser.setInstallationList(new ArrayList(Arrays.asList(installation)));
            }
            
            try {
                
              ec.persist(feeduser);

            LOG.log(Level.FINE, "Created user: {0}", feeduser);

            } catch (Exception e) {
              output = null;
              throw e;
            }
          } else {
            output = null;
          }
        } else if (found.size() == 1) {
          feeduser = (Feeduser)found.get(0);
          output = new User(feeduser, authdetails);
        } else {
          output = null;
          throw new UnsupportedOperationException("Found > 1 records where 1 or less was expected, entity: " + Feeduser.class.getName() + ", parameters: " + authdetails);
        }
    }
    
if(LOG.isLoggable(Level.FINE)){
LOG.log(Level.FINE, "User: {0}", output);
}

    return output;
  }
  
    private void create(EntityManager em, Feeduser entity) throws PreexistingEntityException, Exception {
        try {
            EntityTransaction t = em.getTransaction();
            try{
                t.begin();
                em.persist(entity);
                t.commit();
            }finally{
                if(t.isActive()) {
                    t.rollback();
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
  
  
  boolean getBooleanProperty(HttpServletRequest request, String propertyName, Boolean defaultValue) {
    return this.getAppContext(request).getConfiguration().getBoolean(propertyName, defaultValue);
  }

  Integer getIntegerProperty(HttpServletRequest request, String propertyName, Integer defaultValue) {
    return this.getAppContext(request).getConfiguration().getInteger(propertyName, defaultValue);
  }
  
  long getLongParameter(HttpServletRequest request, String paramName, long defaultValue) 
      throws ServletException {
    
    String paramValue = this.getParameter(request, paramName, false);
    
    return paramValue == null ? defaultValue : Long.parseLong(paramValue);
  }
  
  String getParameter(HttpServletRequest request, String paramName, boolean exceptionIfNone) 
      throws ServletException {
    
    String paramValue = request.getParameter(paramName);
    
    if((paramValue == null || paramValue.isEmpty()) && exceptionIfNone) {
      throw new ValidationException("Required parameter: "+paramName+" not found. Request parameters: "+new RequestParameters(request));  
    }
    
    return paramValue;
  }
}
