<%-- 
    Document   : history
    Created on : May 24, 2021, 1:23:16 PM
    Author     : pc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    </head>
    <body class="bg-dark">    
        <c:if test="${empty sessionScope.ACCOUNT || (sessionScope.ACCOUNT.role eq 'LEADER'?false:true && sessionScope.ACCOUNT.role eq 'EMPLOYEE'?false:true)}">
            <c:url value="login.jsp" var="backHome">
                <c:param name="INVALID" value="Please login with user account!"></c:param>
            </c:url>
            <c:redirect url="${backHome}"></c:redirect>
        </c:if>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="user.jsp">User</a>

                <div class="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">

                    <form class="d-flex" action="MainController" method="POST">
                        <button class="btn btn-outline-dark ">Welcome ${sessionScope.ACCOUNT.fullname}</button>
                        <a href="MainController?action=History" class="btn btn-info mx-1">History(${fn:length(sessionScope.ACCOUNT.history)})</a>
                        <input type="submit" value="Log out" name="action" class="btn btn-danger" />
                    </form>
                </div>
            </div>
        </nav>
        <div class="content">
            <form action="MainController" method="POST" class="search container d-flex justify-content-center align-items-center text-light fs-4 my-3">
                <div class="d-flex">
                    <p class="m-0 mx-2">Name:</p>
                    <input type="text" name="name" class="btn btn-light" value="${param.name}"/>
                </div>
                <div class="d-flex mx-3 ">
                    <p class=" m-0 mx-2">Date From:</p>
                    <input type="date" name="date"class="btn btn-light" value="${param.date}"/>
                </div>
                <input type="submit" name="action" value="Search History" class="btn btn-success" />
            </form>
            <c:if test="${not empty requestScope.LIST_HISTORY && fn:length(requestScope.LIST_HISTORY)>0}" var="listHistory">
                <table class="table table-dark table-striped">
                    <thead class="text-center">
                        <tr>
                            <td>No</td>
                            <td>Name</td>
                            <td>Date</td>
                            <td>Status</td>
                            <td>Admin Approve/Deny</td>
                            <td>Action</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.LIST_HISTORY}" var="dto" varStatus="counter">
                            <tr class="fs-4">
                                <td class="text-center">${counter.count}</td>
                                <td>${dto.resourceName}</td>
                                <td class="text-center">${dto.date}</td>
                                <td class="text-uppercase text-center fw-bold <c:choose><c:when test="${dto.status eq 'Accept'}">text-success</c:when><c:when test="${dto.status eq 'Request'}">text-info</c:when><c:otherwise>text-danger</c:otherwise></c:choose>">${dto.status}</td>
                                <td class="text-center"><c:if test="${empty dto.adminApprove}" var="checkAdmin">No Data</c:if><c:if test="${!checkAdmin}">${dto.adminApprove}</c:if></td>
                                    <td>
                                        <form action="MainController" method="POST">
                                            <input type="hidden" name="resourceID" value="${dto.resourceID}"/>
                                                <input type="hidden" name="name" value="${param.name}"/>
                                        <input type="hidden" name="date" value="${param.date}"/>
                                        <input type="submit" name="action" value="Delete History" class="btn btn-danger"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${!listHistory}">
                <h1 class="display-4 text-light">No record found!</h1>
            </c:if>
        </div>
    </body>
</html>
