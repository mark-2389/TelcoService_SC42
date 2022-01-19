<%@ page import="java.util.List" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.ejb.entities.Order" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.utils.ClientHomeSessionRegistry" %>
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
                ${p.clientString()}
            </div>
        </c:forEach>
        <br/>
        <br/>
        <%
            List<Order> rejected = (List<Order>) request.getSession().getAttribute(ClientHomeSessionRegistry.rejected);
            if ( rejected != null && !rejected.isEmpty() ) {
        %>
        <h3>Rejected orders</h3>
        <p>Click on a rejected order to pay it</p>
        <%--@elvariable id="rejected" type="List<Order>"--%>
        <c:forEach var="o" items="${rejected}">
            <div>
                <a href="../confirmation?order=${o.getId()}"> ${o.getPackage().getName()} <a>
                <br/>
                ${o.getTotalCost()}
                <br/>
            </div>
        </c:forEach>
        <br/>
        <br/>
        <%
            }
        %>

        <a href="buyService.jsp"> START BUYING </a>
    </body>
</html>