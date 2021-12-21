<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Telco Service" %> </h1>
<br/>
<div class="landingLoginForm">
    <form action="login" method="POST">
        <div class="form">
            <%--@declare id="username"--%><label for="username">Username:</label>
            <input type="text" name="username" required>
        </div>
        <div class="form">
            <%--@declare id="password"--%><label for="password">Password:</label>
            <input type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="login" name="landingLoginForm">
        </div>
    </form>
</div>


<div class="form">
    <form action="register" method="POST">
        <div class="form">
            <%--@declare id="username"--%><label for="username">Username:</label>
            <input type="text" name="username" required>
        </div>
        <div class="form">
            <%--@declare id="email"--%><label for="email">Mail:</label>
            <input type="text" name="email" required>
        </div>
        <div class="form">
            <%--@declare id="password"--%><label for="password">Password:</label>
            <input type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="register" name="landingRegister">
        </div>
    </form>
</div>

<a href="HomePage">Continue without browsing</a>

</body>
</html>