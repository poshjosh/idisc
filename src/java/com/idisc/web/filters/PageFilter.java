package com.idisc.web.filters;

import com.bc.webapputil.AbstractRedirect;
import com.bc.webapputil.Redirect;
import com.bc.webapputil.filters.PageRedirectionFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class PageFilter extends PageRedirectionFilter {

    public PageFilter() { }    

    @Override
    public String getDigitsKey() {
        return "feedid";
    }

    @Override
    public Redirect getRedirect(HttpServletRequest request) {
        return this.getFeedRedirect(request);
    }
    
    private Redirect r0_accessViaGetter;
    public Redirect getFeedRedirect(HttpServletRequest request) {
        
        if(r0_accessViaGetter == null) {
            
            // Redirect only urls containing /feed/ e.g /feed/2167.jsp
            // This ensures we do not mistakenly redirect urls of format /404.jsp
            // to feed?feedid=404
            //
            final String requestUri = request.getRequestURI();
            if(requestUri.contains("/feed/")) {
                r0_accessViaGetter = new FeedRedirect();
            }
        }
        
        return r0_accessViaGetter;
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
