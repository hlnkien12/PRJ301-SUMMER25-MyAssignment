package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Role;

@WebServlet(name = "SelectRoleServlet", urlPatterns = {"/select-role"})
public class SelectRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roleIdStr = request.getParameter("rid");

        try {
            int selectedRid = Integer.parseInt(roleIdStr);

            HttpSession session = request.getSession();
            List<Role> userRoles = (List<Role>) session.getAttribute("roles");

            if (userRoles == null) {
                response.sendRedirect("login.html");
                return;
            }

            Role selectedRole = null;

            // ✅ Nếu là employee (rid = 4), tạo mới luôn
            if (selectedRid == 4) {
                selectedRole = new Role();
                selectedRole.setRid(4);
                selectedRole.setRname("Employee");
            } else {
                // ✅ Với các role khác, chỉ cho phép nếu có trong danh sách
                for (Role role : userRoles) {
                    if (role.getRid() == selectedRid) {
                        selectedRole = role;
                        break;
                    }
                }
            }

            if (selectedRole != null) {
                session.setAttribute("role", selectedRole);

                // ✅ Chuyển hướng đến trang phù hợp
                String roleName = selectedRole.getRname().toLowerCase();
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
                    case "employee":
                        response.sendRedirect("home-employee.jsp");
                        break;
                    default:
                        response.sendRedirect("select-role.jsp?error=unknown_role");
                        break;
                }
            } else {
                // Không tìm thấy role hợp lệ
                response.sendRedirect("select-role.jsp?error=invalid");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("select-role.jsp?error=invalid_format");
        }
    }
}
