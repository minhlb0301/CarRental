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
import javax.servlet.http.HttpSession;
import minhlb.daos.UserDAO;
import minhlb.dtos.User;
import minhlb.utilities.APIWrapper;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "FacebookController", urlPatterns = {"/FacebookController"})
public class FacebookController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "index.jsp";
    private static final String INVALID = "login.jsp";
    private static final Logger LOGGER = Logger.getLogger(FacebookController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String code = request.getParameter("code");
            if (code == null || code.isEmpty()) {
                url = INVALID;
            } else {
                APIWrapper wrapper = new APIWrapper();
                String accessToken = wrapper.getAccessToken(code);
                wrapper.setAccessToken(accessToken);
                User user = wrapper.getUserInfo();
                UserDAO userDAO = new UserDAO();
                boolean userIsExist = userDAO.checkFacebookLogin(user.getFacebookId()) != null;
                if (userIsExist == false) {
                    user.setCreateDate(new Date());
                    user.setIsActive(true);
                    user.setIsAdmin(false);
                    user.setStatus("New");
                    userDAO.registerFacebook(user);
                }
                HttpSession session = request.getSession();
                session.setAttribute("User", user);
                url = SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at FacebookController: " + e.getMessage());
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
