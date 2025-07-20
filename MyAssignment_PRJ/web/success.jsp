<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Account, model.Role" %>
<%
    Account acc = (Account) session.getAttribute("user");
    if (acc == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Success</title>
    <link rel="stylesheet" href="CSS/success.css">
</head>
<body>
    <div class="message-box">
        <h2>🎉 The leave application has been successfully submitted!</h2>
        <p>Please wait for approval within 24 hours after submission!</p>
        <p>
            <a href="home-<%= ((Role)session.getAttribute("role")).getRname().toLowerCase().replace(" ", "") %>.jsp">
                ← Back to home page
            </a>
        </p>
    </div>
</body>
</html>
