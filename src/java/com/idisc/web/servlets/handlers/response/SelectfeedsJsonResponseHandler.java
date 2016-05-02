package com.idisc.web.servlets.handlers.response;

import com.idisc.pu.entities.Feed;
import com.idisc.web.AppProperties;
import com.idisc.web.WebApp;
import org.apache.commons.configuration.Configuration;

/**
 * @author poshjosh
 */
public class SelectfeedsJsonResponseHandler extends EntityListJsonResponseHandler<Feed> {

  private final int defaultContentLength;
    
  public SelectfeedsJsonResponseHandler() {
    Configuration config = WebApp.getInstance().getConfiguration();
    this.defaultContentLength = config.getInt(AppProperties.DEFAULT_CONTENT_LENGTH, 1000);
  }

  @Override
  public int getEstimatedBufferCapacity(Feed value) {
    return 3 * defaultContentLength;
  }
}
