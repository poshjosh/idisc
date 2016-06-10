package com.idisc.web.servlets.handlers.request;

import com.idisc.core.jpa.FeedSearch;
import com.idisc.core.jpa.Search;
import com.idisc.pu.entities.Feed;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ListToReaderResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.idisc.web.servlets.handlers.response.SelectfeedsJsonResponseHandler;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class Selectfeeds extends SearchHandler<Feed> {

  public Selectfeeds() { }

  public Selectfeeds(long maxLimit, long defaultLimit, long minLimit) {
    super(maxLimit, defaultLimit, minLimit);
  }
  
  @Override
  public boolean isOutputLarge() {
    return true;
  }
  
  @Override
  public ResponseHandler<List<Feed>, Object> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<List<Feed>, Object> responseHandler;
    if (this.isHtmlResponse(request)) {
      responseHandler = new HtmlResponseHandler();
    } else {
      if(this.isOutputLarge() && this.isStreamLargeResponses()) {  
        responseHandler = new ListToReaderResponseHandler();
      }else{
        responseHandler = new SelectfeedsJsonResponseHandler();
      }
    }
    
    return responseHandler;
  }

  @Override
  protected Class<Feed> getEntityClass()
  {
    return Feed.class;
  }

  @Override
  protected Search<Feed> getSearch() {
    return new FeedSearch();
  }
}
