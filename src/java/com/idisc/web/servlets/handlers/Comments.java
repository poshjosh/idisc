package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Comment;
import com.idisc.web.exceptions.ValidationException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


















public class Comments
  extends Select<Comment>
{
  public boolean isProtected()
  {
    return false;
  }
  
  protected Class<Comment> getEntityClass()
  {
    return Comment.class;
  }
  
  protected Map<String, Object> getSearchParams(HttpServletRequest request) throws ValidationException
  {
    String col = "feedid";
    String sval = request.getParameter(col);
    if (sval == null) {
      throw new ValidationException("Required parameter: " + col + " is missing");
    }
    try {
      Object ival = Integer.valueOf(sval);
      return Collections.singletonMap(col, ival);
    } catch (NumberFormatException e) {
      throw new ValidationException("Invalid value for '" + col + "': " + sval);
    }
  }
  
  protected Map<String, String> getOrderBy(HttpServletRequest request)
    throws ValidationException
  {
    Map<String, String> orderBy = Collections.singletonMap("datecreated", "DESC");
    return orderBy;
  }
}
