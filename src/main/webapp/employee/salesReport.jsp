<%@ page import="it.polimi.db2.telcoservice_sc42.utils.ParameterRegistry" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.utils.EmployeeSessionRegistry" %>
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
    <fieldset style="width:500px">

        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.allPurchasesPerPackage) == null ) { %>
            <c:out value="NO PURCHASES TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="purchases" type="List<java.lang.String>"--%>
            <c:forEach var="p" items="${purchases}">
                <div>
                    <c:out value="${p}"> </c:out>
                </div>
            </c:forEach>
        <% } %>

    </fieldset>
</div>
<div class="PurchasesWithValidity">
    <h2>Purchases per package and Validity</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.allPurchasesPerPackageValidity) == null ) { %>
            <c:out value="NO PURCHASES PER PACKAGE AND VALIDITY TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="purchasesValidity" type="List<java.lang.String>"--%>
            <c:forEach var="p" items="${purchasesValidity}">
                <div>
                    <c:out value="${p}"> </c:out>
                </div>
            </c:forEach>
        <% } %>
    </fieldset>
</div>
<div class="ValuesWithoutOp">
    <h2>Total value of sales per package without optional products</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.allValuesPerPackageWithoutOp) == null ) { %>
            <c:out value="NO SALES PER PACKAGE TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="values" type="List<java.lang.String>"--%>
            <c:forEach var="v" items="${values}">
                <div>
                    <c:out value="${v}"> </c:out>
                </div>
            </c:forEach>
        <% } %>
    </fieldset>
</div>
<div class="ValuesWithOp">
    <h2>Total value of sales per package with optional products</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.allValuesPerPackageOptionalProduct) == null ) { %>
            <c:out value="NO SALES PER PACKAGE WITH OPTIONAL PRODUCT TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="valuesPackageOptional" type="List<java.lang.String>"--%>
            <c:forEach var="v" items="${valuesPackageOptional}">
                <div>
                    <c:out value="${v}"> </c:out>
                </div>
            </c:forEach>
        <% } %>
    </fieldset>
</div>
<div class="Averages">
    <h2>Average number of optional products per service package</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.allAverageOptionalProduct) == null ) { %>
            <c:out value="NO AVERAGES PER PACKAGE TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="averages" type="List<java.lang.String>"--%>
            <c:forEach var="a" items="${averages}">
                <div>
                    <c:out value="${a}"> </c:out>
                </div>
            </c:forEach>
        <% } %>
    </fieldset>
</div>
<div class="BestOptional">
    <h2>Best Optional product</h2>
        <fieldset style="width:500px" >
            <% if ( request.getSession().getAttribute("best") == null ) { %>
                <c:out value="NO BEST PRODUCT"> </c:out>
            <% } else { %>
                <%--@elvariable id="best" type="java.lang.String"--%>
                <c:out value="BEST: ${best}"> </c:out>
            <% } %>
        </fieldset>
</div>
<div class="InsolventClients">
    <h2>Insolvent Clients</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.insolventUsers) == null ) { %>
            <c:out value="NO INSOLVENT USERS TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="it.polimi.db2.telcoservice_SC42.EmployeeSessionRegistry.allPurcheasesPerPackageValidity" type="List<java.lang.String>"--%>
            <c:forEach var="i" items="${insolvents}">
                <div>
                    <c:out value="${i}"> </c:out>
                </div>
            </c:forEach>
        <% } %>
    </fieldset>
</div>
<div class="SuspendedOrders">
    <h2>Suspended Orders</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.suspendedOrders) == null ) { %>
            <c:out value="NO SUSPENDED ORDERS TO BE SHOWN"> </c:out>
        <% } else { %>
        <%--@elvariable id="orders" type="List<java.lang.String>"--%>
        <c:forEach var="o" items="${orders}">
            <div>
                <c:out value="${o}"> </c:out>
            </div>
        </c:forEach>
        <% } %>
    </fieldset>
</div>
<div class="ActiveAlerts">
    <h2>Active Alerts</h2>
    <fieldset style="width:500px">
        <% if ( request.getSession().getAttribute(EmployeeSessionRegistry.activeAlerts) == null ) { %>
            <c:out value="NO ALERTS TO BE SHOWN"> </c:out>
        <% } else { %>
            <%--@elvariable id="alerts" type="List<java.lang.String>"--%>
            <c:forEach var="a" items="${alerts}">
                <div>
                    <c:out value="${a}"> </c:out>
                </div>
            </c:forEach>
        <% } %>
    </fieldset>
</div>

</body>
</html>
