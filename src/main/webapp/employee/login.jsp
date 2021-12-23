<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 23/12/21
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> LOGIN </title>
</head>
<body>
<h1> Login </h1>
<div class="form">
    <form action="../login" method="POST">
        <div class="form">
            <%--@declare id="id"--%><label for="id">Id:</label>
            <input type="text" name="id" required>
        </div>
        <div class="form">
            <%--@declare id="password"--%><label for="password">Password:</label>
            <input type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="login" name="employeeLoginForm">
        </div>
    </form>
</div>
<%
    System.out.println(request.getSession(false));
    if ( request.getSession(false) == null || request.getSession().getAttribute("error") != null  ) {
%>
<p>Login error: <%= request.getSession().getAttribute("error") %></p>
<%
    }
%>
</body>
</html>
