<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_without_slider 
    pageTitle="Why ${appName} is Trending Now" 
    pageKeywords="why ${appName}, reason to get ${appName}" 
    pageDescription="Some very good reasons to get the ${appName} app">
    <jsp:attribute name="pageContent" trim="true">
        <%@include file="/WEB-INF/jspf/applink.jspf"%>
        <%@include file="/WEB-INF/jspf/whynewsminute.jspf"%>
        <%@include file="/WEB-INF/jspf/applink.jspf"%>
    </jsp:attribute>
</idisc:page_without_slider>
