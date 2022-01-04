<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Telco Service - Landing page</title>
</head>
<body>
<h1><%= "Telco Service" %> </h1>
<br/>
<div class="landingLoginForm">
    <form action="login" method="POST">
        <div class="form">
            <label id="username_label" for="log_username">Username:</label>
            <input id="log_username" type="text" name="username" required>
        </div>
        <div class="form">
            <label id="password_label" for="log_password">Password:</label>
            <input id="log_password" type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="login" name="landingLoginForm">
        </div>
    </form>
</div>


<div class="form">
    <form action="register" method="POST">
        <div class="form">
            <label id="reg_username_label" for="reg_username">Username:</label>
            <input type="text" id="reg_username" name="username" required>
        </div>
        <div class="form">
            <label id="reg_email_label" for="reg_email">Mail:</label>
            <input type="text" id="reg_email" name="email" required>
        </div>
        <div class="form">
            <label id="reg_password_label" for="reg_password">Password:</label>
            <input id="reg_password" type="password" name="password" required>
        </div>
        <div class="form">
            <input type="submit" class="btn" value="register" name="landingRegister">
        </div>
    </form>
</div>

<a href="HomePage">Continue without login</a>

</body>
</html>