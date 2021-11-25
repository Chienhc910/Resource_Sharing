package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pc
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "LoginController";
    private static final String SIGNUP = "SignUpController";
    private static final String LOGINGG = "LoginGoogleController";
    private static final String SIGNUPGG = "SignUpGGController";
    private static final String VERIFYACCOUNT = "VerifyAccountController";
    private static final String LOGOUT = "LogOutController";
    private static final String SEARCH = "SearchController";
    private static final String REQUESTRESOURCE = "RequestResourceController";
    private static final String CANCELREQUEST = "CancelRequestController";
    private static final String GETADMINDATA = "GetAllDataAdminController";
    private static final String APPROVEREQUEST = "ApproveRequestController";
    private static final String RECOVERRESOURCE = "RecoverResourceController";
    private static final String DELETEBOOKING = "DeleteBookingController";
    private static final String HISTORY = "HistoryController";
    private static final String DENYRESOURCE = "DenyResourceController";
    private static final String SEARCHHISTORY = "SearchHistoryController";
    private static final String VIEWDETAIL = "ViewDetailController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = ERROR;
        try {
            switch (action) {
                case "Login":
                    url = LOGIN;
                    break;
                case "Sign Up":
                    url = SIGNUP;
                    break;
                case "LoginGoogle":
                    url = LOGINGG;
                    break;
                case "Submit Info":
                    url = SIGNUPGG;
                    break;
                case "VerifyAccount":
                    url = VERIFYACCOUNT;
                    break;
                case "Log out":
                    url = LOGOUT;
                    break;
                case "Search Resource":
                    url = SEARCH;
                    break;
                case "Request Resource":
                    url = REQUESTRESOURCE;
                    break;
                case "Cancel Request":
                    url = CANCELREQUEST;
                    break;
                case "Search Booking":
                    url = GETADMINDATA;
                    break;
                case "Approve Resource":
                    url = APPROVEREQUEST;
                    break;
                case "Recover Resource":
                    url = RECOVERRESOURCE;
                    break;
                case "Delete History":
                    url = DELETEBOOKING;
                    break;
                case "History":
                    url = HISTORY;
                    break;
                case "Deny Resource":
                    url = DENYRESOURCE;
                    break;
                case "Search History":
                    url = SEARCHHISTORY;
                    break;
                case "View Resource":
                    url = VIEWDETAIL;
                    break;
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
