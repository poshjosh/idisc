<%-- 
    Document   : editfeed
    Created on : Oct 12, 2016, 11:25:25 AM
    Author     : Josh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} - Generate Additional Feedhits" 
    pageKeywords="Generate Additional Feedhits" 
    pageDescription="${appName} - Generate Additional Feedhits">
    
    <jsp:attribute name="pageContent" trim="true">
        
        <form id="feedFormId" method="post" action="${pageContext.servletContext.contextPath}/generatefeedhit">
            
            <p><label>&nbsp;Feed ID: <input size="9" maxlength="9" type="text" name="feedid"/></label></p>

            <p><label>Hit count: <input size="9" maxlength="3" type="text" name="hitcount" value="30"/></label></p>
            
            <input type="hidden" name="installationkey" value="652400AA4DB0E9F8297C10C0BF9B4BBF97ADD7A1"/>
            <input type="hidden" name="format" value="text/html"/>
            
            <input type="submit" value="Send"/>
        </form> 
            
    </jsp:attribute> 
</idisc:page_with_slider>
