<%--
  Created by IntelliJ IDEA.
  User: niccolodidoni
  Date: 20/12/21
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> BUY </title>
</head>
<body>

<div>

    <p>
        PATH FOR REDIRECTION: <%= request.getSession().getAttribute("selectedId") %>
        <br>
        PATH FOR REDIRECTION: <%= request.getSession().getAttribute("selected") %>
    </p>

    <form action="../Refresh" method="POST">
        <fieldset>
            <legend>Available Service Packages</legend>
                <select name="selected" id="selected" required>
                    <option value="">-- Please choose a ServicePackage --</option>
                    <%--@elvariable id="packages" type="List<it.polimi.db2.telcoservice_sc42.entities.ServicePackage>"--%>
                    <c:forEach var="p" items="${packages}">
                        <option value="${p.getId()}"
                                <c:if test="${p.getId() eq selectedId}">selected="selected"</c:if>
                        >${p.getName()}</option>
                    </c:forEach>
                </select>
                <input type="submit" class="btn" value="select">
      </fieldset>
    </form>
    <!-- TODO if section for optionality -->
    <form action="../Buy" method="POST">
        <fieldset>
            <legend>Selected Service Package</legend>
            <h4>Included services</h4>
            <%--@elvariable id="services" type="List<it.polimi.db2.telcoservice_sc42.entities.Service>"--%>
            <c:forEach var="s" items="${services}">
                <p>${s.toString()}</p>
            </c:forEach>
            <h4>Available validity periods</h4>
            <%--@elvariable id="validities" type="List<it.polimi.db2.telcoservice_sc42.entities.Validity>"--%>
            <c:forEach var="v" items="${validities}">
                <input type="radio" id="${v.getId()}" name="available_validity" value="no" required>
                <label for="available_validity">${v.toString()}</label>
            </c:forEach>
            <h4>Available optional products</h4>
            <%--@elvariable id="optionals" type="List<it.polimi.db2.telcoservice_sc42.entities.OptionalProduct>"--%>
            <c:forEach var="o" items="${optionals}">
                <input type="checkbox" id="available_optionals" value="${o.getId()}" name="available_optional">
                <label for="${o.getId()}">${o.toString()}</label>
            </c:forEach>
        </fieldset>
        <br>
        <input type="submit" class="btn" value="confirm">
    </form>

</div>
</body>
</html>
