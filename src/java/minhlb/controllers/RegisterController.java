/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minhlb.daos.UserDAO;
import minhlb.dtos.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String INVALID = "register.jsp";
    private static final String SUCCESS = "login.jsp";
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String url = ERROR;
        try {
            String userId = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String name = request.getParameter("txtName");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            Date createDate = new Date();
            User user = new User();
            user.setUserId(userId);
            user.setUsername(name);
            user.setAddress(address);
            user.setPassword(password);
            user.setPhone(phone);
            user.setCreateDate(createDate);
            user.setIsActive(true);
            user.setIsAdmin(false);
            user.setStatus("New");
            UserDAO userDAO = new UserDAO();
            boolean isRegiste = userDAO.register(user);
            if(isRegiste){
                url = SUCCESS;
            }else{
                url = INVALID;
                request.setAttribute("REGISTER_ERROR", "");
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at RegisterController: " + e.getMessage());
            if(e.getMessage().contains("duplicate")){
                url = INVALID;
                request.setAttribute("REGISTER_ERROR", "Email is existed!");
            }
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
