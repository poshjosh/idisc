<%-- 
    Document   : newcomment
    Created on : Apr 12, 2016, 1:59:31 PM
    Author     : poshjosh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_without_slider 
    pageTitle="${appName} - Upload New Comment" 
    pageKeywords="upload new comment" 
    pageDescription="${appName} - Upload New Comment">
    <jsp:attribute name="pageContent" trim="true">
        <h3>Post a Comment</h3>
        <idisc:commentform 
            cfFeedid="${feedid == null ? param.feedid : feedid}" 
            cfRepliedto="${repliedto == null ? param.repliedto : repliedto}"/>
    </jsp:attribute> 
</idisc:page_without_slider>
