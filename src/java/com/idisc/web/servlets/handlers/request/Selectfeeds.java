package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.FeedQuery;
import com.idisc.pu.entities.Feed;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.dao.BuilderForSelect;

public class Selectfeeds extends SearchHandler<Feed> {

  public Selectfeeds() { }

  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
      
    return !this.isHtmlResponse(request);
  }
  
  @Override
  protected Class<Feed> getEntityClass() {

    return Feed.class;
  }

  @Override
  protected BuilderForSelect getSelect(HttpServletRequest request, String query){
      
    return new FeedQuery(this.getJpaContext(request), query);
  }
}
