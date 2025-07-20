<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.RequestForLeave, model.Account, model.Role" %>
<%
    Account acc = (Account) session.getAttribute("user");
    Role role = (Role) session.getAttribute("role");
    if (acc == null || role == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<RequestForLeave> list = (List<RequestForLeave>) request.getAttribute("requests");

    String homeUrl = "login.jsp";
    if (role != null) {
        String roleName = role.getRname().toLowerCase().replaceAll(" ", "");
        homeUrl = "home-" + roleName + ".jsp";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Subordinate Leave Requests</title>
</head>
<body>
    <h2>Subordinate Leave Requests</h2>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>From</th>
            <th>To</th>
            <th>Reason</th>
            <th>Status</th>
            <th>Created By</th>
            <th>Actions</th>
        </tr>
        <%
            if (list != null && !list.isEmpty()) {
                for (RequestForLeave r : list) {
                    String statusText;
                    switch (r.getStatus()) {
                        case 1: statusText = "Approved"; break;
                        case 2: statusText = "Rejected"; break;
                        default: statusText = "Processing"; break;
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
            <td>
                <%
                    if (r.getStatus() == 0) {
                %>
                <form action="<%= request.getContextPath() %>/approve-request" method="post" style="display:inline;">
                    <input type="hidden" name="rid" value="<%= r.getRid() %>">
                    <input type="hidden" name="action" value="1">
                    <button type="submit">Approve</button>
                </form>
                <form action="<%= request.getContextPath() %>/approve-request" method="post" style="display:inline;">
                    <input type="hidden" name="rid" value="<%= r.getRid() %>">
                    <input type="hidden" name="action" value="2">
                    <button type="submit">Reject</button>
                </form>
                <%
                    } else {
                        out.print("N/A");
                    }
                %>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="8">No leave requests found.</td>
        </tr>
        <% } %>
    </table>
    <br>
    <a href="<%= homeUrl %>">← Back to Home</a>
</body>
</html>
