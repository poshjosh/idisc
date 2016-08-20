<%-- 
    Document   : rio2016medalsTable
    Created on : Aug 11, 2016, 11:47:46 PM
    Author     : Josh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<jsp:useBean id="Rio2016Bean" scope="application" class="com.idisc.web.beans.Rio2016Bean"/>
<jsp:setProperty name="Rio2016Bean" property="request" value="<%=request%>"/>
             
<idisc:page_without_slider   
    pageTitle="Rio 2016 Medals Table - ${appName}" 
    pageKeywords="rio2016,medals table" 
    pageDescription="Rio 2016 Medals Table">
    <jsp:attribute name="pageHeadInclude" trim="true">
        <style type="text/css">
            .medals{ background-color:turquoise; }
            .table-medal-countries__link-table{ background-color:aqua; }
        </style>    
    </jsp:attribute>
    <jsp:attribute name="pageContent" trim="true">
        <p><a href="${contextURL}/rio2016/expandedMedalsTable.jsp">View expanded Medals Table</a></p>
        <small>
            ${Rio2016Bean.medalsTableHtmlMinified}
        </small>
    </jsp:attribute> 
</idisc:page_without_slider>
