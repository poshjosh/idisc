package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.bc.jpa.exceptions.PreexistingEntityException;
import com.bc.util.XLogger;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.exceptions.InstallationException;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Newcomment extends NewEntityHandler<Comment> {

    @Override
  public Class getEntityType() {
    return Comment.class;
  }
  
  @Override
  public Boolean execute(HttpServletRequest request)
    throws ServletException, IOException
  {
XLogger.getInstance().log(Level.FINER, "execute(HttpServletRequest, HttpServletResponse)", this.getClass());
    Installation installation = getInstallation(request, true);
    
    if(installation == null) {
      throw new InstallationException("You are not authorized to perform the requested operation");
    }

    Comment comment = new Comment();
    
    comment.setCommentSubject(request.getParameter("commentSubject"));
    comment.setCommentText(request.getParameter("commentText"));
    
    Feed feedId = (Feed)getEntity(request, Feed.class, "feedid");
    comment.setFeedid(feedId);
    
    comment.setInstallationid(installation);
    
    EntityController<Comment, Object> ec = getEntityController();
    
    Integer repliedto_id = getInteger(request, "repliedto");
    if (repliedto_id != null) {
      try {
        Comment repliedto = (Comment)ec.find(repliedto_id);
        comment.setRepliedto(repliedto);
      } catch (Exception e) {
        XLogger.getInstance().log(Level.WARNING, "Error updating repliedto", getClass(), e);
      }
    }
    
    Boolean output = Boolean.FALSE;
    try {
      comment.setDatecreated(new Date());
      ec.create(comment);
      output = Boolean.TRUE;
    } catch (PreexistingEntityException e) {
      throw new ValidationException("The comment you tried to create already exists");
    } catch (Exception e) {
      throw new ServletException("An unexpected error occured posting the comment");
    }
    
    return output;
  }
  
  private <T> T getEntity(HttpServletRequest request, Class<T> aClass, String column) {
    EntityController<T, Object> ec = getEntityController(aClass);
    Integer feedIdVal = getInteger(request, column);
    T entity = ec.find(feedIdVal);
    return entity;
  }
  
  private Integer getInteger(HttpServletRequest request, String paramName) {
    String paramValue = request.getParameter(paramName);
    if (paramValue == null || paramValue.isEmpty()) {
      return null;
    }
    return Integer.valueOf(paramValue);
  }
}
