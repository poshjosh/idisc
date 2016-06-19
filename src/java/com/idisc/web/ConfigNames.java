package com.idisc.web;

public abstract interface ConfigNames {
    
  public static final String START_FEED_UPDATE_SERVICE = "startFeedUpdateService";
  public static final String PROCESS_REQUEST_ASYNC = "processRequestAsync";
  public static final String REQUEST_EXECUTOR_SERVICE_THREAD_COUNT = "requestExecutorService.threadCount";
  public static final String BOTFILTER_ENABLED = "botfilter.enabled";
  public static final String BOTFILTER_ENABLE_AT_MEMORY_BELOW = "botfilter.enableAtMemoryBelow";
  public static final String BOTFILTER_DISABLE_AT_MEMORY_ABOVE = "botfilter.disableAtMemoryAbove";
  public static final String BOTFILTER_CACHEDIR = "botfilter.cacheDir";   
  public static final String MULTITASK_REQUEST_DEFAULT_POOL_SIZE = "multiTaskRequestDefaultPoolSize";
  public static final String MULTITASK_REQUEST_TIMEOUT_SECONDS = "multiTaskRequestTimeoutSeconds";
  public static final String LOGGING_PROPERTIES_FILE = "logging.propertiesFile";
  public static final String SCRAPPER_PROPERTIES_FILE = "scrapperPropertiesFile";
  public static final String IDISCCORE_PROPERTIES_FILE = "idisccorePropertiesFile";
  public static final String PERSISTENCE_FILE = "persistenceFile";

  public static final String EMAILADDRESS = "emailaddress";
  public static final String PASSWORD = "password";
  public static final String MAIL_SEND_INTERVAL = "mailSendInterval";
  public static final String MAIL_BATCH_SIZE = "mailBatchSize";
  public static final String COMMENTS_NOTIFICATIONS_REPEAT = "comments.notifications.repeat";
  public static final String COMMENTS_NOTIFICATIONS_MAXAGE_DAYS = "comments.notifications.maxAgeDays";
  public static final String COMMENTS_NOTIFICATIONS_DIRECTREPLIESONLY = "comments.notifications.directRepliesOnly";
  public static final String MAX_LIMIT = "maxLimit";
  public static final String MIN_LIMIT  = "minLimit";
  public static final String DEFAULT_LIMIT = "defaultLimit";
  public static final String CACHE_LIMIT = "cacheLimit";
  public static final String DEFAULT_CONTENT_LENGTH = "defaultContentLength";
  public static final String FEED_CYCLE_DELAY = "feedCycleDelay";
  public static final String FEED_CYCLE_INTERVAL = "feedCycleInterval";
  public static final String SEND_MAIL_TIMES = "sendMailTimes";
  public static final String ADDED_VALUE_LIMIT = "addedValueLimit";
  public static final String REARRANGE_OUTPUT = "rearrangeOutput";
  public static final String AUTHSVC_EMAIL = "authsvc.emailaddress";
  public static final String AUTHSVC_PASSWORD = "authsvc.password";
  public static final String AUTHSVC_URL = "authsvc.url";
  public static final String TIME_ZONE = "timeZone";
}
