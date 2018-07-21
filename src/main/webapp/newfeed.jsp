<%-- 
    Document   : newfeed
    Created on : Apr 26, 2016, 1:59:31 PM
    Author     : poshjosh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<c:set var="nfInputSize" value="${mobile?20:35}"/>

<idisc:page_with_slider 
    pageTitle="${appName} - Upload News Story" 
    pageKeywords="upload news story" 
    pageDescription="${appName} - Upload News Story">
    <jsp:attribute name="pageContent" trim="true">
        <h3>Post a News Story</h3>
        <jsp:useBean id="now" class="java.util.Date"/>
<%-- author, url, feeddate, content, title, description, keywords, categories, imageurl --%>
        <form id="feedFormId" method="post" action="${pageContext.servletContext.contextPath}/newfeed">
            <b>Mandatory Fields:</b>
            <c:if test="${user == null}">
                <p><label>Email:<br/><input size="${nfInputSize}" type="text" name="emailaddress"/></label></p>
                <p><label>Password:<br/><input size="${nfInputSize}" type="password" name="password"/></label></p>    
            </c:if>
            <p><label>Author:<br/><input size="${nfInputSize}" type="text" name="author" value="Chinomso Ikwuagwu"/></label></p>
            <p><label>Link:<br/><input size="${nfInputSize}" type="text" name="url"/></label></p>
            <p><label>Feed date <small class="smallerLighter">(E.g: 2016 May 23 12:30:45)</small>: 
               <br/><input size="${nfInputSize}" type="text" name="feeddate" value="${now}"/></label></p>
            <p>
                <label>Content: <textarea cols="${nfInputSize}" rows="5" name="content"></textarea></label>
            </p>    
            <br/>
            <b>Non-mandatory Fields:</b>
            <p><label>Title:<br/><input size="${nfInputSize}" type="text" name="title"/></label></p>
            <p><label>Description:<br/><input size="${nfInputSize}" type="text" name="description"/></label></p>
            <p><label>Keywords:<br/><input size="${nfInputSize}" type="text" name="keywords"/></label></p>
            <p><label>Categories:<br/><input size="${nfInputSize}" type="text" name="categories"/></label></p>
            <p><label>Image link:<br/><input size="${nfInputSize}" type="text" name="imageurl"/></label></p>

            <p><label>Hit count:<br/><input size="${nfInputSize}" type="text" name="hitcount" value="30"/></label></p>
            
            <input type="hidden" name="format" value="text/html"/>
            <input type="submit" value="Send"/>
        </form> 
    </jsp:attribute> 
</idisc:page_with_slider>
