<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Role" %>
<%
    List<Role> roles = (List<Role>) session.getAttribute("roles");
    if (roles == null || roles.isEmpty()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Role</title>
    <link rel="stylesheet" type="text/css" href="CSS/select-role.css">
</head>
<body>
<div class="container">
    <h2>Select Your Role</h2>
    <form action="select-role" method="post">
        <select name="rid" required>
            <option value="">-- Choose a Role --</option>
            <% for (Role r : roles) { %>
                <option value="<%= r.getRid() %>"><%= r.getRname() %></option>
            <% } %>
        </select>
        <button type="submit">Continue</button>
    </form>
</div>
</body>
</html>
