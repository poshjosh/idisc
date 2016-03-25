package com.idisc.web;

import com.bc.io.CharFileIO;
import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.Appproperties;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.tribes.util.Arrays;

/**
 * @author Josh
 */
public abstract class FileCache extends HashMap<String, Map> implements FilenameFilter {
    
    public abstract String getDirPath();
    
    public FileCache() {  }
    
    public FileCache(HttpServletRequest request, HttpServletResponse response, boolean load) {
        if(load) {
            FileCache.this.load(request, response, false);
        }
    }
    
    @Override
    public Map remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends Map> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map put(String key, Map value) {
        throw new UnsupportedOperationException();
    }

    public void load(HttpServletRequest request, HttpServletResponse response, boolean clearPrevious) {
        
        if(!this.isEmpty() && clearPrevious) {
            this.clear();
        }
        
        String dirPath = this.getDirPath();

        File dirFile = new File(dirPath);

        String [] names = dirFile.list(this);

XLogger.getInstance().log(Level.FINE, "Dir: {0}, File names: {1}", this.getClass(), names==null?null:Arrays.toString(names));
        
        if(names == null || names.length == 0) {
            return;
        }

        CharFileIO io = new CharFileIO();
        
        Object category;
        try{
            Map map = new Appproperties().execute(request, response);
            category = map.get("advertCategoryName");
XLogger.getInstance().log(Level.FINE, "Advert category name: {0}", this.getClass(), category);
        }catch(ServletException | IOException e) {
            return;
        }    
        Date date = new Date();
        int feedid = Integer.MAX_VALUE;

        for(String name:names) {
            
            String path = null;
            
            try{
                
                path = Paths.get(dirPath, name).toString();
                CharSequence cs = io.readChars(path);
                String contents = cs.toString();
                String title = getTitle(name, contents);
                
                Map data = this.toMap(date, title, contents, category, feedid--);
                
// We use the super class method because this class' #put and #putAll throws
// Exceptions because this class in read-only                
XLogger.getInstance().log(Level.FINE, "Caching tip: {0}", this.getClass(), path);
                
                super.put(path, data);
                
            }catch(IOException e) {
                XLogger.getInstance().log(Level.WARNING, "Error reading: "+path, Tips.class, e);
            }
        }
    }
    
    public Map toMap(Date date, String title, String contents, Object category, int feedid) {
        if(contents != null) {
            Map output = new HashMap(11, 1.0f);
            output.put("feedid", feedid);
            output.put("author", WebApp.getInstance().getAppName());
            output.put("categories", category);
            output.put("content", contents);
            output.put("datecreated", date);
            output.put("feeddate", date);
            output.put("imageurl", this.getUrl("/images/appicon.png"));
            output.put("keywords", category);
            output.put("siteid", "0");
            output.put("title", title);
//            output.put("url", null); // No link actually
            return output;
        }else{
            return null;
        }
    }
    
    public URL getUrl(String fname) {
        if(!fname.startsWith("/")) {
            fname = "/" + fname;
        }
        try{
            return WebApp.getInstance().getServletContext().getResource(fname);
        }catch(MalformedURLException e) {
            return null;
        }
    }
    
    public String getTitle(String fname, String contents) {
        int n = fname.lastIndexOf('.');
        if(n != -1) {
            fname = fname.substring(0, n);
        }
        return fname.replaceAll("\\p{Punct}", " ");
    }

    @Override
    public boolean accept(File dir, String name) {
        String sLower = name.toLowerCase();
        return sLower.endsWith(".html") || sLower.endsWith(".htm") ||
                sLower.endsWith(".xhtml") || sLower.endsWith(".xml") ||
                sLower.endsWith(".jsp") || sLower.endsWith(".jspf");
    }
}

