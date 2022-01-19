<%@ page import="java.math.BigDecimal" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry" %>
<%@ page import="java.text.DecimalFormat" %>
<%--
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
    Integer months = (Integer) request.getSession().getAttribute(BuySessionRegistry.chosenValidityMonths);

    BigDecimal fee = (BigDecimal) request.getSession().getAttribute(BuySessionRegistry.chosenValidityFee);

    BigDecimal totalOptionalsFee = (BigDecimal) request.getSession().getAttribute(BuySessionRegistry.totalOptionalsFee);
    if ( totalOptionalsFee == null ) totalOptionalsFee = new BigDecimal(0);
    BigDecimal price = ( fee.add(totalOptionalsFee) ).multiply(BigDecimal.valueOf(months));

    String servicePackage = (String) request.getSession().getAttribute(BuySessionRegistry.selectedPackageName);
%>
<div>
    <h3> <%= servicePackage %></h3>
</div>
<div>
    <%
    Object attribute = request.getSession().getAttribute(BuySessionRegistry.selectedPackageName);
    if ( attribute instanceof Integer ) {
    %>
    <h4><%= attribute %></h4>
    <%}%>
</div>

<div>
    <h5>Number of months:</h5><%= months %>
    <h5>Monthly fee:</h5><%= new DecimalFormat("#.00").format(fee) %>
</div>

<div>
    <h4>Selected services</h4>
    <div>
        <%--@elvariable id="services" type="java.util.List<it.polimi.db2.telcoservice_sc42.ejb.entities.Service>"--%>
        <c:forEach var="service" items="${services}">
            <c:out value="${service.clientString()}"> </c:out>
        </c:forEach>
    </div>

</div>

<div>
    <h4>Selected optionals</h4>
    <div>
        <%--@elvariable id="chosen_optionals_desc" type="java.util.List<java.lang.String>"--%>
        <c:forEach var="optional" items="${chosen_optionals_desc}">
            <c:out value="${optional}"> </c:out>
        </c:forEach>
    </div>
</div>

<div>
    <h4>Date of subscription:</h4> <%= request.getSession().getAttribute(BuySessionRegistry.chosenSubscription) %>
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
        <form method="get" action="../Payment">
            <input hidden id="fail_input" name="fail" value="1" >
            <button type="submit" > BUY(FAIL) </button>
        </form>
        <form method="get" action="../Payment">
            <input hidden id="success_input" name="fail" value="0" >
            <button type="submit" > BUY(SUCCESS) </button>
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
