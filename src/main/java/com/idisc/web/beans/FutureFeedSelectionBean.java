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

import com.idisc.pu.SelectByDate;
import com.idisc.pu.comparator.feed.DefaultFeedComparator;
import com.idisc.pu.comparator.feed.FeedComparatorRelatedContents;
import com.idisc.pu.entities.Feed;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 7, 2018 10:50:16 PM
 */
public class FutureFeedSelectionBean implements Serializable {

    private transient static final Logger LOG = Logger.getLogger(FutureFeedSelectionBean.class.getName());
    
    private Feed feed;
    
    private transient Future<List<Feed>> future;

    public FutureFeedSelectionBean() { }
    
    public void setRequest(HttpServletRequest request) {
        final AppContext ctx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        if(ctx == null) {
            return;
        }
        this.setAppContext(ctx);
    }

    public void setAppContext(AppContext ctx) {
        Objects.requireNonNull(ctx);
        final FeedSelectionConfig config = new FeedSelectionConfig();
        config.setMaxAgeDays(3);
        config.setMaxSpread(2_000);
        config.setBatchSize(100);
        config.setMaxOutputSize(20);
        final Callable<List<Feed>> task = new FeedSelectionTask(
                config,
                new SelectByDate(ctx.getIdiscApp().getJpaContext(), Feed.class),
                this.getComparator()
        );
        this.setFuture(ctx.getGlobalScheduledExecutorService(true).submit(task));
    }
    
    public Comparator<Feed> getComparator() {
        return feed == null ? new DefaultFeedComparator(true) :
                new FeedComparatorRelatedContents(feed);
    }

    public void setFuture(Future<List<Feed>> future) {
        this.future = Objects.requireNonNull(future);
    }
    
    public List<Feed> getFeeds() {
        try{
            return future == null || future.isCancelled() || !future.isDone() ? 
                    Collections.EMPTY_LIST : future.get();
        }catch(InterruptedException | ExecutionException e) {
            LOG.log(Level.WARNING, null, e);
            return Collections.EMPTY_LIST;
        }
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
