package com.idisc.web.servlets.handlers.response;

import com.idisc.pu.entities.Feed;
import com.idisc.web.WebApp;
import org.apache.commons.configuration.Configuration;
import com.idisc.web.ConfigNames;

/**
 * @author poshjosh
 */
public class SelectfeedsJsonResponseHandler<E extends Feed> extends ListToJsonResponseHandler<E> {

  private final int defaultContentLength;
    
  public SelectfeedsJsonResponseHandler() {
    Configuration config = WebApp.getInstance().getConfiguration();
    this.defaultContentLength = config.getInt(ConfigNames.DEFAULT_CONTENT_LENGTH, 1000);
  }

  @Override
  public int getEstimatedLengthChars(Feed value) {
    return 3 * defaultContentLength;
  }
}
