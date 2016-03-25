package com.idisc.web;

import com.bc.io.CharFileIO;
import com.bc.mailservice.Message;
import com.bc.mailservice.SimpleMessage;
import com.bc.util.XLogger;
import com.idisc.core.AbstractSendNewsAsEmailTask;
import com.idisc.core.IdiscApp;
import com.idisc.pu.References;
import com.idisc.pu.entities.Emailstatus;
import com.idisc.pu.entities.Extractedemail;
import com.idisc.pu.entities.Feed;
import com.bc.jpa.fk.EnumReferences;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * @(#)SendNewsAsEmailService.java   28-Mar-2015 08:13:43
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
public class SendNewsAsEmailTask extends AbstractSendNewsAsEmailTask {

    private final int numberOfFeeds;
    
    public SendNewsAsEmailTask() { 
        this(3);
    }
    
    public SendNewsAsEmailTask(int numberOfFeeds) { 
        this.numberOfFeeds = numberOfFeeds;
    }

    @Override
    public void reset() {
        super.reset(); 
        this.loadOwn = true;
    }

    @Override
    public Map getOrderBy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map getParameters() {
        throw new UnsupportedOperationException();
    }
    
    protected Message createMessage() {
        
        List<Feed> feeds = FeedCache.getLastFeeds();
        if(feeds == null || feeds.isEmpty()) {
            new FeedCache().updateCache();
            List<Feed> update = FeedCache.getLastFeeds();
            if(update != null) {
                feeds = new ArrayList(update);
            }
        }else{
            // We use a copy, as we will perform own sorting here
            feeds = new ArrayList(feeds);
        }
        
XLogger.getInstance().log(Level.FINE, "Number of feeds: {0}, to send: {1}", 
this.getClass(), feeds==null?null:feeds.size(), this.numberOfFeeds);

        StringBuilder message = new StringBuilder();
        
        //<div style="font-size:1.5em"></div>
        message.append("<div style=\"font-size:1.5em\">");
        message.append("<p>Hi,</p>");
        
        String firstHeading = null;
        
        // Add news feeds
        //
        if(feeds != null && !feeds.isEmpty()) {
            
            final int size = feeds.size() < this.numberOfFeeds ? feeds.size() : this.numberOfFeeds;
            
            for(int i=0; i<size; i++) {

                Feed feed = feeds.get(i);
                
                String heading = this.getHeading(feed, null, 200);

                if(heading == null || heading.isEmpty()) {
                    continue;
                }

                if(firstHeading == null) {
                    message.append("<p><b>Here's latest news from NewsMinute</b></p>");
                    firstHeading = heading;
                }        
                
                if(i == 0) {
                    message.append("<ul>");
                }

                message.append("<li>").append(heading).append("</li>");
            }
            
            message.append("</ul>");
        }

        // Add download link
        //
        String downloadurl = WebApp.getInstance().getConfiguration().getString(AppProperties.DOWNLOAD_URL);
        if(downloadurl == null) {
            throw new NullPointerException();
        }
        message.append("<p>I love the NewsMinute app. you should try it. <a href=\"");
        message.append(downloadurl).append("\">Download it here</a></p>");
        
        // Add user testimonies
        //
        String path = WebApp.getInstance().getServletContext().getRealPath("/WEB-INF/jspf/whynewsminute.jspf");
        try{
            CharSequence cs = new CharFileIO().readChars(path);
            if(cs != null) {
                cs = cs.toString().replaceFirst("(<%).+?(%>)", "");
                message.append(cs);
            }
        }catch(IOException e) {
            XLogger.getInstance().log(Level.WARNING, null, this.getClass(), e);
        }
        
        message.append("</div>");

        String subject;
        if(firstHeading == null || firstHeading.isEmpty()) {
            subject = "Latest News from News Minute";
        }else{
            subject = "NewsMinute: " + firstHeading;
        }
        
        SimpleMessage mailMessage = new SimpleMessage();
        mailMessage.setSubject(subject);
        mailMessage.setMessage(message.toString());
        mailMessage.setContentType("text/html");
        
        return mailMessage;
    }
    
    private boolean loadOwn = true;
    @Override
    protected List<Extractedemail> loadNextBatch() {
        
        List<Extractedemail> output;
        
        if(loadOwn) {
            output = this.loadFromOwnDatabase();
        }else{
            output = null;
        }
        
        if(output == null) {
            loadOwn = false;
            output = this.loadFromLooseboxDatabase();
        }
        
        return output;
    }
    
    private List<Extractedemail> loadFromOwnDatabase() {
        
        List<com.idisc.pu.entities.Extractedemail> extractedemails;

        EntityManager em = IdiscApp.getInstance().getControllerFactory().getEntityManager(com.idisc.pu.entities.Extractedemail.class);
        
        try{
            
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<com.idisc.pu.entities.Extractedemail> query = cb.createQuery(com.idisc.pu.entities.Extractedemail.class);
            Root<com.idisc.pu.entities.Extractedemail> root = query.from(com.idisc.pu.entities.Extractedemail.class);

            Emailstatus [] acceptedstatuses = this.getAcceptedStatusEntities();
            
            Predicate [] predicates;
            if(acceptedstatuses != null && acceptedstatuses.length > 0) {
                predicates = new Predicate[acceptedstatuses.length];
                for(int i=0; i<acceptedstatuses.length; i++) {
                    predicates[i] = cb.equal(root.<Emailstatus> get("emailstatus"), acceptedstatuses[i]);
                }
            }else{
                predicates = null;
            }

            if(predicates == null || predicates.length == 0) {
                // no where clause
            }else if(predicates.length == 1) {
                query.where(predicates[0]);
            }else{
                query.where(cb.or(predicates));
            }

            TypedQuery<com.idisc.pu.entities.Extractedemail> typedQuery = em.createQuery(query);
            typedQuery.setFirstResult(this.getOffset());
            typedQuery.setMaxResults(this.getBatchSize());

    // http://java-persistence-performance.blogspot.com/2010/08/batch-fetching-optimizing-object-graph.html
    // http://java-persistence-performance.blogspot.com/2011/06/how-to-improve-jpa-performance-by-1825.html
    //                
            typedQuery.setHint("eclipselink.read-only", "true");

            extractedemails = typedQuery.getResultList();
            
        }finally{
            
            em.close();
        }
        
        return extractedemails;
    }

    private List<com.idisc.pu.entities.Extractedemail> loadFromLooseboxDatabase() {
        
        List<com.idisc.pu.entities.external.UnofficialEmails> extractedemails;

        EntityManager em = IdiscApp.getInstance().getControllerFactory().getEntityManager(com.idisc.pu.entities.external.UnofficialEmails.class);
        
        try{
            
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<com.idisc.pu.entities.external.UnofficialEmails> query = cb.createQuery(com.idisc.pu.entities.external.UnofficialEmails.class);
            Root<com.idisc.pu.entities.external.UnofficialEmails> root = query.from(com.idisc.pu.entities.external.UnofficialEmails.class);

            Short [] acceptedstatuses = this.getAcceptedStatuses();
            
            Predicate [] predicates;
            if(acceptedstatuses != null && acceptedstatuses.length > 0) {
                predicates = new Predicate[acceptedstatuses.length];
                for(int i=0; i<acceptedstatuses.length; i++) {
                    predicates[i] = cb.equal(root.<Short> get("emailStatus"), acceptedstatuses[i]);
                }
            }else{
                predicates = null;
            }

            if(predicates == null || predicates.length == 0) {
                // no where clause
            }else if(predicates.length == 1) {
                query.where(predicates[0]);
            }else{
                query.where(cb.or(predicates));
            }

            TypedQuery<com.idisc.pu.entities.external.UnofficialEmails> typedQuery = em.createQuery(query);
            typedQuery.setFirstResult(this.getOffset());
            typedQuery.setMaxResults(this.getBatchSize());

    // http://java-persistence-performance.blogspot.com/2010/08/batch-fetching-optimizing-object-graph.html
    // http://java-persistence-performance.blogspot.com/2011/06/how-to-improve-jpa-performance-by-1825.html
    //                
            typedQuery.setHint("eclipselink.read-only", "true");

            extractedemails = typedQuery.getResultList();
            
        }finally{
            
            em.close();
        }
        
        List<com.idisc.pu.entities.Extractedemail> output = null;
        
        if(extractedemails != null && !extractedemails.isEmpty()) {
            
            output = new ArrayList<com.idisc.pu.entities.Extractedemail>(extractedemails.size());

            EnumReferences refs = IdiscApp.getInstance().getControllerFactory().getEnumReferences();
            
            List statuses = refs.getEntities(References.emailstatus.unverified);
            
            for(com.idisc.pu.entities.external.UnofficialEmails ue:extractedemails) {

                com.idisc.pu.entities.Extractedemail ee = new com.idisc.pu.entities.Extractedemail();

                Emailstatus es = null;
                for(Object obj:statuses) {
                    Emailstatus status = (Emailstatus)obj;
                    if(status.getEmailstatusid().equals(ue.getEmailStatus())) {
                        es = status;
                        break;
                    }
                }
                
                ee.setEmailAddress(ue.getEmailAddress());
                ee.setEmailstatus(es);
                ee.setUsername(ue.getFirstName()==null?ue.getLastName():ue.getFirstName());
                
                output.add(ee);
            }
        }
        
        return output;
    }
    
    public References.emailstatus [] getAcceptedEnums() {
        return new References.emailstatus []{References.emailstatus.unverified, References.emailstatus.verified};
    }
    
    private Short [] as_accessViaGetter;
    public Short [] getAcceptedStatuses() {
        if(as_accessViaGetter == null) {
            EnumReferences refs = IdiscApp.getInstance().getControllerFactory().getEnumReferences();
            References.emailstatus [] enums = this.getAcceptedEnums();
            if(enums != null && enums.length > 0) {
                as_accessViaGetter = new Short[enums.length];
                for(int i=0; i<enums.length; i++) {
                    as_accessViaGetter[i] = this.getShort(refs.getId(enums[i]));
                }
            }
        }
        return as_accessViaGetter;
    }

    private Emailstatus [] ase;
    public Emailstatus [] getAcceptedStatusEntities() {
        if(ase == null) {
            EnumReferences refs = IdiscApp.getInstance().getControllerFactory().getEnumReferences();
            References.emailstatus [] enums = this.getAcceptedEnums();
            if(enums != null && enums.length > 0) {
                ase = new Emailstatus[enums.length];
                for(int i=0; i<enums.length; i++) {
                    ase[i] = (Emailstatus)refs.getEntity(enums[i]);
                }
            }
        }
        return ase;
    }
    
    private Short getShort(Object oval) {
        Short shval;
        try{
            shval = (Short)oval;
        }catch(ClassCastException e) {
            shval = Short.valueOf(oval.toString());
        }
        if(shval == null) {
            throw new NullPointerException();
        }
        return shval;
    }
    
    private String getHeading(Feed feed, String defaultValue, int maxLength) {

        String text = getHeading(feed, defaultValue);
        
        if(text != null && maxLength != -1 && text.length() > maxLength) {
            if(maxLength > 3) {
                text = text.substring(0, maxLength-3) + "...";
            }else{
                text = text.substring(0, maxLength);
            }
        }
        
        return text;
    }
    
    private String getHeading(Feed feed, String defaultValue) {
        String title = feed.getTitle();
        if(title != null && !(title = title.trim()).isEmpty()) {
            return title.replaceAll("\\s{2,}", " ");
        }
        String content = feed.getContent();
        if(content != null && !(content = content.trim()).isEmpty()) {
            return content.trim();
        }else{
            return defaultValue;
        }
    }
    
    @Override
    public int getBatchSize() {
        return WebApp.getInstance().getConfiguration().getInt(AppProperties.MAIL_BATCH_SIZE, 100);
    }

    @Override
    public int getSendInterval() {
        int sendIntervalMinutes = WebApp.getInstance().getConfiguration().getInt(AppProperties.MAIL_SEND_INTERVAL, 4);
        return (int)TimeUnit.MINUTES.toMillis(sendIntervalMinutes);
    }

    @Override
    public String getSenderEmail() {
        return WebApp.getInstance().getConfiguration().getString(AppProperties.EMAILADDRESS);
    }

    @Override
    public char[] getSenderPassword() {
        return WebApp.getInstance().getConfiguration().getString(AppProperties.PASSWORD).toCharArray();
    }

    @Override
    public Message getEmailMessage() {
        return this.createMessage();
    }
}
