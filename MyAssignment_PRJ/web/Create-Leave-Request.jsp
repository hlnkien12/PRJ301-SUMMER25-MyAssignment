<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Account, model.Role" %>
<%
    Account acc = (Account) session.getAttribute("user");
    Role role = (Role) session.getAttribute("role");

    if (acc == null || role == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String backUrl = "login.jsp";
    if (role != null) {
        String roleName = role.getRname().toLowerCase().replaceAll(" ", "");
        backUrl = "home-" + roleName + ".jsp";
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Leave Request</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/CreateLeaveRequest.css">
    </head>
    <body>
        <div class="form-container">
            <h2>Create Leave Request</h2>
            <form action="<%= request.getContextPath() %>/create-request-for-leave" method="post">
                <label>Title:</label>
                <input type="text" name="title" required><br>

                <label>From Date:</label>
                <input type="date" name="from" required><br>

                <label>To Date:</label>
                <input type="date" name="to" required><br>

                <label>Reason:</label>
                <textarea name="reason" rows="4" required></textarea><br>

                <button type="submit">Submit Request</button>
            </form>
            <a href="<%= backUrl %>">← Back to Home</a>
        </div>
    </body>
</html>
