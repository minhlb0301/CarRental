<%-- 
    Document   : history
    Created on : Mar 14, 2021, 10:21:32 AM
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
        <title>Order Histoy | Car Rental</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <form action="SearchOrderController" method="POST">
            OrderId: <input type="text" name="orderId" value="${requestScope.orderId}"/>
            Order Date From: <input type="date" name="fromDate" value="${requestScope.fromDate}"/>
            To: <input type="date" name="toDate" value="${requestScope.toDate}"/>
            <input type="submit" name="action" class="btn btn-secondary " value="Search"/>
        </form>
        <c:if test="${not empty requestScope.listOrder}">
            <table border="1">
                <thead>
                    <tr>
                        <th>OrderId</th>
                        <th>OrderDate</th>
                            <c:if test="${sessionScope.User.isAdmin eq 'true'}">
                            <th>UserId</th>
                            </c:if>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Detail</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listOrder}" var="order">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.createDate}</td>
                            <c:if test="${sessionScope.User.isAdmin eq 'true'}">
                                <td>${order.userId}</td>
                            </c:if>
                            <td><small>$</small>${order.total}</td>
                            <td>${order.status}</td>
                            <td>
                                <form action="GetOrderDetailController" method="POST">
                                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                                    <button type="submit" name="action" class="btn btn-info" value="GetDetail">View details</button>
                                </form>
                            </td>
                            <td>
                                <c:if test="${order.status ne 'Inactive'}" var="isActive">
                                    <c:url value="DelOrderController" var="DelOrderURL">
                                        <c:param name="orderId" value="${order.orderId}"/>
                                    </c:url>
                                    <a href="${DelOrderURL}">Delete</a>
                                </c:if>
                                <c:if test="${!isActive}">
                                    <a href="${DelOrderURL}" class="btn btn-warning" style="pointer-events: none">Delete</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


        </c:if>
        <h5 id="errorLabel" style="color: red">${requestScope.HISTORY_ERROR}</h5>
    </body>
</html>
