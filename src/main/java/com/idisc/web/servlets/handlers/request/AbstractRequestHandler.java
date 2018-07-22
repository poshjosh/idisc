package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.dao.SelectDao;
import com.bc.jpa.search.BaseSearchResults;
import com.bc.jpa.search.SearchResults;
import com.bc.util.JsonBuilder;
import com.bc.util.MapBuilder;
import com.bc.util.StringEscapeUtils;
import com.idisc.web.servlets.handlers.response.HtmlResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.util.logging.Logger;
import com.idisc.core.util.DefaultEntityMapBuilder;
import com.idisc.core.util.EntityJsonBuilder;
import com.idisc.core.util.TimeZones;
import com.idisc.core.util.mapbuildertransformers.TransformerService;
import com.idisc.core.util.mapbuildertransformers.TransformerServiceImpl;
import com.idisc.core.util.mapbuildertransformers.TransformerService_appVersionCode8orBelow;
import com.idisc.pu.entities.Installation;
import com.idisc.web.ConfigNames;
import com.idisc.web.functions.RequestIsFromOrToWebResourceTest;
import com.idisc.web.servlets.handlers.response.ErrorHandlerContext;
import com.idisc.web.servlets.handlers.response.ObjectToJsonResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.handlers.response.SuccessHandlerContext;
import com.idisc.web.servlets.request.AppVersionCode;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.configuration.Configuration;

public abstract class AbstractRequestHandler<V> 
        extends SessionUserHandlerImpl 
        implements RequestHandler<V, Object> {

  private transient static final Logger LOG = Logger.getLogger(AbstractRequestHandler.class.getName());
    
  private String responseFormat;
  
  private ResponseHandler<V, Object> responseHandler;
  
  private ResponseHandler<Throwable, Object> errorResponseHandler;
  
  private Installation installation;
  
  private AppVersionCode versionCodeManager;

  public AbstractRequestHandler() { }
  
  protected abstract V execute(HttpServletRequest request)
          throws ServletException, IOException;
  
  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
      return false;
  }

  public <X> SearchResults<X> getSearchResults(
          HttpServletRequest request, SelectDao<X> dao, boolean createNew, SearchResults<X> outputIfNone) {
    final Class<X> entityType = dao.getResultType();
    SearchResults<X> output = this.getSearchResults(request, entityType, null);
    if(createNew) {
      if(output != null) {
        this.closeIfCloseable(output);
      }
      output = new BaseSearchResults(dao);
      LOG.log(Level.FINE, "Created instance of: {0}", output.getClass().getName());
      request.getSession(true).setAttribute(this.getSearchResultsAttributeName(entityType), output);
    }
    LOG.log(Level.FINER, "SearchResults: {0}", output);
    return output == null ? outputIfNone : output;
  }

  public <X> SearchResults<X> getSearchResults(
          HttpServletRequest request, Class<X> entityType, SearchResults<X> outputIfNone) {
    final HttpSession session = request.getSession(false);
    SearchResults<X> searchResults;
    if(session == null) {
      searchResults = null;
    }else{
      searchResults = (SearchResults<X>)session.getAttribute(this.getSearchResultsAttributeName(entityType));
    }
    return searchResults == null ? outputIfNone : searchResults;
  }
  
  public String getSearchResultsAttributeName(Class entityType) {
    return "searchResults#" + entityType.getName();
  }

  public void closeIfCloseable(Object obj) {
    if(obj instanceof AutoCloseable) {
      try{
        LOG.log(Level.FINE, "Closing: {0}", obj);  
        ((AutoCloseable)obj).close();
      }catch(Exception e) {
        LOG.log(Level.WARNING, "Exception closing "+obj.getClass().getName(), e);
      }
    }
  }
  
  @Override
  public final V processRequest(HttpServletRequest request) 
      throws ServletException, IOException {

      if((this.getAppContext(request)) == null) {
          throw new ServletException("Application failed to initialize");
      }
      
//      if (isProtected() && !isLoggedIn(request)) {
//        tryLogin(request);
//      }
      
//      if (isProtected() && !isLoggedIn(request)) {
//        throw new LoginException("Login required");
//      }
      
      boolean create = true;
    
      this.installation = getInstallation(request, create);
    
      this.versionCodeManager = new AppVersionCode(request.getServletContext(), installation);
    
      if(LOG.isLoggable(Level.FINER)){
         LOG.log(Level.FINER, "Executing: {0}", this.getClass().getName());
      }
      
      V x = execute(request); 
      
      LOG.log(Level.FINER, "Execution finished, output: {0}", x);
      
      return x;
  }
  
  public <X> ResponseHandler<X, Object> createResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
    ResponseHandler<X, Object> output;
    if (this.isHtmlResponse(request)) {
      output = this.createHtmlResponseHandler(request, context);
    } else {
      output = this.createJsonResponseHandler(request, context);
    }
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "Response handler type: {0}", output.getClass().getName());
    }
    return output;
  }
  
  protected <X> ResponseHandler<X, Object> createJsonResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
      
    final boolean tidyOutput = this.isTidyOutput(request);
    final boolean plainTextOnly = this.isPlainTextOnly(request);
    final int bufferSize = this.getMaxTextLengthPerItem(request);
    
    final MapBuilder mapBuilder = new DefaultEntityMapBuilder();
    mapBuilder.methodFilter(MapBuilder.MethodFilter.ACCEPT_ALL);
    final TransformerService transformerService;
    if(this.versionCodeManager != null && this.versionCodeManager.isLessOrEquals(request, 8, false)) {
      transformerService = new TransformerService_appVersionCode8orBelow(plainTextOnly, bufferSize);
    }else{
      transformerService = new TransformerServiceImpl(plainTextOnly, bufferSize);
    }
    
    final Locale requestLocale = request.getLocale(); // Returns request locale or server default
    
    if(LOG.isLoggable(Level.FINE)){
      LOG.log(Level.FINE, "Request locale: {0}", requestLocale.getDisplayName(Locale.ENGLISH));
    }
    
    final String legacyDateFormatPattern = "EEE MMM dd HH:mm:ss z yyyy";
    final DateFormat outputDateFormat = new SimpleDateFormat(legacyDateFormatPattern);
    outputDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.UTC_ZONEID));
    
    JsonBuilder jsonBuilder = 
            new EntityJsonBuilder(tidyOutput, true, "  ", mapBuilder, transformerService){
                
        @Override
        public void appendJSONString(Object value, Appendable appendTo) throws IOException {
            if(value instanceof Date) {
                value = outputDateFormat.format((Date)value);
            }
            super.appendJSONString(value, appendTo); 
        }

        @Override
        public void escape(CharSequence s, Appendable appendTo) throws IOException {
            final String input = s.toString();
            final String output = StringEscapeUtils.unescapeHtml(input);
            if(input.equals(output)) {
                super.escape(output, appendTo); 
            }else{
                appendTo.append(output);
            }
        }
    }; 
    
    ResponseHandler<X, Object> output = new ObjectToJsonResponseHandler(context, jsonBuilder, bufferSize); 
    
    return output;
  }
  
  protected <X> ResponseHandler<X, Object> createHtmlResponseHandler(
          HttpServletRequest request, ResponseContext<X> context) {
    ResponseHandler<X, Object> output = new HtmlResponseHandler(context);
    return output;
  }
  
  protected ResponseContext<Throwable> createErrorResponseContext(HttpServletRequest request) {
    return new ErrorHandlerContext(request);  
  }
  
  protected ResponseContext<V> createSuccessResponseContext(HttpServletRequest request) {
    return new SuccessHandlerContext(request);  
  }
  
  @Override
  public boolean isHtmlResponse(HttpServletRequest request) {
    return this.isHtmlResponse(this.getResponseFormat(request));
  }
  
  public boolean isHtmlResponse(String responseFormat) {
    boolean output = (responseFormat != null) && (responseFormat.toLowerCase().contains("html"));
    return output;
  }

  public String getResponseFormat(HttpServletRequest request) {
      
    if(responseFormat == null) {
        
      responseFormat = request.getParameter("format");

      if(LOG.isLoggable(Level.FINER)){
        LOG.log(Level.FINER, "format = {0}", responseFormat);
      }       

      if (responseFormat == null) {
          
        if(this.isFromApp(request)) {
          responseFormat = "application/json";    
        }
            
        if(responseFormat == null) {
            
          if(new RequestIsFromOrToWebResourceTest().test(request)) {
            responseFormat = "text/html";
          }
        }
      }
      if(LOG.isLoggable(Level.FINE)){
        LOG.log(Level.FINE, "Response format: {0}", responseFormat);
      }
    }
    
    return responseFormat;
  }
  
  public boolean isFromApp(HttpServletRequest request) {
    return this.getInstallation() != null || request.getParameter(AppVersionCode.PARAM_NAME) != null;
  }

  @Override
  public final ResponseHandler<V, Object> getResponseHandler(HttpServletRequest request) {
    if(responseHandler == null) {
        responseHandler = this.createResponseHandler(
                request, this.createSuccessResponseContext(request));
    }  
    return responseHandler;
  }
  
  @Override
  public final ResponseHandler<Throwable, Object> getErrorResponseHandler(HttpServletRequest request) {
    if(errorResponseHandler == null) {
        errorResponseHandler = this.createResponseHandler(
                request, this.createErrorResponseContext(request));
    }  
    return errorResponseHandler;
  }

  public boolean isPlainTextOnly(HttpServletRequest request) {
    String contentType = request.getParameter("content-type");
    boolean b = (contentType != null) && (contentType.contains("text/plain"));
    if(LOG.isLoggable(Level.FINER)){
      LOG.log(Level.FINER, "Plain text only: {0}", b);
    }
    return b;
  }

  public boolean isTidyOutput(HttpServletRequest request) {
    boolean tidy;
    String tidyParam = request.getParameter("tidy");
    if(tidyParam != null) {
      tidy = "1".equals(tidyParam) || "true".equalsIgnoreCase(tidyParam);
    }else{
      tidy = !this.getAppContext(request).isProductionMode();
    }
    return tidy;
  }
  
  public int getMaxTextLengthPerItem(HttpServletRequest request) {
    Configuration config = this.getAppContext(request).getConfiguration();
    final int defaultLen = config.getInt(ConfigNames.DEFAULT_CONTENT_LENGTH, 1000);
    return getInt(request, "maxlen", defaultLen);
  }
  
  private int getInt(HttpServletRequest request, String key, int defaultValue) {
    String val = request.getParameter(key);
    if ((val == null) || (val.isEmpty())) {
      return defaultValue;
    }
    return Integer.parseInt(val);
  }

  public final Installation getInstallation() {
    return installation;
  }

  public final AppVersionCode getVersionCodeManager() {
    return versionCodeManager;
  }
}
/**
 * 
  protected <X> SearchResults<X> getSearchResults(
          HttpServletRequest request, SelectDao<X> dao) {
    AppContext appCtx = this.getAppContext(request);
    SearchResults<X> searchResults = appCtx.getSearchHandlerFactory(true)
            .get(request.getSession().getId(), dao, true);
    return searchResults;
  }
  public <X> SearchResults<X> getSearchResults(
          HttpServletRequest request, Class<X> enityType, SearchResults<X> outputIfNone) {
    AppContext appCtx = this.getAppContext(request);
    SearchResultsHandlerFactory sf = appCtx.getSearchHandlerFactory(false);
    SearchResults<X> searchResults = sf == null ? null : 
            sf.get(request.getSession().getId(), enityType, outputIfNone);
    return searchResults == null ? outputIfNone : searchResults;
  }
  
 * 
 */