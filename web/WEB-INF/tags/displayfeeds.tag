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

<idisc:displayrows items="${feeds}" nextPage="${nextPage}" page="${page}" pageSize="${pageSize}">
    
    <jsp:attribute name="rowFragment" trim="true">
<%--@currentRowItem Exported request scope variable from displayrows tag--%>    
        <c:set var="feed" value="${currentRowItem}" scope="page"/>
        <p class="content_sub">
<%-- 
Servlet 3.0 / EL 2.2 or newer (Tomcat 7 or newer), EL started to support invoking 
methods with arguments directly. 
In this case we repace all non-word characters
--%>            
            <c:set var="dfUrlTitle" value="${feed.title.replaceAll('[^a-zA-Z0-9]', '_')}"/>
            <c:url var="dfFeedUrl" context="${pageContext.servletContext.contextPath}" 
                   value="/feed/${feed.feedid}_${dfUrlTitle}.jsp"/>
            <a href="${dfFeedUrl}">${feed.title}</a> 
            <br/>
            <span class="smallerLighter">
                By ${feed.siteid.site} on <idisc:displaydate date="${feed.feeddate}" displayAsTimeElapsed="true"/> 
            </span>
        </p>
    </jsp:attribute>
        
    <jsp:body>
      <jsp:doBody/>  
    </jsp:body> 
        
</idisc:displayrows>
