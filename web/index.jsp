<%-- 
    Document   : index
    Created on : Mar 14, 2021, 9:53:22 AM
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
        <title>Car Rental</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <div style="display: inline-table">
            <form action="SearchCarController" method="POST">
                Car name: <input type="text" name="productName" value="${param.productName}"/>
                Rental Date: <input type="date" name="rentalDate" />
                Return Date: <input type="date" name="returnDate" />
                <input type="submit" class="btn btn-info" name="SearchCart" value="SearchCar"/>
            </form>
        </div>
        <c:if test="${not empty requestScope.listProduct}">
            <div class="container">
                <div class="row">
                    <c:forEach items="${requestScope.listProduct}" var="product" varStatus="counter">
                        <div class="row no-gutters" style="padding: 10px 10px 10px 0">
                            <div class="col-sm">
                                <div class="card" style="width: 250px">
                                    <img src="images/${product.image}" style="width: 225px" class="card-img-top" alt="${product.image}">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.productName}</h5>
                                        <h6>Color: ${product.color}</h6>
                                        <h6>Year: ${product.year}</h6>
                                        <h6>Category: ${product.category}</h6>
                                        <h6>Price: ${product.price} (per day)</h6>
                                        <h6>Quantity: ${product.available}</h6>
                                        <c:forEach items="${requestScope.listFeedback}" var="feedback">
                                            <c:if test="${feedback.productId eq product.productId}">
                                                <h6>Quality: ${feedback.rate} / 10</h6>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${product.available > 0}">
                                            <%--<c:if test="${sessionScope.User.isAdmin eq 'false'}">--%>
                                            <c:url value="AddCartController" var="AddCartUrl">
                                                <c:param name="productId" value="${product.productId}"/>
                                                <c:param name="page" value="${requestScope.page}"/>
                                                <c:param name="productName" value="${requestScope.productName}"/>
                                            </c:url>
                                            <a href="${AddCartUrl}" class="btn btn-success">Add To Cart</a>
                                        </c:if>
                                        <c:if test="${product.available <= 0}">
                                            <button type="button" name="order" class="btn btn-danger" disabled="">Out Of Stock</button>
                                        </c:if>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <form action="SearchCarController" method="POST">
                <input type="hidden" name="page" value="${requestScope.page}"/>
                <input type="hidden" name="productName" value="${requestScope.productName}"/>
                <div style="margin-left: 50%">
                    <input type="submit" name="action" value="<"><input type="text" value="${requestScope.page} / ${requestScope.pageLimit}" style="width: 55px; text-align: center" readonly=""><input type="submit" name="action" value=">">
                </div>
            </form>
        </c:if>
        <h5 style="color: red">${requestScope.SEARCH_ERROR}</h5>

    </body>
</html>
