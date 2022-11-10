<%-- 
    Document   : WeeklySchedule
    Created on : 05-Jul-2022, 11:51:58
    Author     : apc
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Weekly Schedule</title>
    </head>
    <body>
        <div class="container">
            <table class="table table-bordered" border=5>
                <thead class="table-primary" >
                    <tr>
                        <th scope="col" rowspan="2" class="col-md-2">
                            <form action="schedule"method="POST">
                                Year 
                                <select name="year" onchange="this.form.submit()">
                                    <option <c:if test="${requestScope.year eq requestScope.currentYear}">selected="selected" </c:if> value="${requestScope.currentYear}">${requestScope.currentYear}</option>
                                    </select> <br/>
                                    <input type="hidden" name="selectedYear" value="${requestScope.year}">
                                Week 
                                <select name="week" onchange="this.form.submit()">
                                    <c:forEach items="${requestScope.weeks}" var="w" varStatus="loop">
                                        <option value="${loop.index}" <c:if test="${loop.index eq requestScope.week}">selected="selected"</c:if>>${w}</option>
                                    </c:forEach>
                                </select>
                            </form>
                        </th>
                        <th scope="col">Mon</th>
                        <th scope="col">Tue</th>
                        <th scope="col">Wed</th>
                        <th scope="col">Thu</th>
                        <th scope="col">Fri</th>
                        <th scope="col">Sat</th>
                        <th scope="col">Sun</th>
                    </tr>
                    <tr>
                        <c:forEach items="${requestScope.days}" var="d">
                            <th scope="col"><fmt:formatDate pattern = "dd/MM" value="${d}"/></th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.slots}" var="sl">
                        <tr>
                            <th scope="row">${sl.slname}</th>
                                <c:forEach items="${requestScope.days}" var="d">
                                <td>
                                    <c:forEach items="${requestScope.sessions}" var="se">
                                        <c:if test="${d eq se.applyDate and sl.slid eq se.slot.slid}">
                                            <a <c:if test="${se.attendances.isEmpty()}">href="attendance/create?gid=${se.group.gid}&seid=${se.seid}"</c:if>
                                               <c:if test="${!se.attendances.isEmpty()}">href="attendance/list?seid=${se.seid}"</c:if>>
                                                ${se.group.gname}
                                            </a><br/>
                                            at ${se.room.rname} <br/>
                                            <c:if test="${se.attendances.isEmpty()}"><a class="text-danger">(not yet)</a></c:if>
                                            <c:if test="${!se.attendances.isEmpty()}"><a class="text-success">(taken)</a></c:if><br/>
                                            <a class="text-white bg-success">(<fmt:formatDate type="time" timeStyle="short" value="${sl.start}" />-<fmt:formatDate type="time" timeStyle="short" value="${sl.end}" />)</a>
                                        </c:if>
                                    </c:forEach>
                                    -
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
