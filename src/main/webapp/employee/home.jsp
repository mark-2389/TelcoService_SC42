<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 23/12/21
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>HOME PAGE</title>
</head>
<body>
<h1><%= "HOME" %> </h1>
<br/>
<a href="../logout" >
    Logout
</a>
<br/>
<%
    if ( request.getSession().getAttribute("id") != null ) {
%>
<label>
    Logged in as: <%= request.getSession().getAttribute("id") %>
</label>
<%
    }
%>
<div class="packageForm">
    <h3> Service Package Creation </h3>
    <form>
        <div>
            <h5>Selected optional products</h5>
            <div class="form">
                <%--@declare id="package_name"--%><label for="package_name">Name:</label>
                <input type="text" name="package_name" required>
            </div>
            <div class="form">
                <%--@declare id="package_expiration_date"--%>
                <label for="package_expiration_date">Expiration date:</label>
                <input type="date" name="package_expiration_date"
                       value="<%= LocalDate.now().toString() %>" min="<%= LocalDate.now().toString() %>" required >
            </div>
            <%--@elvariable id="selected" type="List<it.polimi.db2.telcoservice_sc42.entities.OptionalProduct>"--%>
            <c:forEach var="e" items="${selected}">
                <p>${e.toString()}</p>
            </c:forEach>
            <div class="form">
                <input type="submit" class="btn" value="Create" name="createServicePackage">
            </div>
        </div>
        <h5>Available optional products</h5>
        <%--@elvariable id="optionals" type="List<it.polimi.db2.telcoservice_sc42.entities.OptionalProduct>"--%>
        <c:forEach var="e" items="${optionals}">
            <div>
                <a href="../add_optional_product?optional=${e.id}">${e.toString()}</a>
            </div>
        </c:forEach>
    </form>
</div>
<div class="optionalProductForm">
    <h3> Optional Product Creation </h3>
    <form action="../new_optional_product" method="POST">
        <div class="form">
            <%--@declare id="username"--%><label for="username">Name:</label>
            <input type="text" name="name" required>
        </div>
        <div class="form">
            <%--@declare id="monthly_fee"--%><label for="monthly_fee">Monthly fee:</label>
            <input type="number" step="0.01" name="monthly_fee" required>
        </div>
        <div class="form">
            <%--@declare id="expiration_date"--%><label for="expiration_date">Expiration date:</label>
            <input type="date" name="expiration_date" value="<%= LocalDate.now().toString() %>"
                   min="<%= LocalDate.now().toString() %>" required >
        </div>
        <div class="form">
            <input type="submit" class="btn" value="Create" name="createOptionalProduct">
        </div>
    </form>
    <div>
        <%
            String error = (String) request.getSession().getAttribute("error");
            if ( error != null ) {
        %>
        <p> <%= error %> </p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
