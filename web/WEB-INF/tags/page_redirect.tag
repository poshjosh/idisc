<%@tag description="a web page which simply redirects the user to another" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@attribute name="context" required="false"%>
<%@attribute name="url" required="true"%>

<c:if test="${context == null || context == ''}">
    <c:set var="context" value="${pageContext.servletContext.contextPath}"/>  
</c:if>

<c:redirect context="${context}" url="${url}"/>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>${appName} - Redirecting to ${url}</title>
        <meta name="keywords" content="news,latest news,breaking news,extracted news,various sources"/>
        <meta name="description" content="Latest breaking news feeds from various sources"/>
    </head>
    <body class="content">
        <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
        <h3>... Please wait redirecting</h3>    
        <p>
            If redirection does not complete in a few seconds click 
            <a href="${context}${url}">here</a>
        </p>
    </body>
</html>
