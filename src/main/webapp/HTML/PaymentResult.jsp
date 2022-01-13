<%@ page import="it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry" %><%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 13/01/22
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Payment result</title>
</head>
<body>
    <%
        String msg = (String) session.getAttribute(BuySessionRegistry.paymentMsg);
        if ( msg == null ) msg = "";
    %>
    <h1><%= msg %></h1>

    <form method="get" action="../HomePage">
        <label> Press to return to the home page </label>
        <button type="submit"> RETURN TO HOME </button>
    </form>
</body>
</html>
