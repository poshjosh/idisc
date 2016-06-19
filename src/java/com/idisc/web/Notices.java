package com.idisc.web;

public class Notices extends FileCache {
    
  public Notices(){
    this(true);
  }

  public Notices(boolean load) {
    super(WebApp.getInstance().getServletContext().getRealPath("/notices"), load);
  }
}
