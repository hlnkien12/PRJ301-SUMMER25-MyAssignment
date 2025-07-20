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
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import dal.RequestForLeaveDAO;
import model.Account;
import model.RequestForLeave;
import model.Role;
/**
 *
 * @author Admin
 */
@WebServlet(name="SubordinateRequestsServlet", urlPatterns={"/view-subordinate-requests"})
public class SubordinateRequestsServlet extends HttpServlet {
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("user");
        Role role = (Role) session.getAttribute("role");

        if (acc == null || role == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<RequestForLeave> list = new RequestForLeaveDAO()
            .getRequestsOfSubordinates(acc.getEid(), role.getRname());

        request.setAttribute("requests", list);
        request.getRequestDispatcher("subordinate-leave-request.jsp").forward(request, response);
    }
}