package com.idisc.web.servlets.handlers.request;

import com.bc.util.JsonBuilder;
import com.bc.util.XLogger;
import com.idisc.core.SpreadBySite;
import com.idisc.core.comparator.BaseFeedComparator;
import com.idisc.core.util.EntityJsonBuilder;
import com.idisc.core.util.EntityMapBuilder;
import com.idisc.core.util.EntityMapBuilder_appVersionCode8orBelow;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import com.idisc.web.FeedComparatorUserSiteHitcountImpl;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext_outdatedApps;
import com.idisc.web.servlets.handlers.response.ObjectToJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import com.idisc.web.servlets.request.AppVersionCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletContext;
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
    throws ServletException {
      
XLogger.getInstance().entering(this.getClass(), "#select(HttpServletRequest)", "");

    List<Feed> feeds;
    
    if(this.isHtmlResponse(request)) {
     
      feeds = super.select(request);
        
    }else{
     
      feeds = selectFeedsForJsonResponse(request);
      
      AppContext appContext = this.getAppContext(request);
      
      feeds = this.sort(appContext, feeds);
    }

    return feeds;
  }
  
  @Override
  protected int getLimit(HttpServletRequest request) throws ValidationException {
    return this.isHtmlResponse(request) ? super.getLimit(request) : getMaxLimit(request);
  }
  
  private List<Feed> selectFeedsForJsonResponse(HttpServletRequest request) 
      throws ServletException {
      
    ServletContext context = request.getServletContext();
      
    List<Feed> feeds = (List<Feed>)context.getAttribute(Attributes.FEEDS);
    
    if(feeds == null || feeds.isEmpty()) {
        
      feeds = super.select(request);
      
    }else{
      
      feeds = new ArrayList(feeds);
    }
    
    final int limitFromSuper = super.getLimit(request);
      
    feeds = new SpreadBySite().spread(feeds, limitFromSuper);      
    
    return feeds == null ? Collections.EMPTY_LIST : feeds;
  }

  private List<Feed> sort(AppContext appContext, List<Feed> feeds) {
    
    if(feeds != null && !feeds.isEmpty()) {
        
        feeds = new ArrayList(feeds);
        
        final boolean reverseOrder = true;

        Comparator<Feed> comparator = this.getComparator(appContext, reverseOrder);

long tb4 = System.currentTimeMillis();
long mb4 = Runtime.getRuntime().freeMemory();

        try{
            Collections.sort(feeds, comparator);
        }finally{
          if(comparator instanceof AutoCloseable) {
            try{
              ((AutoCloseable)comparator).close();
            }catch(Exception e) {
              XLogger.getInstance().log(Level.WARNING, "Exception closing: "+comparator, this.getClass(), e);
            }
          }    
        }

        final boolean debugTimeAndMemory = 
                appContext.getConfiguration().getBoolean(ConfigNames.DEBUG_TIME_AND_MEMORY, false);

XLogger.getInstance().log(debugTimeAndMemory ? Level.INFO : Level.FINE, 
"Comparator: {0}, sorted {1} feeds. Consumed time: {2}, memory: {3}", this.getClass(), 
comparator.getClass().getName(), feeds.size(), (System.currentTimeMillis()-tb4), (mb4-Runtime.getRuntime().freeMemory()));
    }

    return feeds == null ? Collections.EMPTY_LIST : feeds;
  }
  
  protected Comparator<Feed> getComparator(AppContext appContext, boolean reverseOrder) {
    Comparator<Feed> output;
    final String type = appContext.getConfiguration().getString(ConfigNames.COMPARATOR_FEED_TYPE, "hitcount");
    switch(type) {
      case "userSiteHitcount":
        output = new FeedComparatorUserSiteHitcountImpl(appContext, installation, reverseOrder); 
        break;
      default:  
        output = new BaseFeedComparator(reverseOrder);
        break;
    }
    return output;  
  }
}
