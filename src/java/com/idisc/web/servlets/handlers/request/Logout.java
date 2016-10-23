package com.idisc.web.servlets.handlers.request;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Logout extends AbstractRequestHandler<Boolean> {
    
  @Override
  protected Boolean execute(HttpServletRequest request) throws ServletException, IOException {
    if (!isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    setLoggedout(request);
    
    return Boolean.TRUE;
  }
}
