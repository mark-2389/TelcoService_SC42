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
<!-- TODO RADIO BUTTONS FOR VALID SERVICE PACKAGE AND REFRESH PAGE -->
<!-- TODO RADIO BUTTONS FOR VALIDITY TO CHOOSE WITH PACKAGE -->
<!-- TODO CHECKBOX FOR OPTIONAL PRODUCT PACKAGE -->
<!-- TODO CONFIRM BUTTON AND REDIRECTION TO CONFIRMATION PAGE -->
<div>

    <p>
        PATH FOR REDIRECTION: <%= request.getServletContext().getContextPath() + "/HTML/HomePage" %>
    </p>

    <form action="../HomePage" method="GET">
      <fieldset>
        <legend>Available Service Packages</legend>
          <select name="pets" id="pet-select">
              <option value="">-- Please choose a ServicePackage --</option>
              <%--@elvariable id="packages" type="List<it.polimi.db2.telcoservice_sc42.entities.ServicePackage>"--%>
                  <c:forEach var="p" items="${packages}">
                      <div>
                          <option value="${p.getId()}"></option>
                          <label for="${p.getId()}">${p.getName()}</label>
                      </div>
                  </c:forEach>
          </select>
          <input type="submit" class="btn" name="radio" value="select">
      </fieldset>
    </form>

</div>
</body>
</html>
