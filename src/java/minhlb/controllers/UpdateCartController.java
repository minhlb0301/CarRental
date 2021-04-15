/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "UpdateCartController", urlPatterns = {"/UpdateCartController"})
public class UpdateCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String VIEW_CART = "view_cart.jsp";
    private static final Logger LOGGER = Logger.getLogger(UpdateCartController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Cart shoppingCart = (Cart) session.getAttribute("shoppingCart");
            String productId = request.getParameter("productId");
            String orderQuantity = request.getParameter("orderQuantity");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date pickupDate = formatter.parse(request.getParameter("pickupDate"));
            Date returnDate = formatter.parse(request.getParameter("returnDate"));
            int rentalDate = (int) ((returnDate.getTime() - pickupDate.getTime()) / (1000 * 60 * 60 * 24));
            if(rentalDate <= 0){
                rentalDate = 1;
            }
            shoppingCart.updateCart(productId, Integer.parseInt(orderQuantity), formatter.format(pickupDate), formatter.format(returnDate), rentalDate);
            session.setAttribute("shoppingCart", shoppingCart);
            url = VIEW_CART;
        } catch (Exception e) {
            LOGGER.error("ERROR at UpdateCartController: " + e.getMessage());
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
