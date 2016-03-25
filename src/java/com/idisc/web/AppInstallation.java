package com.idisc.web;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;














public class AppInstallation
{
  private static int count;
  private static final long timeOffsetMillis;
  
  static
  {
    Calendar cal = Calendar.getInstance();
    cal.set(2016, 0, 22, 0, 0, 0);
    timeOffsetMillis = cal.getTimeInMillis();
  }
  

  public static Installation getEntity(HttpServletRequest request, User user, boolean create)
    throws ServletException
  {
    if (user != null) {
      List<Installation> list = user.getInstallationList();
      if ((list != null) && (!list.isEmpty()))
      {
        return (Installation)list.get(list.size() - 1);
      }
    }
    
    String col = "installationkey";
    
    String sval = request.getParameter(col);
    
    if ((sval == null) || (sval.isEmpty())) {
      throw new ServletException("Required parameter '" + col + "' is missing");
    }
    Installation output;
    try
    {
      output = getEntity(col, sval);
      
      if ((output == null) && (create))
      {
        Date date = new Date();
        
        String screenname = request.getParameter("screenname");
        
        if ((screenname != null) && (!screenname.isEmpty())) {
          Installation _orignal_screenname_owner = getEntity("screenname", screenname);
          if (_orignal_screenname_owner != null)
          {
            screenname = generateUniqueId(date.getTime());
          }
        }
        
        output = new Installation();
        
        output.setInstallationkey(sval);
        
        output.setScreenname(screenname);
        
        long firsttime = getRequiredLongParameter(request, "firstinstallationdate");
        output.setFirstinstallationdate(new Date(firsttime));
        
        long lasttime = getRequiredLongParameter(request, "lastinstallationdate");
        output.setLastinstallationdate(new Date(lasttime));
        
        output.setDatecreated(date);
        
        output.setFeeduserid(user == null ? null : user.getDelegate());
        try
        {
          EntityController<Installation, Integer> ec = getEntityController();
          ec.create(output);
        } catch (Exception e) {
          throw new ServletException("Error creating database record for installation with installationkey: " + sval, e);
        }
      }
    } catch (RuntimeException e) {
      throw new ServletException("Error accessing database installation record for installationkey: " + sval, e);
    }
    
    return output;
  }
  
  public static String generateUniqueId(long time) {
    long n = time - timeOffsetMillis;
    return "user_" + Long.toHexString(n) + "_" + count++;
  }
  
  public static Installation getEntity(HttpServletRequest request, String columnName, boolean required)
    throws ServletException
  {
    String columnValue = request.getParameter(columnName);
    
    if ((columnName == null) || (columnName.isEmpty())) {
      if (required) {
        throw new ServletException("Required parameter '" + columnName + "' is missing");
      }
      return null;
    }
    

    return getEntity(columnName, columnValue);
  }
  
  public static Installation getEntity(String columnName, String columnValue) throws ServletException {
    try {
      EntityController<Installation, Integer> ec = getEntityController();
      return (Installation)getEntity(ec, columnName, columnValue);
    } catch (RuntimeException e) {
      throw new ServletException("Error accessing database installation record for installationkey: " + columnValue, e);
    }
  }
  

  public static <E> E getEntity(EntityController<E, Integer> ec, String columnName, String columnValue)
    throws ServletException
  {
    if ((columnValue == null) || (columnValue.isEmpty())) {
      throw new ServletException("Required parameter 'installationkey' is missing");
    }
    E output;
    try {
      List<E> found = ec.select(columnName, columnValue, -1, -1);
      int count = found == null ? 0 : found.size();
      if (count < 1) {
        output = null; 
      } else { 
        if (count == 1) {
          output = found.get(0);
        } else
          throw new ServletException("Found > 1 record where only 1 was expected for installationkey=" + columnValue);
      }
    } catch (RuntimeException e) {
      throw new ServletException("Error accessing database installation record for installationkey: " + columnValue, e);
    }
    return output;
  }
  
  public static EntityController<Installation, Integer> getEntityController() {
    ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
    EntityController<Installation, Integer> ec = factory.getEntityController(Installation.class, Integer.class);
    return ec;
  }
  
  private static long getRequiredLongParameter(HttpServletRequest request, String name) throws ServletException {
    long lval = getLongParameter(request, name, -1L);
    if (lval == -1L) {
      throw new ServletException("Required parameter: " + name + " is missing");
    }
    return lval;
  }
  
  private static long getLongParameter(HttpServletRequest request, String name, long defaultValue)
  {
    String sval = request.getParameter(name);
    return sval == null ? defaultValue : Long.parseLong(sval);
  }
}
