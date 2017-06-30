<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${locale == null ? 'en_US' : locale}" />
<fmt:setBundle basename="lang" var="bundle" />
<fmt:requestEncoding value="UTF-8" />
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="icon" href="../images/icon.png">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action="/login" method="post"  role="form" data-toggle="validator">
        <c:if test ="${empty action}">
            <c:set var="action" value="login"/>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-danger">
                    ${message}
            </div>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="idCreditCard" name="idCreditCard" value="${credit_card.id}">
        <h2>Login</h2>
        <div class="form-group col-xs-4">
            <label for="number" class="control-label col-xs-4">Credit Card Number:</label>
            <input type="text" name="number" id="number" class="form-control" value="${credit_card.number}" required="true"/>

            <label for="password" class="control-label col-xs-4">Password:</label>
            <input type="password" name="password" id="password" class="form-control" value="${credit_card.password}" required="true"/>

            <br></br>
            <button type="submit" class="btn btn-primary  btn-md">Login</button>
        </div>
    </form>
</div>
</body>
</html>