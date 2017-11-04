package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.util.BaseFeedCreator;
import com.idisc.pu.entities.Feed;
import com.idisc.shared.feedid.Feedids;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletContext;

public class FileFeedBuilder extends BaseFeedCreator implements FilenameFilter {
    
  private long lastLoadTime;
    
  private final ServletContext context;
  
  public FileFeedBuilder(ServletContext context) {
    super(((AppContext)context.getAttribute(Attributes.APP_CONTEXT)).getAppProperties().get(Appproperties.ADVERT_CATEGORY_NAME).toString());
    this.context = context;
  }
  
  public AppContext getAppContext() {
    return (AppContext)context.getAttribute(Attributes.APP_CONTEXT);
  }
  
  public Map<String, Feed> load(Path dirPath) {
      
    lastLoadTime = System.currentTimeMillis();
      
    File dirFile = dirPath.toFile();
    
    String[] names = dirFile.list(this);
    
    XLogger.getInstance().log(Level.FINE, "Dir: {0}, File names: {1}", 
    getClass(), dirPath, names == null ? null : Arrays.toString(names));
    
    if ((names == null) || (names.length == 0)) {
      return Collections.EMPTY_MAP;
    }
    
    Map output = new HashMap(names.length * 2);
    
    AppContext appContext = (AppContext)context.getAttribute(Attributes.APP_CONTEXT);
    
    final Feedids feedids = appContext.getSharedContext().getFeedidsService().getFeedids();
    final int start = feedids.getNoticeFeedidStart();
    final int end = feedids.getNoticeFeedidEnd();
    int feedid = start;
    
    for (String name : names) {
        
      if(feedid == end) {
          break;
      }
        
      Path path = Paths.get(dirPath.toString(), name);
      
      Feed feed = this.getFeed(feedid, path, null);
      
      if(feed != null) {
      
          output.put(path.toString(), feed);
          
          ++feedid;
      }
    }
    
    return output;
  }

  public Feed getFeed(Integer feedid, Path path, Feed defaultValue) {
    Feed feed = new Feed();
    if(this.updateFeed(feed, feedid, path)) {
      return feed;
    }else{
      return defaultValue;
    }
  }
  
  public Feed getFeed(Integer feedid, String title, String contents) {
    
    Feed feed = new Feed();
    
    this.updateFeed(feed, feedid, title, contents);
    
    return feed;
  }

  public boolean updateFeed(Feed feed, Integer feedid, Path path) {
      
      try {
          
        final String contents = new String(Files.readAllBytes(path));

        final String title = getTitle(path.getFileName().toString(), contents);
        
        XLogger.getInstance().log(Level.FINE, "Loading notice: {0}", getClass(), path);
        
        this.updateFeed(feed, feedid--, title, contents);
        
        return true;
        
      } catch (IOException e) {
        XLogger.getInstance().log(Level.WARNING, "Error reading: " + path, this.getClass(), e);
        return false;
      }
  }
  
  public URL getUrl(String fname) {
    if (!fname.startsWith("/")) {
      fname = "/" + fname;
    }
    try {
      return context.getResource(fname);
    } catch (MalformedURLException e) {}
    return null;
  }
  
  public String getTitle(String fname, String contents) {
    int n = fname.lastIndexOf('.');
    if (n != -1) {
      fname = fname.substring(0, n);
    }
    fname = fname.replaceAll("\\p{Punct}", " ");
    fname = Character.toTitleCase(fname.charAt(0)) + fname.trim().substring(1);
    return fname;
  }
  
  @Override
  public boolean accept(File dir, String name) {
    String sLower = name.toLowerCase();
    return (sLower.endsWith(".html")) || (sLower.endsWith(".htm")) || (sLower.endsWith(".xhtml")) || (sLower.endsWith(".xml")) || (sLower.endsWith(".jsp")) || (sLower.endsWith(".jspf"));
  }

  public long getLastLoadTime() {
    return lastLoadTime;
  }
}
