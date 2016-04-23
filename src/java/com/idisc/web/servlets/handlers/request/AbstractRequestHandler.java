package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.EntityJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.bc.util.XLogger;
import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import com.idisc.web.exceptions.LoginException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractRequestHandler<V> 
  implements RequestHandler<V>
{
  
  public abstract V execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException;
  
  @Override
  public RequestHandler.RequestHandlerEntry getNextRequestHandler(HttpServletRequest request) {
      return null;
  }
  
  @Override
  public V processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    XLogger.getInstance().entering(getClass(), "processRequest(HttpServletRequest, HttpServletResponse)", null);

      if ((isProtected()) && (!isLoggedIn(request))) {
        tryLogin(request, response);
      }
      
      if ((isProtected()) && (!isLoggedIn(request))) {
        throw new LoginException("Login required");
      }
      
      XLogger.getInstance().log(Level.FINER, "Executing", getClass());
      
      V x = execute(request, response);
      
      XLogger.getInstance().log(Level.FINER, "Execution finished, output:\n{0}", getClass(), x);
      
      return x;
  }
  
  public final boolean isHtmlResponse(HttpServletRequest request) {
    String responseFormat = getResponseFormat(request);
    boolean output = (responseFormat != null) && (responseFormat.contains("text/html"));
    return output;
  }

  private ResponseHandler<V> responseHandler;
  @Override
  public final ResponseHandler<V> getResponseHandler(HttpServletRequest request)
  {
      if(responseHandler == null) {
          responseHandler = this.createResponseHandler(request);
      }
      return responseHandler;
  }
  
  public ResponseHandler<V> createResponseHandler(HttpServletRequest request)
  {
    ResponseHandler<V> output;
    if (this.isHtmlResponse(request))
    {
      output = new HtmlResponseHandler();
    }
    else
    {
      output = new EntityJsonResponseHandler();
    }
    
    return output;
  }
  
  public String getResponseFormat(HttpServletRequest request)
  {
    String format = request.getParameter("format");
XLogger.getInstance().log(Level.FINER, "Request.getParameter(format): {0}", this.getClass(), format);
    
    if (format == null)
    {
      String requestUri = request.getRequestURI();
      
      int start = requestUri.lastIndexOf('/');
      
      if (start != -1)
      {
        int n = requestUri.indexOf('.', start);
        
        format = n == -1 ? null : "text/html";
      }
    }
XLogger.getInstance().log(Level.FINER, "Response format: {0}", this.getClass(), format);
    
    return format;
  }
  
  public Installation getInstallation(HttpServletRequest request, HttpServletResponse response, boolean create)
  {
XLogger.getInstance().log(Level.FINER, "getInstallation(HttpServletRequest, HttpServletResponse, boolean)", this.getClass());
    Installation installation;
    
    try
    {
      User user = findUser(request, response);
      installation = AppInstallation.getEntity(request, user, create);
    }
    catch (Exception ignored) {
      installation = null;
    }
    if(installation != null) {
        
      // ////////////////////////
      //
      request.getSession().setAttribute("installation", installation);
    }
    return installation;
  }

  @Override
  public boolean isProtected()
  {
    return true;
  }
  
  @Override
  public User tryLogin(HttpServletRequest request, HttpServletResponse response)
  {
    User user;
    try {
      Login login = new Login();
      login.execute(request, response);
      
      user = getUser(request);
    }
    catch (ServletException|IOException|RuntimeException e)
    {
      user = null;
    }
    return user;
  }
  
  @Override
  public void setLoggedout(HttpServletRequest request)
  {
    request.getSession().removeAttribute("user");
  }
  
  @Override
  public void setLoggedIn(HttpServletRequest request, User user)
  {
    request.getSession().setAttribute("user", user);
XLogger.getInstance().log(Level.FINER, "Updated session attribute user = {0}", this.getClass(), user);
  }
  
  @Override
  public User findUser(HttpServletRequest request, HttpServletResponse response) {
XLogger.getInstance().log(Level.FINER, "findUser(HttpServletRequest, HttpServletResponse)", this.getClass());
    User user;
    if (isLoggedIn(request)) {
      user = getUser(request);
    } else {
      user = tryLogin(request, response);
    }
XLogger.getInstance().log(Level.FINE, "Found user: {0}", User.class, user);
    return user;
  }
  
  @Override
  public User getUser(HttpServletRequest request)
  {
    User user = (User)request.getSession().getAttribute("user");
XLogger.getInstance().log(Level.FINER, "User: {0}", this.getClass(), user);
    return user;
  }
  
  @Override
  public boolean isLoggedIn(HttpServletRequest request)
  {
    return getUser(request) != null;
  }

  @Override
  public User setLoggedIn(HttpServletRequest request, Map authuserdetails, boolean create)
    throws ServletException
  {
    User user = null;
    try {
      user = User.getInstance(authuserdetails, create);
XLogger.getInstance().log(Level.FINER, "User: {0}", this.getClass(), user);
      setLoggedIn(request, user);
    } catch (Exception e) {
      throw new ServletException("Unexpected Error", e);
    }
    return user;
  }
}
