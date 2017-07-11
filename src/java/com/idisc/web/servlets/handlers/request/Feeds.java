package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.SpreadBySite;
import com.idisc.core.comparator.feed.BaseFeedComparator;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import com.idisc.web.FeedComparatorUserSiteHitcountImpl;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext_outdatedApps;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.request.AppVersionCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Feeds extends Selectfeeds {
    
  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !this.isHtmlResponse(request);
  }
  
  @Override
  protected ResponseContext<List<Feed>> createSuccessResponseContext(HttpServletRequest request) {
      
    final Installation installation = this.getInstallation(request, true);
    
    final AppVersionCode versionCodeManager = this.getVersionCodeManager();
    
    final int versionCode = versionCodeManager == null ? -1 : versionCodeManager.get(request, -1);
    
    final List<Integer> hotnewsFeedids = versionCode > 37 ? 
            Addhotnewsfeedid.getHotnewsFeedids() : Collections.EMPTY_LIST;
    
    if(versionCodeManager != null && versionCodeManager.isLessThanLatest(request, false)) {
        
      return new FeedsResponseContext_outdatedApps(request, installation, hotnewsFeedids);
      
    }else{
        
      return new FeedsResponseContext(request, installation, hotnewsFeedids);
    }
  }
  
  @Override
  public synchronized List<Feed> select(HttpServletRequest request)
    throws ServletException {
      
XLogger.getInstance().entering(this.getClass(), "#select(HttpServletRequest)", "");

    List<Feed> output;
    
    final ServletContext context = request.getServletContext();
      
    final List<Feed> cached = (List<Feed>)context.getAttribute(Attributes.FEEDS);
    
    if(cached == null || cached.isEmpty()) {
        
        output = super.select(request);
        
    }else{
        
        output = new ArrayList(cached);
    }
    
    if(!this.isHtmlResponse(request)) {
        
        output = this.formatFeedsForJsonResponse(request, output);
    }

    return output;
  }
  
  @Override
  protected int getLimit(HttpServletRequest request) throws ValidationException {
    return this.isHtmlResponse(request) ? super.getLimit(request) : getMaxLimit(request);
  }
  
  private List<Feed> formatFeedsForJsonResponse(HttpServletRequest request, List<Feed> feeds) 
        throws ValidationException {
      
    final int limitFromSuper = super.getLimit(request);
      
    feeds = new SpreadBySite().spread(feeds, limitFromSuper); 
    
    final AppContext appContext = this.getAppContext(request);
    
    return this.sort(appContext, feeds);
  }

  private List<Feed> sort(AppContext appContext, List<Feed> feeds) {
    
    if(feeds != null && !feeds.isEmpty()) {
final long tb4 = System.currentTimeMillis();
final long mb4 = appContext.getMemoryManager().getAvailableMemory();
        
        feeds = new ArrayList(feeds);
        
        final boolean reverseOrder = true;

        Comparator<Feed> comparator = this.getComparator(appContext, reverseOrder);

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
comparator.getClass().getName(), feeds.size(), (System.currentTimeMillis()-tb4), (mb4-appContext.getMemoryManager().getAvailableMemory()));
    }

    return feeds == null ? Collections.EMPTY_LIST : feeds;
  }
  
  protected Comparator<Feed> getComparator(AppContext appContext, boolean reverseOrder) {
    Comparator<Feed> output;
    final String type = appContext.getConfiguration().getString(ConfigNames.COMPARATOR_FEED_TYPE, "hitcount");
    switch(type) {
      case "userSiteHitcount":
        output = new FeedComparatorUserSiteHitcountImpl(appContext, this.getInstallation(), reverseOrder); 
        break;
      default:  
        output = new BaseFeedComparator(reverseOrder);
        break;
    }
    return output;  
  }
}
