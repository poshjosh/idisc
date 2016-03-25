package com.idisc.web.servlets.handlers;

import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feeduser;
import com.idisc.web.exceptions.ValidationException;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.jpa.exceptions.PreexistingEntityException;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @(#)Newcomment.java   30-Nov-2014 20:35:58
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
public class Newcomment extends BaseRequestHandler<Boolean> {
    
    public Newcomment() { }

    @Override
    public int getSuccessCode(Boolean x) {
        return x == null ? super.getSuccessCode(x) : HttpServletResponse.SC_CREATED;
    }

    @Override
    public Boolean execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        Comment comment = new Comment();

//@column literals @avoid these        
        comment.setCommentSubject(request.getParameter("commentSubject"));
        comment.setCommentText(request.getParameter("commentText"));
        
        Feed feedId = this.getEntity(request, Feed.class, "feedid");
        comment.setFeedid(feedId);
        
        Feeduser feeduserId = this.getEntity(request, Feeduser.class, "feeduserid");
        comment.setFeeduserid(feeduserId);
        
        comment.setRepliedto(this.getInteger(request, "repliedto"));
        
        EntityController<Comment, Object> ec = this.getEntityController(Comment.class);
        
        Boolean output = Boolean.FALSE;
        try{
            comment.setDatecreated(new Date());
            ec.create(comment);
            output = Boolean.TRUE;
        }catch(PreexistingEntityException e) {
            throw new ValidationException("The comment you tried to create already exists");
        }catch(Exception e) {
            throw new ServletException("Unexpected error occured!");
        }
        
        return output;
    }
    
    private <T> T getEntity(HttpServletRequest request, Class<T> aClass, String column) {
        EntityController<T, Object> ec = this.getEntityController(aClass);
        Integer feedIdVal = this.getInteger(request, column);
        T entity = ec.find(feedIdVal);
        return entity;
    }   
    
    private Integer getInteger(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        if(paramValue == null) {
            return null;
        }else{
            return Integer.valueOf(paramValue);
        }
    }
    
    private <T> EntityController<T, Object> getEntityController(Class<T> aClass) {
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
        return factory.getEntityController(aClass);
    }
}
