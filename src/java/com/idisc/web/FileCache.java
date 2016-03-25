package com.idisc.web;

import com.bc.io.CharFileIO;
import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.Appproperties;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.tribes.util.Arrays;

public abstract class FileCache extends HashMap<String, Map> implements FilenameFilter
{
  public abstract String getDirPath();
  
  public FileCache() {}
  
  public FileCache(HttpServletRequest request, boolean load)
  {
    if (load) {
      load(request, false);
    }
  }
  
  public Date getDate() {
    Calendar cal = Calendar.getInstance();
    
    cal.set(2000, 0, 1, 0, 0, 0);
    return cal.getTime();
  }
  
  public Map remove(Object key)
  {
    throw new UnsupportedOperationException();
  }
  
  public void putAll(Map<? extends String, ? extends Map> m)
  {
    throw new UnsupportedOperationException();
  }
  
  public Map put(String key, Map value)
  {
    throw new UnsupportedOperationException();
  }
  
  public void load(HttpServletRequest request, boolean clearPrevious)
  {
    if ((!isEmpty()) && (clearPrevious)) {
      clear();
    }
    
    String dirPath = getDirPath();
    
    File dirFile = new File(dirPath);
    
    String[] names = dirFile.list(this);
    
    XLogger.getInstance().log(Level.FINE, "Dir: {0}, File names: {1}", getClass(), names == null ? null : Arrays.toString(names));
    
    if ((names == null) || (names.length == 0)) {
      return;
    }
    
    CharFileIO io = new CharFileIO();
    Object category;
    try
    {
      category = new Appproperties().load().get(Appproperties.ADVERT_CATEGORY_NAME);
      XLogger.getInstance().log(Level.FINE, "Advert category name: {0}", getClass(), category);
    } catch (IOException e) {
      return;
    }
    
    Date date = getDate();
    

    int feedid = Integer.MAX_VALUE;
    
    for (String name : names)
    {
      String path = null;
      
      try
      {
        path = Paths.get(dirPath, new String[] { name }).toString();
        CharSequence cs = io.readChars(path);
        String contents = cs.toString();
        String title = getTitle(name, contents);
        
        Map data = toMap(date, title, contents, category, feedid--);
        


        XLogger.getInstance().log(Level.FINE, "Caching tip: {0}", getClass(), path);
        
        super.put(path, data);
      }
      catch (IOException e) {
        XLogger.getInstance().log(Level.WARNING, "Error reading: " + path, Notices.class, e);
      }
    }
  }
  
  public Map toMap(Date date, String title, String contents, Object category, int feedid) {
    if (contents != null) {
      Map output = new HashMap(11, 1.0F);
      output.put("feedid", Integer.valueOf(feedid));
      output.put("author", WebApp.getInstance().getAppName());
      output.put("categories", category);
      output.put("content", contents);
      output.put("datecreated", date);
      output.put("feeddate", date);
      output.put("imageurl", getUrl("/images/appicon.png"));
      output.put("keywords", category);
      output.put("siteid", "0");
      output.put("title", title);
      
      return output;
    }
    return null;
  }
  
  public URL getUrl(String fname)
  {
    if (!fname.startsWith("/")) {
      fname = "/" + fname;
    }
    try {
      return WebApp.getInstance().getServletContext().getResource(fname);
    } catch (MalformedURLException e) {}
    return null;
  }
  
  public String getTitle(String fname, String contents)
  {
    int n = fname.lastIndexOf('.');
    if (n != -1) {
      fname = fname.substring(0, n);
    }
    return fname.replaceAll("\\p{Punct}", " ");
  }
  
  public boolean accept(File dir, String name)
  {
    String sLower = name.toLowerCase();
    return (sLower.endsWith(".html")) || (sLower.endsWith(".htm")) || (sLower.endsWith(".xhtml")) || (sLower.endsWith(".xml")) || (sLower.endsWith(".jsp")) || (sLower.endsWith(".jspf"));
  }
}
