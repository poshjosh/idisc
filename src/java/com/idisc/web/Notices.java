package com.idisc.web;

import javax.servlet.ServletContext;

public class Notices extends FileCache {
    
  public Notices(ServletContext context){
    this(context, true);
  }

  public Notices(ServletContext context, boolean load) {
    super(context, context.getRealPath("/notices"), load);
  }
}
