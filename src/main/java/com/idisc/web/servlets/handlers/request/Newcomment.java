package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.controller.EntityController;
import com.bc.jpa.exceptions.PreexistingEntityException;
import java.util.logging.Logger;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Newcomment extends NewEntityHandler<Comment> {
    private transient static final Logger LOG = Logger.getLogger(Newcomment.class.getName());

    @Override
  public Class getEntityType() {
    return Comment.class;
  }
  
  @Override
  protected Boolean execute(HttpServletRequest request)
    throws ServletException, IOException {
      
    Installation installation = getInstallationOrException(request);
    
    Comment comment = new Comment();
    
    comment.setCommentSubject(request.getParameter("commentSubject"));
    comment.setCommentText(request.getParameter("commentText"));
    
    Feed feedId = (Feed)getEntity(request, Feed.class, "feedid");
    comment.setFeedid(feedId);
    
    comment.setInstallationid(installation);
    
    EntityController<Comment, Integer> ec = this.getJpaContext(request).getEntityController(Comment.class, Integer.class);
    
    Integer repliedto_id = getInteger(request, "repliedto");
    if (repliedto_id != null) {
      try {
        Comment repliedto = (Comment)ec.find(repliedto_id);
        comment.setRepliedto(repliedto);
      } catch (Exception e) {
        if(LOG.isLoggable(Level.WARNING)){
            LOG.log(Level.WARNING, "Error updating repliedto", e);
        }
      }
    }
    
    Boolean output = Boolean.FALSE;
    try {
      comment.setDatecreated(new Date());
      ec.persist(comment);
      output = Boolean.TRUE;
    } catch (PreexistingEntityException e) {
      throw new ValidationException("The comment you tried to create already exists");
    } catch (Exception e) {
      throw new ServletException("An unexpected error occured posting the comment");
    }
    
    return output;
  }
  
  private <T> T getEntity(HttpServletRequest request, Class<T> aClass, String column) {
    EntityController<T, Object> ec = this.getJpaContext(request).getEntityController(aClass);
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
