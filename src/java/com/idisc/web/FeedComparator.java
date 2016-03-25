package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.AbstractFeedComparator;
import com.idisc.web.servlets.handlers.Appproperties;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.commons.configuration.Configuration;













public class FeedComparator
  extends AbstractFeedComparator
{
  private final long addValuePerHit;
  private final long addedValueMax;
  
  public FeedComparator()
  {
    Configuration config = WebApp.getInstance().getConfiguration();
    this.addValuePerHit = TimeUnit.MINUTES.toMillis(config.getInt("feedCycleInterval"));
    this.addedValueMax = TimeUnit.MINUTES.toMillis(config.getInt("addedValueLimit"));
    try {
      String sval = (String)new Appproperties().load().get(Appproperties.HEADLINE_PATTERN);
      if (sval != null) {
        Pattern headlinePattern = Pattern.compile(sval);
        setElite(Collections.singletonMap(headlinePattern, Integer.valueOf(2)));
      }
    } catch (IOException e) {
      XLogger.getInstance().log(Level.WARNING, null, getClass(), e);
    }
  }
  
  public long getAddValuePerHit()
  {
    return this.addValuePerHit;
  }
  
  public long getAddedValueMax()
  {
    return this.addedValueMax;
  }
}
