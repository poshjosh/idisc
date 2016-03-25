<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
        <title>${appName} Home Page</title>
        <meta name="keywords" content="news,latest news,breaking news,extracted news,various sources"/>
        <meta name="description" content="Latest breaking news feeds from various sources"/>
	<link rel="stylesheet" type="text/css" href="engine1/style.css" />
	<script type="text/javascript" src="engine1/jquery.js"></script>
        <style type="text/css">
            .slideshow_pagestyle{ background-color:#d7d7d7; width:640px; margin:auto; }
        </style>                
    </head>
    <body class="content slideshow_pagestyle">
        <c:set var="url_premium" value="https://play.google.com/store/apps/details?id=com.looseboxes.idisc.newsminuteplus"/>
        <div style="text-align:center">
            <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
            <%@include file="/WEB-INF/jspf/applink.jspf"%>
        </div>
	<div id="wowslider-container1">
            <div class="ws_images"><ul>
                    <li><a href="${url_premium}" target="_blank"><img src="data1/images/banner_headlines.png" alt="" title="" id="wows1_0"/></a></li>
                    <li><a href="${url_premium}" target="_blank"><img src="data1/images/banner_social.png" alt="" title="" id="wows1_1"/></a></li>
                    <li><a href="${url_premium}" target="_blank"><img src="data1/images/banner_sports.png" alt="" title="" id="wows1_2"/></a></li>
                    <li><a href="${url_premium}" target="_blank"><img src="data1/images/banner_notification.png" alt="" title="" id="wows1_3"/></a></li>
                    <li><a href="${url_premium}" target="_blank"><img src="data1/images/banner_contribute.png" alt="" title="" id="wows1_4"/></a></li>
            </ul></div>
            <div class="ws_bullets">
                <div>
                    <a href="#" title=""><span><img src="data1/tooltips/banner_headlines.png" alt=""/>1</span></a>
                    <a href="#" title=""><span><img src="data1/tooltips/banner_social.png" alt=""/>2</span></a>
                    <a href="#" title=""><span><img src="data1/tooltips/banner_sports.png" alt=""/>3</span></a>
                    <a href="#" title=""><span><img src="data1/tooltips/banner_notification.png" alt=""/>4</span></a>
                    <a href="#" title=""><span><img src="data1/tooltips/banner_contribute.png" alt=""/>5</span></a>
                </div>
            </div>
            <div class="ws_shadow"></div>
	</div>	
	<script type="text/javascript" src="engine1/wowslider.js"></script>
	<script type="text/javascript" src="engine1/script.js"></script>
        <div style="text-align:center">
            <%@include file="/WEB-INF/jspf/applink.jspf"%>
        </div>
    </body>
</html>
