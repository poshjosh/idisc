package com.idisc.web.servlets.handlers;

import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.User;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @(#)Updatehitcount.java   24-Jan-2015 23:16:12
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
public class Addfeedhit extends BaseRequestHandler<Long> {

    @Override
    public boolean isProtected() {
        // We need this data, so no need to be protective
        return false;
    }

    @Override
    public Long execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException {
        
        Integer feedid;
        long hittime;
        String param = null;
        try{
            param = "feedid";
            feedid = Integer.valueOf(request.getParameter(param));
            param = "hittime";
            hittime = Long.parseLong(request.getParameter(param));
        }catch(NumberFormatException e) {
            throw new ServletException("Invalid value for required parameter: "+param, e);
        }catch(NullPointerException e) {
            throw new ServletException("Required parameter: "+param+" is missing", e);
        }
        
        // Create the installation record if it doesn't exist
        // This is a tangential task. Its failure should not affect our execution
        // 
        Installation installation;
        // Create the installation record if it doesn't exist
        boolean create = true;
        try{
            User user = this.findUser(request, response);
            installation = AppInstallation.getEntity(request, user, create);
        }catch(Exception ignored) {
            // If not ignored, too much stack trace
            installation = null;
        }

        return this.execute(installation, feedid, hittime);
    }
    
    protected Long execute(
            Installation installation, Integer feedid, long hittime) 
            throws ServletException {
                
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
        
        EntityController<Feed, Integer> feedCtrl = factory.getEntityController(Feed.class, Integer.class);
        
        Feed feed = feedCtrl.find(feedid);
        
        if(feed == null) {
            throw new ServletException("News record not found in database, id: "+feedid);
        }
        
        Feedhit feedhit = new Feedhit();
        
        feedhit.setFeedid(feed);
        
        feedhit.setInstallationid(installation);
        
        feedhit.setHittime(new Date(hittime));
        
        EntityController<Feedhit, Integer> feedhitCtrl = factory.getEntityController(Feedhit.class, Integer.class);

        Long output;
        try{
            
            feedhitCtrl.create(feedhit);
            
            Map params = Collections.singletonMap("feedid", feedid);
            
            output = feedhitCtrl.count(params);
            
        }catch(Exception e) {
            throw new ServletException("Database Error updating hitcount");
        }
        
        return output;
    }
}
