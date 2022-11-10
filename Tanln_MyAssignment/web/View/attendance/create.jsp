<%-- 
    Document   : create
    Created on : 08-Jul-2022, 18:51:01
    Author     : apc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create</title>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <form action="create" method="POST">
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
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.students}" var="s" varStatus="loop">
                        <tr>
                            <th scope="row">${loop.index+1}</th>
                            <td><a>${requestScope.group.gname}</a></td>
                            <td><a>${s.scode}</a></td>
                            <td><a>${s.sname}</a></td>
                            <td><img src="${s.img}" class="rounded" width="150" height="150" alt="student picture"/></td>
                            <td>
                                <input id="absent${loop.index}" type="radio" checked="checked" name="status${loop.index}" value="absent" /> <label for="absent${loop.index}" class="text-danger">absent</label> 
                                <input id="attend${loop.index}" type="radio" name="status${loop.index}" value="attend" /> <label for="attend${loop.index}" class="text-success">attend</label>
                                <input type="hidden" name="id${loop.index}" value="${s.sid}"/>
                                <input type="hidden" name="index" value="${loop.index}"/>
                            </td>
                            <td><input type="text" name="comment${loop.index}"></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                <input type="hidden" name="seid" value="${requestScope.seid}">
                <input type="submit" value="Save">
                <a href="../schedule"><input type="button" value="Cancel"></a>
                
            </div>
        </form>
    </body>
</html>
