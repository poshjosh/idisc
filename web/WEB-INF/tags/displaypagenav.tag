<%-- 
    Document   : displaypagenav
    Created on : Jun 3, 2016, 8:01:57 PM
    Author     : Josh
--%>
<%@tag description="displays a navigation for a list of items, in batches" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@attribute name="totalSize" required="true"%>
<%@attribute name="pageSize" required="false" description="The page size, default is 10"%>
<%@attribute name="page" required="false" description="The page to display, default is 1, i.e first page"%>
<%@attribute name="nextPage" required="false" type="java.lang.String" 
             description="The next page beginning with a forward slash e.g /webappContext/servletName or /jspPage.jsp"%>

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
    
<fmt:parseNumber var="pages" integerOnly="true" type="number" value="${totalSize / pageSize}"/>
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
