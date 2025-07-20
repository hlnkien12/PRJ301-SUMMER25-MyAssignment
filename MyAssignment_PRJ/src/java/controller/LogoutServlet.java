/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Huỷ phiên làm việc hiện tại
        HttpSession session = request.getSession(false); // false => không tạo mới nếu chưa có

        // Xoá toàn bộ session (đăng xuất)
        if (session != null) {
            session.invalidate();
        }

        // Chuyển hướng về trang đăng nhập
        response.sendRedirect("login?");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Xử lý POST giống GET nếu người dùng gọi POST
        doGet(request, response);
    }
}
