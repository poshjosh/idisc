<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="${appName} - Login" 
    pageKeywords="login page" 
    pageDescription="${appName} - Login">
    <jsp:attribute name="pageContent" trim="true">
        <h3>Login</h3>
        <form id="loginFormId" method="post" action="${pageContext.servletContext.contextPath}/login">
            <p><label>&emsp;&nbsp;&nbsp;Email: <input size="50" type="text" name="emailaddress"/></label></p>
            <p><label>Password: <input size="50" type="password" name="password"/></label></p>    
            <input type="hidden" name="format" value="text/html"/>
            <input type="submit" value="Login"/>
        </form>
    </jsp:attribute>
</idisc:page_with_slider>
