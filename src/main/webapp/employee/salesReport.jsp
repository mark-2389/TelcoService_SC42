<%--
  Created by IntelliJ IDEA.
  User: Mark
  Date: 28/12/2021
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>SALES REPORT</title>
</head>
<body>
<h1><%= "SALES REPORT" %></h1>
<br/>
<a href="home.jsp" >
    Go back
</a>
<div class="Purchases">
    <h2>Purchases per package</h2>
    <%--@elvariable id="purchases" type="List<java.lang.String>"--%>
    <c:forEach var="p" items="${purchases}">
        <div>
            <c:out value="${p}"></c:out>
        </div>
    </c:forEach>
</div>
<h2>Purchases per package and Validity</h2>
<h2>Total value of sales per package without optional products</h2>
<h2>Total value of sales per package with optional products</h2>
<h2>Average number of optional products per service package</h2>

<div>
    <div>
        <h2>Best Optional product</h2>
            <% if ( request.getSession().getAttribute("best") == null ) { %>
                <c:out value="NO BEST PRODUCT"></c:out>
            <% } else { %>
                <%--@elvariable id="best" type="it.polimi.db2.telcoservice_sc42.views.BestOptionalProduct"--%>
                <c:out value="BEST: ${best.toString()}"></c:out>
            <% } %>
    </div>
</div>
</body>
</html>
