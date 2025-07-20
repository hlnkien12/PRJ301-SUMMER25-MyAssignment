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
        <title>Employee Dashboard</title>
        <link rel="stylesheet" href="CSS/home.css">
    </head>
    <body>
        <div class="header">👤 Employee - <%= acc.getDisplayname() %></div>
        <div class="container">
            <h2>Employee Actions:</h2>
            <ul>
                <li><a href="/dashboard">📊 View Dashboard</a></li>
                <li><a href="/rfl/create">📝 Create Leave Request</a></li>
                <li><a href="/rfl/self">📂 View My Leave Requests</a></li>
                <li><a href="/rfl/balance">📅 View Leave Balance</a></li>
            </ul>
            <div class="logout">
                <a href="logout">Logout</a>
            </div>
        </div>
    </body>
</html>
