package controller;

import dal.AccountDAO;
import model.Account;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import model.Role;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountDAO dao = new AccountDAO();
        Account acc = dao.checkLogin(username, password);

        if (acc != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", acc);

            // Thêm đoạn lấy roles:
            dal.RoleDAO roleDAO = new dal.RoleDAO();
            List<Role> roles = roleDAO.getRolesByAccountId(acc.getAid());
            session.setAttribute("roles", roles);

            if (roles.size() == 1) {
                // Gán luôn nếu chỉ có 1 role:
                session.setAttribute("role", roles.get(0));
                String roleName = roles.get(0).getRname().toLowerCase();
                switch (roleName) {
                    case "admin":
                        response.sendRedirect("home-admin.jsp");
                        break;
                    case "head of department":
                        response.sendRedirect("home-headofdepartment.jsp");
                        break;
                    case "leader":
                        response.sendRedirect("home-leader.jsp");
                        break;
                    default:
                        response.sendRedirect("home-employee.jsp");
                }

            } else {
                // Nếu nhiều role thì chọn:
                response.sendRedirect("select-role.jsp");
            }

        } else {
            request.setAttribute("error", "❌ Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
