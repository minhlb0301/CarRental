<%-- 
    Document   : register
    Created on : Mar 15, 2021, 7:59:49 AM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <title>Register | Car Rental</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <h1>Register</h1>
        <form id="registerForm" action="SendVerifyController" method="POST">
            Email: <input id="email" type="email" name="txtEmail" value=""/> <p id="emailError" style="color: red"></p><br/>
            Password: <input id="password" type="password" name="txtPassword" value=""/> <p id="passError" style="color: red"></p><br/>
            Confirm Password: <input id="confirm" type="password" name="txtConfirm" value=""/> <p id="confirmError" style="color: red"></p><br/>
            Name: <input id="name" type="text" name="txtName" value=""/> <p id="nameError" style="color: red"></p><br/>
            Phone: <input id="phone" type="text" name="txtPhone" value=""/> <p id="phoneError" style="color: red"></p><br/>
            Address: <input id="address" type="text" name="txtAddress" value=""/> <p id="addressError" style="color: red"></p><br/>
            <input type="button" onclick="confirmPassword()" class="btn btn-secondary" name="Register" value="Register"/>
        </form>
        <h5 id="errorLabel" style="color: red">${requestScope.REGISTER_ERROR}</h5>
        <script>
            var password, confirm, email, name, phone, address, flag;
            function confirmPassword() {
                flag = true;
                email = document.getElementById("email").value;
                password = document.getElementById("password").value;
                confirm = document.getElementById("confirm").value;
                name = document.getElementById("name").value;
                phone = document.getElementById("phone").value;
                address = document.getElementById("address").value;
                if (email.length == 0) {
                    document.getElementById("emailError").innerHTML = "Email can't be blank!";
                    flag = false;
                } else if (!email.match(/^.+@.+$/)) {
                    document.getElementById("emailError").innerHTML = "Invalid Email!";
                    flag = false;
                } else {
                    document.getElementById("emailError").innerHTML = "";
                }
                if (password.length == 0) {
                    document.getElementById("passError").innerHTML = "Password can't be blank!";
                    flag = false;
                } else {
                    document.getElementById("passError").innerHTML = "";
                }
                if (password !== confirm) {
                    document.getElementById("confirmError").innerHTML = "Password did not match!";
                    flag = false;
                } else {
                    document.getElementById("confirmError").innerHTML = "";
                }
                if (name.length == 0) {
                    document.getElementById("nameError").innerHTML = "Name can't be blank!";
                    flag = false;
                } else {
                    document.getElementById("nameError").innerHTML = "";
                }
                if (phone.length == 0) {
                    document.getElementById("phoneError").innerHTML = "Phone can't be blank!";
                    flag = false;
                } else if (phone.length != 10) {
                    document.getElementById("phoneError").innerHTML = "Phone must be 10 digits. ex: 0123456789";
                    flag = false;
                } else {
                    document.getElementById("phoneError").innerHTML = "";
                }
                if (address.length == 0) {
                    document.getElementById("addressError").innerHTML = "Address can't be blank!";
                    flag = false;
                } else {
                    document.getElementById("addressError").innerHTML = "";
                }

                if (flag == true) {
                    document.getElementById("registerForm").submit();
                }
            }
        </script>
    </body>
</html>
