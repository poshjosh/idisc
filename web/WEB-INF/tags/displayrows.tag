<%-- 
    Document   : displayrows
    Created on : Apr 12, 2016, 10:53:10 AM
    Author     : poshjosh
--%>
<%@tag description="displays a list of items, in batches" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@attribute name="items" required="true" type="java.util.Collection" 
             description="The list of items to display"%>
<%@attribute name="pageSize" required="false" description="The page size, default is 10"%>
<%@attribute name="page" required="false" description="The page to display, default is 1, i.e first page"%>
<%@attribute name="nextPage" required="false" type="java.lang.String" 
             description="The next page beginning with a forward slash e.g /webappContext/servletName or /jspPage.jsp"%>

<%@attribute name="rowFragment" fragment="true" required="false"%>

<%-- Init pageSize --%>
<c:set var="pageSize" value="${pageSize == null || pageSize == '' ? param.pageSize : pageSize}"/>
<c:if test="${pageSize == null || pageSize == ''}"><c:set var="pageSize" value="10"/></c:if>

<%-- Init page --%>
<c:set var="page" value="${page == null || page == '' ? param.page : page}"/>
<c:if test="${page == null || page == ''}"><c:set var="page" value="1"/></c:if>

<%-- Init nextPage --%>
<c:set var="nextPage" value="${nextPage == null || nextPage == '' ? param.nextPage : nextPage}"/>
<c:if test="${nextPage == null || nextPage == ''}">
    <c:set var="nextPage" value=""/>
</c:if>
    
<c:set var="totalSize" value="${fn:length(items)}"/>    
<c:set var="pages" value="${totalSize / pageSize}"/>
<c:if test="${(totalSize % pageSize) > 0}">
    <c:set var="pages" value="${pages + 1}"/>
</c:if>
<c:set var="offset" value="${pageSize * (page -1)}"/>
<c:set var="end" value="${offset + pageSize}"/>
<c:if test="${end > totalSize}"><c:set var="end" value="${totalSize}"/></c:if>

<c:if test="${!App.productionMode}">
  <p>Total size: ${totalSize}. Pages: ${pages}. Page: ${page}. Offset: ${offset}. End: ${end}</p>
</c:if>

<p class="content_sub">
    <c:if test="${page > 1}"><a href="${pageContext.servletContext.contextPath}${nextPage}?page=${page - 1}">&lt;</a>&emsp;</c:if>
    <tt>${offset + 1} - ${end} of ${totalSize} results <c:if test="${query != null && query != ''}"> for</tt> <b>${query}</b></c:if>
    <c:if test="${page < pages}">&emsp;<a href="${pageContext.servletContext.contextPath}${nextPage}?page=${page + 1}">&gt;</a></c:if>
</p>

<c:forEach begin="${offset}" end="${end - 1}" var="item" items="${items}">

    <%--@currentRowItem Export this variable to request scope for use by fragments below --%>    
    <c:set var="currentRowItem" value="${item}" scope="request"/>  

    <c:choose>
      <c:when test="${rowFragment == null}">
        ${item}   
      </c:when>  
      <c:otherwise>
        <jsp:invoke fragment="rowFragment"/> 
      </c:otherwise>
    </c:choose>  
</c:forEach>    
<%--c:forEach varStatus="vs" begin="0" end="${pages - 1}">
    <a href="${pageContext.servletContext.contextPath}${nextPage}?page=${vs.index + 1}">${vs.index + 1}</a>&emsp;
</c:forEach--%>    
