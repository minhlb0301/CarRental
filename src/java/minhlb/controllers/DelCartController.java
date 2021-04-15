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
import minhlb.dtos.Cart;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "DelCartController", urlPatterns = {"/DelCartController"})
public class DelCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String VIEW_CART = "view_cart.jsp";
    private static final Logger LOGGER = Logger.getLogger(DelCartController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Cart shoppingCart = (Cart) session.getAttribute("shoppingCart");
            String productId = request.getParameter("productId");
            if (productId != null) {
                shoppingCart.removeCart(productId);
            }
            if (shoppingCart.getCart().isEmpty()) {
                session.removeAttribute("shoppingCart");
            } else {
                session.setAttribute("shoppingCart", shoppingCart);
            }
            url = VIEW_CART;
        } catch (Exception e) {
            LOGGER.error("ERROR at DelCartController: " + e.getMessage());
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
