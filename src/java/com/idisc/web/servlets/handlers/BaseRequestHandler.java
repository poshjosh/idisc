package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.EntityJsonFormat;
import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;












public abstract class BaseRequestHandler<X>
  extends JsonResponseHandler<X>
  implements RequestHandler<X>
{
  private EntityJsonFormat jsonFormat;
  private RequestHandlerProvider _rhp_accessViaGetter;
  
  public void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    XLogger.getInstance().entering(getClass(), "processRequest(HttpServletRequest, HttpServletResponse)", null);
    


    try
    {
      if ((isProtected()) && (!isLoggedIn(request))) {
        tryLogin(request, response);
      }
      
      if ((isProtected()) && (!isLoggedIn(request))) {
        throw new ServletException("Login required");
      }
      
      XLogger.getInstance().log(Level.FINER, "Executing", getClass());
      
      X x = execute(request, response);
      
      XLogger.getInstance().log(Level.FINER, "Execution finished, output:\n{0}", getClass(), x);
      
      getResponseHandler(request).respond(request, response, x);

    }
    catch (ServletException|IOException|RuntimeException t)
    {

      getResponseHandler(request).respond(request, response, t);
      
      XLogger.getInstance().log(Level.WARNING, "Error processing request", getClass(), t);
    }
  }
  




  public ResponseHandler<X> getResponseHandler(HttpServletRequest request)
  {
    String responseFormat = getResponseFormat(request);
    ResponseHandler<X> responseHandler;
    if ((responseFormat != null) && (responseFormat.contains("text/html")))
    {
      responseHandler = new HtmlResponseHandler();
    }
    else
    {
      responseHandler = this;
    }
    
    String paramName = getRequestHandlerProvider().getRequestHandlerParamName(request);
    
    XLogger.getInstance().log(Level.FINER, "ResponseHandler type: {0}, parameter name: {1}", getClass(), responseHandler.getClass().getName(), paramName);
    

    responseHandler.setParameterName(paramName);
    
    return responseHandler;
  }
  









  public String getResponseFormat(HttpServletRequest request)
  {
    String format = request.getParameter("format");
    
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
    
    return format;
  }
  
























  public int getBufferLength()
  {
    return 100;
  }
  



  public Installation getInstallation(HttpServletRequest request, HttpServletResponse response, boolean create)
  {
    Installation installation;
    

    try
    {
      User user = findUser(request, response);
      installation = AppInstallation.getEntity(request, user, create);
    }
    catch (Exception ignored) {
      installation = null;
    }
    return installation;
  }
  




  public boolean isProtected()
  {
    return true;
  }
  
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
  
  public void setLoggedout(HttpServletRequest request)
  {
    request.getSession().removeAttribute("user");
  }
  
  public void setLoggedIn(HttpServletRequest request, User user)
  {
    request.getSession().setAttribute("user", user);
  }
  
  public User findUser(HttpServletRequest request, HttpServletResponse response) {
    User user;
    if (isLoggedIn(request)) {
      user = getUser(request);
    } else {
      user = tryLogin(request, response);
    }
    return user;
  }
  
  public User getUser(HttpServletRequest request)
  {
    return (User)request.getSession().getAttribute("user");
  }
  
  public boolean isLoggedIn(HttpServletRequest request)
  {
    return getUser(request) != null;
  }
  


  public User setLoggedIn(HttpServletRequest request, Map authuserdetails, boolean create)
    throws ServletException
  {
    User user = null;
    try {
      user = User.getInstance(authuserdetails, create);
      setLoggedIn(request, user);
    } catch (Exception e) {
      throw new ServletException("Unexpected Error", e);
    }
    return user;
  }
  

  public EntityJsonFormat getJsonFormat()
  {
    if (this.jsonFormat == null) {
      this.jsonFormat = new EntityJsonFormat();
    }
    return this.jsonFormat;
  }
  

  public RequestHandlerProvider getRequestHandlerProvider()
  {
    if (this._rhp_accessViaGetter == null) {
      this._rhp_accessViaGetter = new RequestHandlerProviderImpl();
    }
    return this._rhp_accessViaGetter;
  }
}
