package com.idisc.web;

import com.bc.io.CharFileIO;
import com.bc.jpa.fk.EnumReferences;
import com.bc.util.XLogger;
import com.idisc.core.AbstractSendNewsAsEmailTask;
import com.idisc.core.IdiscApp;
import com.idisc.core.comparator.BaseFeedComparator;
import com.idisc.pu.References;
import com.idisc.pu.entities.Emailstatus;
import com.idisc.pu.entities.Extractedemail;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.external.UnofficialEmails;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

public class SendNewsAsEmailTask extends AbstractSendNewsAsEmailTask {
    
  private final int numberOfFeeds;
  
  private String subject;
  
  private String message;
  
  private final AppContext appContext;
  
  private final String path;
  
  public SendNewsAsEmailTask(AppContext appCtx, String path) {
    this(appCtx, 12, path);
  }
  
  public SendNewsAsEmailTask(AppContext appCtx, int numberOfFeeds, String path) {
    this.appContext = appCtx;
    this.numberOfFeeds = numberOfFeeds;
    this.path = path;
    this.createMessage();
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
  
  protected void createMessage() {
    List<Feed> feeds = appContext.getCachedFeeds();
    if ((feeds == null) || (feeds.isEmpty())) {
      List<Feed> update = new DefaultFeedCache(appContext.getConfiguration()).updateCache();
      if (update != null) {
        feeds = new ArrayList(update);
      }
    }else {
      feeds = new ArrayList(feeds);
    }
    
    XLogger.getInstance().log(Level.FINE, "Number of feeds: {0}, to send: {1}", getClass(), feeds == null ? null : Integer.valueOf(feeds.size()), Integer.valueOf(this.numberOfFeeds));
    
    StringBuilder builder = new StringBuilder();
    
    builder.append("<div style=\"font-size:1.5em\">");
    builder.append("<p>Hi,</p>");
    
    String firstHeading = null;

    if ((feeds != null) && (!feeds.isEmpty())) {
        
      Collections.sort(feeds, new BaseFeedComparator(true));
        
      final int size = feeds.size() < this.numberOfFeeds ? feeds.size() : this.numberOfFeeds;
      
      for (int i = 0; i < size; i++) {
          
        Feed feed = (Feed)feeds.get(i);
        
        String heading = getHeading(feed, null, 200);
        
        if ((heading != null) && (!heading.isEmpty())) {

          if (firstHeading == null) {
            builder.append("<p><b>Here's latest news from NewsMinute</b></p>");
            firstHeading = heading;
          }
          
          if (i == 0) {
            builder.append("<ul>");
          }
          
          builder.append("<li>").append(heading).append("</li>");
        }
      }
      builder.append("</ul>");
    }
    
    String appUrl = appContext.getAppUrl();
    if (appUrl == null) {
      throw new NullPointerException();
    }
    builder.append("<p>I love the NewsMinute app. you should try it. <a href=\"");
    builder.append(appUrl).append("\">Download it here</a></p>");

    if(path != null) {
        try {
          CharSequence cs = new CharFileIO().readChars(path);
          if (cs != null) {
            cs = cs.toString().replaceFirst("(<%).+?(%>)", "");
            builder.append(cs);
          }
        } catch (IOException e) {
          XLogger.getInstance().log(Level.WARNING, null, getClass(), e);
        }
    }
    
    builder.append("</div>");
    if ((firstHeading == null) || (firstHeading.isEmpty())) {
      subject = "Latest News from News Minute";
    } else {
      subject = "NewsMinute: " + firstHeading;
    }
    
    message = builder.toString();
  }
  
  private boolean loadOwn = true;
  
  @Override
  protected List<Extractedemail> loadNextBatch() { 
      
    List<Extractedemail> output;
    if (this.loadOwn) {
      output = loadFromOwnDatabase();
    } else {
      output = null;
    }
    
    if (output == null) {
      this.loadOwn = false;
      output = loadFromLooseboxDatabase();
    }
    
    return output;
  }

  private List<Extractedemail> loadFromOwnDatabase(){
      
    EntityManager em = IdiscApp.getInstance().getJpaContext().getEntityManager(Extractedemail.class);
    
    List<Extractedemail> extractedemails;
    try {
        
      CriteriaBuilder cb = em.getCriteriaBuilder();
      
      CriteriaQuery<Extractedemail> query = cb.createQuery(Extractedemail.class);
      Root<Extractedemail> root = query.from(Extractedemail.class);
      
      Emailstatus[] acceptedstatuses = getAcceptedStatusEntities();
      
      Predicate[] predicates;
      if ((acceptedstatuses != null) && (acceptedstatuses.length > 0)) {
        predicates = new Predicate[acceptedstatuses.length];
        for (int i = 0; i < acceptedstatuses.length; i++) {
          predicates[i] = cb.equal(root.get("emailstatus"), acceptedstatuses[i]);
        }
      } else {
        predicates = null;
      }
      
      if ((predicates != null) && (predicates.length != 0)) {
          
        if (predicates.length == 1) {
          query.where(predicates[0]);
        } else {
          query.where(cb.or(predicates));
        }
      }
      TypedQuery<Extractedemail> typedQuery = em.createQuery(query);
      typedQuery.setFirstResult(getOffset());
      typedQuery.setMaxResults(getBatchSize());

      typedQuery.setHint("eclipselink.read-only", "true");
      
      extractedemails = typedQuery.getResultList();
    }finally {
      em.close();
    }
    
    return extractedemails;
  }
  
  private List<Extractedemail> loadFromLooseboxDatabase() {
      
    EntityManager em = IdiscApp.getInstance().getJpaContext().getEntityManager(UnofficialEmails.class);
    List<UnofficialEmails> extractedemails;
    try {
        
      CriteriaBuilder cb = em.getCriteriaBuilder();
      
      CriteriaQuery<UnofficialEmails> query = cb.createQuery(UnofficialEmails.class);
      Root<UnofficialEmails> root = query.from(UnofficialEmails.class);
      
      Short[] acceptedstatuses = getAcceptedStatuses();
      
      Predicate[] predicates;
      if ((acceptedstatuses != null) && (acceptedstatuses.length > 0)) {
        predicates = new Predicate[acceptedstatuses.length];
        for (int i = 0; i < acceptedstatuses.length; i++) {
          predicates[i] = cb.equal(root.get("emailStatus"), acceptedstatuses[i]);
        }
      } else {
        predicates = null;
      }
      
      if ((predicates != null) && (predicates.length != 0)) {
        if (predicates.length == 1) {
          query.where(predicates[0]);
        } else {
          query.where(cb.or(predicates));
        }
      }
      TypedQuery<UnofficialEmails> typedQuery = em.createQuery(query);
      typedQuery.setFirstResult(getOffset());
      typedQuery.setMaxResults(getBatchSize());
      
      typedQuery.setHint("eclipselink.read-only", "true");
      
      extractedemails = typedQuery.getResultList();
    } finally {
      em.close();
    }
    
    List<Extractedemail> output = null;
    List statuses;
    if ((extractedemails != null) && (!extractedemails.isEmpty())) {
        
      output = new ArrayList(extractedemails.size());
      
      EnumReferences refs = IdiscApp.getInstance().getJpaContext().getEnumReferences();
      
      statuses = refs.getEntities(References.emailstatus.unverified);
      
      for (UnofficialEmails ue : extractedemails) {
          
        Extractedemail ee = new Extractedemail();
        
        Emailstatus es = null;
        for (Object obj : statuses) {
          Emailstatus status = (Emailstatus)obj;
          if (status.getEmailstatusid().equals(ue.getEmailStatus())) {
            es = status;
            break;
          }
        }
        
        ee.setEmailAddress(ue.getEmailAddress());
        ee.setEmailstatus(es);
        ee.setUsername(ue.getFirstName() == null ? ue.getLastName() : ue.getFirstName());
        
        output.add(ee);
      }
    }
    
    return output;
  }
  
  public References.emailstatus[] getAcceptedEnums() {
    return new References.emailstatus[] { 
        References.emailstatus.registered_user,
        References.emailstatus.verified, 
        References.emailstatus.verification_attempted_but_status_unknown,
        References.emailstatus.unverified 
    };
  }
  
  private Short[] as_accessViaGetter;
  public Short[] getAcceptedStatuses() {
    if (this.as_accessViaGetter == null) {
      EnumReferences refs = IdiscApp.getInstance().getJpaContext().getEnumReferences();
      References.emailstatus[] enums = getAcceptedEnums();
      if ((enums != null) && (enums.length > 0)) {
        this.as_accessViaGetter = new Short[enums.length];
        for (int i = 0; i < enums.length; i++) {
          this.as_accessViaGetter[i] = getShort(refs.getId(enums[i]));
        }
      }
    }
    return this.as_accessViaGetter;
  }
  
  private Emailstatus[] ase;
  public Emailstatus[] getAcceptedStatusEntities() {
    if (this.ase == null) {
      EnumReferences refs = IdiscApp.getInstance().getJpaContext().getEnumReferences();
      References.emailstatus[] enums = getAcceptedEnums();
      if ((enums != null) && (enums.length > 0)) {
        this.ase = new Emailstatus[enums.length];
        for (int i = 0; i < enums.length; i++) {
          this.ase[i] = ((Emailstatus)refs.getEntity(enums[i]));
        }
      }
    }
    return this.ase;
  }
  
  private Short getShort(Object oval) {
    Short shval;
    try {
      shval = (Short)oval;
    } catch (ClassCastException e) {
      shval = Short.valueOf(oval.toString());
    }
    if (shval == null) {
      throw new NullPointerException();
    }
    return shval;
  }
  
  private String getHeading(Feed feed, String defaultValue, int maxLength) {
      
    String text = getHeading(feed, defaultValue);
    
    if ((text != null) && (maxLength != -1) && (text.length() > maxLength)) {
      if (maxLength > 3) {
        text = text.substring(0, maxLength - 3) + "...";
      } else {
        text = text.substring(0, maxLength);
      }
    }
    
    return text;
  }
  
  private String getHeading(Feed feed, String defaultValue) {
    String title = feed.getTitle();
    if ((title != null) && (!(title = title.trim()).isEmpty())) {
      return title.replaceAll("\\s{2,}", " ");
    }
    String content = feed.getContent();
    if ((content != null) && (!(content = content.trim()).isEmpty())) {
      return content.trim();
    }
    return defaultValue;
  }
  
  @Override
  public int getBatchSize() {
    return appContext.getConfiguration().getInt("mailBatchSize", 100);
  }
  
  @Override
  public int getSendInterval() {
    int sendIntervalMinutes = appContext.getConfiguration().getInt("mailSendInterval", 4);
    return (int)TimeUnit.MINUTES.toMillis(sendIntervalMinutes);
  }
  
  @Override
  public String getSenderEmail() {
    return appContext.getConfiguration().getString("emailaddress");
  }
  
  @Override
  public char[] getSenderPassword() {
    return appContext.getConfiguration().getString("password").toCharArray();
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
