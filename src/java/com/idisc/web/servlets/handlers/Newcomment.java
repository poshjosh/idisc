package com.idisc.web.servlets.handlers;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.jpa.exceptions.PreexistingEntityException;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
















public class Newcomment
  extends BaseRequestHandler<Boolean>
{
  public boolean isProtected()
  {
    return false;
  }
  
  public int getStatusCode(HttpServletRequest request, Boolean x)
  {
    return x == null ? super.getStatusCode(request, x) : 201;
  }
  


  public Boolean execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Installation installation = getInstallation(request, response, true);
    
    if (installation == null) {
      throw new NullPointerException();
    }
    
    Comment comment = new Comment();
    

    comment.setCommentSubject(request.getParameter("commentSubject"));
    comment.setCommentText(request.getParameter("commentText"));
    
    Feed feedId = (Feed)getEntity(request, Feed.class, "feedid");
    comment.setFeedid(feedId);
    
    comment.setInstallationid(installation);
    
    EntityController<Comment, Object> ec = getEntityController(Comment.class);
    
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
      throw new ServletException("Unexpected error occured!");
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
    if (paramValue == null) {
      return null;
    }
    return Integer.valueOf(paramValue);
  }
  
  private <T> EntityController<T, Object> getEntityController(Class<T> aClass)
  {
    ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
    return factory.getEntityController(aClass);
  }
}
