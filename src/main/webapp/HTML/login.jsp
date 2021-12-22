<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 20/12/21
  Time: 17:20
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
                <%--@declare id="username"--%><label for="username">Username:</label>
                <input type="text" name="username" required>
            </div>
            <div class="form">
                <%--@declare id="password"--%><label for="password">Password:</label>
                <input type="password" name="password" required>
            </div>
            <div class="form">
                <input type="submit" class="btn" value="login" name="loginForm">
            </div>
        </form>
    </div>
    <%
        if ( request.getSession().getAttribute("invalid") != null ) {
    %>
    <p>Login error</p>
    <%
        }
    %>
</body>
</html>
