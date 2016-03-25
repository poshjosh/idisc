<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>${appName} Service - Index Page</title>
    </head>
    <body class="content">
        <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
        <h3>${appName} Service - Index Page</h3>
        <ul>
            <li><a href="${pageContext.servletContext.contextPath}/feeds.jsp">Latest News Feed</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/usage.jsp">Usage instruction</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/whynewsminute.jsp">Reasons to use ${appName}</a></li>    
            <li><a href="${pageContext.servletContext.contextPath}/testimonials.jsp">Testimonials</a></li>    
        </ul>
    </body>
</html>
