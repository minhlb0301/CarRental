<%-- 
    Document   : view_cart
    Created on : Mar 19, 2021, 7:27:54 PM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <title>View Cart | Car Rental</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <c:if test="${not empty sessionScope.shoppingCart}">
            <table border="1">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Car Name</th>
                        <th>Price(per day)</th>
                        <th>Quantity</th>
                        <th>Pickup Date</th>
                        <th>Return Date</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${sessionScope.shoppingCart.cart.values()}" var="product" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${product.productName}</td>
                            <td><small>$</small>${product.price}</td>
                    <form action="UpdateCartController" method="POST">
                        <td>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <input type="number" name="orderQuantity" value="${product.available}" min="1" required=""/>

                        </td>
                        <td>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <input type="date" name="pickupDate" value="${product.pickupDate}" required=""/>

                        </td>
                        <td>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <input type="date" name="returnDate" value="${product.returnDate}" required=""/>
                        </td>
                        <input type="submit" name="action" value="UpdateCart" hidden=""/>
                    </form>
                    <td align="center">
                        <c:url value="DelCartController" var="DelCartUrl">
                            <c:param name="productId" value="${product.productId}"/>
                        </c:url>
                        <button type="button" data-toggle="modal" class="btn btn-warning" data-target="#delCartModal">&times;</button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2" align="center">
                    Total
                </td>
                <td colspan="2" align="center">
                    <p>$</p>${sessionScope.shoppingCart.total}
                </td>
                <td colspan="3" align="center">
                    <form action="OrderController" method="POST">
                        <button type="submit" class="btn btn-primary" name="action" value="Order">Order</button>
                    </form>
                </td>
            </tr>
        </tbody>
    </table>

</c:if>
<c:if test="${empty sessionScope.shoppingCart}">
    Your Cart is empty?
    <a href="index.jsp" class="btn btn-primary">ORDER NOW</a>
</c:if>
<c:if test="${not empty requestScope.OutOfStocks}">
    <c:forEach items="${requestScope.OutOfStocks}" var="productName">
        <p style="color: red">${productName} is out of stocks!</p>
    </c:forEach>
</c:if>
<div class="modal" id="delCartModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Delete Confirm</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                Are you sure to delete ?
            </div>
            <div class="modal-footer">
                <a href="${DelCartUrl}" class="btn btn-success">Yes</a>
                <button type="button" class="btn btn-danger" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
