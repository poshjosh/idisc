<%-- 
    Document   : displaycomments
    Created on : Apr 9, 2016, 7:37:28 PM
    Author     : poshjosh
--%>
<%@tag description="displays a list of comments, in batches" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<%@attribute name="comments" required="true" type="java.util.List<com.idisc.pu.entities.Comment>" 
             description="The list of Comment objects to display"%>
<%@attribute name="pageSize" required="false" description="The page size, default is 10"%>
<%@attribute name="page" required="false" description="The page to display, default is 1, i.e first page"%>
<%@attribute name="nextPage" required="false" type="java.lang.String" 
             description="The next page beginning with a forward slash e.g /servletName or /jspPage.jsp"%>

<%@attribute name="displayPageNav" required="false"%>
<%@attribute name="displayPageLinks" required="false"%>

<c:if test="${displayPageNav == 'true'}">
    <idisc:displaypagenav nextPage="${nextPage}" page="${page}" pageSize="${pageSize}" totalSize="${fn:length(comments)}"/>
</c:if>

<idisc:displayrows items="${comments}" page="${page}" pageSize="${pageSize}">
    
    <jsp:attribute name="rowFragment" trim="true">
<%--@currentRowItem Exported request scope variable from displayrows tag--%>    
        <c:set var="comment" value="${currentRowItem}" scope="page"/>
        <jsp:useBean id="comment" class="com.idisc.pu.entities.Comment" scope="page"/>
        <p class="content_sub">
            <c:if test="${comment.commentSubject != null && comment.commentSubject != ''}">
                <b>${comment.commentSubject}</b><br/>
            </c:if>
            ${comment.commentText}
            <br/>
            <span class="smallerLighter">
                By ${comment.installationid.screenname == null ? 'annonymous' : comment.installationid.screenname} on
                <idisc:displaydate date="${comment.datecreated}" displayAsTimeElapsed="true"/>
                
                <c:url context="${pageContext.servletContext.contextPath}" 
                       value="/newcomment.jsp" var="dcNewcommentUrl">
                    <c:param name="feedid" value="${comment.feedid.feedid}"/>    
                    <c:param name="repliedto" value="${comment.commentid}"/>    
                </c:url>
                <a style="float:right" href="${dcNewcommentUrl}">Reply</a>
            </span>
        </p>
    </jsp:attribute>
        
    <jsp:body>
      <jsp:doBody/>  
    </jsp:body> 
        
</idisc:displayrows>

<c:if test="${displayPageLinks == 'true'}">
    <idisc:displaypagelinks nextPage="${nextPage}" page="${page}" pageSize="${pageSize}" totalSize="${fn:length(comments)}"/>
</c:if>
        
 