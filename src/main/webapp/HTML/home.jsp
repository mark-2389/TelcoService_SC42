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
        <!-- <a href="buyService.jsp"> START BUYING </a> -->
        <label <%= request.getSession().getAttribute("username") != null  ? "" : "hidden" %> >
            Logged in as: <%= request.getSession().getAttribute("username") %>
        </label>
        <br/>
    </body>
</html>