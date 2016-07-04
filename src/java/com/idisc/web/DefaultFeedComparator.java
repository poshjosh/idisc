package com.idisc.web;

import com.idisc.core.comparator.FeedComparatorUserSiteHitcount;
import com.idisc.pu.entities.Installation;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.commons.configuration.Configuration;

/**
 * <b>Implements {@link java.lang.AutoCloseable} hence must be closed after use</b>
 * @author poshjosh
 */
public class DefaultFeedComparator extends FeedComparatorUserSiteHitcount {
    
  private final long addValuePerHit;
  private final long addedValueMax;
  
  public DefaultFeedComparator(AppContext appContext) { 
      this(appContext, null);
  }
  
  public DefaultFeedComparator(AppContext appContext, Installation installation) { 
    this(appContext, installation, false);
  }  
  
  public DefaultFeedComparator(AppContext appContext, Installation installation, boolean reverseOrder) {
      
    super(
      installation, 
      (
        (String)appContext.getAppProperties().get(Appproperties.HEADLINE_PATTERN) == null ? null :
        Collections.singletonMap(Pattern.compile((String)appContext.getAppProperties().get(Appproperties.HEADLINE_PATTERN)), 2)
      ),
      reverseOrder
    );
    Configuration config = appContext.getConfiguration();
    this.addValuePerHit = TimeUnit.MINUTES.toMillis(config.getLong(ConfigNames.FEED_CYCLE_INTERVAL, super.getAddValuePerHit()));
    this.addedValueMax = TimeUnit.MINUTES.toMillis(config.getLong(ConfigNames.ADDED_VALUE_LIMIT, super.getAddedValueMax()));
  }
  
  @Override
  public final long getAddValuePerHit() {
    return this.addValuePerHit;
  }
  
  @Override
  public final long getAddedValueMax() {
    return this.addedValueMax;
  }
}
