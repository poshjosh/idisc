package com.idisc.web;

import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Installation;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.User;
//import com.idisc.pu.entities.Installation_;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


/**
 * @(#)DeviceInstallation.java   28-Mar-2015 05:50:36
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class AppInstallation {

    public static Installation getEntity(HttpServletRequest request, User user, boolean create) throws ServletException {
        
        Installation output;
        
        if(user != null) {
            List<Installation> list = user.getInstallationList();
            if(list != null && !list.isEmpty()) {
// Always return the most recent installation by the user                
                return list.get(list.size()-1);
            }
        }
        
        String col = "installationkey";//Installation_.installationkey.getName();
        
        String sval = request.getParameter(col);
        
        if(sval == null || sval.isEmpty()) {
            throw new ServletException("Required parameter 'installationkey' is missing");
        }
        
        try{
            
            output = AppInstallation.getEntity(col, sval);
            
            if(output == null && create) {
                output = new Installation();
                output.setInstallationkey(sval);
                long firsttime = getRequiredLongParameter(request, "firstinstallationdate");//Installation_.firstinstallationdate.getName());
                output.setFirstinstallationdate(new Date(firsttime));
                long lasttime = getRequiredLongParameter(request, "lastinstallationdate");//Installation_.lastinstallationdate.getName());
                output.setLastinstallationdate(new Date(lasttime));
                output.setDatecreated(new Date());
                output.setFeeduserid(user==null?null:user.getDelegate()); 
                try{
                    EntityController<Installation, Integer> ec = getEntityController();
                    ec.create(output);
                }catch(Exception e) {
                    throw new ServletException("Error creating database record for installation with installationkey: "+sval, e);
                }
            }
        }catch(RuntimeException e) {
            throw new ServletException("Error accessing database installation record for installationkey: "+sval, e);
        }
        
        return output;
    }

    public static Installation getEntity(String columnName, String columnValue) throws ServletException {
        
        Installation output;
        
        if(columnValue == null || columnValue.isEmpty()) {
            throw new ServletException("Required parameter 'installationkey' is missing");
        }
        
        try{
            EntityController<Installation, Integer> ec = getEntityController();
            List<Installation> found = ec.select(columnName, columnValue, -1, -1);
            int count = found == null ? 0 : found.size();
            if(count < 1) {
                output = null;
            }else if (count == 1) {
                output = found.get(0);
            }else{
                throw new ServletException("Found > 1 record where only 1 was expected for installationkey="+columnValue);
            }
        }catch(RuntimeException e) {
            throw new ServletException("Error accessing database installation record for installationkey: "+columnValue, e);
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
        if(lval == -1L) {
            throw new ServletException("Required parameter: "+name+" is missing");
        }else{
            return lval;
        }
    }
    
    private static long getLongParameter(HttpServletRequest request, String name, long defaultValue) {
        String sval = request.getParameter(name);
        return sval == null ? defaultValue : Long.parseLong(sval);
    }
}
