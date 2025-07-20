<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.RequestForLeave, java.util.List, model.Role" %>
<%
    List<RequestForLeave> myRequests = (List<RequestForLeave>) request.getAttribute("myRequests");
    Role role = (Role) session.getAttribute("role");
    String homeUrl = "login.jsp";
    if (role != null) {
        String roleName = role.getRname().toLowerCase().replaceAll(" ", "");
        homeUrl = "home-" + role.getRname().toLowerCase().replaceAll(" ", "") + ".jsp";
    }
    


%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Leave Requests</title>
    <link rel="stylesheet" href="CSS/my-leave-requests.css">
</head>
<body>
    <h2>📄 My Leave Requests</h2>
    <table>
        <tr>
            <th>Title</th>
            <th>From</th>
            <th>To</th>
            <th>Reason</th>
            <th>Status</th>
        </tr>
        <%
            if (myRequests != null) {
                for (RequestForLeave r : myRequests) {
                    String statusText;
                    switch (r.getStatus()) {
                        case 1: statusText = "✅ Approved"; break;
                        case 2: statusText = "❌ Rejected"; break;
                        default: statusText = "⌛ Processing"; break;
                    }
        %>
        <tr>
            <td><%= r.getTitle() %></td>
            <td><%= r.getFrom() %></td>
            <td><%= r.getTo() %></td>
            <td><%= r.getReason() %></td>
            <td><%= statusText %></td>
        </tr>
        <% } } %>
    </table>
    <a href="<%= homeUrl %>">← Back to Home</a>
</body>
</html>
