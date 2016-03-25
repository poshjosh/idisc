package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Bookmarkfeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.ArrayList;
import java.util.List;



public class Getbookmarkfeedids
  extends GetUserPreferenceFeedids
{
  public List getFeedids(Installation installation)
  {
    List bookmarkFeedids = null;
    List<Bookmarkfeed> bookmarks = installation.getBookmarkfeedList();
    if ((bookmarks != null) && (!bookmarks.isEmpty())) {
      bookmarkFeedids = new ArrayList(bookmarks.size());
      for (Bookmarkfeed bookmark : bookmarks) {
        bookmarkFeedids.add(bookmark.getFeedid().getFeedid());
      }
    }
    return bookmarkFeedids;
  }
}
