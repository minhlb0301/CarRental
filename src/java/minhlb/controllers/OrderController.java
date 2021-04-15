/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhlb.daos.OrderDAO;
import minhlb.daos.ProductDAO;
import minhlb.dtos.Cart;
import minhlb.dtos.Order;
import minhlb.dtos.OrderDetail;
import minhlb.dtos.Product;
import minhlb.dtos.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String VIEW_CART = "view_cart.jsp";
    private static final Logger LOGGER = Logger.getLogger(OrderController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Cart shoppingCart = (Cart) session.getAttribute("shoppingCart");
            User user = (User) session.getAttribute("User");
            OrderDAO orderDAO = new OrderDAO();
            ProductDAO productDAO = new ProductDAO();
            List<String> outOfStockProduct = new ArrayList<>();
            for (Product product : shoppingCart.getCart().values()) {
                Product productDTO = productDAO.getProductById(product.getProductId());
                if (productDTO.getAvailable() < product.getAvailable()) {
                    outOfStockProduct.add(product.getProductName());
                }
            }
            if (outOfStockProduct.isEmpty()) {
                int orderCount = orderDAO.getOrderCount();
                String orderId = "OD-" + user.getUserId() + "-" +(orderCount + 1);
                Date createDate = new Date();
                Order order = new Order(orderId, user.getUserId(), "Waiting", shoppingCart.getTotal(), createDate);
                boolean isOrder = orderDAO.insertOrder(order);
                boolean isInsertDetail = false;
                if (isOrder == true) {
                    int index = 1;
                    for (Product product : shoppingCart.getCart().values()) {
                        String orderDetailId = orderId + "-" + (index++);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        OrderDetail orderDetail = new OrderDetail(orderDetailId, orderId, product.getProductId(), product.getAvailable(), 
                                product.getPrice(), formatter.parse(product.getPickupDate()), formatter.parse(product.getReturnDate()));
                        isInsertDetail = orderDAO.insertOrderDetail(orderDetail);
                        if(isInsertDetail == true){
                            productDAO.updateRemainCar(product.getProductId(), product.getAvailable(), false);
                            session.removeAttribute("shoppingCart");
                            url = VIEW_CART;
                        }
                    }
                }
            } else {
                request.setAttribute("OutOfStocks", outOfStockProduct);
                url = VIEW_CART;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at OrderController: " + e.getMessage());
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
