/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idisc.web.functions;

import com.bc.web.core.util.FileExtensions;
import com.bc.web.core.util.ServletUtil;
import java.io.Serializable;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 7, 2018 9:11:58 PM
 */
public class RequestIsFromOrToWebResourceTest 
        implements Predicate<HttpServletRequest>, Serializable {

  private transient static final Logger LOG = Logger.getLogger(RequestIsFromOrToWebResourceTest.class.getName());

  @Override
  public boolean test(HttpServletRequest request) {
    String referer = request.getHeader("referer");
    String uri = request.getRequestURI();
    boolean output = this.isRequestFromOrToAWebPage(referer, uri);
    if(LOG.isLoggable(Level.FINER)) {
        LOG.log(Level.FINER, "Request is from or to web page: {0}, Referer: {1}, Request URI: {2}", 
                new Object[]{output, referer, uri});
    }
    return output;
  }  
    
  public boolean isRequestFromOrToAWebPage(String referer, String uri) {
    referer = this.getExtensionOrEmptyString(referer);
    uri = this.getExtensionOrEmptyString(uri);
    boolean output = (isJspPage(referer) || isJspPage(uri) || isStaticPage(referer) || isStaticPage(uri));
    return output;
  }

  public boolean isStaticResource(String str) {
      boolean output = str != null && FileExtensions.isStaticResource(str);
//System.out.println(this.getClass().getName()+". Static resource: "+output+", value: "+str);      
      return output;
  }
  
  public boolean isStaticPage(String str) {
      boolean output = str != null && FileExtensions.isStaticPage(str);
//System.out.println(this.getClass().getName()+". Static page: "+output+", value: "+str);      
      return output;
  }
  
  public boolean isJspPage(String str) {
      boolean output = str != null && FileExtensions.isJspPage(str);
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
}
