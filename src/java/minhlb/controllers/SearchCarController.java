/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minhlb.daos.ProductDAO;
import minhlb.dtos.Feedback;
import minhlb.dtos.Product;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "SearchCarController", urlPatterns = {"/SearchCarController"})
public class SearchCarController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String INDEX_PAGE = "index.jsp";
    private static final Logger LOGGER = Logger.getLogger(SearchCarController.class);
    private static final int PAGE_SIZE = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            int page;
            String pageId = request.getParameter("page");
            if (pageId == null) {
                page = 1;
            } else {
                page = Integer.parseInt(pageId);
            }
            if (request.getParameter("action") == null) {

            } else {
                switch (request.getParameter("action")) {
                    case "<":
                        page--;
                        break;
                    case ">":
                        page++;
                        break;
                    default:
                        break;
                }
            }
            String productName = request.getParameter("productName");
            ProductDAO productDAO = new ProductDAO();
            int from = page * PAGE_SIZE + 1 - PAGE_SIZE;
            int to = page * PAGE_SIZE;
            List<Product> listProduct = productDAO.getProductByName(productName, from, to);
            int count = productDAO.getProductCount(productName);
            int pageLimit = 0;
            if (count > 0) {
                if (count / PAGE_SIZE < 1) {
                    pageLimit = 1;
                } else {
                    pageLimit = Math.round(count / PAGE_SIZE);
                    if(count % PAGE_SIZE > 0){
                        pageLimit += 1;
                    }
                }
            }
            List<Feedback> listFeedback = new ArrayList<>();
            Feedback feedback = null;
            for (Product product : listProduct) {
                feedback = productDAO.getFeedbackRate(product.getProductId());
                if(feedback.getRate() <= 0){
                    feedback = new Feedback();
                    feedback.setProductId(product.getProductId());
                    feedback.setRate(10);
                }
                listFeedback.add(feedback);
            }
            if (listProduct != null && listProduct.size() > 0) {
                request.setAttribute("listProduct", listProduct);
                request.setAttribute("pageLimit", pageLimit);
                request.setAttribute("page", page);
                request.setAttribute("productName", productName);
                request.setAttribute("listFeedback", listFeedback);
                url = INDEX_PAGE;
            } else {
                request.setAttribute("SEARCH_ERROR", "Product is not found!");
                url = INDEX_PAGE;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at SearchCarController: " + e.getMessage());
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
