package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import com.idisc.web.exceptions.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public abstract class GetUserPreferenceFeeds<K> extends Selectfeeds {
    
    private User user;
    
    public abstract Class<K> getPreferenceEntityClass();

    public abstract List<K> getPreferenceFeedList(Installation installation);
    
    public abstract com.idisc.pu.entities.Feed getFeed(K pref);

    @Override
    public boolean isProtected() {
        return true;
    }

    @Override
    public List<Map> execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.user = this.findUser(request, response);
        return super.execute(request, response); 
    }
    
    @Override
    protected List<com.idisc.pu.entities.Feed> select(HttpServletRequest request) throws ValidationException {
        Installation installation;
        try{
            boolean create = true;
            installation = AppInstallation.getEntity(request, user, create);
        }catch(ServletException e) {
            XLogger.getInstance().log(Level.WARNING, "", this.getClass(), e);
            installation = null;
            throw new ValidationException("Error validating installation");
        }
        List<K> prefs = this.getPreferenceFeedList(installation);
        List<com.idisc.pu.entities.Feed> feeds = this.toFeedList(prefs);
        return feeds;
    }

    public List<com.idisc.pu.entities.Feed> toFeedList(List<K> prefs) {
        if(prefs == null) {
            return null;
        }else{
            List<com.idisc.pu.entities.Feed> feeds = new ArrayList<>(prefs.size());
            for(K pref:prefs) {
                com.idisc.pu.entities.Feed feed = this.getFeed(pref);
                feeds.add(feed);
            }
            return feeds;
        }
    }
    
    
    @Override
    protected List<com.idisc.pu.entities.Feed> ensureEquality(
            List<com.idisc.pu.entities.Feed> feeds, int outputSize) {
        List<com.idisc.pu.entities.Feed> output;
        if(feeds == null) {
            output = null;
        }else if(feeds.size() > outputSize) {
            output = feeds.subList(0, outputSize);
        }else{
            output = feeds;
        }
        return output;
    }
}

