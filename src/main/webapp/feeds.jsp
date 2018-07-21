<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} News Feeds" 
    pageKeywords="news,nigeria news update, latest nigeria news,breaking news,extracted news,various sources" 
    pageDescription="Latest breaking news feeds from various nigerian sources">
    <jsp:attribute name="pageContent" trim="true">
        <c:if test="${AppContext != null && !AppContext.productionMode}">
            <p>Search Results: ${fn:length(searchresults)}</p>
            <p>Feeds: ${fn:length(feeds)}</p>
            <p>FeedsBean.feeds: ${FeedsBean == null ? 0 : fn:length(FeedsBean.feeds)}</p>
            <p>FeedSelectionBean.feeds: ${fn:length(FeedSelectionBean.feeds)}</p>
        </c:if>    
        <c:choose>
            <c:when test="${searchresults != null && not empty searchresults}">
                <idisc:displayfeeds displayPageNav="true" displayPageLinks="true" 
                                    feeds="${searchresults}" page="${page}" nextPage="/feeds.jsp"/>
            </c:when>    
            <c:when test="${feeds != null && not empty feeds}">
                <idisc:displayfeeds displayPageNav="true" displayPageLinks="true" 
                                    feeds="${feeds}" page="${page}" nextPage="/feeds.jsp"/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>        
    </jsp:attribute>
</idisc:page_with_slider>
