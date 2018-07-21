<%-- 
    Sample URL: www.looseboxes.com/idisc?req=feeds&
    req currently supports the following values:
    1. feeds: In which case twitter feeds, rss feeds, direct web feeds are returned
    Note: for each req parameter value (e.g feeds in this case) a corresponding
    Servlet helper (Feeds.class in this case) must exist.

    Created on : 14-Oct-2014, 16:10:57
    Author     : Josh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"  errorPage="/oops.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider 
    pageTitle="How to use the ${appName} Service" 
    pageKeywords="how to use, usage" 
    pageDescription="How to use ${appName}">

    <jsp:attribute name="pageHeadInclude" trim="true">
        <link href="${contextURL}/resources/simpletable.css" rel="stylesheet" type="text/css"/>
    </jsp:attribute>    
        
    <jsp:attribute name="pageContent" trim="true">
        <h3>Usage</h3>
        <p>
          Sample query:<br/>?req=feeds&amp;format=application/json&amp;limit=50
        </p>
        <p>
          The following parameters may be used:
        </p>
        <div class="allow_horizontalscrolls">
    <%--
        Usage of table style:
        --------------------
        - Make sure you do this: <table cellspacing="0"...
        - To give alternating rows emphasis use class="even" for each alternating row
    --%>
            <table class="simpletable" cellspacing="0"> 
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Value</th>
                  <th>Description</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><tt>req</tt></td>
                  <td><tt>feeds</tt></td>
                  <td>Names what is being requested from the Service</td>
                </tr>
                <tr class="even">
                  <td><tt>timeout</tt></td>
                  <td>Number (milliseconds)</td>
                  <td>How long to wait for the news feeds to load before returning</td>
                </tr>
                <tr>
                  <td><tt>offset</tt></td>
                  <td>Number between 0 and 200</td>
                  <td>A subset of the total results starting at this offset will be returned</td>
                </tr>
                <tr class="even">
                  <td><tt>limit</tt></td>
                  <td>Number between 0 and 200 (inclusive)</td>
                  <td>The maximum amount of news feeds that may be returned</td>
                </tr>
                <tr>
                  <td><tt>after</tt></td>
                  <td>Date</td>
                  <td>Only news feeds after the specified 'after' date will be returned</td>
                </tr>
              </tbody>
            </table>
        </div>
    </jsp:attribute>
</idisc:page_with_slider>
