package com.idisc.web.beans;

import com.bc.jpa.dao.Select;
import com.idisc.pu.SelectByDate;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Feed_;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 7, 2018 9:26:16 PM
 */
public class FeedSelectionTask implements Serializable, Callable<List<Feed>> {

    private transient static final Logger LOG = Logger.getLogger(FeedSelectionTask.class.getName());
    
    private final FeedSelectionConfig config;

    private final SelectByDate<Feed, Integer> feedDao;
    
    private final Comparator<Feed> comparator;
    
    public FeedSelectionTask(
            FeedSelectionConfig config, 
            SelectByDate<Feed, Integer> feedDao, 
            Comparator<Feed> comparator) {
        this.config = Objects.requireNonNull(config);
        this.feedDao = Objects.requireNonNull(feedDao);  
        this.comparator = Objects.requireNonNull(comparator);
    }
    
    @Override
    public List<Feed> call() {
        try{

            List<Feed> selected = feedDao.getResultList(
                    Feed_.feeddate.getName(), Select.GT, config.getMaxAgeDays(), 
                    TimeUnit.DAYS, config.getMaxSpread(), config.getBatchSize());

            if(selected != null && !selected.isEmpty()) {
                selected = new ArrayList(feedDao.sort(selected, comparator, config.getMaxOutputSize()));
            }
            
            LOG.log(Level.FINE, "Selected: {0}", selected);
            
            return selected;

        }catch(RuntimeException e) {
            LOG.log(Level.WARNING, "Thread: "+Thread.currentThread().getName(), e);
            return Collections.EMPTY_LIST;
        }
    }
}
