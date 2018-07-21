package com.idisc.web;

import com.idisc.pu.comparator.feed.FeedComparatorUserHitsOnSite;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import com.idisc.web.servlets.handlers.request.Appproperties;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * <b>Implements {@link java.lang.AutoCloseable} hence must be closed after use</b>
 * @author poshjosh
 */
public class FeedComparatorUserHitsOnSiteImpl extends FeedComparatorUserHitsOnSite {
    
  public FeedComparatorUserHitsOnSiteImpl(AppContext appContext) { 
    this(appContext, null);
  }
  
  public FeedComparatorUserHitsOnSiteImpl(AppContext appContext, Installation installation) { 
    this(appContext, installation, false);
  }  
  
  public FeedComparatorUserHitsOnSiteImpl(
          AppContext appContext, 
          Installation installation, 
          boolean reverseOrder) {
      
    super(
      appContext.getIdiscApp().getJpaContext().getEntityManager(Feedhit.class),
      installation, 
      (
        (String)appContext.getAppProperties().get(Appproperties.HEADLINE_PATTERN) == null ? null :
        Collections.singletonMap(Pattern.compile((String)appContext.getAppProperties().get(Appproperties.HEADLINE_PATTERN)), 2)
      ),
      TimeUnit.MINUTES.toMillis(appContext.getConfiguration().getLong(ConfigNames.FEED_CYCLE_INTERVAL, 900_000)),
      TimeUnit.MINUTES.toMillis(appContext.getConfiguration().getLong(ConfigNames.ADDED_VALUE_LIMIT, 2_880)),
      reverseOrder
    );
  }
}
