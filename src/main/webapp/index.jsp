<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} Service - Index Page" 
    pageKeywords="${appName}, index page" 
    pageDescription="${appName} Service - Index Page">
    <jsp:attribute name="pageContent" trim="true">
        <h3>${appName} Service - Index Page</h3>
        <ul>
            <li><a href="${pageContext.servletContext.contextPath}/feeds.jsp">Latest News Feed</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/login.jsp">Login</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/usage.jsp">Usage Instruction</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/whynewsminute.jsp">Reasons to use ${appName}</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/testimonials.jsp">Testimonials</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/privacypolicy.jsp">Privacy Policy</a></li>    
        </ul>
    </jsp:attribute>
</idisc:page_with_slider>
