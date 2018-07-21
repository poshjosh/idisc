<%-- 
    Document   : displayfeeds
    Created on : Apr 9, 2016, 7:37:28 PM
    Author     : poshjosh
--%>
<%@tag description="displays a list of feeds, in batches" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<%@attribute name="feeds" required="true" type="java.util.List<com.idisc.pu.entities.Feed>" 
             description="The list of Feed objects to display"%>
<%@attribute name="pageSize" required="false" description="The page size, default is 10"%>
<%@attribute name="page" required="false" description="The page to display, default is 1, i.e first page"%>
<%@attribute name="nextPage" required="false" type="java.lang.String" 
             description="The next page beginning with a forward slash e.g /servletName or /jspPage.jsp"%>

<%@attribute name="displayPageNav" required="false"%>
<%@attribute name="displayPageLinks" required="false"%>

<c:if test="${displayPageNav == 'true'}">
    <idisc:displaypagenav nextPage="${nextPage}" page="${page}" pageSize="${pageSize}" totalSize="${fn:length(feeds)}"/>
</c:if>

<table>
    <idisc:displayrows items="${feeds}" page="${page}" pageSize="${pageSize}">

        <jsp:attribute name="rowFragment" trim="true">
    <%--@currentRowItem Exported request scope variable from displayrows tag--%>    
            <c:set var="feed" value="${currentRowItem}" scope="page"/>
            <jsp:useBean id="feed" class="com.idisc.pu.entities.Feed"/>
            
            <tr class="content_sub" style="margin:0.5em; padding:0.5em; vertical-align:top;">
                <td style="margin:0.5em; padding:0.5em;">
                    <c:set var="placeholderUrl" value="${pageContext.servletContext.contextPath}/images/placeholder.png"/>
                    <c:set var="siteiconUrl" value="${feed.siteid.iconurl}"/>
                    <c:set var="alternateIconUrl" value="${siteiconUrl != null || siteiconUrl != '' ? siteiconUrl : placeholderUrl}"/>
                    <c:set var="iconUrl" value="${feed.imageurl != null && feed.imageurl != '' ? feed.imageurl : alternateIconUrl}"/>
                    <img style="width:64px; height:64px" width="64" height="64" src="${iconUrl}"/>    
                </td>    
                <td style="margin:0.5em; padding:0.5em;">
    <%-- 
    Servlet 3.0 / EL 2.2 or newer (Tomcat 7 or newer), EL started to support invoking 
    methods with arguments directly. 
    In this case we repace all non-word characters
    --%>            
                    <c:set var="dfFeedTitle" value="${feed.title}"/>
                    <c:if test="${dfFeedTitle == null || '' == dfFeedTitle}">
                        <c:set var="dfFeedTitle" value="${feed.description}"/>
                    </c:if>
                    <c:if test="${dfFeedTitle == null || '' == dfFeedTitle}">
                        <c:set var="dfFeedTitle" value="${feed.content}"/>
                    </c:if>
                    <c:if test="${(dfFeedTitle != null && '' != dfFeedTitle) && fn:length(dfFeedTitle) > 128}">
                        <c:set var="dfFeedTitle" value="${fn:substring(dfFeedTitle, 0, 128)}..."/>        
                    </c:if>
                    <c:set var="dfUrlTitle" value="${dfFeedTitle.replaceAll('[^a-zA-Z0-9]', '_')}"/>
                    <c:url var="dfFeedUrl" context="${pageContext.servletContext.contextPath}" 
                           value="/feed/${feed.feedid}_${dfUrlTitle}.jsp"/>
                    <a href="${dfFeedUrl}">${dfFeedTitle}</a> 
                    <br/>
                    <span class="smallerLighter">
                        <c:choose>
                            <c:when test="${feed.feeddate == null || feed.feeddate.time < 86400000}">
                                By ${feed.siteid.site} on <idisc:displaydate date="${feed.datecreated}" displayAsTimeElapsed="true"/> 
                            </c:when>
                            <c:otherwise>
                                By ${feed.siteid.site} on <idisc:displaydate date="${feed.feeddate}" displayAsTimeElapsed="true"/> 
                            </c:otherwise>
                        </c:choose>
                    </span>
                </td>  
            </tr>
        </jsp:attribute>

        <jsp:body>
          <jsp:doBody/>  
        </jsp:body> 

    </idisc:displayrows>
</table>    

<c:if test="${displayPageLinks == 'true'}">
    <idisc:displaypagelinks nextPage="${nextPage}" page="${page}" pageSize="${pageSize}" totalSize="${fn:length(feeds)}"/>
</c:if>
