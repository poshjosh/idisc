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

package com.idisc.web.tasks;

import com.bc.util.XLogger;
import com.idisc.pu.entities.Feed;
import com.idisc.web.Attributes;
import com.idisc.web.FileFeedBuilder;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletContext;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 14, 2016 1:51:10 PM
 */
public class UpdateFileFeeds extends com.bc.task.AbstractStoppableTask<Map<String, Feed>> {
    
    private final ServletContext context;

    public UpdateFileFeeds(ServletContext context) {
        this.context = context;
    }

    @Override
    public Map<String, Feed> doCall() {
        
        XLogger.getInstance().log(Level.INFO, "Creating notices", getClass()); 
      
        FileFeedBuilder fileCache = new FileFeedBuilder(context);
      
        Map<String, Feed> noticesCache = fileCache.load(Paths.get(context.getRealPath("/notices")));
      
        context.setAttribute(Attributes.NOTICES_MAP, noticesCache);
        
        return noticesCache;
    }

    @Override
    public String getTaskName() {
        return this.getClass().getSimpleName();
    }
}
