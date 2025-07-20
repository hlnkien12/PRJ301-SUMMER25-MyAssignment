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
import dal.RequestForLeaveDAO;
import model.Account;
import model.RequestForLeave;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
/**
 *
 * @author Admin
 */
@WebServlet(name = "CreateRequestServlet", urlPatterns = {"/create-request-for-leave"})
public class CreateRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("user");

        if (acc == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String title = request.getParameter("title");
            Date from = Date.valueOf(request.getParameter("from"));
            Date to = Date.valueOf(request.getParameter("to"));
            String reason = request.getParameter("reason");

            // Lấy eid từ class account trong package model:
            int eid = acc.getEid(); 

            RequestForLeave rfl = new RequestForLeave();
            rfl.setTitle(title);
            rfl.setFrom(from);
            rfl.setTo(to);
            rfl.setReason(reason);
            rfl.setCreatedBy(eid);

            boolean success = new RequestForLeaveDAO().insert(rfl);

            if (success) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("Create-Leave-Requset.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Create-Leave-Requset.jsp?error=exception");
        }
    }
}
