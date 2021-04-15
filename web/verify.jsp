<%-- 
    Document   : verify
    Created on : Mar 16, 2021, 1:30:24 AM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <title>Verify User Account</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <h3>You have been sent a verify code to your registration email.</h3>
        <h4>Enter Verify Code</h4>
        <form id="verifyForm" action="RegisterController" method="POST">
            <input type="hidden" name="txtEmail" value="${requestScope.txtEmail}"/>
            <input type="hidden" name="txtPassword" value="${requestScope.txtPassword}"/>
            <input type="hidden" name="txtName" value="${requestScope.txtName}"/>
            <input type="hidden" name="txtPhone" value="${requestScope.txtPhone}"/>
            <input type="hidden" name="txtAddress" value="${requestScope.txtAddress}"/>
            <c:url value="SendVerifyController" var="ResendURL">
                <c:param name="txtEmail" value="${requestScope.txtEmail}"/>
                <c:param name="txtPassword" value="${requestScope.txtPassword}"/>
                <c:param name="txtName" value="${requestScope.txtName}"/>
                <c:param name="txtPhone" value="${requestScope.txtPhone}"/>
                <c:param name="txtAddress" value="${requestScope.txtAddress}"/>
            </c:url>
            Verify code: <input id="verify" type="text" name="verify" value="" required=""/><a href="${ResendURL}" class="btn btn-secondary">Re-Send</a> <br/>
            <input type="button" onclick="doVerify()" class="btn btn-primary" name="Verify" value="Verify"/>
        </form>
        <h5 id="errorLabel" style="color: red"></h5>

        <script>
            var verifyCode;
            var input;
            function doVerify() {
                verifyCode = ${sessionScope.verifyCode};
                input = document.getElementById("verify").value;
                if (verifyCode != input) {
                    document.getElementById("errorLabel").innerHTML = "Verify code is invalid!";
                } else {
                    document.getElementById("errorLabel").innerHTML = "";
                    document.getElementById("verifyForm").submit();
                }
            }
        </script>
    </body>
</html>
