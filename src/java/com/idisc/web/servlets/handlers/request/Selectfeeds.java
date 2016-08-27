package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Feed;
import javax.servlet.http.HttpServletRequest;

public class Selectfeeds extends com.idisc.web.servlets.handlers.request.Select<Feed> {

  public Selectfeeds() {
    super(Feed.class);
  }

  @Override
  protected boolean isPaginate(HttpServletRequest request) {
    return this.isHtmlResponse(request);
  }

  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !this.isPaginate(request);
  }
}
