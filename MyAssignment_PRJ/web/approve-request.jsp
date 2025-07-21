<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.RequestForLeave, model.Role" %>
<%
    List<RequestForLeave> pending = (List<RequestForLeave>) request.getAttribute("pendingRequests");
    Role role = (Role) session.getAttribute("role");
    String homeUrl = "home.jsp";
    if (role != null) {
        homeUrl = "home-" + role.getRname().toLowerCase().replaceAll(" ", "") + ".jsp";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Approve Leave Requests</title>
    <link rel="stylesheet" href="CSS/approve-requests.css">
</head>
<body>
    <h2>📝 Pending Leave Requests</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>From</th>
            <th>To</th>
            <th>Reason</th>
            <th>Created By</th>
            <th>Actions</th>
        </tr>
        <%
            if (pending != null && !pending.isEmpty()) {
                for (RequestForLeave r : pending) {
        %>
        <tr>
            <td><%= r.getRid() %></td>
            <td><%= r.getTitle() %></td>
            <td><%= r.getFrom() %></td>
            <td><%= r.getTo() %></td>
            <td><%= r.getReason() %></td>
            <td><%= r.getCreatedBy() %></td>
            <td>
                <form action="approve-request" method="post" style="display:inline;">
                    <input type="hidden" name="rid" value="<%= r.getRid() %>">
                    <input type="hidden" name="action" value="1">
                    <button type="submit" onclick="return confirm('Approve this request?')">✅ Approve</button>
                </form>
                <form action="approve-request" method="post" style="display:inline;">
                    <input type="hidden" name="rid" value="<%= r.getRid() %>">
                    <input type="hidden" name="action" value="2">
                    <button type="submit" onclick="return confirm('Reject this request?')">❌ Reject</button>
                </form>
            </td>
        </tr>
        <% } } else { %>
            <tr><td colspan="7" style="text-align:center;">No pending requests.</td></tr>
        <% } %>
    </table>

    <br><a href="<%= homeUrl %>">← Back to Home</a>
</body>
</html>
