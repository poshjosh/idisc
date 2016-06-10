<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} - Search Results" 
    pageKeywords="${query}, search results, news,latest news,breaking news,extracted news,various sources" 
    pageDescription="Search results for ${query}">
    <jsp:attribute name="pageContent" trim="true">
        <c:choose>
            <c:when test="${searchresults == null || empty searchresults}">
                <p>0 search results <c:if test="${query != null && query != ''}"> for <tt>${query}</tt></c:if></p>
            </c:when>    
            <c:otherwise>
                <idisc:displayfeeds displayPageNav="true" displayPageLinks="true" 
                                    feeds="${searchresults}" page="${page}" nextPage="/searchresults.jsp"/>
            </c:otherwise>
        </c:choose>        
    </jsp:attribute>
</idisc:page_with_slider>
