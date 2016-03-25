<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="feed" scope="request" class="com.idisc.pu.entities.Feed"/> 

<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>News by ${appName}: ${feed.title}</title>
        <meta name="keywords" content="${feed.keywords}"/>
        <meta name="description" content="News feed extracted by ${appName} originally written by ${feed.author == null ? feed.siteid.site : feed.author } and published by ${feed.siteid.site} on ${feed.feeddate}"/>
    </head>
    <body class="content">
        <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
        <%@include file="/WEB-INF/jspf/google_leaderboardad_script.jspf"%>
        <h3>${feed.title}</h3>
        <p class="smallerLighter">
            Written by: ${feed.author == null ? feed.siteid.site : feed.author }<br/>
            Published by: ${feed.siteid.site} on ${feed.feeddate}
        </p>
        <p> 
            ${feed.content == null ? feed.description : feed.content} 
        </p>
        <p>
            <small>Categories:  ${feed.categories}</small>
        </p>
        <a href="${feed.url}" target="_blank">Go to source</a>
        <p><b>More News</b></p>
        <c:if test="${lastFeeds != null}">
            <c:forEach begin="0" end="19" var="lastFeed" items="${lastFeeds}">
                <p><a href="${pageContext.servletContext.contextPath}/feed/${lastFeed.feedid}.jsp">${lastFeed.title}</a></p>    
            </c:forEach>
        </c:if>
    </body>
</html>




