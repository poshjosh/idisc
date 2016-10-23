<%-- 
    Document   : oops
    Created on : 01-Nov-2014, 16:08:54
    Author     : poshjosh
--%>
<%@page contentType="text/html" isErrorPage="true" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} - Error Page" 
    pageKeywords="error page" 
    pageDescription="${appName} - Error Page">
    <jsp:attribute name="pageContent" trim="true">
        <p>The following error occurred while processing the request:</p>
        <p>
            Requested resource: ${pageContext.errorData.requestURI}
            <br/><br/>
            Response status code: ${ pageContext.errorData.statusCode}
            <br/><br/>
            Response message: ${ pageContext.errorData.throwable.message}
        </p>
    </jsp:attribute>
    <jsp:attribute name="pageAfterBodyInclude" trim="true">
        <c:if test="${AppContext != null && !AppContext.productionMode && 
                      pageContext.errorData.throwable.stackTrace != null}">
            <h3>App is being run in <tt>DEVELOPMENT MODE</tt> hence printing Stacktrace:</h3>  
            <span>
                <c:forEach var="elem" items="${pageContext.errorData.throwable.stackTrace}">
                    ${elem}<br/>
                </c:forEach>
            </span>
        </c:if>
    </jsp:attribute>    
</idisc:page_with_slider>
