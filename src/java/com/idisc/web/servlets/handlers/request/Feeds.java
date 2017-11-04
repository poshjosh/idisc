package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import com.idisc.pu.SpreadBySite;
import com.idisc.core.comparator.feed.BaseFeedComparator;
import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import com.idisc.web.FeedComparatorUserSiteHitcountImpl;
import com.idisc.web.MemoryManager;
import com.idisc.web.exceptions.ValidationException;
import com.idisc.web.functions.GetJsonArrayParameter;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext;
import com.idisc.web.servlets.handlers.response.FeedsResponseContext_outdatedApps;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import com.idisc.web.servlets.request.AppVersionCode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class Feeds extends Selectfeeds {

    private static final Logger logger = Logger.getLogger(Feeds.class.getName());

  private final BiFunction<ServletRequest, String, List<String>> getJsonArray;

  public Feeds() {
    this(new GetJsonArrayParameter());
  }

  public Feeds(BiFunction<ServletRequest, String, List<String>> getJsonArray) {
    this.getJsonArray = Objects.requireNonNull(getJsonArray);
  }

  @Override
  public boolean isOutputLarge(HttpServletRequest request) {
    return !this.isHtmlResponse(request);
  }

  @Override
  protected ResponseContext<List<Feed>> createSuccessResponseContext(HttpServletRequest request) {

    final Installation installation = this.getInstallation(request, true);

    final AppVersionCode versionCodeManager = this.getVersionCodeManager();

    final int versionCode = versionCodeManager == null ? -1 : versionCodeManager.get(request, -1);

    final List<Integer> hotnewsFeedids = versionCode > 37 ?
            Addhotnewsfeedid.getHotnewsFeedids() : Collections.EMPTY_LIST;

    if(versionCodeManager != null && versionCodeManager.isLessThanLatest(request, false)) {

      return new FeedsResponseContext_outdatedApps(request, installation, hotnewsFeedids);

    }else{

      return new FeedsResponseContext(request, installation, hotnewsFeedids);
    }
  }

  @Override
  public synchronized List<Feed> select(HttpServletRequest request)
    throws ServletException {

    logger.entering(this.getClass().getName(), "#select(HttpServletRequest)");

    List<Feed> output;

    final ServletContext context = request.getServletContext();

    final List<Feed> cached = (List<Feed>)context.getAttribute(Attributes.FEEDS);

    if(cached == null || cached.isEmpty()) {

//@todo should this not be cached
        output = super.select(request);

    }else{

        output = new ArrayList(cached);
    }

    if(!this.isHtmlResponse(request) && 
            (this.hasSearchTerms(request) || this.getSearchTerm(request) != null)) {

        output = new SpreadBySite().spread(output, super.getLimit(request));

        output = this.sort(this.getAppContext(request), output);
    }

//    if(!this.isHtmlResponse(request)) {

//        output = this.formatFeedsForJsonResponse(request, output);
//    }

    return output;
  }
  
  private boolean hasSearchTerms(HttpServletRequest request) throws ValidationException {
      return this.getSearchTerms(request).length > 0;
  }

  private String [] _$st;
  @Override
  protected String[] getSearchTerms(HttpServletRequest request) throws ValidationException {
    if(_$st == null) {
        final List<String> all = new ArrayList<>();
        final List<String> preferredSources = this.getPreferredSources(request);
        final List<String> preferredCategories = this.getPreferredCategories(request);
        all.addAll(preferredSources);
        all.addAll(preferredCategories);
        final int max = this.getSearchTermsMax(request);
        if(all.size() <= max) {
          _$st = all.isEmpty() ? new String[0] : all.toArray(new String[0]);
        }else{
          Collections.shuffle(all);
          _$st = all.subList(0, max).toArray(new String[0]);
        }
    }
    return _$st;
  }

  @Override
  protected int getLimit(HttpServletRequest request) throws ValidationException {
    return this.isHtmlResponse(request) ? super.getLimit(request) : getMaxLimit(request);
  }

  private List<Feed> formatFeedsForJsonResponse(HttpServletRequest request, List<Feed> allFeeds)
        throws ValidationException {

    final AppContext appContext = this.getAppContext(request);

    final boolean debug = appContext.getConfiguration().getBoolean(ConfigNames.DEBUG_TIME_AND_MEMORY, false);

    final int limitFromSuper = super.getLimit(request);

    Predicate<Feed> negation = null;

    final List<String> preferredSources = this.getPreferredSources(request);

    final Predicate<Feed> userSourcesTest = preferredSources.isEmpty() ? (feed) -> false :
            (feed) -> preferredSources.contains(feed.getSiteid().getSite());
    negation = this.and(negation, userSourcesTest.negate());

    final List<Feed> allSelected = new ArrayList(limitFromSuper);

    allSelected.addAll(
        this.filter(allFeeds, userSourcesTest, "UserSourcesTest", limitFromSuper, appContext.getMemoryManager(), debug)
    );

    if(allSelected.size() < limitFromSuper) {

        final List<String> preferredCategories = this.getPreferredCategories(request);

        final Predicate<Feed> userCategoriesTest = preferredCategories.isEmpty() ? (feed) -> false :
                this.getCategoriesTest(preferredCategories);
        negation = this.and(negation, userCategoriesTest.negate());

        allSelected.addAll(
            this.filter(allFeeds, userCategoriesTest, "UserCategoriesTest", limitFromSuper, appContext.getMemoryManager(), debug)
        );

        final Predicate<Feed> i8SourceTest = (feed) -> feed.getSiteid().getCountryid() == null;
        negation = this.and(negation, i8SourceTest.negate());

        allSelected.addAll(
            this.filter(allFeeds, i8SourceTest, "InternationalSourceTest", limitFromSuper, appContext.getMemoryManager(), debug)
        );
    }

    final List<Feed> output;

    if(allSelected.size() < limitFromSuper * 0.25f) {

        output = new SpreadBySite().spread(allFeeds, limitFromSuper);

    }else if(allSelected.size() <= limitFromSuper * 0.5f) {

        final List<Feed> remainder = allFeeds.stream().filter(negation).collect(Collectors.toList());

        output = new ArrayList(limitFromSuper);
        output.addAll(allSelected);
        output.addAll(new SpreadBySite().spread(remainder, limitFromSuper - allSelected.size()));

    }else{

        output = allSelected;
    }

    return this.sort(appContext, output);
  }

  private List<String> getPreferredSources(HttpServletRequest request) {
    final List<String> preferredSources = getJsonArray.apply(request, "preferredSources");
    return preferredSources;
  }

  private List<String> getPreferredCategories(HttpServletRequest request) {
    final List<String> preferredCategories = getJsonArray.apply(request, "preferredCategories");
    return preferredCategories;
  }

  private List<Feed> filter(List<Feed> list, Predicate<Feed> test, String testName,
          int limit, MemoryManager memoryManager, boolean debug) {

    final long tb4 = System.currentTimeMillis();
    final long mb4 = memoryManager.getAvailableMemory();

    final long sizeB4 = list.size();
    list = list.stream().filter(test).limit(limit).collect(Collectors.toList());
    final long sizeAfter = list.size();

    logger.log(debug ? Level.INFO : Level.FINE,
    () -> MessageFormat.format("{0}. Accepted: {1} / {2} feeds. Consumed time: {3}, memory: {4}",
    testName, sizeAfter, sizeB4, System.currentTimeMillis()-tb4, mb4-memoryManager.getAvailableMemory()));

    return list;
  }

  private Predicate<Feed> and(Predicate<Feed> a, Predicate<Feed> b) {
      return a == null ? b : a.and(Objects.requireNonNull(b));
  }

  private Predicate<Feed> getCategoriesTest(List<String> categories) {
    return (feed) -> {
        boolean output;
        if(categories.isEmpty() || feed.getCategories() == null || feed.getCategories().isEmpty()) {
            output = false;
        }else{
            final String catArr = feed.getCategories().toLowerCase();
            for(String cat : categories) {
                if(catArr.contains(cat.toLowerCase())) {
                    output = true;
                    break;
                }
            }
            output = false;
        }
        return output;
    };
  }

  private List<Feed> sort(AppContext appContext, List<Feed> feeds) {

    if(feeds != null && !feeds.isEmpty()) {
final long tb4 = System.currentTimeMillis();
final long mb4 = appContext.getMemoryManager().getAvailableMemory();

        feeds = new ArrayList(feeds);

        final boolean reverseOrder = true;

        final Comparator<Feed> comparator = this.getComparator(appContext, reverseOrder);

        try{
            Collections.sort(feeds, comparator);
        }finally{
          if(comparator instanceof AutoCloseable) {
            try{
              ((AutoCloseable)comparator).close();
            }catch(Exception e) {
              XLogger.getInstance().log(Level.WARNING, "Exception closing: "+comparator, this.getClass(), e);
            }
          }
        }

        final boolean debugTimeAndMemory =
                appContext.getConfiguration().getBoolean(ConfigNames.DEBUG_TIME_AND_MEMORY, false);

        final int numberOfFeeds = feeds.size();
logger.log(debugTimeAndMemory ? Level.INFO : Level.FINE,
() -> MessageFormat.format("Comparator: {0}, sorted {1} feeds. Consumed time: {2}, memory: {3}",
      comparator.getClass().getName(), numberOfFeeds, System.currentTimeMillis()-tb4,
      mb4-appContext.getMemoryManager().getAvailableMemory()
));
    }

    return feeds == null ? Collections.EMPTY_LIST : feeds;
  }

  protected Comparator<Feed> getComparator(AppContext appContext, boolean reverseOrder) {
    Comparator<Feed> output;
    final String type = appContext.getConfiguration().getString(ConfigNames.COMPARATOR_FEED_TYPE, "hitcount");
    switch(type) {
      case "userSiteHitcount":
        output = new FeedComparatorUserSiteHitcountImpl(appContext, this.getInstallation(), reverseOrder);
        break;
      default:
        output = new BaseFeedComparator(reverseOrder);
        break;
    }
    return output;
  }
}
