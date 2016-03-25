package com.idisc.web.servlets.handlers;



public class Appproperties
  extends ReadLocalJson
{
  public static String HEADLINE_PATTERN = "headlinePattern";
  public static String ADVERT_CATEGORY_NAME = "advertCategoryName";
  
  public String getFilename()
  {
    return "META-INF/json/appProperties.json";
  }
}
