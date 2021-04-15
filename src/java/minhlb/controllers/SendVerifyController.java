/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
@WebServlet(name = "SendVerifyController", urlPatterns = {"/SendVerifyController"})
public class SendVerifyController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String VERIFY_PAGE = "verify.jsp";
    private static final Logger LOGGER = Logger.getLogger(SendVerifyController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String url = ERROR;
        try {
            Properties mailServerProperties;
            Session mailSession;
            MimeMessage mailMessage;
            HttpSession session = request.getSession();
            String userId = request.getParameter("txtEmail");
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");
            mailSession = Session.getDefaultInstance(mailServerProperties, null);
            mailMessage = new MimeMessage(mailSession);
            mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(userId));
            mailMessage.setSubject("Verify user account");
            Random generator = new Random(100000);
            String verifyCode = String.valueOf(generator.nextInt(100000));
            session.setAttribute("verifyCode", verifyCode);
            mailMessage.setText(verifyCode);
            try (Transport transport = mailSession.getTransport("smtp")) {
                transport.connect("smtp.gmail.com", "killblueeyedraagon@gmail.com", "123456123asd");
                transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            }
            url = VERIFY_PAGE;
            request.setAttribute("txtEmail", userId);
            request.setAttribute("txtPassword", request.getParameter("txtPassword"));
            request.setAttribute("txtName", request.getParameter("txtName"));
            request.setAttribute("txtPhone", request.getParameter("txtPhone"));
            request.setAttribute("txtAddress", request.getParameter("txtAddress"));
        } catch (Exception e) {
            LOGGER.error("ERROR at SendVerifyController: " + e.getMessage());
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
