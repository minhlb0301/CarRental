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
import minhlb.dtos.Cart;
import minhlb.dtos.Product;
import minhlb.dtos.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "AddCartController", urlPatterns = {"/AddCartController"})
public class AddCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String RELOAD_PAGE = "SearchCarController";
    private static final Logger LOGGER = Logger.getLogger(AddCartController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("User");
            Cart shoppingCart = (Cart) session.getAttribute("shoppingCart");
            if (shoppingCart == null) {
                shoppingCart = new Cart(user.getUserId());
            }
            String productId = request.getParameter("productId");
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(productId);
            product.setAvailable(1);
            shoppingCart.addToCart(product);
            session.setAttribute("shoppingCart", shoppingCart);
            request.setAttribute("page", request.getParameter("page"));
            request.setAttribute("productName", request.getParameter("productName"));
            url = RELOAD_PAGE;
        } catch (Exception e) {
            LOGGER.error("ERROR at AddCartController: " + e.getMessage());
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
