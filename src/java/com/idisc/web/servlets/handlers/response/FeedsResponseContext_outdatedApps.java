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

package com.idisc.web.servlets.handlers.response;

import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.shared.feedid.Feedids;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.FileFeedBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 12, 2016 3:00:00 PM
 */
public class FeedsResponseContext_outdatedApps extends FeedsResponseContext {

    public FeedsResponseContext_outdatedApps(HttpServletRequest request, Installation installation) {
        super(request, installation);
    }

    @Override
    public Collection<Feed> getNotices(List feeds, Collection defaultValue) {
        
        final ServletContext context = this.getServletContext();
        
        FileFeedBuilder noticesCache = new FileFeedBuilder(context);
        
        Path updateNoticePath = Paths.get(context.getRealPath("/notices_other"), "updateNotice.html");
        
        AppContext appContext = (AppContext)context.getAttribute(Attributes.APP_CONTEXT);
        
        final Feedids feedids = appContext.getSharedContext().getFeedidsService().getFeedids();
        
        Feed updateNotice = noticesCache.getFeed(feedids.getUpdateNoticeFeedid(), updateNoticePath, null);
        
        Collection<Feed> notices = super.getNotices(feeds, defaultValue); 
        
        if(updateNotice != null) {
            if(notices == null) {
                notices = new ArrayList<>();
            }
            notices.add(updateNotice);
        }
        
        return notices;
    }
}
