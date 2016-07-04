package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.query.QueryBuilder;
import com.idisc.core.IdiscApp;
import com.idisc.pu.FeedQuery;
import com.idisc.pu.entities.Feed;
import javax.servlet.http.HttpServletRequest;

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
  protected QueryBuilder getQueryBuilder(HttpServletRequest request, String query){
      
    return new FeedQuery(IdiscApp.getInstance().getJpaContext(), query);
  }
}
