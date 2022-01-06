<%@ page import="it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry" %><%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 23/12/21
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title> LOGIN </title>
</head>
<body>
<h1> Login </h1>
<div class="form">
    <form action="../login" method="POST">
        <div class="form">
            <label id="id_label" for="id">Id:</label>
            <input id="id" type="text" name="id" required>
        </div>
        <div class="form">
            <label id="password_label" for="password">Password:</label>
            <input id="password" type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="login" name="employeeLoginForm">
        </div>
    </form>
</div>
<%
    System.out.println(request.getSession(false));

    if ( request.getSession(false) != null ) {
        String errorMsg = (String) request.getSession().getAttribute(SessionAttributeRegistry.error);
        if ( errorMsg != null ) {
%>
<p>Login error: <%= errorMsg %></p>
<%
        }
    }
%>
</body>
</html>
