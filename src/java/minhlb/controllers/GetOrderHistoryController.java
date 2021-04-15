/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
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
@WebServlet(name = "GetOrderHistoryController", urlPatterns = {"/GetOrderHistoryController"})
public class GetOrderHistoryController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String HISTORY_PAGE = "history.jsp";
    private static final Logger LOGGER = Logger.getLogger(GetOrderHistoryController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("User");
            OrderDAO orderDAO = new OrderDAO();
            List<Order> listOrder = null;
            if (user.isIsAdmin() == false) {
                listOrder = orderDAO.getOrderByUserId(user.getUserId());

            } else {
                listOrder = orderDAO.getOrder("", null, null);
            }
            if (listOrder.isEmpty()) {
                request.setAttribute("HISTORY_ERROR", "No record found! You may get some order first!");
                url = HISTORY_PAGE;
            } else {
                request.setAttribute("listOrder", listOrder);
                url = HISTORY_PAGE;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at GetOrderHistoryController: " + e.getMessage());
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
