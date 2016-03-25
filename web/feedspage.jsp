<%@page import="com.idisc.web.FeedCache"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>${appName} News Feeds</title>
        <meta name="keywords" content="news,latest news,breaking news,extracted news,various sources"/>
        <meta name="description" content="Latest breaking news feeds from various sources"/>
    </head>
    <body class="content">
        <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
        <c:set var="lastFeeds" value="<%=FeedCache.getLastFeeds()%>"/>
        <c:set var="totalSize" value="${fn:length(lastFeeds)}"/>    
        <c:set var="pageSize" value="20"/>
        <c:set var="pages" value="${totalSize / pageSize}"/>
        <c:if test="${(totalSize % pageSize) > 0}">
            <c:set var="pages" value="${pages + 1}"/>
        </c:if>
        <c:set var="page" value="${page == null ? param.page : page}"/>
        <c:if test="${page == null}"><c:set var="page" value="1"/></c:if>
        <c:set var="offset" value="${pageSize * (page -1)}"/>
        <c:set var="end" value="${offset + pageSize}"/>
        <c:if test="${end > totalSize}"><c:set var="end" value="${totalSize}"/></c:if>
        
        <c:if test="${false}">
          <p>Total size: ${totalSize}. Pages: ${pages}. Page: ${page}. Offset: ${offset}. End: ${end}</p>
        </c:if>
          
        <c:choose>
            <c:when test="${lastFeeds == null || empty lastFeeds}">
                <h3>No current feeds available, check back in <%=FeedCache.getFeedCycleIntervalMillis()/60000%> minutes</h3>
            </c:when>    
            <c:otherwise>
                
                <p>
                    <c:if test="${page > 1}"><a href="${pageContext.servletContext.contextPath}/feedspage.jsp?page=${page - 1}">&lt;</a>&emsp;</c:if>
                    ${offset + 1} - ${end} of ${totalSize} results
                    <c:if test="${page < pages}">&emsp;<a href="${pageContext.servletContext.contextPath}/feedspage.jsp?page=${page + 1}">&gt;</a></c:if>
                </p>
                <ul>
                <c:forEach begin="${offset}" end="${end - 1}" var="feed" items="${lastFeeds}">
                    <li>
                        <p>
                            <a href="${pageContext.servletContext.contextPath}/feed/${feed.feedid}.jsp">${feed.title}</a> 
                            <c:if test="${!mobile}"> by ${feed.siteid.site}</c:if>
                        </p>
                    </li>
                </c:forEach>    
                </ul>
                <c:forEach varStatus="vs" begin="0" end="${pages - 1}">
                    <a href="${pageContext.servletContext.contextPath}/feedspage.jsp?page=${vs.index + 1}">${vs.index + 1}</a>&emsp;
                </c:forEach>    
            </c:otherwise>
        </c:choose>
    </body>
</html>
