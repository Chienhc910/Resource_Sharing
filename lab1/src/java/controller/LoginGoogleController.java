package controller;

import account.AccountDAO;
import account.AccountDTO;
import booking.BookingDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "LoginGoogleController", urlPatterns = {"/LoginGoogleController"})
public class LoginGoogleController extends HttpServlet {

    private static final String LOGIN = "AuthenticativeController";
    private static final String SIGNUP = "signup.jsp";
    private static final String LOGINPAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SIGNUP;
        try {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("gmail");
            AccountDAO dao = new AccountDAO();
            AccountDTO dto = dao.checkExist(email);
            if (dto == null) {
                request.setAttribute("EMAIL", email);
                request.setAttribute("FULLNAME", fullname);
                url = SIGNUP;
            } else {
                boolean check = dto.getStatus().equals("ACTIVE");
                if (!(dto.getStatus().equals("ACTIVE"))) {
                    url = LOGINPAGE;
                    request.setAttribute("INVALID_VALIDATION", "Your account is not accepted!");
                } else {
                    url = LOGIN;
                    HttpSession session = request.getSession();
                    List<BookingDTO> history = dao.getListHistory(email,"","");
                    dto.setHistory(history);
                    session.setAttribute("ACCOUNT", dto);
                }
            }
        } catch (Exception e) {
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
