<%-- 
    Document   : page_with_slider
    Created on : Apr 12, 2016, 2:17:14 PM
    Author     : poshjosh
--%>
<%@tag description="A page with a slider" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<%@attribute name="pageTitle" required="true"%>
<%@attribute name="pageKeywords" required="true"%>
<%@attribute name="pageDescription" required="true"%>
<%@attribute name="pageContent" required="true" fragment="true"%>
<%@attribute name="pageHeadInclude" required="false" fragment="true"%>
<%@attribute name="pageAfterBodyInclude" required="false" fragment="true"%>

<c:set var="sliderDir" value="${pageContext.servletContext.contextPath}/slider"/>

<idisc:page_without_slider 
    pageTitle="${pageTitle}" 
    pageKeywords="${pageKeywords}" 
    pageDescription="${pageDescription}">
    
    <jsp:attribute name="pageHeadInclude" trim="true">
        <link rel="stylesheet" type="text/css" href="${sliderDir}/engine1/style.css" />
        <jsp:invoke fragment="pageHeadInclude"/>
    </jsp:attribute>

    <jsp:attribute name="pageContent" trim="true">
        
        <jsp:invoke fragment="pageContent"/>
    
    </jsp:attribute>
    
    <jsp:attribute name="pageAfterBodyInclude" trim="true">
        <jsp:invoke fragment="pageAfterBodyInclude"/>
        <script type="text/javascript" src="${sliderDir}/engine1/jquery.js"></script>
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>  
    </jsp:body> 
    
</idisc:page_without_slider>
