<%-- 
    Document   : newfeed
    Created on : Apr 26, 2016, 1:59:31 PM
    Author     : poshjosh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_without_slider 
    pageTitle="${appName} - Upload News Story" 
    pageKeywords="upload news story" 
    pageDescription="${appName} - Upload News Story">
    <jsp:attribute name="pageContent" trim="true">
        <h3>Post a News Story</h3>
        <jsp:useBean id="now" class="java.util.Date"/>
<%-- author, url, feeddate, content, title, description, keywords, categories, imageurl --%>
        <form id="feedFormId" method="post" action="${pageContext.servletContext.contextPath}/newfeed">
            <b>Mandatory Fields:</b>
            <p><label>Author: <input size="50" type="text" name="author" value="Chinomso Ikwuagwu"/></label></p>
            <p><label>Link: <input size="50" type="text" name="url"/></label></p>
            <p><label>Feed date <span class="smallerLighter">(E.g: 2016 May 23 12:30:45)</span>: 
               <input size="50" type="text" name="feeddate" value="${now}"/></label></p>
            <p>
                <label>Content: <textarea cols="38" rows="5" name="content"></textarea></label>
            </p>    
            <br/>
            <b>Non-mandatory Fields:</b>
            <p><label>Title: <input size="50" type="text" name="title"/></label></p>
            <p><label>Description: <input size="50" type="text" name="description"/></label></p>
            <p><label>Keywords: <input size="50" type="text" name="keywords"/></label></p>
            <p><label>Categories: <input size="50" type="text" name="categories"/></label></p>
            <p><label>Image link: <input size="50" type="text" name="imageurl"/></label></p>

            <p><label>Hit count: <input size="50" type="text" name="hitcount" value="30"/></label></p>
            
            <input type="hidden" name="format" value="text/html"/>
            <input type="submit" value="Send"/>
        </form> 
    </jsp:attribute> 
</idisc:page_without_slider>
