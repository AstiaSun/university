<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="icon" href="../images/icon.png">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action="/replenish" name="myForm" method="post" role="form" data-toggle="validator" >
        <c:if test ="${empty action}">
            <c:set var="action" value="replenish"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <h2>Replenishment</h2>
        <div class="form-group col-xs-4">
            <label for="creditCardNumber" class="control-label col-xs-4">Credit card number:</label>
            <input type="text" name="creditCardNumber" id="creditCardNumber" class="form-control" value='<c:out value="${sessionScope.creditCard}"/>' required="true"/>
            <label for="code" class="control-label col-xs-4">Replenishment Code:</label>
            <input type="text" name="code" id="code" class="form-control" required="true" onchange="checkPattern()"/>
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
        function checkPattern() {
            var input=document.forms["myForm"]["amount"].value;
            if (isNaN(input) || input.size() !== 7) {
                alert("Invalid input.");
            }
        }
    </script>
</div>
</body>
</html>