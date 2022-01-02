<%@ page import="java.math.BigDecimal" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.utils.SafeParser" %><%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 20/12/21
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    int months = (Integer) request.getSession().getAttribute("chosen_validity_months");

    BigDecimal fee = (BigDecimal) request.getSession().getAttribute("chosen_validity_fee");

    BigDecimal totalFee = SafeParser.safeParseBigDecimal( (String) request.getSession().getAttribute("totalFee"));
    if ( totalFee == null ) totalFee = new BigDecimal(0);
    BigDecimal price = ( fee.add(totalFee) ).multiply(BigDecimal.valueOf(months));
%>
<div>
    <%
    Object attribute = request.getSession().getAttribute("selectedPackage_name");
    if ( attribute instanceof Integer ) {
    %>
    <h4><%= attribute %></h4>
    <%}%>
</div>

<div>
    <h5>Number of months:</h5><%= months %>
    <h5>Monthly fee:</h5><%= fee %>
</div>

<div>
    <h4>Selected services</h4>
    <div>
        <%--@elvariable id="services" type="java.util.List<it.polimi.db2.telcoservice_sc42.entities.Service>"--%>
        <c:forEach var="service" items="${services}">
            <c:out value="${service}"> </c:out>
        </c:forEach>
    </div>

</div>

<div>
    <h4>Selected optionals</h4>
    <div>
        <%--@elvariable id="chosen_optionals_desc" type="java.util.List<it.polimi.db2.telcoservice_sc42.entities.String>"--%>
        <c:forEach var="optional" items="${chosen_optionals_desc}">
            <c:out value="${optional}"> </c:out>
        </c:forEach>
    </div>
</div>

<div>
    <h4>Date of subscription:</h4> <%= request.getSession().getAttribute("chosen_subscription")%>
</div>

<div>
    <h4>Total price: </h4><%= price %>
</div>

<%
    if ( request.getSession().getAttribute("username") != null ) {
%>
        <form method="get" action="../Payment">
            <button type="submit" > BUY </button>
        </form>
<%
    } else {
%>
        <form method="get" action="login.jsp">
            <button type="submit"> LOGIN </button>
        </form>
        <form method="get" action="register.jsp">
            <button type="submit"> REGISTER </button>
        </form>
<%
    }
%>


<!-- TODO PRINT ALL DETAILS (SEE SPECIFICATIONS) OF THE ORDER TO BE PAYED -->
<!-- TODO BUY BUTTON ONLY AFTER LOGIN (CREATE FILTER) -->
<!-- TODO BUY BUTTON ACTION: CREATE ORDER (ENTRY OF THE TABLE), REDIRECT TO HOME PAGE -->
</body>
</html>
