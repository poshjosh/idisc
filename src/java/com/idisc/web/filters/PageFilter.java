package com.idisc.web.filters;

import com.bc.web.core.util.AbstractDigitsRedirect;
import com.bc.web.core.util.Redirect;
import com.bc.web.core.filters.AbstractPageRedirectionFilter;
import com.bc.web.core.util.SimpleRedirect;
import com.idisc.pu.entities.Feed_;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class PageFilter extends AbstractPageRedirectionFilter {

  private final Pattern feedByIdPattern;

  public PageFilter() {
    feedByIdPattern = Pattern.compile("/feed/(\\d{1,}).+?");      
  }
  
  /**
   * Redirects <tt>/feed/23605.jsp</tt> to <tt>/feed?feedid=23605</tt>
   */  
  private final class FeedRedirect extends AbstractDigitsRedirect {
    public FeedRedirect() { }
    public FeedRedirect(boolean clientSideRedirection) {
      super(clientSideRedirection);
    }
    @Override
    public String getRedirectUri(HttpServletRequest request) {
      final String requestUri = request.getRequestURI();  
      return feedByIdPattern.matcher(requestUri).find() ? super.getRedirectUri(request) : null;
    }
    @Override
    protected String getPrefix() {
      return "/feed";
    }
    @Override
    protected String getDigitsKey() {
      return Feed_.feedid.getName();
    }
  }
  
  @Override
  public Redirect getRedirect(HttpServletRequest request) {
      
    final String requestUri = request.getRequestURI();
    
    if (feedByIdPattern.matcher(requestUri).find()) {
        
      return new PageFilter.FeedRedirect();
      
    }else if(requestUri.endsWith("/feeds.jsp")) { 
        
//      return new SimpleRedirect("/feeds.jsp", "/searchresults?" + Searchresults.NO_QUERY + "=1");
      return new SimpleRedirect("/feeds.jsp", "/searchresults");
      
    }else{
        
      return null;  
    }
  }
}
