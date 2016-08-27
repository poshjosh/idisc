/*
 * Copyright 2016 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.idisc.web;

import com.bc.jpa.JpaContext;
import com.bc.util.XLogger;
import com.idisc.core.FeedService;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Feed;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.configuration.Configuration;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 21, 2016 8:15:13 AM
 */
public class DefaultFeedService extends FeedService {

    private final boolean debugTimeAndMemory;
    
    public DefaultFeedService(AppContext appContext) {
        this(appContext.getIdiscApp().getJpaContext(),
                appContext.getConfiguration().getInt(ConfigNames.CACHE_LIMIT, 200),
                appContext.getConfiguration().getBoolean(ConfigNames.REARRANGE_OUTPUT, true),
                appContext.getConfiguration().getBoolean(ConfigNames.DEBUG, true)
        );
    }
    
    public DefaultFeedService(IdiscApp idiscApp, Configuration config) {
        this(idiscApp.getJpaContext(), 
                config.getInt(ConfigNames.CACHE_LIMIT, 200),
                config.getBoolean(ConfigNames.REARRANGE_OUTPUT, true),
                config.getBoolean(ConfigNames.DEBUG, true)
        );
    }

    public DefaultFeedService(JpaContext jpaContext, int limit, boolean spreadOutput, boolean debug) {
        super(jpaContext, limit, spreadOutput);
        this.debugTimeAndMemory = debug;
    }

  @Override
  protected List<Feed> selectFeeds() {
      
long tb4 = System.currentTimeMillis();
long mb4 = Runtime.getRuntime().freeMemory();

    List<Feed> loadedFeeds = super.selectFeeds();
            
XLogger.getInstance().log(debugTimeAndMemory ? Level.INFO : Level.FINE, 
"Selected {0} feeds. Consumed time: {1}, memory: {2}", this.getClass(), 
sizeOf(loadedFeeds), (System.currentTimeMillis()-tb4), (mb4-Runtime.getRuntime().freeMemory()));
    return loadedFeeds;
  }
  
  @Override
  public List<Feed> spreadOutput(List<Feed> feeds, int outputSize) {

long tb4 = System.currentTimeMillis();
long mb4 = Runtime.getRuntime().freeMemory();
      
    List<Feed> output =  super.spreadOutput(feeds, outputSize);
    
XLogger.getInstance().log(debugTimeAndMemory ? Level.INFO : Level.FINE, 
"After spreading output. Consumed time: {0}, memory: {1}", this.getClass(), 
(System.currentTimeMillis()-tb4), (mb4-Runtime.getRuntime().freeMemory()));
    
    return output;
  }

  public boolean isDebugTimeAndMemory() {
    return debugTimeAndMemory;
  }
}
