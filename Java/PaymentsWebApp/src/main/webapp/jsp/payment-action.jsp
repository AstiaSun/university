<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="icon" href="../images/icon.png">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action="/payment" name="myForm" method="post" role="form" data-toggle="validator" >
        <c:if test ="${empty action}">
            <c:set var="action" value="payment"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <h2>Payment</h2>
        <div class="form-group col-xs-4">
            <label for="sourceCreditCardNumber" class="control-label col-xs-4">Source credit card number:</label>
            <input type="text" name="sourceCreditCardNumber" id="sourceCreditCardNumber" class="form-control" value='<c:out value="${sessionScope.creditCard}"/>' required="true"/>
            <label for="targetCreditCardNumber" class="control-label col-xs-4">Target credit card number:</label>
            <input type="text" name="targetCreditCardNumber" id="targetCreditCardNumber" class="form-control" value="${payment.targetCreditCardNumber}" required="true"/>
            <label for="amount" class="control-label col-xs-4">Amount:</label>
            <input type="text" name="amount" id="amount" class="form-control" value="${payment.amount}" required="true" onchange="checkNegativeNumber()"/>
            <br></br>
            <button type="submit" class="btn btn-primary  btn-md" data-toggle="modal" data-target="#myModal">Submit</button>
        </div>
    </form>

    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Modal Header</h4>
                </div>
                <div class="modal-body">
                    <p><c:out value="${requestScope.message}"/></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" href="jsp/user-actions.jsp">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function checkNegativeNumber() {
            var input=document.forms["myForm"]["amount"].value;
            if (isNaN(input) || (input <= 0)) {
                alert("Invalid input.");
            }
        }
    </script>
</div>
</body>
</html>