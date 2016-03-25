<%-- 
    Document   : oops
    Created on : 01-Nov-2014, 16:08:54
    Author     : Josh
--%>
<%@page contentType="text/html" isErrorPage="true" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>${appName} Service - Error Page</title>
    </head>
    <body class="content">
        <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
        <p>The following error occured while processing the request:</p>
        <p>
            Requested resource: ${ pageContext.errorData.requestURI}
            <br/><br/>
            Response status code: ${ pageContext.errorData.statusCode}
            <br/><br/>
            Response message: ${ pageContext.errorData.throwable.message}
        </p>
    </body>
</html>
