<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Account" %>
<%
    Account acc = (Account) session.getAttribute("user");
    if (acc == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="CSS/home.css">
    </head>
    <body>
        <div class="header">👑 Admin - Welcome <%= acc.getDisplayname() %></div>
        <div class="container">
            <h2>Admin Features:</h2>
            <ul>
                <li><a href="/dashboard">📊 View Dashboard</a></li>
                <li><a href="Create-Leave-Request.jsp">📝 Create Leave Request</a></li>
                <li><a href="view-subordinate-requests">📁 View Subordinate Leave Requests</a></li>
                <li><a href="approve-request">✅ Approve Leave Request</a></li>
                <li><a href="my-leave-request">📂 View My Leave Requests</a></li>
                <li><a href="/employee/manage">👥 Manage Employees</a></li>
                <li><a href="/department/staffs">🏢 View Department Staff</a></li>
                <li><a href="/rfl/history">📜 View Leave Approval History</a></li>
                <li><a href="/rfl/balance">📅 View Leave Balance</a></li>
                <li><a href="/admin">⚙️ Admin Control Panel</a></li>
            </ul>
            <div class="logout">
                <a href="logout">Logout</a>
            </div>
        </div>
    </body>
</html>
