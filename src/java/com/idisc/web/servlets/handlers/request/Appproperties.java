package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.context.JpaContext;
import com.idisc.pu.entities.Site;
import com.idisc.pu.entities.Site_;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.dao.Select;

public class Appproperties extends ReadJsonFromLocalMachine {
    
  public static String HEADLINE_PATTERN = "headlinePattern";
  public static String ADVERT_CATEGORY_NAME = "advertCategoryName";
  public static String CONNECT_TIMEOUT_MILLIS = "connectTimeoutMillis";
  public static String READ_TIMEOUT_MILLIS = "readTimeoutMillis";
  
  public Appproperties() {
    super("META-INF/json/appProperties.json");
  }

  @Override
  public Map load(HttpServletRequest request) throws IOException {
    final Map loaded = super.load(request); 
    Objects.requireNonNull(loaded);
    final Map output = new HashMap(loaded);
    output.put("sources", this.getSiteNames(request));
    return Collections.unmodifiableMap(output);
  }
    
  public List<String> getSiteNames(HttpServletRequest request) {
    final JpaContext jpa = this.getJpaContext(request);
    final Select<String> select = jpa.getDaoForSelect(Site.class, String.class);  
    final List<String> siteNames = select.from(Site.class).select(Site_.site).getResultsAndClose();
    return siteNames == null ? Collections.EMPTY_LIST : siteNames;
  }
}
