package com.idisc.web.servlets.handlers.response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler<V, O> {
    
  String getCharacterEncoding();
  
  String getContentType();
  
  ResponseContext<V> getContext();
    
  void processAndSendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException;

  O processResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, V value)
    throws ServletException, IOException;
  
  void sendResponse(
          HttpServletRequest request, HttpServletResponse response, 
          String name, O value)
    throws ServletException, IOException;
}
/**
 * 
    
  boolean isHtmlResponse();
  
  String getResponseFormat();
  
  String getReferer();
    
  String getCharacterEncoding();
 * 
 */
