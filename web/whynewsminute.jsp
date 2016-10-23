<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="Why ${appName} is Trending Now" 
    pageKeywords="why ${appName}, reason to get ${appName}" 
    pageDescription="Some very good reasons to get the ${appName} app">
    <jsp:attribute name="pageContent" trim="true">
        <p>
            <a href="${appUrl}" class="button button_prompt">Get ${appName}</a>
            or <b><a href="${appUrl}">Learn more</a></b>
        </p>
        <%@include file="/WEB-INF/jspf/whynewsminute.jspf"%>
        <p>
            <a href="${appUrl}" class="button button_prompt">Get ${appName}</a>
            or <b><a href="${appUrl}">Learn more</a></b>
        </p>
    </jsp:attribute>
</idisc:page_with_slider>
