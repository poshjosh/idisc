<%-- 
    Document   : socialshare
    Created on : Apr 11, 2016, 10:07:05 PM
    Author     : poshjosh
--%>
<%@tag trimDirectiveWhitespaces="true" description="holds icons for sharing on fb, twitter etc" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@attribute name="ssFeed" required="true" type="com.idisc.pu.entities.Feed"%>

<c:url value="http://www.facebook.com/sharer.php" var="fbShareUrl">
  <c:param name="u" value="${contextURL}/feed/${ssFeed.feedid}.jsp"/>    
</c:url>
<a title="share this item on facebook" class="borderless" 
  href="${fbShareUrl}" rel="nofollow" target="_blank">
  <img style="width:2em; height:2em;" alt="facebook" src="${contextURL}/images/facebook.png"/>
</a>

<c:url value="http://twitter.com/home" var="twtShareUrl">
    <c:choose>
        <c:when test="${twitterUsername != null}">
            <c:param name="status" value="@${twitterUsername} ${ssFeed.title}"/>    
        </c:when>
        <c:otherwise>
            <c:param name="status" value="${ssFeed.title}"/>    
        </c:otherwise>
    </c:choose>  
</c:url>
<a title="tweet this item" class="borderless" 
  href="${twtShareUrl}" rel="nofollow" target="_blank">
  <img style="width:2em; height:2em;" alt="twitter" src="${contextURL}/images/twitter.png"/>
</a>
