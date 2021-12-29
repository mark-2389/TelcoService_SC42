<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.entities.Order" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.entities.ServicePackage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>HOME PAGE</title>
</head>
    <body>
        <h1><%= "Home" %> </h1>
        <br/>
        <a href="../logout" <%= request.getSession().getAttribute("username") != null ? "" : "hidden" %> >
            Logout
        </a>
        <br/>
        <a href="login.jsp" <%= request.getSession().getAttribute("username") == null ? "" : "hidden" %> >
            Login
        </a>
        <br/>
        <a href="registration.jsp" <%= request.getSession().getAttribute("username") == null ? "" : "hidden" %> >
            Registration
        </a>
        <br/>
        <label <%= request.getSession().getAttribute("username") != null  ? "" : "hidden" %> >
            Logged in as: <%= request.getSession().getAttribute("username") %>
        </label>
        <br/>
        <br/>
        <h3>Available packages</h3>
        <%--@elvariable id="packages" type="List<ServicePackage>"--%>
        <c:forEach var="p" items="${packages}">
            <div>
                <a href="#">${p.name}</a>
                <br/>
            </div>
        </c:forEach>
        <br/>
        <br/>
        <%
            List<Order> rejected = (List<Order>) request.getSession().getAttribute("rejected");
            if ( rejected != null && !rejected.isEmpty() ) {
        %>
        <h3>Rejected orders</h3>
        <%--@elvariable id="rejected" type="List<Order>"--%>
        <c:forEach var="o" items="${rejected}">
            <div>
                <a href="#">${o.servicePackage.name}</a>
                <br/>
                <a href="#">${o.totalCost}</a>
                <br/>
            </div>
        </c:forEach>
        <br/>
        <br/>
        <%
            }
        %>
        <!-- TODO create servlet to prepare new page and redirection -->
        <a href="buyService.jsp"> START BUYING </a>
    </body>
</html>