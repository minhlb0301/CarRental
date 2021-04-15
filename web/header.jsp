<%-- 
    Document   : header
    Created on : Mar 20, 2021, 11:21:50 PM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <style>
            .dropdown:hover>.dropdown-menu {
                display: inherit;
            }
        </style>
        <title>Header</title>
    </head>
    <body>
        <header>
            <div class="header fixed-top ">
                <nav class="nav" style="height:50px; line-height: 50px; padding-top: 5px; background-image: url('images/bg-01.jpg')" >
                    <div class="col-7">
                    </div>
                    <div class="row col-5 d-flex bd-highlight" >
                        <a class="col text-center fa-1x" id="tabHome" href="index.jsp"><i class="fas fa-home fa-2x" style="color:white"></i></a>
                        <a class="col text-center" href="view_cart.jsp"><i class="fas fa-shopping-cart fa-2x" style="color:white"></i></a>
                        <a class="col text-center" href="GetOrderHistoryController"><i class="fas fa-file-alt fa-2x" style="color:white"></i></a>
                            <c:if test="${not empty sessionScope.User}">
                            <div class="dropdown" >
                                <button class="btn btn-warning dropdown-toggle" style="margin-bottom: 12px" id="dropdownButton" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ${sessionScope.User.name} ${sessionScope.User.username}
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownButton">
                                    <form action="LogoutController" method="post" class="dropdown-item">
                                        <button type="submit" class="btn btn-warning" value="Logout">
                                            Logout
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${empty sessionScope.User}">
                        <a href="login.jsp" class="btn btn-primary" style="margin-bottom: 10px">Login</a>
                        </c:if>
                    </div>
                </nav>
            </div> 
        </header>
    </body>
</html>
