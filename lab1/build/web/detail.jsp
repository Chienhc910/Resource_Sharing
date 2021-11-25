<%-- 
    Document   : detail
    Created on : May 26, 2021, 9:10:46 PM
    Author     : pc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <title>Detail</title>
        <link rel="StyleSheet" href="./css/search.css"/>
    </head>
    <c:if test="${empty sessionScope.ACCOUNT || (sessionScope.ACCOUNT.role eq 'LEADER'?false:true && sessionScope.ACCOUNT.role eq 'EMPLOYEE'?false:true)}">
        <c:url value="login.jsp" var="backHome">
            <c:param name="INVALID" value="Please login with user account!"></c:param>
        </c:url>
        <c:redirect url="${backHome}"></c:redirect>
    </c:if>
    <body class="bg-dark">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="user.jsp">User</a>

                <div class="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">

                    <form class="d-flex" action="MainController" method="POST">
                        <button class="btn btn-outline-dark mx-3">Welcome ${sessionScope.ACCOUNT.fullname}</button>
                        <input type="submit" value="Log out" name="action" class="btn btn-danger" />
                    </form>
                </div>
            </div>
        </nav>
        <div class="text-center text-light">
            <c:if test="${not empty requestScope.DETAIL}">
                <h1>${requestScope.DETAIL.name}</h1>
                <p>${requestScope.DETAIL.detail}</p>
            </c:if>
        </div>


    </body>
</html>

