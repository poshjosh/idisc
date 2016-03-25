package com.idisc.web.servlets.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















public class Logout
  extends BaseRequestHandler<Boolean>
{
  public boolean isProtected()
  {
    return false;
  }
  



  public Boolean execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    if (!isLoggedIn(request)) {
      return Boolean.TRUE;
    }
    
    setLoggedout(request);
    
    return Boolean.TRUE;
  }
}
