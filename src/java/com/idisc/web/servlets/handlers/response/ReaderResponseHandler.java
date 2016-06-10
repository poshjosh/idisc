package com.idisc.web.servlets.handlers.response;

import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public abstract class ReaderResponseHandler<V> extends DirectResponseHandler<V, Reader> {
    
  private final int bufferSize;
  
  public ReaderResponseHandler() {
    this(1024);
  }
  
  public ReaderResponseHandler(int bufferSize) {
    this.bufferSize = bufferSize;
  }

  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, V message)
      throws ServletException, IOException {
    try {
        
      Reader in = getOutput(request, name, message);
      PrintWriter out = response.getWriter();
      
      Throwable localThrowable2 = null;
      try {
          
        this.copyChars(in, out);
        
      }catch (Throwable localThrowable1) {
          
        localThrowable2 = localThrowable1;
        
        throw localThrowable1;
      }finally{
        this.close(localThrowable2, in);
        this.close(localThrowable2, out);
      }
    } catch (Throwable t) { 
      XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  @Override
  public void sendResponse(
      HttpServletRequest request, HttpServletResponse response, String name, Throwable message)
      throws ValidationException, ServletException, IOException {
    try {
        
      Reader in = getOutput(request, name, message);
      
      PrintWriter out = response.getWriter();
      
      Throwable localThrowable2 = null;
      
      try {
        this.copyChars(in, out);
      }catch (Throwable localThrowable1) {
          
        localThrowable2 = localThrowable1;
        
        throw localThrowable1;
      } finally {
        this.close(localThrowable2, in);
        this.close(localThrowable2, out);
      }
    } catch (Throwable t) { 
      XLogger.getInstance().log(Level.WARNING, "Failed to write response", getClass(), t);
    }
  }
  
  private long copyChars(Readable from, Appendable to) throws IOException {
        
    CharBuffer buf = CharBuffer.allocate(0x800); // 2K chars (4K bytes) 

    long total = 0;

    while (from.read(buf) != -1) {
        buf.flip();
        to.append(buf);
        total += buf.remaining();
        buf.clear();
    }
    return total;
  }
  
  private void close(Throwable t, AutoCloseable c) {
    if(c == null) {
      return;
    }  
    if(t != null) {
      try { c.close(); } catch (Throwable x2) { t.addSuppressed(x2); }   
    }else{
      try { c.close(); } catch (Throwable x2) { 
        XLogger.getInstance().log(Level.WARNING, "Failed to close", getClass(), x2);
      }     
    }  
  }

  public final int getBufferSize() {
    return bufferSize;
  }
}
