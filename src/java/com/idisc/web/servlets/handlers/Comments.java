package com.idisc.web.servlets.handlers;

import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feeduser;
import com.idisc.pu.entities.Gender;
import com.idisc.web.exceptions.ValidationException;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


/**
 * @(#)Comments.java   02-Dec-2014 02:10:51
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
public class Comments extends Select<Comment> {
    
    public Comments() { }

    @Override
    public boolean isProtected() {
        // One may see other's comments -- temptation
        // However one may not comment without logging in
        return false;
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    protected Map toMap(EntityController<Comment, Object> ec, Comment comment) {
        
        Feeduser feeduser = comment.getFeeduserid();
        
        Feed feed = comment.getFeedid();

        // The map views of these will be added directly to the 
        // map view of the comment
        //
        comment.setFeedid(null);
        comment.setFeeduserid(null); 
        
        Map commentMap = ec.toMap(comment, false);
        
        Map feedMap = this.getOutput(feed);
        
        if(feedMap != null) {
            commentMap.put("feedid", feedMap);
        }
        
        Map feeduserMap = this.getOutput(feeduser);
        
        if(feeduserMap != null) {
            commentMap.put("feeduserid", feeduserMap);
        }
        
        return commentMap;
    }

    @Override
    protected Map<String, Object> getSearchParams(HttpServletRequest request) throws ValidationException {
        String col = "feedid";
        String sval = request.getParameter(col);
        if(sval == null) {
            throw new ValidationException("Required parameter: "+col+" is missing");
        }
        try{
            Object ival = Integer.valueOf(sval);
            return Collections.singletonMap(col, ival);
        }catch(NumberFormatException e) {
            throw new ValidationException("Invalid value for '"+col+"': "+sval);
        }
    }
    
    @Override
    protected Map<String, String> getOrderBy(HttpServletRequest request) throws ValidationException {
//@column literal @todo literals not good        
        Map<String, String> orderBy = Collections.singletonMap("datecreated", "DESC");
        return orderBy;
    }

    private Map getOutput(Feeduser feeduser) {
        
        Map feeduserMap;
        
        if(feeduser != null) {
            
            // These ones will not result in any relevant json output
            //
            feeduser.setInstallationList(null);
            feeduser.setCommentList(null);
            final Gender gender = feeduser.getGender();
            if(gender != null) {
                feeduser.setGender(new Gender(){
                    @Override
                    public String toString() {
                        return gender.getGender();
                    }
                });
            }
            feeduser.setHowDidYouFindUs(null);
            
            ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
            EntityController<Feeduser, Object> feeduserEc = 
                    factory.getEntityController(Feeduser.class);
            
            feeduserMap = feeduserEc.toMap(feeduser, false);
        }else{
            feeduserMap = null;
        }
        return feeduserMap;
    }

    private Map getOutput(Feed feed) {
        
        Map feedMap;
        
        if(feed != null) {
            
            Integer id = feed.getFeedid();
            
            // All we need is the id for now
            feed = new Feed();
            feed.setFeedid(id);
            
            ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
            EntityController<Feed, Object> feedEc = factory.getEntityController(Feed.class);
            
            feedMap = feedEc.toMap(feed, false);
            
        }else{
            
            feedMap = null;
        }
        
        return feedMap;
    }
}
