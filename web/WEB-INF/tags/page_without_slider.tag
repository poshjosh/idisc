<%-- 
    Document   : page_without_slider
    Created on : Apr 12, 2016, 2:17:14 PM
    Author     : poshjosh
--%>
<%@tag description="A page with a slider" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@attribute name="pageTitle" required="true"%>
<%@attribute name="pageKeywords" required="true"%>
<%@attribute name="pageDescription" required="true"%>
<%@attribute name="pageContent" required="true" fragment="true"%>
<%@attribute name="pageHeadInclude" required="false" fragment="true"%>
<%@attribute name="pageAfterBodyInclude" required="false" fragment="true"%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
    <title>${pageTitle}</title>
    <meta name="keywords" content="${pageKeywords}"/>
    <meta name="description" content="${pageDescription}"/>
    <jsp:invoke fragment="pageHeadInclude"/>
  </head>
  <body>
      
    <div id="main_container">
        
      <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
      
<%-- @include file="/WEB-INF/jspf/google_leaderboardad_script.jspf"--%>
      
      <div class="content">
        <jsp:invoke fragment="pageContent"/>
      </div>
      
      <%@include file="/WEB-INF/jspf/bottombanner.jspf"%>
      
    </div>
      
    <jsp:invoke fragment="pageAfterBodyInclude"/>
  </body>
</html>
