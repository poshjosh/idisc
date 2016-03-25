package com.idisc.web;

/**
 * @(#)AppProperties.java   16-Oct-2014 10:27:18
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
public interface AppProperties {
    
    String MULTITASK_REQUEST_DEFAULT_POOL_SIZE = "multiTaskRequestDefaultPoolSize";

    String MULTITASK_REQUEST_TIMEOUT_SECONDS = "multiTaskRequestTimeoutSeconds";

    String APP_NAME = "appName";
    String EMAILADDRESS = "emailaddress";
    String PASSWORD = "password";
    String MAIL_SEND_INTERVAL = "mailSendInterval";
    String MAIL_BATCH_SIZE = "mailBatchSize";
    String DOWNLOAD_URL = "downloadurl";
    String REQUESTHANDLER_PROVIDER = "requesthandlerFactory";
    
    String MAX_LIMIT = "maxLimit";
    String DEFAULT_LIMIT = "defaultLimit";
    String CACHE_LIMIT = "cacheLimit";
    
    String DEFAULT_CONTENT_LENGTH = "defaultContentLength";
    String FEED_CYCLE_DELAY = "feedCycleDelay";
    String FEED_CYCLE_INTERVAL = "feedCycleInterval";
    String SEND_MAIL_TIMES = "sendMailTimes";
    String ADDED_VALUE_LIMIT = "addedValueLimit";
    
    String REARRANGE_OUTPUT = "rearrangeOutput";
    
    String AUTHSVC_EMAIL = "authsvc.emailaddress";
    
    String AUTHSVC_PASSWORD = "authsvc.password";

    String AUTHSVC_URL = "authsvc.url";

    String TIME_ZONE = "timeZone";
}
