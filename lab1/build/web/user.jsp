<%-- 
    Document   : user
    Created on : May 15, 2021, 12:50:00 PM
    Author     : pc
--%>

<%@page import="resource.ResourceDTO"%>
<%@page import="account.AccountDTO"%>
<%@page import="category.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="category.CategoryDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <title>User Page</title>
        <link rel="StyleSheet" href="./css/search.css"/>
    </head>
    <c:if test="${empty sessionScope.ACCOUNT || (sessionScope.ACCOUNT.role eq 'LEADER'?false:true && sessionScope.ACCOUNT.role eq 'EMPLOYEE'?false:true)}">
        <c:url value="login.jsp" var="backHome">
            <c:param name="INVALID" value="Please login with user account!"></c:param>
        </c:url>
        <c:redirect url="${backHome}"></c:redirect>
    </c:if>
    <c:if test="${empty param.page}">
        <c:url value="MainController" var="searchRedirect">
            <c:param name="action" value="Search Resource"></c:param>
            <c:param name="page" value="1"></c:param>
            <c:param name="name" value="${param.name}"></c:param>
            <c:param name="category" value="${param.category}"></c:param>
            <c:param name="dateFrom" value="${param.dateFrom}"></c:param>
            <c:param name="dateTo" value="${param.dateTo}"></c:param>
        </c:url>
        <c:redirect url="${searchRedirect}"></c:redirect>
    </c:if>
    <body class="bg-dark">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">User</a>

                <div class="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">

                    <form class="d-flex" action="MainController" method="POST">
                        <button class="btn btn-outline-dark ">Welcome ${sessionScope.ACCOUNT.fullname}</button>
                        <a href="MainController?action=History" class="btn btn-info mx-1">History(${fn:length(sessionScope.ACCOUNT.history)})</a>
                        <input type="submit" value="Log out" name="action" class="btn btn-danger" />
                    </form>
                </div>
            </div>
        </nav>

        <div class="search">
            <h1 class="display-4 text-light" >Search Resource</h1>
            <form action="MainController" method="POST" class="text-light">
                <div>
                    <span>Name: </span>
                    <input type="text" class="btn btn-outline-light" placeholder="Name of resource" name="name" value="${param.name}" />
                </div>
                <select class="form-select btn btn-outline-light" aria-label="Default select example" name="category">
                    <option selected value="">Category</option>
                    <option value="IT" <c:if test="${param.category eq 'IT'}">selected</c:if>>IT</option>
                    <option value="History" <c:if test="${param.category eq 'History'}">selected</c:if>>History</option>
                    <option value="Design" <c:if test="${param.category eq 'Design'}">selected</c:if>>Design</option>
                    <option value="Bussiness" <c:if test="${param.category eq 'Bussiness'}">selected</c:if>>Bussiness</option>
                    <option value="Manager" <c:if test="${param.category eq 'Manager'}">selected</c:if>>Manager</option>
                    </select>
                    <div>
                        <span>From:</span>
                        <input type="date" class="btn btn-outline-light" name="dateFrom" value="${param.dateFrom}" />
                </div>
                <div>
                    <span>To: </span>
                    <input type="date" class="btn btn-outline-light" name="dateTo"  value="${param.dateTo}"/>
                </div> 
                <input type="submit" name="action" value="Search Resource" class="btn btn-outline-success" />
            </form>

            <c:if test="${not empty requestScope.LIST || requestScope.LIST_SIZE eq '0'}">
                <c:if test="${requestScope.LIST_SIZE > 0}" var="list">
                    <div style="height: 372px">
                        <table class="table table-dark table-striped">
                            <thead  class="text-center">
                                <tr>
                                    <td>No</td>
                                    <td>Name</td>
                                    <td>Quantity</td>
                                    <td>Color</td>
                                    <td>Category</td>
                                    <td>Action</td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.LIST}" var="dto" varStatus="counter">
                                    <tr>
                                        <td class="text-center">${counter.count}</td>
                                        <td>${dto.name}</td>
                                        <td class="text-center">${dto.quantity}</td>
                                        <td class="text-center">${dto.color}</td>
                                        <td class="text-center">${dto.category}</td>
                                        <td class="text-center">
                                            <form action="MainController" method="POST">
                                                <input type="hidden" name="resourceID" value="${dto.id}"/>
                                                <input type="hidden" name="name" value="${param.name}"/>
                                                <input type="hidden" name="category" value="${param.category}"/>
                                                <input type="hidden" name="dateFrom" value="${param.dateFrom}"/>
                                                <input type="hidden" name="dateTo" value="${param.dateTo}"/>
                                                <input type="hidden" name="page" value="${param.page}"/>
                                                <c:choose>
                                                    <c:when test="${dto.statusBooking eq 'Request'}">
                                                        <input type="submit" name="action" value="Cancel Request" class="btn btn-danger" />
                                                    </c:when>
                                                    <c:when test="${dto.statusBooking eq 'Accept'}">
                                                        <input type="submit" name="action" value="View Resource" class="btn btn-success" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="submit" name="action" value="Request Resource" class="btn btn-outline-light" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <nav aria-label="..." class="d-flex justify-content-center">
                        <ul class="pagination">
                            <c:forEach begin="1" end="${requestScope.PAGE_INDEX}" var="i">
                                <li class="page-item mx-1 <c:if test="${param.page eq i}">active</c:if>"><a class="page-link" href="MainController?action=Search+Resource&page=${i}&name=${param.name}&category=${param.category}&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}">${i}</a></li>
                                </c:forEach>
                        </ul>
                    </nav>
                </c:if>
                <c:if test="${requestScope.LIST_SIZE eq '0'}">
                    <h3 class="text-light display-6">No record found!</h3>
                </c:if>
            </c:if>
        </div>



        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script type="text/javascript">
            function preventBack() {
                window.history.forward();
            }
            setTimeout("preventBack()", 0);
        </script>
        <script>
            <c:if test="${not empty requestScope.USER_ALERT}">
            swal("Notification", "${requestScope.USER_ALERT}", "success");
            </c:if>
        </script>


    </body>
</html>
