<%-- 
    Document   : user
    Created on : May 15, 2021, 12:50:00 PM
    Author     : 9550
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${empty sessionScope.ACCOUNT || (sessionScope.ACCOUNT.role eq 'MANAGER'?false:true)}">
        <c:url value="login.jsp" var="backHome">
            <c:param name="INVALID" value="Please login with admin account!"></c:param>
        </c:url>
        <c:redirect url="${backHome}"></c:redirect>
    </c:if>
    <c:if test="${requestScope.LIST_BOOKING == null}">
        <c:url value="MainController" var="getAdminData">
            <c:param name="action" value="Search Booking"></c:param>
        </c:url>
        <c:redirect url="${getAdminData}"></c:redirect>
    </c:if>
    <body class="bg-dark">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Admin</a>

                <div class="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">

                    <form class="d-flex" action="MainController" method="POST">
                        <button class="btn btn-outline-dark mx-3">Welcome ${sessionScope.ACCOUNT.fullname}</button>
                        <input type="submit" value="Log out" name="action" class="btn btn-danger" />
                    </form>
                </div>
            </div>
        </nav>
        <div class="admin text-light my-5">
            <form action="MainController" method="POST" class="search container d-flex justify-content-center align-items-center text-light fs-4 my-3">
                <div class="d-flex">
                    <p class="m-0 mx-2">Name:</p>
                    <input type="text" name="name" class="btn btn-light" value="${param.name}"/>
                </div>
                <div class="d-flex mx-3 ">
                    <p class=" m-0 mx-2">Status:</p>
                    <select name="status" class="btn btn-light">
                        <option value="">All</option>
                        <option value="ST1" <c:if test="${param.status eq 'ST1'}">selected</c:if>>Request</option>
                        <option value="ST3" <c:if test="${param.status eq 'ST3'}">selected</c:if>>Deny</option>
                        <option value="ST2" <c:if test="${param.status eq 'ST2'}">selected</c:if>>Accept</option>
                        </select>
                    </div>
                    <input type="submit" name="action" value="Search Booking" class="btn btn-success" />
                </form>
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <c:if test="${not empty requestScope.LIST_BOOKING}">
                    <table class="table table-dark table-striped">
                        <thead  class="text-center">
                            <tr>
                                <td>No</td>
                                <td>Name</td>
                                <td>Quantity</td>
                                <td>User Request</td>
                                <td>Date Request</td>
                                <td>Status</td>
                                <td>Action</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.LIST_BOOKING}" var="dto" varStatus="counter">
                                <c:set var="flag" value="true"></c:set>
                                    <tr>
                                <form action="MainController" method="POST">
                                    <td class="text-center">${counter.count}</td>
                                <td>${dto.resourceName}</td>
                                <td class="text-center">${dto.quantity}</td>
                                <td class="text-center">${dto.username}</td>
                                <td class="text-center">${dto.date}</td>
                                <td class="text-center <c:choose><c:when test="${dto.status eq 'Accept'}">text-success</c:when><c:when test="${dto.status eq 'Request'}">text-info</c:when><c:otherwise>text-danger</c:otherwise></c:choose>">${dto.status}</td>
                                <td class="text-center">
                                    <input type="hidden" name="resourceID" value="${dto.resourceID}"/>
                                    <input type="hidden" name="userID" value="${dto.username}"/>
                                    <input type="hidden" name="name" value="${param.name}"/>
                                    <input type="hidden" name="status" value="${param.status}"/>
                                    <c:choose>
                                        <c:when test="${dto.status eq 'Request'}">
                                            <input type="submit" name="action"  class="btn btn-outline-success" value="Approve Resource"/>
                                            <input type="submit" name="action"  class="btn btn-outline-danger" value="Deny Resource"/>
                                        </c:when>
                                        <c:when test="${dto.status eq 'Accept'}">
                                            <input type="submit" name="action" value="Recover Resource" class="btn btn-danger" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" name="action" value="Approve Resource" class="btn btn-outline-success" />
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </form>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty flag}">
                    <h1 class="display-4">No record found!</h1>
                </c:if>
            </div>

        </div>
    </div>


    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript">
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
    </script>
    <script>
        <c:if test="${not empty requestScope.ADMIN_ALERT}">
        swal("Notification", "${requestScope.ADMIN_ALERT}", "success");
        </c:if>

        <c:if test="${not empty requestScope.ADMIN_ALERT_WARNING}">
        swal("Notification", "${requestScope.ADMIN_ALERT_WARNING}", "warning");
        </c:if>


    </script>

    <script>
        const confirmDelete = (id) => {
            swal({
                title: "Do you want to continute?",
                text: "The data will not be recoverable!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                    document.getElementById(id).click();

//                        window.location = "admin.jsp"
                }
            });
        }
    </script>
</body>
</html>
