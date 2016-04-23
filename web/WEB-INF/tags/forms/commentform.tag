<%-- 
    Document   : commentform
    Created on : Apr 12, 2016, 2:54:03 PM
    Author     : posh
--%>

<%@tag description="Form for uploading comments" pageEncoding="UTF-8"%>

<%@attribute name="cfFeedid" type="java.lang.Integer" required="true"%>
<%@attribute name="cfRepliedto" type="java.lang.Integer" required="false"%>

<form id="commentFormId" method="post" action="${pageContext.servletContext.contextPath}/newcomment">
    <p><label>Subject: <input size="50" type="text" name="commentSubject"/></label></p>
    <p>
        <label>&emsp;&nbsp;&nbsp;Text: <textarea cols="38" rows="5" name="commentText"></textarea></label>
    </p>    
    <input type="hidden" name="feedid" value="${feedid == null ? param.feedid : feedid}"/>
    <input type="hidden" name="repliedto" value="${repliedto == null ? param.repliedto : repliedto}"/>
    <input type="submit" value="Submit"/>
</form> 
