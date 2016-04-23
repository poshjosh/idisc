<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:useBean id="feed" scope="session" class="com.idisc.pu.entities.Feed"/> 

<idisc:page_with_slider 
    pageTitle="News by ${appName}: ${feed.title}" 
    pageKeywords="${feed.keywords}" 
    pageDescription="News feed extracted by ${appName} originally written by ${feed.author == null ? feed.siteid.site : feed.author } and published by ${feed.siteid.site} on ${feed.feeddate}">
    <jsp:attribute name="pageContent" trim="true">
        <h3>${feed.title}</h3>
        <p class="smallerLighter">
            Written by: ${feed.author == null ? feed.siteid.site : feed.author }<br/>
            Published by: ${feed.siteid.site} on
            <idisc:displaydate date="${feed.feeddate}" displayAsTimeElapsed="false"/>
        </p>
        <c:set var="fContent" value="${feed.content == null ? feed.description : feed.content}"/>
        <c:if test="${fContent != null && fContent != ''}">
            <c:set var="fHtml" value="<html><head><title></title></head><body>${fContent}</body></html>"/>
        </c:if>
        <c:if test="${fHtml != null}">
<%-- WE DO THIS BECAUSE iframe MAY BE DISABLED BY DEFAULT ON IE etc
            <iframe sandbox seamless class="content_sub" 
            srcdoc="<c:out escapeXml='true' value='${fHtml}'/>">
                <p class="content_sub"> 
                    <c:out escapeXml="false" value="${fHtml}"/>
                </p>
            </iframe>
--%>
            <p class="content_sub">
                <c:out escapeXml="false" value="${fContent}"/>
            </p>
        </c:if>
        <p>
            <small>Categories:  ${feed.categories}</small>
        </p>
        <div style="width:100%; background-color:white; text-align:center;">
            <idisc:socialshare ssFeed="${feed}"/>
            &emsp;<a href="${feed.url}" target="_blank">Go to source</a>
        </div>
        <br/>
        <div><b>Comment on This</b></div>
        <idisc:commentform cfFeedid="${feed.feedid}"/>
        <br/>
        <c:if test="${comments != null && not empty comments}">
            <div><b>Comments</b></div>
            <idisc:displaycomments comments="${comments}"/> 
        </c:if>
        <c:set var="lastFeeds" value="<%=com.idisc.web.FeedCache.getLastFeeds()%>"/>
        <c:set var="feeds" value="${searchresults == null || empty searchresults ? (feeds == null || empty feeds ? lastFeeds : feeds) : searchresults}"/>
        <c:if test="${feeds != null && not empty feeds}">
            <div><b>More News</b></div>
            <idisc:displayfeeds feeds="${feeds}" nextPage="/feeds.jsp"/> 
        </c:if>
    </jsp:attribute>
</idisc:page_with_slider>
