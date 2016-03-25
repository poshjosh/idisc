package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.web.exceptions.ValidationException;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.web.FeedCache;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @(#)Feed.java   14-Feb-2015 11:06:08
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
public class Feed extends BaseRequestHandler<com.idisc.pu.entities.Feed> {
    
    @Override
    public String getResponseFormat(HttpServletRequest request) {
        return "text/html";
    }

    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    public com.idisc.pu.entities.Feed execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        final int feedid = this.getId(request);
        
        com.idisc.pu.entities.Feed feed = null;

        List<com.idisc.pu.entities.Feed> lastFeeds = FeedCache.getLastFeeds();
        
        if(lastFeeds != null && !lastFeeds.isEmpty()) {
            
            synchronized(lastFeeds) {
                for(com.idisc.pu.entities.Feed lastFeed:lastFeeds) {
                    if(lastFeed.getFeedid() == feedid) {
                        feed = lastFeed;
                        break;
                    }
                }
            }
            
            request.getSession().getServletContext().setAttribute("lastFeeds", lastFeeds);
        }
        
        if(feed == null) {
            
            // Select from database
            //
            feed = select(feedid);
        }
        
XLogger.getInstance().log(Level.FINER, "Selected feed: {0}", this.getClass(), feed);
        
        return feed;
    }
    
    public com.idisc.pu.entities.Feed select(int feedid) {
        
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();

        EntityController<com.idisc.pu.entities.Feed, Integer> ec = factory.getEntityController(
                com.idisc.pu.entities.Feed.class, Integer.class);

        return ec.find(feedid);
    }
    
    public Integer getId(HttpServletRequest request) throws ValidationException {
        
        String sval = request.getParameter("feedid");

        return getId(sval);
    }
        
    public Integer getId(String sval) throws ValidationException {
        
        if(sval == null || sval.isEmpty()) {
            throw new ValidationException("Required parameter 'feedid' not found");
        }
        
        int ival;
        try{
            ival = Integer.parseInt(sval);
        }catch(NumberFormatException e) {
            throw new ValidationException("Invalid value for required parameter: 'feedid'");
        }
        return ival;
    }
}
