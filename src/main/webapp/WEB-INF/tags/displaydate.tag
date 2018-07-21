<%-- 
    Document   : displaydate
    Created on : Apr 12, 2016, 1:37:01 PM
    Author     : poshjosh
--%>
<%@tag description="Displays a date in the default format, or as time elapsed" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@attribute name="date" type="java.util.Date" required="true"%>
<%@attribute name="displayAsTimeElapsed" required="false"%>

<c:if test="${date != null}">
    <fmt:formatDate value="${date}" pattern="dd MMM yy HH:mm:ss" var="ddFormattedDate"/>${ddFormattedDate} 
</c:if>
