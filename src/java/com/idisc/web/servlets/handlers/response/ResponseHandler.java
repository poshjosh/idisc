package com.idisc.web.servlets.handlers.response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler<V>
{
  
  void processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException;

  void processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Throwable value)
    throws ServletException, IOException;

  void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException;
  
  void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, Throwable value)
    throws ServletException, IOException;
  
  String getContentType(HttpServletRequest request);
  
  String getCharacterEncoding(HttpServletRequest request);
  
  int getStatusCode(HttpServletRequest paramHttpServletRequest, String name, V value);
  
  int getStatusCode(HttpServletRequest paramHttpServletRequest, String name, Throwable value);
  
  Object getOutput(HttpServletRequest paramHttpServletRequest, String name, V value);
  
  Object getOutput(HttpServletRequest paramHttpServletRequest, String name, Throwable value);
}
