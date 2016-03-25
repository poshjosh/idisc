package com.idisc.web.servlets.handlers;

import com.bc.util.JsonFormat;
import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class JsonResponseHandler<X>
  extends AbstractResponseHandler<X>
{
  private JsonFormat _jf;
  
  public String getContentType(HttpServletRequest request)
  {
    return "text/plain;charset=" + getCharacterEncoding(request);
  }
  
  protected void sendResponse(HttpServletRequest request, HttpServletResponse response, X message)
    throws ServletException, IOException
  {
    try
    {
      PrintWriter out = response.getWriter();Throwable localThrowable2 = null;
      try {
        Object output = getMessage(request, message);
        
        out.println(output.toString());
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;

      }
      finally
      {

        if (out != null) if (localThrowable2 != null) try { out.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else out.close();
      }
    } catch (Throwable t) { XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  protected void sendResponse(HttpServletRequest request, HttpServletResponse response, Throwable message)
    throws ValidationException, ServletException, IOException
  {
    try
    {
      PrintWriter out = response.getWriter();Throwable localThrowable2 = null;
      try {
        Object output = getMessage(request, message);
        
        out.println(output.toString());
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;

      }
      finally
      {

        if (out != null) if (localThrowable2 != null) try { out.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else out.close();
      }
    } catch (Throwable t) { XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  public Object getMessage(HttpServletRequest request, X x)
  {
    String key = getParameterName();
    Object msg = super.getMessage(request, x);
    XLogger.getInstance().log(Level.FINER, "{0}={1}", getClass(), key, msg);
    return getJsonMessage(request, key, msg);
  }
  
  public Object getMessage(HttpServletRequest request, Throwable t)
  {
    String key = getParameterName();
    Object msg = super.getMessage(request, t);
    XLogger.getInstance().log(Level.FINER, "{0}={1}", getClass(), key, msg);
    return getJsonMessage(request, key, msg);
  }
  
  public Object getJsonMessage(HttpServletRequest request, String key, Object msg)
  {
    if (key == null) {
      throw new NullPointerException();
    }
    
    Map m = msg == null ? Collections.emptyMap() : Collections.singletonMap(key, msg);
    
    StringBuilder builder = msg == null ? new StringBuilder() : new StringBuilder(getBufferLength());
    
    JsonFormat jsonFormat = getJsonFormat();
    
    XLogger.getInstance().log(Level.FINER, "JsonFormat type: {0}", getClass(), jsonFormat == null ? null : jsonFormat.getClass().getName());
    

    if (jsonFormat == null) {
      throw new NullPointerException();
    }
    
    jsonFormat.setTidyOutput(true);
    
    jsonFormat.appendJSONString(m, builder);
    
    return builder;
  }
  
  public int getBufferLength() {
    return 16;
  }
  
  public JsonFormat getJsonFormat()
  {
    if (this._jf == null) {
      this._jf = new JsonFormat();
    }
    return this._jf;
  }
}
