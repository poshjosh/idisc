package com.idisc.web.servlets.handlers.request;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Installation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Getbookmarkfeedids extends GetUserPreferenceFeedids {
    
  @Override
  public List getFeedids(Installation installation) {
    List bookmarkFeedids = null;
    List<Bookmarkfeed> bookmarks = installation.getBookmarkfeedList();
    if ((bookmarks != null) && (!bookmarks.isEmpty())) {
      bookmarkFeedids = new ArrayList(bookmarks.size());
      for (Bookmarkfeed bookmark : bookmarks) {
        bookmarkFeedids.add(bookmark.getFeedid().getFeedid());
      }
    }
    return bookmarkFeedids == null ? Collections.EMPTY_LIST : bookmarkFeedids;
  }
}
