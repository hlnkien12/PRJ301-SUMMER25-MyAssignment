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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dal.RequestForLeaveDAO;
import java.util.List;
import model.Account;
import model.RequestForLeave;
import model.Role;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ApproveRequesrServlet", urlPatterns = {"/approve-request"})
public class ApproveRequestServlet extends HttpServlet {
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

        List<RequestForLeave> pendingList = new RequestForLeaveDAO()
            .getPendingRequestsForApproval(acc.getEid(), role.getRname());

        request.setAttribute("pendingRequests", pendingList);
        request.getRequestDispatcher("approve-request.jsp").forward(request, response);
    }
    
}
