<%-- 
    Sample URL: www.looseboxes.com/idisc?req=feeds
    req currently supports the following values:
    1. feeds: In which case twitter feeds, rss feeds, direct web feeds are returned
    Note: for each req parameter value (e.g feeds in this case) a corresponding
    Servlet helper (Feeds.class in this case) must exist.

    Created on : 14-Oct-2014, 16:10:57
    Author     : Josh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"  errorPage="/oops.jsp"%>
<!DOCTYPE html>
<html>
  <head>
        <%@include file="/WEB-INF/jspf/defaultHeadContents.jspf"%>
    <title>How to use the ${appName} Service</title>
  </head>
  <body class="content">
    <%@include file="/WEB-INF/jspf/topbanner.jspf"%>
    <table>
      <thead>
        <tr>
          <th>Parameter Name</th>
          <th>Description</th>
          <th>Possbile Values</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>req</td>
          <td>Names what is being requested from the Service</td>
          <td>feeds</td>
        </tr>
        <tr>
          <td>timeout</td>
          <td>How long to wait for the feeds to load before returning</td>
          <td>number (milliseconds)</td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
