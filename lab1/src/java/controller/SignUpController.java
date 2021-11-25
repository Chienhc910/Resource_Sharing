package controller;

import account.AccountDAO;
import account.AccountDTO;
import account.AccountERROR;
import utils.SentMail;
import utils.VerifyRecaptcha;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pc
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        AccountERROR error = new AccountERROR();
        try {
            String username = request.getParameter("usernamesu");
            String password = request.getParameter("passwordsu");
            String rePassword = request.getParameter("rePasswordsu");
            String fullname = request.getParameter("fullnamesu");
            String address = request.getParameter("addresssu");
            String phone = request.getParameter("phonesu");

            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean recaptchaResult = VerifyRecaptcha.verify(gRecaptchaResponse);
            boolean checkValidation = true;

            if (!password.equals(rePassword)) {
                checkValidation = false;
                error.setConfirmPassword("Password and Re-password must be matched!");
            }

            if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                checkValidation = false;
                error.setPhone("Your phone is invalid!");
            }

            if (address.isEmpty()) {
                error.setAddress("Not allow empty!");
            }

            if (password.isEmpty()) {
                error.setPassword("Not allow empty!");
            }

            if (username.isEmpty()) {
                error.setUsername("Not allow empty!");
            }

            if (fullname.isEmpty()) {
                error.setFullname("Not allow empty!");
            }

            if (!checkValidation) {
                request.setAttribute("INVALID_SIGNUP", error);
            }

            if (!recaptchaResult) {
                request.setAttribute("INVALID_VALIDATION_SIGNUP", "You must confirm you are not a robot!");
            } else {
                if (checkValidation) {
                    AccountDTO dto = new AccountDTO(username, phone, fullname, address, "", "3", "1");
                    dto.setPassword(password);
                    AccountDAO dao = new AccountDAO();
                    if (dao.signUp(dto, "1")) {
                        Random rd = new Random();
                        float random = rd.nextFloat();
                        String code = rd.toString();
                        code = code.substring(code.length() - 5, code.length());
                        dao.setActiveCode(username, code);
                        request.setAttribute("ACTIVE_CODE", code);
                        SentMail sent = new SentMail();
                        sent.sentEmail(code, dto);
                        request.setAttribute("SIGNUP_SUCCESS", "Your account is created, go to your email to activate your account!");
                    }
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate")) {
                error.setUsername("This email already in the system!");
                request.setAttribute("INVALID_SIGNUP", error);
            }
        } finally {
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
