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

<%@attribute name="rowFragment" fragment="true" required="false"%>

<%-- Init pageSize --%>
<c:set var="pageSize" value="${pageSize == null || pageSize == '' ? param.pageSize : pageSize}"/>
<c:if test="${pageSize == null || pageSize == ''}"><c:set var="pageSize" value="10"/></c:if>

<%-- Init page --%>
<c:set var="page" value="${page == null || page == '' ? param.page : page}"/>
<c:if test="${page == null || page == ''}"><c:set var="page" value="1"/></c:if>

<c:set var="totalSize" value="${fn:length(items)}"/>    
<c:set var="offset" value="${pageSize * (page -1)}"/>
<c:set var="end" value="${offset + pageSize}"/>
<c:if test="${end > totalSize}"><c:set var="end" value="${totalSize}"/></c:if>

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

