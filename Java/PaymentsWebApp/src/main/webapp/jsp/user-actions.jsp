<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="icon" href="../images/icon.png">
    <script src="../js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
</head>
<body>
<div class="container">
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>
    <br/>
    <h2>Hello,  <c:out value="${sessionScope.firstName} ${sessionScope.lastName}"/>!</h2>
    <div class="form-group col-md-4">
        <div class="well">Balance:   <c:out value="${requestScope.balance} ${requestScope.currency}"/></div>
    </div>
    <div class="form-group col-xs-4">
        <button type="button" class="btn btn-primary btn-md" onclick="location.href='jsp/payment-action.jsp'">Payment</button>
        <br></br>
        <button type="button" class="btn btn-md" onclick="location.href='jsp/replenishment-action.jsp'">Refill</button>
        <br></br>
        <button type="button" class="btn btn-md" data-toggle="modal" data-target="#myModal" onclick="location.href='jsp/block-action.jsp'">Block</button>
        <br></br>
        <button type="button" class="btn btn-md" onclick="location.href='jsp/login.jsp'">Logout</button>
    </div>
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Modal Header</h4>
                </div>
                <div class="modal-body">
                    <p>This is a large modal.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        function showModal()
        {
            $("#myModal").modal("show");
        }
    </script>
</div>
</body>
</html>