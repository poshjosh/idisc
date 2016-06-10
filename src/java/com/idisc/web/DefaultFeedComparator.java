package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.FeedComparator;
import com.idisc.pu.entities.Installation;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.commons.configuration.Configuration;

/**
 * <b>Implements {@link java.lang.AutoCloseable} hence must be closed after use</b>
 * @author poshjosh
 */
public class DefaultFeedComparator extends FeedComparator {
    
  private final long addValuePerHit;
  private final long addedValueMax;
  
  public DefaultFeedComparator() { 
      this(null);
  }
  
  public DefaultFeedComparator(Installation installation) {
      
    super(installation);
    
    Configuration config = WebApp.getInstance().getConfiguration();
    this.addValuePerHit = TimeUnit.MINUTES.toMillis(config.getInt("feedCycleInterval"));
    this.addedValueMax = TimeUnit.MINUTES.toMillis(config.getInt("addedValueLimit"));
    try {
      String sval = (String)new Appproperties().load().get(Appproperties.HEADLINE_PATTERN);
      if (sval != null) {
        Pattern headlinePattern = Pattern.compile(sval);
        DefaultFeedComparator.this.setElite(Collections.singletonMap(headlinePattern, 2));
      }
    } catch (IOException e) {
      XLogger.getInstance().log(Level.WARNING, null, getClass(), e);
    }
  }
  
  @Override
  public long getAddValuePerHit()
  {
    return this.addValuePerHit;
  }
  
  @Override
  public long getAddedValueMax()
  {
    return this.addedValueMax;
  }
}