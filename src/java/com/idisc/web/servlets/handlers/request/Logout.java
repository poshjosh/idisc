package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.HtmlBooleanResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout
  extends AbstractRequestHandler<Boolean>
{
  @Override
  public boolean isProtected()
  {
    return false;
  }
  
  @Override
  public ResponseHandler<Boolean> createResponseHandler(HttpServletRequest request)
  {
    ResponseHandler<Boolean> responseHandler;
    if (this.isHtmlResponse(request))
    {
      responseHandler = new HtmlBooleanResponseHandler();
    }
    else
    {
      responseHandler = super.createResponseHandler(request);
    }
    
    return responseHandler;
  }

  @Override
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
