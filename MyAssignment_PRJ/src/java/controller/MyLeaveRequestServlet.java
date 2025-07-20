package controller;

import dal.RequestForLeaveDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.RequestForLeave;

@WebServlet(name = "MyLeaveRequestServlet", urlPatterns = {"/my-leave-request"})
public class MyLeaveRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("user");

        if (acc == null || acc.getEid() == -1) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<RequestForLeave> myRequests = new RequestForLeaveDAO().getRequestsByEid(acc.getEid());
        request.setAttribute("myRequests", myRequests);
        request.getRequestDispatcher("my-leave-request.jsp").forward(request, response);
    }
}
