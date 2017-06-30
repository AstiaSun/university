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
<div class="container center-block">
    <form action="/block" method="post" role="form" name="myForm" data-toggle="validator">
        <input type="hidden" id="confirm" name="confirm" value="yes">
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="creditCard" name="idCreditCard" value="${sessionScope.creditCard}">
        <c:if test ="${empty action}">
            <c:set var="action" value="block"/>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-danger">
                    ${message}
            </div>
        </c:if>

        <h3 class="center-block">Are you sure to block your account?</h3>
        <h3 class="center-block">Only administrator can unblock it.</h3>
        <button type="submit" class="btn btn-primary" onclick="confirm()">Yes</button>
        <button type="submit" class="btn btn-default">No</button>
    </form>
    <script>
        function confirm() {
            document.forms["myForm"].getElementById("confirm").value = "yes";
        }
    </script>
</div>
</body>
</html>