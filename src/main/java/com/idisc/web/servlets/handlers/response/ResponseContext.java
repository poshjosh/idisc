package com.idisc.web.servlets.handlers.response;

import com.idisc.web.exceptions.ValidationException;

/**
 * @author Josh
 */
public interface ResponseContext<V> {
    
  Object format(String name, V value);
  
  int getStatusCode(String name, V value);

  String getResponseMessage(String name, V value);
  
  String getTargetPage(String name, V message) throws ValidationException;
}
