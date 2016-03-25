/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Installation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josh
 */
public class Getbookmarkfeedids  extends GetUserPreferenceFeedids {
    
    @Override
    public List getFeedids(Installation installation) {
        List bookmarkFeedids = null;
        List<Bookmarkfeed> bookmarks = installation.getBookmarkfeedList();
        if(bookmarks != null && !bookmarks.isEmpty()) {
            bookmarkFeedids = new ArrayList(bookmarks.size());
            for(Bookmarkfeed bookmark:bookmarks) {
                bookmarkFeedids.add(bookmark.getFeedid().getFeedid());
            }
        }
        return bookmarkFeedids;
    }
}

