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
import model.Account;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ApproveRequesrServlet", urlPatterns = {"/approve-request"})
public class ApproveRequestServlet extends HttpServlet {

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
            int rid = Integer.parseInt(request.getParameter("rid"));
            int action = Integer.parseInt(request.getParameter("action")); // 1: approve, 2: reject
            int approverEid = acc.getEid();

            boolean success = new RequestForLeaveDAO().updateStatus(rid, action, approverEid);

            if (success) {
                response.sendRedirect("view-subordinate-requests");
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp"); // hoặc 404 nếu muốn chặn GET
    }
}
