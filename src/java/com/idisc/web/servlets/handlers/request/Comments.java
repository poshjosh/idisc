package com.idisc.web.servlets.handlers.request;

import com.idisc.core.jpa.FeedSearchResults;
import com.idisc.pu.entities.Comment;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.CommentsJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ListToReaderResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Comments extends Select<Comment> {
    
  @Override
  public boolean isOutputLarge() {
    return true;
  }
  
  @Override
  public ResponseHandler<List<Comment>, Object> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<List<Comment>, Object> responseHandler;
    if (this.isHtmlResponse(request)) {
      responseHandler = new HtmlResponseHandler();
    }else {
      if(this.isOutputLarge() && this.isStreamLargeResponses()) {
        responseHandler = new ListToReaderResponseHandler();  
      }else{
        responseHandler = new CommentsJsonResponseHandler();  
      }
    }
    
    return responseHandler;
  }
    
  @Override
  public boolean isProtected()
  {
    return false;
  }
  
  @Override
  protected Class<Comment> getEntityClass()
  {
    return Comment.class;
  }
  
  @Override
  protected List<Comment> select(HttpServletRequest request) throws ServletException
  {
      
    Map<String, Object> searchParams = getSearchParams(request);
    
    Map<String, String> orderBy = getOrderBy(request);

    int offset = getOffset(request);
    
    int limit = getLimit(request);
    
    return getEntityController().select(searchParams, orderBy, offset, limit);
  }

  public List<com.idisc.pu.entities.Feed> select(String toFind, Date after, int offset, int limit)
  {

    FeedSearchResults searchresults = new FeedSearchResults(toFind, after, limit);
    
    return searchresults.getAllResults();
  }

  public Object getFeedid(HttpServletRequest request) throws ValidationException {
    String col = "feedid";
    String sval = request.getParameter(col);
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
  protected Map<String, Object> getSearchParams(HttpServletRequest request) throws ValidationException
  {
    String col = "feedid";
    return Collections.singletonMap(col, this.getFeedid(request));
  }
  
  @Override
  protected Map<String, String> getOrderBy(HttpServletRequest request)
    throws ValidationException
  {
    Map<String, String> orderBy = Collections.singletonMap("datecreated", "DESC");
    return orderBy;
  }
}
