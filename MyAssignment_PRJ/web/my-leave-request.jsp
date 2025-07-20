<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>My Leave Requests</title>
</head>
<body>
    <h2>My Leave Requests</h2>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>Title</th>
            <th>From</th>
            <th>To</th>
            <th>Reason</th>
            <th>Status</th>
            <th>Processed By</th>
        </tr>
        <c:forEach var="r" items="${myRequests}">
            <tr>
                <td>${r.title}</td>
                <td>${r.from}</td>
                <td>${r.to}</td>
                <td>${r.reason}</td>
                <td>
                    <c:choose>
                        <c:when test="${r.status == 0}">In Progress</c:when>
                        <c:when test="${r.status == 1}">Approved</c:when>
                        <c:when test="${r.status == 2}">Rejected</c:when>
                        <c:otherwise>Unknown</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${r.processedByName == null}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${r.processedByName}
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <!-- Điều hướng quay về đúng trang chủ -->
    <c:choose>
        <c:when test="${sessionScope.account.roleName == 'employee'}">
            <a href="home-employee.jsp">Back</a>
        </c:when>
        <c:when test="${sessionScope.account.roleName == 'leader'}">
            <a href="home-leader.jsp">Back</a>
        </c:when>
        <c:when test="${sessionScope.account.roleName == 'head of department'}">
            <a href="home-hod.jsp">Back</a>
        </c:when>
        <c:when test="${sessionScope.account.roleName == 'admin'}">
            <a href="home-admin.jsp">Back</a>
        </c:when>
        <c:otherwise>
            <a href="home.jsp">Back</a>
        </c:otherwise>
    </c:choose>
</body>
</html>
