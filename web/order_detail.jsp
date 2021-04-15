<%-- 
    Document   : order_detail
    Created on : Mar 20, 2021, 7:23:16 PM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <title>Order Detail | Car Rental</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <c:if test="${not empty requestScope.listDetail}">
            <table border="1">
                <thead>
                    <tr>
                        <th>OrderId</th>
                        <th>OrderDetailId</th>
                        <th>Car Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>PickupDate</th>
                        <th>ReturnDate</th>
                            <c:if test="${sessionScope.User.isAdmin eq 'false'}">
                            <th>Rating</th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listDetail}" var="detail">
                        <tr>
                            <td>${detail.orderId}</td>
                            <td>${detail.detailId}</td>
                            <td>${detail.productName}</td>
                            <td>${detail.quantity}</td>
                            <td><small>$</small>${detail.price}</td>
                            <td>${detail.pickupDate}</td>
                            <td>${detail.returnDate}</td>
                            <c:if test="${sessionScope.User.isAdmin eq 'false'}">
                                <td>
                                    <form action="FeedbackController" method="POST">
                                        <input type="hidden" name="orderId" value="${detail.orderId}"/>
                                        <input type="hidden" name="productId" value="${detail.productId}"/>
                                        <input type="number" name="rate" value="${requestScope.rate}"/>
                                        <input type="submit" name="action" value="Rate" hidden=""/>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <button type="button" onclick="window.history.back()" class="btn btn-primary">Go Back</button>
    </body>
</html>
