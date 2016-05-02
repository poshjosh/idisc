package com.idisc.web.filters;

import com.bc.util.XLogger;
import com.bc.web.core.util.ServletUtil;
import com.bc.web.core.filters.BaseFilter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author poshjosh
 */
public class AppFilter extends BaseFilter {
    
  public AppFilter() { }    

  @Override
  protected HttpServletRequest wrapRequest(final HttpServletRequest request){
    if(this.isRequestFromOrToAWebPage(request)) {
XLogger.getInstance().log(Level.FINE, "Wrapped request to: {0}", this.getClass(), AppFilter.HtmlRequestWrapper.class.getName());
      return new AppFilter.HtmlRequestWrapper(request);
    }else{
      return super.wrapRequest(request);
    }
  }
  
  private boolean isRequestFromOrToAWebPage(HttpServletRequest request) {
    String referer = request.getHeader("referer");
    String refererExt = this.getExtensionOrEmptyString(referer);
    String uri = request.getRequestURI();
    String uriExt = this.getExtensionOrEmptyString(uri);
    
    boolean output = ((uriExt != null && !ServletUtil.isStaticResource(uriExt)) && 
            (refererExt != null && ServletUtil.isJspPage(refererExt)) || 
            (uriExt != null && ServletUtil.isJspPage(uriExt)) || 
            (refererExt != null && ServletUtil.isStaticPage(refererExt)) || 
            (uriExt != null && ServletUtil.isStaticPage(uriExt)));

if(output) {    
    XLogger.getInstance().log(Level.FINER, "Referer: {0}, Request URI: {1}", this.getClass(), referer, uri);
}

    return output;
  }
  
  private String getExtensionOrEmptyString(String s) {
      String output;
      if(s == null || s.isEmpty()) {
          output = null;
      }else {
          output = ServletUtil.getExtension(s);
      }
      return output;
  }
  
    /**
     * This request wrapper class extends the support class
     * HttpServletRequestWrapper, which implements all the methods in the
     * HttpServletRequest interface, as delegations to the wrapped request. 
     */
    private static final class HtmlRequestWrapper extends HttpServletRequestWrapper {
        
        public HtmlRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String output;
            if("format".equals(name)) {
                output = "text/html";
            }else{
                output = super.getParameter(name);
            }
XLogger.getInstance().log(Level.FINER, "#getParameter. continue filter chain: {0}", this.getClass(), output);
            return output;
        }

        @Override
        public Enumeration getParameterNames() {

            Enumeration<String> output = getRequest().getParameterNames();
            
            return this.getCompositeNames(output);
        }
        
        private Enumeration getCompositeNames(Enumeration<String> enumeration) {
            
            if(enumeration != null) {

                // Add both names and their long versions
                //
                final List<String> names = new ArrayList<>();
                
                while(enumeration.hasMoreElements()) {
                    
                    String name = enumeration.nextElement();
                    
                    names.add(name);
                }
                
                names.add("format");

XLogger.getInstance().log(Level.FINER, "Composite names: {0}", this.getClass(), names);
                
                if(!names.isEmpty()) {
                    
                    enumeration = new Enumeration<String>() {
                        int off;
                        @Override
                        public boolean hasMoreElements() {
                            return off < names.size();
                        }
                        @Override
                        public String nextElement() {
                            try{
                                return names.get(off);
                            }finally{
                                ++off;
                            }
                        }
                    };
                }
            }
            
            return enumeration;
        }        
        
        @Override
        public Map getParameterMap() {

            Map output = getRequest().getParameterMap();
            
            if(output != null) {
                
                output.put("format", "text/html");
            }
            
            return output;
        }
    }
}
