<%-- 
    Document   : commentform
    Created on : Apr 12, 2016, 2:54:03 PM
    Author     : posh
--%>

<%@tag description="Form for uploading comments" pageEncoding="UTF-8"%>

<%@attribute name="cfFeedid" type="java.lang.Integer" required="true"%>
<%@attribute name="cfRepliedto" type="java.lang.Integer" required="false"%>

<c:set var="cfInputSize" value="${mobile?28:46}"/>

<form id="commentFormId" method="post" action="${pageContext.servletContext.contextPath}/newcomment">
    <p>
        <label>Subject<br/>
            <input type="text" name="commentSubject"/>
        </label>
    </p>
    <p>
        <label>Text<br/>
            <textarea style="width:16em;" rows="5" name="commentText"></textarea>
        </label>
    </p>    
    <input type="hidden" name="format" value="text/html"/>
    <input type="hidden" name="feedid" value="${feedid == null ? param.feedid : feedid}"/>
    <input type="hidden" name="repliedto" value="${repliedto == null ? param.repliedto : repliedto}"/>
    <input type="submit" value="Submit"/>
</form> 
