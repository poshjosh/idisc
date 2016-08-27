<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} News Feeds" 
    pageKeywords="news,nigeria news update, latest nigeria news,breaking news,extracted news,various sources" 
    pageDescription="Latest breaking news feeds from various nigerian sources">
    <jsp:attribute name="pageContent" trim="true">
        <c:choose>
            <c:when test="${searchresults == null || empty searchresults}">
                <h4>
                    No current feeds available, check back later
                </h4>
            </c:when>    
            <c:otherwise>
                <idisc:displayfeeds displayPageNav="true" displayPageLinks="true" 
                                    feeds="${searchresults}" page="${page}" nextPage="/feeds.jsp"/>
            </c:otherwise>
        </c:choose>        
    </jsp:attribute>
</idisc:page_with_slider>
