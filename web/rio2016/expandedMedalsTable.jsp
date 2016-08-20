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
            .table-expand{visibility: visible; }
        </style>    
        <script type="text/javascript">
            function toggleVisibility() {
                var tags = document.getElementsByClassName('table-expand');
                var total = tags.length;
                var newtext;
                for(var i=0; i<total; i++) {
                    var newvis;
                    var tag = tags[i];
                    if(tag.style.visibility === 'collapse') {
                        newvis = 'visible';
                        newtext = 'Less';
                    }else{
                        newvis = 'collapse';
                        newtext = 'More';
                    }
                    tag.style.visibility = newvis;
                }
                var btn = document.getElementById('expandTableButton');
                btn.value = newtext;
                btn.innerHTML = newtext;
            }
        </script>    
    </jsp:attribute>    
    <jsp:attribute name="pageContent" trim="true">
        <button id="expandTableButton" onclick="toggleVisibility(); return true;" style="padding:0.5em;">Less</button>
        <small>
            ${Rio2016Bean.medalsTableHtml}
        </small>
    </jsp:attribute> 
</idisc:page_without_slider>
