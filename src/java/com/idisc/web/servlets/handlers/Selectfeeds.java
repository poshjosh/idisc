package com.idisc.web.servlets.handlers;

import com.bc.htmlparser.ParseJob;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Site;
import com.idisc.web.AppProperties;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.bc.util.IntegerArray;
import com.idisc.core.FeedFrequency;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.eclipse.persistence.config.QueryHints;
import org.htmlparser.util.Translate;


/**
 * @(#)Searchfeeds.java   20-Mar-2015 19:41:11
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
public class Selectfeeds extends Select<com.idisc.pu.entities.Feed> {
    
    private boolean plainTextOnly;
    
    private final int defaultLen;
    
    private int maxLen;
    
    private ParseJob htmlParser;
    
    public Selectfeeds() {
        plainTextOnly = true;
        Configuration config = WebApp.getInstance().getConfiguration();
        defaultLen = config == null ? 1000 : config.getInt(AppProperties.DEFAULT_CONTENT_LENGTH, 1000);
    }
    
    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    protected Class<com.idisc.pu.entities.Feed> getEntityClass() {
        return com.idisc.pu.entities.Feed.class;
    }
    
    protected Predicate [] buildPredicates(
            CriteriaBuilder cb, Root<com.idisc.pu.entities.Feed> root, 
            Date after, String [] columnNames, String toFind) {
        
        final List<Predicate> predicates = new ArrayList();
        
        if(toFind == null) {
            
XLogger.getInstance().log(Level.FINER, "Select feeds after: {0}", this.getClass(), after);
            if(after != null) {
                
                predicates.add(cb.greaterThan(root.<Date>get("feeddate"), after));
            }
        }else{
            
            toFind = "%" + toFind + "%";

            for (String key:columnNames) {

                Predicate p;

                if(after == null) {
                    // where col_0 like val_0
                    p = cb.like(root.<String> get(key), toFind);
                }else{
                    // where feeddate > after AND col_0 like val_0
//@column literal not good 
                    p = cb.and(
                        cb.greaterThan(root.<Date>get("feeddate"), after),
                        cb.like(root.<String> get(key), toFind));
                }

                predicates.add(p);
            }
        }

        return predicates.toArray(new Predicate[0]);
    }

    @Override
    protected List<com.idisc.pu.entities.Feed> select(HttpServletRequest request) throws ValidationException {
        
        String toFind = this.getSearchTerm(request);

        Date after = this.getAfter(request);

        int offset = this.getOffset(request);

        int limit = this.getLimit(request);

        return this.select(toFind, after, offset, limit);
    }
        
    public List<com.idisc.pu.entities.Feed> select(
            String toFind,
            Date after,
            int offset,
            int limit) {
        
long mb4 = Runtime.getRuntime().freeMemory();
long tb4 = System.currentTimeMillis();
        
        List<com.idisc.pu.entities.Feed> feeds;

        EntityManager em = this.getEntityController().getEntityManager();
        
        try{
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<com.idisc.pu.entities.Feed> query = cb.createQuery(com.idisc.pu.entities.Feed.class);
            Root<com.idisc.pu.entities.Feed> feed = query.from(com.idisc.pu.entities.Feed.class);

            String [] columnNames = {"title", "keywords", "description", "content"};
            
            Predicate [] predicates = this.buildPredicates(cb, feed, after, columnNames, toFind);
                
            if(predicates == null || predicates.length == 0) {
                // no where clause
            }else if(predicates.length == 1) {
                query.where(predicates[0]);
            }else{
                query.where(cb.or(predicates));
            }

            // Most recent feed first
            //
//@column literal not good            
            query.orderBy(cb.desc(feed.get("feeddate")));

            TypedQuery<com.idisc.pu.entities.Feed> typedQuery = em.createQuery(query);
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(limit);

// http://java-persistence-performance.blogspot.com/2010/08/batch-fetching-optimizing-object-graph.html
// http://java-persistence-performance.blogspot.com/2011/06/how-to-improve-jpa-performance-by-1825.html
//                
            typedQuery.setHint("eclipselink.read-only", "true");

// http://vard-lokkur.blogspot.com/2011/05/eclipselink-jpa-queries-optimization.html                
//                
            typedQuery.setHint(QueryHints.BATCH, "f.commentList");
            typedQuery.setHint(QueryHints.BATCH, "f.feedhitList");
            typedQuery.setHint(QueryHints.BATCH, "f.bookmarkfeedList");
            typedQuery.setHint(QueryHints.BATCH, "f.favoritefeedList");
            typedQuery.setHint(QueryHints.BATCH_TYPE, BatchFetchType.IN);

            feeds = typedQuery.getResultList();

XLogger.getInstance().log(Level.INFO, "Expected: {0}, retreived {1} feeds from database. Consumed memory: {2}, time: {3}", 
this.getClass(), limit, feeds==null?null:feeds.size(), mb4-Runtime.getRuntime().freeMemory(), System.currentTimeMillis()-tb4);

        }finally{

            em.close();
        }

        return feeds;
    }
    
    protected String getSearchTerm(HttpServletRequest request) {
        return request.getParameter(com.idisc.web.RequestParameters.QUERY);
    }
    
    @Override
    protected Map<String, String> getOrderBy(HttpServletRequest request) throws ValidationException {
//@column literal @todo literals not good        
        Map<String, String> orderBy = Collections.singletonMap("feeddate", "DESC");
        return orderBy;
    }
    
    private String getPlainText(String s) {
        if(s == null) {
            return s;
        }
        if(htmlParser == null) {
            htmlParser = new ParseJob();
        }
        String output;
        try{
//            output = htmlParser.separator("\n").maxSeparators(2).comments(false).plainText(true).parse(s).toString();
            output = htmlParser.separator("\n\n").maxSeparators(1).comments(false).plainText(true).parse(s).toString();
        }catch(IOException e) {
            XLogger.getInstance().log(Level.WARNING, 
            "Error extracting plain text from: "+(s.length()<=100?s:s.substring(0, 100)), this.getClass(), e);
            output = s;
        }
        return output;
    }

    @Override
    protected Map toMap(
            EntityController<com.idisc.pu.entities.Feed, Object> ec, 
            com.idisc.pu.entities.Feed feed) {
        
        // Keep these within max length
        //
        feed.setTitle(format(feed.getTitle()));
        feed.setKeywords(format(feed.getKeywords()));

        String content = this.isPlainTextOnly() ? this.getPlainText(feed.getContent()) : feed.getContent();
        
        feed.setContent(format(content));
        
        feed.setDescription(format(this.getPlainText(feed.getDescription())));
        
        // If the feed doesn't have an image url, get the feed's site image url
        //
        String imageUrl = feed.getImageurl();
        if(imageUrl == null) {
            Site site = feed.getSiteid();
            if(site != null) {
                feed.setImageurl(site.getIconurl());
            }else{
XLogger.getInstance().log(Level.WARNING, 
"No site found for feed:: id: {0}, author: {1}, title: {2}", 
this.getClass(), feed.getFeedid(), feed.getAuthor(), feed.getTitle());
            }
        }
        
        // These ones will not result in any relevant json output
        //
        feed.setCommentList(null);
//        feed.setSiteid(null); // Siteid is an important reference
        
        // Get this first
        List<Feedhit> feedhits = feed.getFeedhitList();
        feed.setFeedhitList(null);
        
        Map map = ec.toMap(feed, false);
        
        // We want to add hitcount
        //
        long hitcount;
        
        if(false) {
            
            Integer feedid = feed.getFeedid(); 

            hitcount = this.getHitcount(feedid);
            
        }else{
            
            hitcount = feedhits == null ? 0 : feedhits.size();
        }
        
        map.put("hitcount", hitcount);
        
        return map;
    }
    
    public long getHitcount(int feedid) {
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
        EntityController<Feedhit, Object> ec = factory.getEntityController(Feedhit.class);
        Map<String, Integer> params = Collections.singletonMap("feedid", feedid);
        return ec.count(params);
    }

    @Override
    public List<Map> execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String contentType = request.getParameter("content-type");
        
        this.plainTextOnly = contentType != null && contentType.contains("text/plain");
        
XLogger.getInstance().log(Level.FINER, "Plain text only: {0}", this.getClass(), this.plainTextOnly);
        
        maxLen = this.getInt(request, com.idisc.web.RequestParameters.MAX_LEN, defaultLen);
        
        return super.execute(request, response); 
    }

    protected List<com.idisc.pu.entities.Feed> ensureEquality(List<com.idisc.pu.entities.Feed> feeds, int outputSize) {
        
this.printFirstDateLastDateAndFeedIds("BEFORE REARRANGE", feeds, Level.FINER);
        
        // We use availableSites rather than total sites. 
        //    
        // If there are 500 feeds, 2 sites only in results and 20 sites total
        // Give a output size of 100 and a multiple of 2:
        // If we use total sites of 20 average will be outputSize/total = 100/20 = 5;
        // If we use available sites of 2 average will be outputSize/available = 100/2 = 20;
        // In the first case, having average == 5, there will be problems
        //
//        int numOfSites = IdiscApp.getInstance().getCapturerApp().getSiteNames().length;
        FeedFrequency ff = new FeedFrequency(feeds);
        int numOfSites = ff.getSiteCount();
        

// Use a multiple of the original 'limit' value went with the request, then on the results
// * Sort by hitcount/feeddate. 
// * Select 'limit' number of feeds whereby no news medium should have more than 
// average x 2 appearances            
//        
        final int multiple = 2;
        
        if(outputSize > numOfSites && feeds.size() > (numOfSites * multiple)) {
            
            int ave = outputSize / numOfSites;
            if(ave < 1) {
                ave = 1;
            }

            final int max = ave * multiple;

            IntegerArray siteIds = new IntegerArray(numOfSites);
            IntegerArray siteCounts = new IntegerArray(numOfSites);

            Iterator<com.idisc.pu.entities.Feed> iter = feeds.iterator();
            
            List<com.idisc.pu.entities.Feed> appendAtEnd = null;

            int index = 0;
            
            while(iter.hasNext()) {
                
                ++index;
                
                com.idisc.pu.entities.Feed feed = iter.next();

                Site site = feed.getSiteid();

                if(site == null) {
XLogger.getInstance().log(Level.WARNING, 
"No site found for Feed:: ID: {0}, title: {1}", 
this.getClass(), feed.getFeedid(), feed.getTitle());
                    continue;
                }

                int siteid = site.getSiteid();

                int siteIndex = siteIds.indexOf(siteid);

                int siteCount;

                if(siteIndex == -1) {
                    siteCount = 0;
                    siteIds.add(siteid);
                    siteCounts.add(++siteCount);
                }else{
                    siteCount = siteCounts.get(siteIndex);
                    assert siteCount > 0 : "Expected count > 0 found: "+siteCount;
                    siteCounts.set(siteIndex, ++siteCount);
                }

                if(siteCount >= max) {
                
XLogger.getInstance().log(Level.FINER, "Index: {0}. Site: {1}, has appeared {2} times",
this.getClass(), index, feed.getSiteid()==null?null:feed.getSiteid().getSite(), siteCount);

                    iter.remove();
                    
                    if(appendAtEnd == null) {
                        appendAtEnd = new ArrayList(ave * multiple);
                    }
                    
XLogger.getInstance().log(Level.FINEST, "Relocating Feed. ID: {0}, Date: {1}",
this.getClass(), feed.getFeedid(), feed.getFeeddate());

                    appendAtEnd.add(feed);
                }
            }
            
            if(appendAtEnd != null && !appendAtEnd.isEmpty()) {
                
                feeds.addAll(appendAtEnd);
            }
            
this.printFirstDateLastDateAndFeedIds("AFTER REARRANGE", feeds, Level.FINER);
        }
        
        return feeds.size() <= outputSize ? feeds : feeds.subList(0, outputSize);
    }

    protected String format(String sval) {
        
        return this.format(sval, maxLen);
    }
    
    protected String format(String sval, int maximumLength) {

        if(sval == null || sval.isEmpty()) {
            return sval;
        }
        
        sval = this.truncate(sval, maximumLength);
        
        boolean old = Translate.DECODE_LINE_BY_LINE;
        try{
            Translate.DECODE_LINE_BY_LINE = true;
            sval = org.htmlparser.util.Translate.decode(sval);
        }finally{
            Translate.DECODE_LINE_BY_LINE = old;
        }
        
        return sval;
    }
    
    protected String truncate(String sval, int maxLen) {
        if(sval == null) {
            return sval;
        }
        if(sval.length() <= maxLen) {
            return sval;
        }
        return sval.substring(0, maxLen);
    }
    
    protected void printFirstDateLastDateAndFeedIds(String key, List<com.idisc.pu.entities.Feed> feeds, Level level) {
if(feeds != null && feeds.size() > 1) {
    com.idisc.pu.entities.Feed first = feeds.get(0);
    com.idisc.pu.entities.Feed last = feeds.get(feeds.size()-1);
XLogger.getInstance().log(level, "{0}. First feed, date: {1}. Last feed, date: {2}\n{3}", 
this.getClass(), key, first.getFeeddate(), last.getFeeddate(), this.toString(feeds));
}
    }
    
    protected String toString(List<com.idisc.pu.entities.Feed> feeds) {
        StringBuilder ids = new StringBuilder();
        for(com.idisc.pu.entities.Feed feed:feeds) {
            ids.append(feed.getFeedid()).append(',');
        }
        return ids.toString();
    }

    public boolean isPlainTextOnly() {
        return plainTextOnly;
    }

    public void setPlainTextOnly(boolean plainTextOnly) {
        this.plainTextOnly = plainTextOnly;
    }
}
