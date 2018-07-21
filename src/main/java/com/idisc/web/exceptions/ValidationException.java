package com.idisc.web.exceptions;

import javax.servlet.ServletException;






















public class ValidationException
  extends ServletException
{
  public ValidationException() {}
  
  public ValidationException(String msg)
  {
    super(msg);
  }
  
  public ValidationException(String msg, Throwable rootCause) {
    super(msg, rootCause);
  }
}
