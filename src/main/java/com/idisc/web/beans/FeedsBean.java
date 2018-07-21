/*
 * Copyright 2018 NUROX Ltd.
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

package com.idisc.web.beans;

import com.bc.jpa.context.PersistenceUnitContext;
import com.bc.util.Util;
import com.idisc.pu.SelectByDate;
import com.idisc.pu.comparator.feed.DefaultFeedComparator;
import com.idisc.pu.entities.Feed;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 9, 2018 4:03:08 PM
 */
public class FeedsBean implements Serializable {

    private transient static final Logger LOG = Logger.getLogger(FeedsBean.class.getName());

    private List<Feed> feeds;
    
    public void setRequest(HttpServletRequest request) {
        
        feeds = (List<Feed>)request.getServletContext().getAttribute(Attributes.FEEDS);
        
        if(feeds == null) {
            
            feeds = (List<Feed>)request.getSession().getAttribute("searchresults");
            
            if(feeds == null) {
                
                final AppContext appCtx = (AppContext)request
                        .getServletContext().getAttribute(Attributes.APP_CONTEXT);
                
                feeds = this.selectFeeds(appCtx);
            }
        }
    }
    
    public List<Feed> selectFeeds(AppContext appCtx) {
        
        long mb4 = Util.availableMemory();
        long tb4 = System.currentTimeMillis();
                
        final PersistenceUnitContext puCtx = appCtx.getIdiscApp().getJpaContext();

        final EntityManager em = puCtx.getEntityManager();

        final FeedSelectionConfig config = new FeedSelectionConfig();
        config.setMaxAgeDays(1);
        config.setBatchSize(100);
        config.setMaxOutputSize(10);
        config.setMaxSpread(300);

        final SelectByDate selector = new SelectByDate(puCtx, Feed.class);

//        final Comparator<Feed> comparator = new FeedComparatorUserHitsOnSite(em, installation, true);
        final Comparator<Feed> comparator = new DefaultFeedComparator(true);

        final FeedSelectionTask task = new FeedSelectionTask(config, selector, comparator);

        final List<Feed> selection;
        try{
            selection = task.call();
            LOG.fine(() -> "Done selecting feed " + selection.size() + 
                    " feeds. Consumed. memory: " + Util.usedMemory(mb4) +
                    "time: " + (System.currentTimeMillis() - tb4));
        }finally{
            if(em.isOpen()) {
                em.close();
            }
        }    
        
        return selection;
    }
    
    public List<Feed> getFeeds() {
        return feeds == null ? Collections.EMPTY_LIST : feeds;
    }
}
