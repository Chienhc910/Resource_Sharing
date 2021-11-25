<%-- 
    Document   : searchadmin.jsp
    Created on : May 18, 2021, 3:17:06 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
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
                                    <input type="hidden" name="resourceID" value="${dto.id}"/>
                                    <input type="hidden" name="name" value="${param.name}"/>
                                    <input type="hidden" name="category" value="${param.category}"/>
                                    <input type="hidden" name="dateFrom" value="${param.dateFrom}"/>
                                    <input type="hidden" name="dateTo" value="${param.dateTo}"/>
                                    <button type="text" class="btn btn-outline-secondary">Can't use</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                </c:if>
                <c:if test="${requestScope.LIST_SIZE eq '0'}">
                    <h3 class="text-light display-6">No record found!</h3>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
