/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhlb.daos.ProductDAO;
import minhlb.dtos.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "FeedbackController", urlPatterns = {"/FeedbackController"})
public class FeedbackController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String RELOAD = "GetOrderDetailController";
    private static final Logger LOGGER = Logger.getLogger(FeedbackController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("User");
            String orderId = request.getParameter("orderId");
            String productId = request.getParameter("productId");
            int rate = Integer.parseInt(request.getParameter("rate"));
            ProductDAO productDAO = new ProductDAO();
            boolean isFeedback = productDAO.insertFeedback(user.getUserId(), productId, rate);
            if (isFeedback == true) {
                request.setAttribute("rate", rate);
                url = RELOAD + "?orderId=" + orderId;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at FeedbackController: " + e.getMessage());
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
