<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.RequestForLeave, model.Account, model.Role" %>
<%
    Account acc = (Account) session.getAttribute("user");
    Role role = (Role) session.getAttribute("role");

    if (acc == null || role == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<RequestForLeave> requests = (List<RequestForLeave>) request.getAttribute("requests");

    String backUrl = "login.jsp";
    if (role != null) {
        backUrl = "home-" + role.getRname().toLowerCase().replaceAll(" ", "") + ".jsp";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Approve Leave Requests</title>
</head>
<body>
    <h2>📁 Approve Leave Requests</h2>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>RID</th>
            <th>Title</th>
            <th>From</th>
            <th>To</th>
            <th>Reason</th>
            <th>Status</th>
            <th>Created By</th>
            <th>Action</th>
        </tr>
        <%
            if (requests != null) {
                for (RequestForLeave r : requests) {
        %>
        <tr>
            <td><%= r.getRid() %></td>
            <td><%= r.getTitle() %></td>
            <td><%= r.getFrom() %></td>
            <td><%= r.getTo() %></td>
            <td><%= r.getReason() %></td>
            <td>
                <%
                    String statusText;
                    switch (r.getStatus()) {
                        case 1: statusText = "✅ Approved"; break;
                        case 2: statusText = "❌ Rejected"; break;
                        default: statusText = "⌛ Processing"; break;
                    }
                    out.print(statusText);
                %>
            </td>
            <td><%= r.getCreatedBy() %></td>
            <td>
                <%
                    if (r.getStatus() == 0) {
                %>
                <form action="<%= request.getContextPath() %>/approve-request" method="post" style="display:inline;">
                    <input type="hidden" name="rid" value="<%= r.getRid() %>">
                    <input type="hidden" name="action" value="1">
                    <button type="submit" onclick="return confirm('Approve this request?')">✅ Approve</button>
                </form>
                <form action="<%= request.getContextPath() %>/approve-request" method="post" style="display:inline;">
                    <input type="hidden" name="rid" value="<%= r.getRid() %>">
                    <input type="hidden" name="action" value="2">
                    <button type="submit" onclick="return confirm('Reject this request?')">❌ Reject</button>
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
            }
        %>
    </table>

    <br>
    <a href="<%= backUrl %>">← Back to Home</a>
</body>
</html>
