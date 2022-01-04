<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 20/12/21
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title> Registration </title>
</head>
<body>
    <h1> Registration </h1>
    <div class="form">
        <form action="../register" method="POST">
            <div class="form">
                <label id="username_label" for="username">Username:</label>
                <input id="username" type="text" name="username" required>
            </div>
            <div class="form">
                <label id="email_label" for="email">Mail:</label>
                <input id="email" type="text" name="email" required>
            </div>
            <div class="form">
                <label id="password_label" for="password">Password:</label>
                <input id="password" type="password" name="password" required>
            </div>
            <div class="form">
                <input type="submit" class="btn" value="register" name="register">
            </div>
        </form>
    </div>
</body>
</html>
