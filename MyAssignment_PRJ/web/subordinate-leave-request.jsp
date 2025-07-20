<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.RequestForLeave, model.Account, model.Role" %>
<%
    Account acc = (Account) session.getAttribute("user");
    Role role = (Role) session.getAttribute("role");
    if (acc == null || role == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<RequestForLeave> list = (List<RequestForLeave>) request.getAttribute("requests");

    String backUrl = "home.jsp";
    switch (role.getRname().toLowerCase()) {
        case "employee": backUrl = "home-employee.jsp"; break;
        case "leader": backUrl = "home-leader.jsp"; break;
        case "head of department": backUrl = "home-headofdepartment.jsp"; break;
        case "admin": backUrl = "home-admin.jsp"; break;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Subordinate Leave Requests</title>
    <link rel="stylesheet" href="CSS/subordinate-requests.css">
</head>
<body>
    <h2>📁 Subordinate Leave Requests</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>From</th>
            <th>To</th>
            <th>Reason</th>
            <th>Status</th>
            <th>Created By (EID)</th>
        </tr>
        <%
            if (list != null && !list.isEmpty()) {
                for (RequestForLeave r : list) {
                    String statusText;
                    switch (r.getStatus()) {
                        case 1: statusText = "✅ Approved"; break;
                        case 2: statusText = "❌ Rejected"; break;
                        default: statusText = "⏳ Processing"; break;
                    }
        %>
        <tr>
            <td><%= r.getRid() %></td>
            <td><%= r.getTitle() %></td>
            <td><%= r.getFrom() %></td>
            <td><%= r.getTo() %></td>
            <td><%= r.getReason() %></td>
            <td><%= statusText %></td>
            <td><%= r.getCreatedBy() %></td>
        </tr>
        <%      }
            } else { %>
        <tr><td colspan="7">No leave requests found.</td></tr>
        <% } %>
    </table>

    <br>
    <a href="<%= backUrl %>">← Back</a>
</body>
</html>
