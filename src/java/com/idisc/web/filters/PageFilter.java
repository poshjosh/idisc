package com.idisc.web.filters;

import com.bc.web.core.util.AbstractRedirect;
import com.bc.web.core.util.Redirect;
import com.bc.web.core.filters.PageRedirectionFilter;
import javax.servlet.http.HttpServletRequest;

public class PageFilter extends PageRedirectionFilter {
    
  private Redirect r0_accessViaGetter;

  @Override
  public String getDigitsKey() {
    return "feedid";
  }
  
  @Override
  public Redirect getRedirect(HttpServletRequest request) {
    return getFeedRedirect(request);
  }

  public Redirect getFeedRedirect(HttpServletRequest request) {
      
    if (this.r0_accessViaGetter == null) {

      String requestUri = request.getRequestURI();
      
      if (requestUri.contains("/feed/")) {
          
        this.r0_accessViaGetter = new PageFilter.FeedRedirect();
      }
    }
    
    return this.r0_accessViaGetter;
  }

  private static final class FeedRedirect extends AbstractRedirect {
    @Override
    protected void appendDigits(long lval, StringBuilder appendTo) {
      appendTo.append("/feed?").append(this.getDigitsKey()).append('=').append(lval);
    }
    @Override
    protected String getDigitsKey() {
      return "feedid";
    }
  }
}
