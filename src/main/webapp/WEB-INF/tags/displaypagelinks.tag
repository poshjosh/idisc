<%-- 
    Document   : displaypagelinks
    Created on : Jun 3, 2016, 8:08:15 PM
    Author     : Josh
--%>
<%@tag description="displays links to the pages of a paginated list of items" pageEncoding="UTF-8"%>
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
<c:if test="${page == null || page == '' || page == 0 || page == '0'}"><c:set var="page" value="1"/></c:if>

<%-- Init nextPage --%>
<c:set var="nextPage" value="${nextPage == null || nextPage == '' ? param.nextPage : nextPage}"/>
<c:if test="${nextPage == null || nextPage == ''}">
    <c:set var="nextPage" value=""/>
</c:if>
    
<fmt:parseNumber var="pages" integerOnly="true" type="number" value="${totalSize / pageSize}"/>
<c:if test="${(totalSize % pageSize) > 0}">
    <c:set var="pages" value="${pages + 1}"/>
</c:if>

<c:if test="${pages > 1}">
    
    <c:set var="drPagesEnd" value="${pages - 1}"/>

    <c:set var="drExcessPagelinks" value="${drPagesEnd >= 9}"/>

    <c:set var="drPagelinksEnd" value="${!drExcessPagelinks ? drPagesEnd : 9}"/>

    <p class="content_sub">
        <small>
            <c:forEach varStatus="vs" begin="0" end="${drPagelinksEnd}">
                <c:set var="drPagelinkStyle" value="${vs.index + 1 == page ? 'font-size:1.5em; font-weight:900;' : 'font-size:1em;'}"/>
                <a style="${drPagelinkStyle}" href="${pageContext.servletContext.contextPath}${nextPage}?page=${vs.index + 1}">${vs.index + 1}</a>&emsp;
            </c:forEach> 
            <c:if test="${drExcessPagelinks}">
            ...<a href="${pageContext.servletContext.contextPath}${nextPage}?page=${drPagesEnd + 1}">${drPagesEnd + 1}</a>        
            </c:if>
        </small>    
    </p>
</c:if>
