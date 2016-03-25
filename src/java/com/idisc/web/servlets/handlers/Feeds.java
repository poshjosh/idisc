package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.User;
import com.idisc.pu.entities.Feed;
import com.idisc.web.AppInstallation;
import com.idisc.web.FeedComparator;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.pu.entities.Installation;
import com.idisc.web.FeedCache;
import com.idisc.web.Tips;
import com.idisc.web.WebApp;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @(#)FeedRequestHandler.java   16-Oct-2014 10:05:00
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */
/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class Feeds extends Selectfeeds {
    
    private Installation installation;
    
    private static Collection<Map> tips;

    public void addTips(List<Map> output,
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if(output == null || output.isEmpty()) {
            return;
        }

        try{
            
            if(tips == null) {
XLogger.getInstance().log(Level.INFO, "Creating tips cache", this.getClass());
                tips = new Tips(request, response, true).values();
            }

            if(tips != null && !tips.isEmpty()) {
XLogger.getInstance().log(Level.FINER, "Adding {0} tips to output", this.getClass(), tips.size());
                output.addAll(tips);
            }
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Error loading tips", this.getClass(), e);
        }
    }
    
////////////////////////////////////////////
// AS OF NOW THESE 2 ADVERTS A PACKAGED WITH THE APP
///////////////////////    
    public void addLocalAdverts(List<Map> output,
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if(output == null || output.isEmpty()) {
            return;
        }

//@todo only display these if user is not activated        
        try{

            Date date = new Date();

            Map map = new Appproperties().execute(request, response);

            Object category = map.get("advertCategoryName");
            
            String appName = WebApp.getInstance().getAppName();

//@todo look for a consistent way of generating ids, that will not conflict with downloaded feed ids
            int id = Integer.MAX_VALUE;
            addLocalAdverts(output, date, "WEB-INF/jspf/whynewsminute.jspf", "Ever wanted to know why "+appName+"?", category, id, 1);

            id = id - 1;
            addLocalAdverts(output, date, "WEB-INF/jspf/testimonials.jspf", "See what others are saying about "+appName, category, id, 2);

        }catch(ServletException | IOException e) {
            XLogger.getInstance().log(Level.WARNING, "Error adding advert to response feeds", this.getClass(), e);
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Error adding advert to response feeds", this.getClass(), e);
        }
    }
    
    private boolean addLocalAdverts(List<Map> appendTo, Date date, String filename, String title, Object category, int feedid, int pos) {
        Map m = this.getLocalAdverts(date, filename, title, category, feedid);
        if(m != null) {
XLogger.getInstance().log(Level.FINE, "Adding map with {0} items to output", this.getClass(), m.size());
// We add twice
            if(pos == 0) {
                throw new IllegalArgumentException("Start from 1 not 0");
            }
            int n = pos * 5;
            int off = appendTo.size() > n ? n : 0;
            appendTo.add(off, m);
            if(off != 0) {
                appendTo.add(m);
            }
            return true;
        }else{
            return false;
        }
    }

    private Map getTips(Date date, String title, String contents, Object category, int feedid) {
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
            output.put("url", null); // No link actually
            return output;
        }else{
            return null;
        }
    }
    
    private Map getLocalAdverts(Date date, String filename, String title, Object category, int feedid) {
        String contents = this.getHtml(filename, title);
        if(contents != null) {
            Map output = new HashMap();
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
            output.put("url", this.getUrl(filename));
            return output;
        }else{
            return null;
        }
    }
    
    private URL getUrl(String fname) {
        if(!fname.startsWith("/")) {
            fname = "/" + fname;
        }
        try{
            return WebApp.getInstance().getServletContext().getResource(fname);
        }catch(MalformedURLException e) {
            return null;
        }
    }
    
    private String getHtml(String fname, String title) {
        
        String realPath = WebApp.getInstance().getServletContext().getRealPath(fname);
        
        try(Reader r = 
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(realPath), "utf-8"))) {
        
            com.bc.io.CharFileInput cfi = new com.bc.io.CharFileInput();
            CharSequence cs = cfi.readChars(r);
XLogger.getInstance().log(Level.FINER, "Read from: {0}, contents:\n{1}", this.getClass(), fname, cs);

//            String prefix_a = "<html><head><title>";
//            String prefix_b = "</title></head><body>";
//           String suffix = "</body></html>";
            
//            int size =  10 + prefix_a.length() + title.length() + prefix_b.length() + cs.length() + suffix.length();
            
//            StringBuilder bld = new StringBuilder(size);
//            bld.append(prefix_a).append(title).append(prefix_b).append(cs).append(suffix);

            String output = cs.toString().replace("${appName}", WebApp.getInstance().getAppName());
            
// Remove            <%@ page pageEncoding="UTF-8" %>
            int start = output.indexOf("<%");
            if(start != -1){
                int end = output.indexOf("%>", start);
                if(end != -1) {
                    output = output.substring(end + 1);
                }
            }
            
            return output;
            
        }catch(Exception e) {
            
            XLogger.getInstance().log(Level.WARNING, "Error reading: "+fname, this.getClass(), e);
            
            return null;
        }
    }
    
    @Override
    public List<Map> execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // This is a tangential task. Its failure should not affect our execution
        // 
        try{
            User user = this.findUser(request, response);
            // Create the installation record if it doesn't exist
            boolean create = true;
            installation = AppInstallation.getEntity(request, user, create);
        }catch(Exception ignored) { 
            // If not ignored, too much stack trace
        }
        
        List<Map> output = super.execute(request, response);

        this.addTips(output, request, response);
        
//        this.addLocalAdverts(output, request, response);
        
        return output;
    }

    @Override
    public synchronized List<Feed> select(HttpServletRequest request) throws ValidationException {
        
        if(!FeedCache.isCachedFeedsAvailable()) {
            
            FeedCache fc = new FeedCache();
            
            fc.updateCache();
        }
        
        int userLimit = this.getLimit(request);
        
        List<Feed> lastFeeds = FeedCache.getLastFeeds();
        
// This is a servlet context attribute        
        request.getSession().getServletContext().setAttribute("lastFeeds", lastFeeds);

        int cacheSize = lastFeeds.size();

        List<Feed> output = new ArrayList(cacheSize <= userLimit ? lastFeeds : lastFeeds.subList(0, userLimit));

        FeedComparator comparator = new FeedComparator(){
            // Having an installation specified makes the sort user specific
            @Override
            public Installation getInstallation() {
                return installation;
            }
        };

//    For input: 0, 1, 2, 3, 4       
//   Output was: 4, 3, 2, 1, 0
//   Hence we use invertSort=true to ensure DESCENING ORDER of input is maintained
//        
        comparator.setInvertSort(true);

        Collections.sort(output, comparator);
        
// This is a session attribute        
        request.getSession().setAttribute("output", lastFeeds);

        return output;
    }
}