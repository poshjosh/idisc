package com.idisc.web.beans;

import com.idisc.core.FeedComparatorRelatedContents;
import com.idisc.pu.entities.Feed;
import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Josh
 */
public class RelatedFeedSelectorBean extends FeedSelectorBean implements Serializable {
    
    private Feed feed;

    public RelatedFeedSelectorBean() { }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
    
    @Override
    protected Comparator<Feed> createComparator() {
        return new FeedComparatorRelatedContents(this.feed);
    }
}

