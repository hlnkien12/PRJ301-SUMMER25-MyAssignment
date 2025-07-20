<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.RequestForLeave, model.Account" %>
<%
    Account acc = (Account) session.getAttribute("user");
    if (acc == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<RequestForLeave> list = (List<RequestForLeave>) request.getAttribute("requests");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Subordinate Leave Requests</title>
    <link rel="stylesheet" href="CSS/request-table.css">
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
        <% } %>
    </table>
    <a href="home-<%= session.getAttribute("role") != null ? ((model.Role)session.getAttribute("role")).getRname().toLowerCase().replace(" ", "") : "hod" %>.jsp">← Back</a>
</body>
</html>
