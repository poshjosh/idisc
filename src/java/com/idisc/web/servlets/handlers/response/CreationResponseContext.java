package com.idisc.web.servlets.handlers.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class CreationResponseContext extends SuccessHandlerContext<Boolean> {
    
  public CreationResponseContext(HttpServletRequest request) { 
    super(request);
  }
  
  @Override
  public int getStatusCode(String name, Boolean success) {
    return success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  }
}
