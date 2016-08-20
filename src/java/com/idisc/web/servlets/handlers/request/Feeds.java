package com.idisc.web.servlets.handlers.request;

import com.bc.util.JsonBuilder;
import com.bc.util.XLogger;
import com.idisc.core.comparator.BaseFeedComparator;
import com.idisc.core.util.EntityJsonBuilder;
import com.idisc.core.util.EntityMapBuilder;
import com.idisc.core.util.EntityMapBuilder_appVersionCode8orBelow;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.DefaultFeedCache;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext_outdatedApps;
import com.idisc.web.servlets.handlers.response.ObjectToJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.idisc.web.servlets.request.AppVersionCode;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Feeds extends Selectfeeds {
    
  private Installation installation;
  
  private AppVersionCode versionCodeManager;

  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !this.isHtmlResponse(request);
  }

  @Override
  protected <X> ResponseHandler<X, Object> createJsonResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
      
    final boolean tidyOutput = this.isTidyOutput(request);
    final boolean plainTextOnly = this.isPlainTextOnly(request);
    final int bufferSize = this.getMaxTextLengthPerItem(request);
    
    EntityMapBuilder mapBuilder;
    if(this.versionCodeManager != null && this.versionCodeManager.isLessOrEquals(request, 8, false)) {
      mapBuilder = new EntityMapBuilder_appVersionCode8orBelow(plainTextOnly, bufferSize);
    }else{
      mapBuilder = new EntityMapBuilder(plainTextOnly, bufferSize);
    }
    
    JsonBuilder jsonBuilder = 
            new EntityJsonBuilder(tidyOutput, true, "  ", mapBuilder); 
    
    ResponseHandler<X, Object> output = new ObjectToJsonResponseHandler(context, jsonBuilder, bufferSize); 
    
    return output;
  }
  
  @Override
  protected ResponseContext<List<Feed>> createSuccessResponseContext(HttpServletRequest request) {
    
    if(this.versionCodeManager != null && this.versionCodeManager.isLessThanLatest(request, false)) {
      return new FeedsResponseContext_outdatedApps(request, installation);
    }else{
      return new FeedsResponseContext(request, installation);
    }
  }
  
  @Override
  public List<Feed> execute(HttpServletRequest request) throws ServletException, IOException {
      
    boolean create = true;
    
    this.installation = getInstallation(request, create);
    this.versionCodeManager = new AppVersionCode(request.getServletContext(), installation);
    
    List<Feed> output = super.execute(request);
    
    return output;
  }
  
  @Override
  public synchronized List<Feed> select(HttpServletRequest request)
    throws ValidationException {
      
XLogger.getInstance().entering(this.getClass(), "#select(HttpServletRequest)", "");
      
    AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);

    DefaultFeedCache fc = new DefaultFeedCache(appContext.getConfiguration());

    List<Feed> output;
    if (!fc.isCachedFeedsAvailable()) {
        
      output = fc.updateCache();
    }else{
      output = fc.getCachedFeeds(getLimit(request));
    }
    
long tb4 = System.currentTimeMillis();
long mb4 = Runtime.getRuntime().freeMemory();

    final boolean reverseOrder = true;

//    try (DefaultFeedComparator autoCloseableFeedComparator = 
//            new DefaultFeedComparator(appContext, installation, reverseOrder)) {
          
//      Collections.sort(output, autoCloseableFeedComparator);
//    }
    
    Collections.sort(output, new BaseFeedComparator(reverseOrder));
    
XLogger.getInstance().log(Level.FINE, "Sorted {0} feeds. Consumed time: {1}, memory: {2}", 
this.getClass(), output.size(), (System.currentTimeMillis()-tb4), (mb4-Runtime.getRuntime().freeMemory()));

    return output;
  }
}
