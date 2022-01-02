<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 20/12/21
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>
    <!-- All this paragraph can be canceled. I'm leaving it just to show the name of the attribute
        that are set in the session, ready to be retrieved when creating this page with the corresponding form
        for buying. To be noticed: all this values are saved by id but services which are retrived by the servicePackage
        (saved by id) -->
    PACKAGE: <%= request.getSession().getAttribute("selectedPackage") %>
    <br>
    SERVICES: <%= request.getSession().getAttribute("services") %>
    <br>
    VALIDITY: <%= request.getSession().getAttribute("chosen_validity") %>
    <br>
    OPTIONAL PRODUCTS: <%= request.getSession().getAttribute("chosen_optionals") %>
    <br>
    STARTING DATE OF SUBSCRIPTION: <%= request.getSession().getAttribute("chosen_subscription") %>
</p>
<!-- TODO PRINT ALL DETAILS (SEE SPECIFICATIONS) OF THE ORDER TO BE PAYED -->
<!-- TODO BUY BUTTON ONLY AFTER LOGIN (CREATE FILTER) -->
<!-- TODO BUY BUTTON ACTION: CREATE ORDER (ENTRY OF THE TABLE), REDIRECT TO HOME PAGE -->
</body>
</html>
