package controller;

import dal.RequestForLeaveDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.RequestForLeave;

@WebServlet(name = "MyLeaveRequestServlet", urlPatterns = {"/my-leave-requests"})
public class MyLeaveRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        RequestForLeaveDAO dao = new RequestForLeaveDAO();
        List<RequestForLeave> myRequests = dao.getRequestsCreatedBy(acc.getEid());

        request.setAttribute("myRequests", myRequests);
        request.getRequestDispatcher("my-leave-requests.jsp").forward(request, response);
    }
}
