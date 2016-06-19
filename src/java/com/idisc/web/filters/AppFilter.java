package com.idisc.web.filters;

import com.bc.util.XLogger;
import com.bc.web.core.util.ServletUtil;
import com.bc.web.core.filters.BaseFilter;
import com.idisc.web.Attributes;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author poshjosh
 */
public class AppFilter extends BaseFilter {
  
    public AppFilter() { }    

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        final long sessionTimeoutSeconds;

        if(this.isRequestFromOrToAWebPage(request)) { 

          sessionTimeoutSeconds = 30 * 60;

          request.setAttribute(Attributes.REQUEST_FROM_OR_TO_WEBPAGE, Boolean.TRUE);

        }else{

          sessionTimeoutSeconds = TimeUnit.MILLISECONDS.toSeconds(
                  WebApp.getInstance().getAppSessionTimeoutMillis());
        }

        request.getSession().setMaxInactiveInterval((int)sessionTimeoutSeconds);

        return super.doBeforeProcessing(request, response);
    }

  public boolean isRequestFromOrToAWebPage(HttpServletRequest request) {
    String referer = request.getHeader("referer");
    String uri = request.getRequestURI();
    boolean output = this.isRequestFromOrToAWebPage(referer, uri);
XLogger.getInstance().log(Level.FINER, "Request is from or to web page: {0}, Referer: {1}, Request URI: {2}", 
        this.getClass(), output, referer, uri);
    return output;
  }  
    
  public boolean isRequestFromOrToAWebPage(String referer, String uri) {
    referer = this.getExtensionOrEmptyString(referer);
    uri = this.getExtensionOrEmptyString(uri);
    boolean output = (isJspPage(referer) || isJspPage(uri) || isStaticPage(referer) || isStaticPage(uri));
    return output;
  }

  public boolean isStaticResource(String str) {
      boolean output = str != null && ServletUtil.isStaticResource(str);
//System.out.println(this.getClass().getName()+". Static resource: "+output+", value: "+str);      
      return output;
  }
  
  public boolean isStaticPage(String str) {
      boolean output = str != null && ServletUtil.isStaticPage(str);
//System.out.println(this.getClass().getName()+". Static page: "+output+", value: "+str);      
      return output;
  }
  
  public boolean isJspPage(String str) {
      boolean output = str != null && ServletUtil.isJspPage(str);
//System.out.println(this.getClass().getName()+". Jsp page: "+output+", value: "+str);      
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
            String output = super.getParameter(name);
            if(output == null && "format".equals(name)) {
                output = "text/html";
            }
XLogger.getInstance().log(Level.FINEST, "#getParameter({0}) = {1}", this.getClass(), name, output);
            return output;
        }

        @Override
        public Enumeration getParameterNames() {

            Enumeration<String> parameterNames = getRequest().getParameterNames();
            
            final String formatParamName = "format";
            boolean hasFormatParam = false;
            while(parameterNames.hasMoreElements()) {
                String next = parameterNames.nextElement();
                if(formatParamName.equals(next)) {
                    hasFormatParam = true;
                    break;
                }
            }
            
            // This enumeration has already be used and must not be returned
            parameterNames = null; 
            
            return hasFormatParam ? getRequest().getParameterNames() : this.getCompositeNames(parameterNames);
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
                
                Object value = output.get("format");
                
                if(value == null) {
                
                    output.put("format", "text/html");
                }
            }
            
            return output;
        }
    }
}
