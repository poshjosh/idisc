package com.idisc.web.servlets.handlers;

import com.idisc.pu.entities.Favoritefeed;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.List;

/**
 * @author Josh
 */
public class Syncfavorites extends AbstractPreferenceSync<Favoritefeed> {

    @Override
    public String getKey() {
        return "syncfavorites";
    }

    @Override
    public Class<Favoritefeed> getEntityClass() {
        return Favoritefeed.class;
    }

    @Override
    public List<Favoritefeed> getFeedList(Installation installation) {
        return installation.getFavoritefeedList();
    }

    @Override
    public com.idisc.pu.entities.Feed getFeed(Favoritefeed bookmark) {
        return bookmark.getFeedid();
    }

    @Override
    public Favoritefeed createFor(Installation installation, com.idisc.pu.entities.Feed feed) {
        Favoritefeed bookmark = new Favoritefeed();
        bookmark.setDatecreated(new Date());
        bookmark.setInstallationid(installation);
        bookmark.setFeedid(feed);
        return bookmark;
    }
}

