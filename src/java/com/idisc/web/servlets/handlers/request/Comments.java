package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Comment;
import com.idisc.pu.entities.Comment_;
import com.idisc.web.exceptions.ValidationException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Comments extends Select<Comment> {

  public Comments() {
    super(Comment.class);
  }

  public Object getFeedid(HttpServletRequest request) throws ValidationException {
    final String col = Comment_.feedid.getName();
    final String sval = request.getParameter(col);
    if (sval == null || sval.isEmpty()) {
      throw new ValidationException("Required parameter: " + col + " is missing");
    }
    try {
      return Integer.valueOf(sval);
    } catch (NumberFormatException e) {
      throw new ValidationException("Invalid value for '" + col + "': " + sval);
    }
  }
  
  @Override
  protected Map<String, Object> getSearchParams(HttpServletRequest request) 
          throws ValidationException {
    return Collections.singletonMap(Comment_.feedid.getName(), this.getFeedid(request));
  }
  
  @Override
  protected Map<String, String> getOrderBy(HttpServletRequest request)
    throws ValidationException {
    Map<String, String> orderBy = Collections.singletonMap(Comment_.datecreated.getName(), "DESC");
    return orderBy;
  }
}
