<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.RequestForLeave" %>
<%@ page import="java.util.List" %>
<%
    List<RequestForLeave> myRequests = (List<RequestForLeave>) request.getAttribute("myRequests");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Leave Requests</title>
        <link rel="stylesheet" href="CSS/my-leave-requests.css">

    </head>
    <body>
        <h2>📄 My Leave Requests</h2>
        <table border="1">
            <tr>
                <th>Title</th>
                <th>From</th>
                <th>To</th>
                <th>Reason</th>
                <th>Status</th>
            </tr>
            <%
                if (myRequests != null) {
                    for (RequestForLeave r : myRequests) {
            %>
            <tr>
                <td><%= r.getTitle() %></td>
                <td><%= r.getFrom() %></td>
                <td><%= r.getTo() %></td>
                <td><%= r.getReason() %></td>
                <td>
                    <%
                        String statusText = "";
                         switch (r.getStatus()) {
                            case 1:
                                statusText = "✅ Approved";
                                break;
                            case 2:
                                statusText = "❌ Rejected";
                                break;
                            default:
                                statusText = "⌛ Processing";
                        };
                    %>
                    <%= statusText %>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <br>
        <a href="home-<%= ((model.Role)session.getAttribute("role")).getRname().toLowerCase().replace(" ", "") %>.jsp">← Back to Home</a>
    </body>
</html>
