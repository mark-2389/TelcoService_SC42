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
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Purchases Per Package</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE NAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>N. PURCHASES</b></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="purchases" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
        <c:forEach var="p" items="${purchases}">
            <tr style="border: 1px black">
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("ID")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("NAME")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("PURCHASES")}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="PurchasesWithValidity">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Purchases Per Package And Validity</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE NAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>VALIDITY ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>VALIDITY PERIOD</b></th>
            <th colspan="1" style="border: 3px solid black"><b>N. PURCHASES</b></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="purchasesValidity" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
        <c:forEach var="p" items="${purchasesValidity}">
            <tr style="border: 1px black">
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("PACKAGEID")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("PACKAGENAME")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("VALIDITYID")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("PERIOD")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("PURCHASES")}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="ValuesWithoutOp">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Total value of sales per package without optional products</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE NAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>VALUE OF SALES</b></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="values" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
        <c:forEach var="p" items="${values}">
            <tr style="border: 1px black">
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("ID")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("NAME")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("VALUE")}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="ValuesWithOp">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Total value of sales per package with optional products</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE NAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>VALUE OF SALES</b></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="valuesPackageOptional" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
        <c:forEach var="p" items="${valuesPackageOptional}">
            <tr style="border: 1px black">
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("ID")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("NAME")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("VALUE")}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="Averages">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Average number of optional products per service package</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>PACKAGE NAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>AVERAGE</b></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="averages" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
        <c:forEach var="p" items="${averages}">
            <tr style="border: 1px black">
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("ID")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("NAME")}</td>
                <td style="border: 1px solid black; border-right: 3px solid black">${p.get("AVERAGE")}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="BestOptional">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Best optional product</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>OPTIONAL ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>OPTIONAL NAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>VALUE OF SALES</b></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="best" type="java.util.Map<java.lang.String, java.lang.String>"--%>
        <tr style="border: 1px black">
            <td style="border: 1px solid black; border-right: 3px solid black">${best.get("ID")}</td>
            <td style="border: 1px solid black; border-right: 3px solid black">${best.get("NAME")}</td>
            <td style="border: 1px solid black; border-right: 3px solid black">${best.get("VALUE")}</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="InsolventClients">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Insolvent clients</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>USERNAME</b></th>
            <th colspan="1" style="border: 3px solid black"><b>EMAIL</b></th>
            <th colspan="1" style="border: 3px solid black"><b>REJECTIONS</b></th>
        </tr>
        </thead>
        <tbody>
            <% if(  request.getSession().getAttribute(EmployeeSessionRegistry.insolventUsers) == null  ) { %>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
            <% } else { %>
                <%--@elvariable id="insolvents" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
                <c:forEach var="p" items="${insolvents}">
                    <tr style="border: 1px black">
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("USERNAME")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("EMAIL")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("REJECTIONS")}</td>
                    </tr>
                </c:forEach>
            <% } %>
        </tbody>
    </table>
</div>
<div class="SuspendedOrders">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Suspended orders</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>ORDER ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>CREATION DATE</b></th>
            <th colspan="1" style="border: 3px solid black"><b>CREATION HOUR</b></th>
            <th colspan="1" style="border: 3px solid black"><b>DATE SUBSCRIPTION</b></th>
            <th colspan="1" style="border: 3px solid black"><b>TOTAL COST</b></th>
        </tr>
        </thead>
        <tbody>
            <% if(  request.getSession().getAttribute(EmployeeSessionRegistry.suspendedOrders) == null  ) { %>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
            <% } else { %>
                <%--@elvariable id="orders" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
                <c:forEach var="p" items="${orders}">
                    <tr style="border: 1px black">
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("ORDER_ID")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("CREATION_DATE")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("CREATION_HOUR")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("DATE_SUBSCRIPTION")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("TOTAL_COST")}</td>
                    </tr>
                </c:forEach>
            <% } %>
        </tbody>
    </table>
</div>
<div class="ActiveAlerts">
    <table style="width: 550px; border: 3px solid black; border-collapse: collapse">
        <caption>
            <br> <br>
            <b style="font-size: 30px">Active alerts</b>
            <br><br>
        </caption>

        <thead style="background-color: darkgrey">
        <tr style="border: 3px solid black">
            <th colspan="1" style="border: 3px solid black"><b>ORDER ID</b></th>
            <th colspan="1" style="border: 3px solid black"><b>EMAIL</b></th>
            <th colspan="1" style="border: 3px solid black"><b>AMOUNT</b></th>
            <th colspan="1" style="border: 3px solid black"><b>CREATION DATE</b></th>
            <th colspan="1" style="border: 3px solid black"><b>CREATION HOUR</b></th>
        </tr>
        </thead>
        <tbody>
            <% if(  request.getSession().getAttribute(EmployeeSessionRegistry.activeAlerts) == null  ) { %>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
                <td style="border: 1px solid black; border-right: 3px solid black"></td>
            <% } else { %>
                <%--@elvariable id="alerts" type="java.util.List<java.util.Map<java.lang.String, java.lang.String>>"--%>
                <c:forEach var="p" items="${alerts}">
                    <tr style="border: 1px black">
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("USERNAME")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("EMAIL")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("AMOUNT")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("REJECTION_DATE")}</td>
                        <td style="border: 1px solid black; border-right: 3px solid black">${p.get("REJECTION_TIME")}</td>
                    </tr>
                </c:forEach>
            <% } %>
        </tbody>
    </table>
</div>

<br> <br>
</body>
</html>
