<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 23/12/21
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.entities.ServiceType" %>
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
    <form action="../add_optional_product" method="POST">
        <div>

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
            <h5>Validity Period</h5>
            <form action="">
                <div class="form">
                    <label for="validity_period">Period:</label>
                    <input type="number" step="1" id="validity_period" name="validity_period" required>
                </div>
                <div class="form">
                    <label for="validity_monthly_fee">Monthly fee:</label>
                    <input type="number" step="0.01" id="validity_monthly_fee" name="validity_monthly_fee" required>
                </div>
                <div class="form">
                    <input type="submit" class="btn" value="Add Validity Period" name="addValidities">
                </div>
            </form>

            <h5>Selected Services</h5>
            <h5>Selected optional products</h5>
            <!--
                <div>
                    <input type="radio" id="expiration_date" name="expiration_date" value="nil" checked>
                    <label for="huey">No expiration date</label>
                </div>

                <div>
                  <input type="radio" id="expiration_date" name="expiration_date" value="set">
                  <label for="dewey">Expiration date</label>
                  <input type="date" name="package_expiration_date">
                </div>
            -->
            <%--@elvariable id="optionals" type="List<it.polimi.db2.telcoservice_sc42.entities.OptionalProduct>"--%>
            <c:forEach var="e" items="${optionals}">
                <div>
                    <input type="checkbox" id="selected" name="selected" value="${e.id}" >
                    <label for="${e.id}">${e.toString()}</label>
                </div>
            </c:forEach>
            <div class="form">
                <input type="submit" class="btn" value="Create Service Package" name="createServicePackage">
            </div>
            <!-- <div class="form"> -->
            <!--     <input type="submit" class="btn" value="Create" name="createServicePackage"> -->
            <!-- </div> -->
        </div>
    </form>
</div>
<div class="optionalProductForm">
    <h3> Optional Product Creation </h3>
    <form action="../new" method="POST">
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
<div class="serviceForm">
    <h3> Service Creation </h3>
    <form action="../new" method="POST"> <!-- TODO: also consider new?service=1 -->
        <div class="form">
            <label for="types">Service type:</label>
            <input list="types" name="types" required>
            <datalist id="types">
                <% ServiceType[] types = ServiceType.values(); %>
                <c:forEach var="t" items="<%= types %>">
                    <option value="${t.toString()}">
                </c:forEach>
            </datalist>
        </div>
        <div class="form">
            <label for="expiration_date">Expiration date:</label>
            <input type="date" name="expiration_date" value="<%= LocalDate.now().toString() %>"
                   min="<%= LocalDate.now().toString() %>" required >
        </div>
        <div class="form">
            <label for="gb_fee">Gigabytes fee:</label>
            <input type="number" step="0.01" id="gb_fee" name="gb_fee" value="0.00" required >
        </div>
        <div class="form">
            <label for="gbs">Gigabytes:</label>
            <input type="number" step="1" id="gbs" name="gbs" value="0" required >
        </div>
        <div class="form">
            <label for="sms_fee">Sms fee:</label>
            <input type="number" step="0.01" id="sms_fee" name="sms_fee" value="0.00" required >
        </div>
        <div class="form">
            <label for="sms">Sms:</label>
            <input type="number" step="1" id="sms" name="sms" value="0" required >
        </div>
        <div class="form">
            <label for="call_fee">Call fee:</label>
            <input type="number" step="0.01" id="call_fee" name="call_fee" value="0.00" required >
        </div>
        <div class="form">
            <label for="minutes">Minutes:</label>
            <input type="number" step="1" id="minutes" name="minutes" value="0" required >
        </div>

        <div class="form">
            <input type="submit" class="btn" value="Create" name="createService">
        </div>
    </form>
    <div>
        <%
            error = (String) request.getSession().getAttribute("error");
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
