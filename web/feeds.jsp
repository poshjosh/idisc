<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} News Feeds" 
    pageKeywords="news,latest news,breaking news,extracted news,various sources" 
    pageDescription="Latest breaking news feeds from various sources">
    <jsp:attribute name="pageContent" trim="true">
        <c:if test="${feeds == null}">
            <c:set var="feeds" value="${cachedFeeds}"/>
        </c:if>
        <c:choose>
            <c:when test="${feeds == null || empty feeds}">
                
                <h3>
                    No current feeds available, check back later
                </h3>
            </c:when>    
            <c:otherwise>
                <idisc:displayfeeds feeds="${feeds}" page="${page}" nextPage="/feeds.jsp"/>
            </c:otherwise>
        </c:choose>        
    </jsp:attribute>
</idisc:page_with_slider>
