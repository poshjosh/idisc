package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Installation;
import java.util.List;

/**
 * @author Josh
 */
public class Getbookmarkfeeds extends GetUserPreferenceFeeds<Bookmarkfeed> {

    @Override
    public Class<Bookmarkfeed> getPreferenceEntityClass() {
        return Bookmarkfeed.class;
    }

    @Override
    public List<Bookmarkfeed> getPreferenceFeedList(Installation installation) {
        return installation.getBookmarkfeedList();
    }

    @Override
    public com.idisc.pu.entities.Feed getFeed(Bookmarkfeed pref) {
        return pref.getFeedid();
    }
}

