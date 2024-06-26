<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 20/12/21
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title> BUY </title>
</head>
<body>
<div>
    <h1 style="background-color:cadetblue">BUY SERVICE PACKAGE PAGE</h1>
</div>
<hr style="height:2px;border-width:0;color:darkblue;background-color:darkblue">
<div>
    <%
        if ( request.getSession().getAttribute("username") != null ) {
    %>
        <label>
            Logged in as: <b> <%= request.getSession().getAttribute("username") %> </b>
        </label>
    <%
        }
    %>
</div>
<br>
<div>
    <a href="home.jsp">GO BACK TO HOME PAGE</a>
</div>
<hr style="height:2px;border-width:0;color:darkblue;background-color:darkblue">
<br>
<div>
    <%
        Integer packageId = (Integer) request.getSession().getAttribute(BuySessionRegistry.selectedPackage);
        String packageString = packageId != null ? String.valueOf(packageId) : "";
    %>

    <p>
        PACKAGE: <%= packageString %>
    </p>

    <form action="../BuyPage" method="POST">
        <fieldset>
            <legend>Available Service Packages</legend>
                <select name="selected" id="selected" required>
                    <option value="">-- Please choose a ServicePackage --</option>
                    <%--@elvariable id="packages" type="List<it.polimi.db2.telcoservice_sc42.ejb.entities.ServicePackage>"--%>
                    <c:forEach var="p" items="${packages}">
                        <option value="${p.getId()}"
                                <c:if test="${p.getId() eq selectedPackage}">selected="selected"</c:if>
                        >${p.clientString()}</option>
                    </c:forEach>
                </select>
                <input type="submit" class="btn" value="select">
      </fieldset>
    </form>

    <br> <br>
    <% if ( packageId != null ) { %>
        <form action="../BuyPage" method="POST">
            <fieldset>
                <legend>Selected Service Package</legend>
                <h4>Included services</h4>
                <%--@elvariable id="services" type="List<it.polimi.db2.telcoservice_sc42.ejb.entities.Service>"--%>
                <c:forEach var="s" items="${services}">
                    <p>${s.clientString()}</p>
                </c:forEach>
                <h4>Available validity periods</h4>
                <%--@elvariable id="validities" type="List<it.polimi.db2.telcoservice_sc42.ejb.entities.Validity>"--%>
                <c:forEach var="v" items="${validities}">
                    <div>
                        <input type="radio" id="validity" name="available_validity" value="${v.getId()}" required>
                        <label for="validity">${v.clientString()}</label>
                    </div>
                </c:forEach>
                <h4>Available optional products</h4>
                <%--@elvariable id="optionals" type="List<it.polimi.db2.telcoservice_sc42.ejb.entities.OptionalProduct>"--%>
                <c:forEach var="o" items="${optionals}">
                    <div>
                        <input type="checkbox" id="available_optionals" name="available_optional" value="${o.getId()}" >
                        <label for="${o.getId()}">${o.clientString()}</label>
                    </div>
                </c:forEach>
                <h4>Starting date of subscription</h4>
                <input type="date" name="starting_date_of_subscription" value="<%= LocalDate.now().toString() %>"
                       min="<%= LocalDate.now().toString() %>" required>
        </fieldset>
            <br>
            <input type="submit" class="btn" value="confirm">
        </form>
    <% } %>

</div>
</body>
</html>
