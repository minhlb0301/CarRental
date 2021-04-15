/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhlb.daos.OrderDAO;
import minhlb.dtos.Order;
import minhlb.dtos.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "SearchOrderController", urlPatterns = {"/SearchOrderController"})
public class SearchOrderController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String HISTORY_PAGE = "history.jsp";
    private static final Logger LOGGER = Logger.getLogger(SearchOrderController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("User");
            String searchId = request.getParameter("orderId");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");
            Date fromDate, toDate;
            if (fromDateStr.isEmpty()) {
                fromDate = null;
            } else {
                fromDate = formatter.parse(fromDateStr);
            }
            if (toDateStr.isEmpty()) {
                toDate = null;
            } else {
                toDate = formatter.parse(toDateStr);
            }
            OrderDAO orderDAO = new OrderDAO();
            List<Order> listOrder = null;
            if (user.isIsAdmin() == true) {
                listOrder = orderDAO.getOrder(searchId, fromDate, toDate);
            } else {
                listOrder = orderDAO.searchOrder(searchId, user.getUserId(), fromDate, toDate);
            }
            System.out.println("listOrder.size = " + listOrder.size());
            if (listOrder != null || listOrder.size() > 0) {
                request.setAttribute("listOrder", listOrder);
                url = HISTORY_PAGE;
            } else {
                request.setAttribute("HISTORY_ERROR", "No record found!");
                url = HISTORY_PAGE;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at SearchOrderController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
