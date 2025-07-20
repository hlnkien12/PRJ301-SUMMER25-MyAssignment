<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login JSP</title>
        <link rel="stylesheet" type="text/css" href="CSS/login.css">
    </head>
    <body>
        <div class="login-container">
            <h2></h2>

            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
            <div class="error-message"><%= error %></div>
            <% } %>

            <h2>Login</h2>
            <form method="post" action="login">
                <label>Username:</label>
                <input type="text" name="username" required>

                <label>Password:</label>
                <input type="password" name="password" required>

                <input type="submit" value="Login">
            </form>
        </div>
    </body>
</html>
