<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List</title>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container text-center" >
            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Group</th>
                        <th scope="col">Code</th>
                        <th scope="col">Name</th>
                        <th scope="col">Image</th>
                        <th scope="col">Status</th>
                        <th scope="col">Comment</th>
                        <th scope="col">Taker</th>
                        <th scope="col">Record Time</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.attendances}" var="a" varStatus="loop">
                        <tr>
                            <th scope="row">${loop.index+1}</th>
                            <td><a>${a.session.group.gname}</a></td>
                            <td><a>${a.student.scode}</a></td>
                            <td><a>${a.student.sname}</a></td>
                            <td><img src="${a.student.img}" class="rounded" width="150" height="150" alt="student picture"/></td>
                            <td><span <c:if test="${a.status eq 'attended'}">class="text-success"</c:if><c:if test="${a.status ne 'attended'}">class="text-danger"</c:if>>${a.status}</span></td>
                            <td>${a.comment}</td>
                            <td>${a.lecturer.lname}</td>
                            <td><fmt:formatDate type="both" pattern = "dd-MM-yyyy hh:mm:ss" value="${a.editDate}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input type="hidden" name="seid" value="${requestScope.seid}">
            <a href="../attendance/edit?seid=${requestScope.seid}"><input type="button" value="Edit"></a>
            <a href="../schedule"><input type="button" value="Back"></a>
        </div>
    </body>
</html>
