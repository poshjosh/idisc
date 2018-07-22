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

import com.bc.jpa.context.JpaContext;
import java.util.logging.Logger;
import com.idisc.pu.FeedDao;
import com.idisc.pu.entities.Feed;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 21, 2016 8:15:13 AM
 */
public class DefaultFeedService extends FeedDao {
    private transient static final Logger LOG = Logger.getLogger(DefaultFeedService.class.getName());

  private final boolean debugTimeAndMemory;
  
  private final MemoryManager memoryManager;
  
  public DefaultFeedService(AppContext appContext) {
    this(appContext.getIdiscApp().getJpaContext(), appContext.getMemoryManager(),
            appContext.getConfiguration().getBoolean(ConfigNames.DEBUG_TIME_AND_MEMORY, false)
    );
  }
    
  public DefaultFeedService(JpaContext jpaContext, MemoryManager memoryManager, boolean debugTimeAndMemory) {
    super(jpaContext);
    this.memoryManager = Objects.requireNonNull(memoryManager);
    this.debugTimeAndMemory = debugTimeAndMemory;
  }

  @Override
  public List<Feed> selectFeeds(int offset, int limit) {
      
long tb4 = System.currentTimeMillis();
long mb4 = memoryManager.getAvailableMemory();

    List<Feed> loadedFeeds = super.selectFeeds(offset, limit);
            
    final Level level = debugTimeAndMemory ? Level.INFO : Level.FINE;

    if(LOG.isLoggable(level)) {
      LOG.log(level, "Selected {0} feeds. Consumed time: {1}, memory: {2}", 
      new Object[]{sizeOf(loadedFeeds), (System.currentTimeMillis()-tb4), (mb4-memoryManager.getAvailableMemory())});
    }
    return loadedFeeds;
  }
  
  @Override
  public List<Feed> spreadOutput(List<Feed> feeds, int outputSize) {

long tb4 = System.currentTimeMillis();
long mb4 = memoryManager.getAvailableMemory();
      
    List<Feed> output =  super.spreadOutput(feeds, outputSize);
    
    final Level level = debugTimeAndMemory ? Level.INFO : Level.FINE;

    if(LOG.isLoggable(level)) {
      LOG.log(level, "After spreading output. Consumed time: {0}, memory: {1}", 
      new Object[]{(System.currentTimeMillis()-tb4), (mb4-memoryManager.getAvailableMemory())});
    }
    
    return output;
  }

  public final boolean isDebugTimeAndMemory() {
    return debugTimeAndMemory;
  }
}