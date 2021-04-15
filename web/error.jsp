<%-- 
    Document   : error
    Created on : Mar 20, 2021, 10:47:35 PM
    Author     : Minh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ERROR PAGE</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <h5 style="color: red">${requestScope.ERROR_MSG}</h5>
        <button type="button" onclick="window.history.back()">Go Back</button>
    </body>
</html>
