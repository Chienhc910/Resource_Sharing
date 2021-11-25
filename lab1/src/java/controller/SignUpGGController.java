package controller;

import account.AccountDAO;
import account.AccountDTO;
import account.AccountERROR;
import utils.VerifyRecaptcha;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
@WebServlet(name = "SignUpGGController", urlPatterns = {"/SignUpGGController"})
public class SignUpGGController extends HttpServlet {

    private static final String INVALID = "signup.jsp";
    private static final String SUCCESS = "AuthenticativeController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID;
        try {
            String username = request.getParameter("usernamesu");
            String fullname = request.getParameter("fullnamesu");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean recaptchaResult = VerifyRecaptcha.verify(gRecaptchaResponse);
            boolean checkValidation = true;
            AccountERROR error = new AccountERROR();
            if (!recaptchaResult) {
                request.setAttribute("INVALID_RECAPTCHA", "You must confirm you are not a robot!");
            }
            if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                checkValidation = false;
                error.setPhone("Your phone is invalid!");
            }
            if(recaptchaResult && checkValidation){
                AccountDTO dto = new AccountDTO(username, phone, fullname, address, "", "3", "2");
                AccountDAO dao = new AccountDAO();
                if(dao.signUp(dto, "2")){
                    HttpSession session = request.getSession();
                    session.setAttribute("ACCOUNT", dao.checkExist(username));
                    url = SUCCESS;
                }
            }else{
                request.setAttribute("INVALID", error);
            }
        } catch (Exception e) {
        }finally{
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
