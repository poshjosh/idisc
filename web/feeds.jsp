<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:redirect context="${pageContext.servletContext.contextPath}" url="/feedspage.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>${appName} News Feeds</title>
        <meta name="keywords" content="news,latest news,breaking news,extracted news,various sources"/>
        <meta name="description" content="Latest breaking news feeds from various sources"/>
    </head>
    <body class="content">
        <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
        <h3>... Please wait redirecting</h3>    
        <p>If redirection does not complete in 7 seconds click <a href="${pageContext.servletContext.contextPath}/feedspage.jsp">here</a></p>
    </body>
</html>
