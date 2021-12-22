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
        <p><%= request.getSession().getAttribute("employee") %></p>
        <br/>
        <br/>
        <h3>Available packages</h3>
        <%--@elvariable id="packages" type="java.util.List"--%>
        <c:forEach var="p" items="${packages}">
            <a href="#">${p}</a>
            <br/>
        </c:forEach>
        <h3>Rejected orders</h3>
        <%--@elvariable id="rejected" type="java.util.List"--%>
        <c:forEach var="o" items="${rejected}">
            <a href="#">${o}</a>
            <br/>
        </c:forEach>
        <br/>
        <br/>
        <a href="buyService.jsp"> START BUYING </a>
    </body>
</html>