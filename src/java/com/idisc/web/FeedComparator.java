package com.idisc.web;

import com.idisc.core.AbstractFeedComparator;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.Configuration;


/**
 * @(#)FeedComparator.java   03-Mar-2015 21:16:22
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class FeedComparator extends AbstractFeedComparator {

    private final long addValuePerHit;
    
    private final long addedValueMax;
    
    public FeedComparator() { 
        Configuration config = WebApp.getInstance().getConfiguration();
        addValuePerHit = TimeUnit.MINUTES.toMillis(config.getInt(AppProperties.FEED_CYCLE_INTERVAL));
        addedValueMax = TimeUnit.MINUTES.toMillis(config.getInt(AppProperties.ADDED_VALUE_LIMIT));
    }    

    @Override
    public long getAddValuePerHit() {
        return addValuePerHit;
    }

    @Override
    public long getAddedValueMax() {
        return addedValueMax;
    }
}
