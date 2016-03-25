package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.util.List;

/**
 * @author Josh
 */
public class Getfavoritefeeds extends GetUserPreferenceFeeds<Favoritefeed> {

    @Override
    public Class<Favoritefeed> getPreferenceEntityClass() {
        return Favoritefeed.class;
    }

    @Override
    public List<Favoritefeed> getPreferenceFeedList(Installation installation) {
        return installation.getFavoritefeedList();
    }

    @Override
    public Feed getFeed(Favoritefeed pref) {
        return pref.getFeedid();
    }
}
