package com.idisc.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class Notices
  extends FileCache
{
  public Notices() {}
  
  public Notices(HttpServletRequest request, boolean load)
  {
    super(request, load);
  }
  
  public String getDirPath()
  {
    return WebApp.getInstance().getServletContext().getRealPath("/notices");
  }
}
