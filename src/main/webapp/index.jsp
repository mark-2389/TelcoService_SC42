<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %> </h1>
<br/>
<a href="hello-servlet">HelloServlet</a>

<div class="form">
    <form action="hello-servlet" method="POST">
        <div class="form">
            <%--@declare id="username"--%><label for="username">Username:</label>
            <input type="text" name="username" required>
        </div>
        <div class="form">
            <%--@declare id="password"--%><label for="password">Password:</label>
            <input type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="login">
        </div>
    </form>
</div>


</body>
</html>