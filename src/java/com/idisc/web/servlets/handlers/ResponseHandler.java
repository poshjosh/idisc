package com.idisc.web.servlets.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface ResponseHandler<X>
{
  public abstract String getParameterName();
  
  public abstract void setParameterName(String paramString);
  
  public abstract void respond(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, X paramX)
    throws ServletException, IOException;
  
  public abstract void respond(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Throwable paramThrowable)
    throws ServletException, IOException;
  
  public abstract String getContentType(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getCharacterEncoding(HttpServletRequest paramHttpServletRequest);
  
  public abstract int getStatusCode(HttpServletRequest paramHttpServletRequest, X paramX);
  
  public abstract int getStatusCode(HttpServletRequest paramHttpServletRequest, Throwable paramThrowable);
  
  public abstract Object getMessage(HttpServletRequest paramHttpServletRequest, X paramX);
  
  public abstract Object getMessage(HttpServletRequest paramHttpServletRequest, Throwable paramThrowable);
}
