package com.idisc.web.servlets.handlers.request;

public class Appproperties extends ReadLocalJson {
    
  public static String HEADLINE_PATTERN = "headlinePattern";
  public static String ADVERT_CATEGORY_NAME = "advertCategoryName";
  public static String CONNECT_TIMEOUT_MILLIS = "connectTimeoutMillis";
  public static String READ_TIMEOUT_MILLIS = "readTimeoutMillis";
  
  public Appproperties() {
    super("META-INF/json/appProperties.json");
  }
}
