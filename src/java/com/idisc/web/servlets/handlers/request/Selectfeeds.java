package com.idisc.web.servlets.handlers.request;

import com.idisc.core.jpa.FeedSearch;
import com.idisc.core.jpa.Search;
import com.idisc.pu.entities.Feed;
import javax.servlet.http.HttpServletRequest;

public class Selectfeeds extends SearchHandler<Feed> {

  public Selectfeeds() { }

  public Selectfeeds(long maxLimit, long defaultLimit, long minLimit) {
    super(maxLimit, defaultLimit, minLimit);
  }
  
  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !this.isHtmlResponse(request);
  }
  
  @Override
  protected Class<Feed> getEntityClass() {
    return Feed.class;
  }

  @Override
  protected Search<Feed> getSearch() {
    return new FeedSearch();
  }
}
