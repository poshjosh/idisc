package com.idisc.web.beans;

import com.idisc.pu.entities.Feed;
import com.idisc.web.DefaultFeedCache;
import com.idisc.web.WebApp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author Josh
 */
public class WebappContext implements Serializable {
    
    public BigDecimal getMemoryLevel() {
      long fm = Runtime.getRuntime().freeMemory();
      long mas = WebApp.getInstance().getMemoryAtStartup();
      BigDecimal freeMemory = new BigDecimal(fm);
      BigDecimal memoryAtStartup = new BigDecimal(mas);
      return freeMemory.divide(memoryAtStartup, 2, RoundingMode.HALF_UP);
    }

    public long getMemoryAtStartup() {
        return WebApp.getInstance().getMemoryAtStartup();
    }
    
    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }
    
    public List<Feed> getCachedFeeds() {
        return DefaultFeedCache.getCachedFeeds();
    }
}
