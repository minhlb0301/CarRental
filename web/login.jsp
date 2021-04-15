<%-- 
    Document   : login
    Created on : Mar 14, 2021, 9:53:30 AM
    Author     : Minh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <title>Login | Car Rental</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <h1>Login</h1>
        <form action="LoginController" method="POST">
            <h5 style="color: red">${requestScope.AUTHEN_ERROR}</h5>
            UserId:     <input type="text" name="userId" value=""/><br>
            Password:   <input type="password" name="password" value=""/><br>
            <div class="g-recaptcha" data-sitekey="6Ld0kX4aAAAAAPTnou1FJc-jbmrUntYX4ZIr886_"></div>
            <input type="submit" class="btn btn-secondary" name="Login" value="Login"/>
            Not have account? <a href="register.jsp">Register here</a>
            <h5 style="color: red">${requestScope.LOGIN_ERROR}</h5>
        </form>
        <a href="https://www.facebook.com/dialog/oauth?client_id=341241570632859&redirect_uri=http://localhost:8084/J3.L.P0015/FacebookController">
            Login with Facebook
        </a>

    </body>
</html>
