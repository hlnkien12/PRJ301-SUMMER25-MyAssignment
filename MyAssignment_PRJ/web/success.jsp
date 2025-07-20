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
        <h2>🎉 Đơn xin nghỉ đã được gửi thành công!</h2>
        <p>Chúng tôi sẽ xử lý yêu cầu của bạn sớm nhất có thể.</p>
        <p>
            <a href="home-<%= ((Role)session.getAttribute("role")).getRname().toLowerCase().replace(" ", "-") %>.jsp">
                ← Quay lại trang chủ
            </a>
        </p>
    </div>
</body>
</html>
