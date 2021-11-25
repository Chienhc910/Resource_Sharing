package controller;

import account.AccountDTO;
import booking.BookingDAO;
import resource.ResourceDAO;
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
@WebServlet(name = "ApproveRequestController", urlPatterns = {"/ApproveRequestController"})
public class ApproveRequestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "GetAllDataAdminController";
        try {
            BookingDAO dao = new BookingDAO();
            ResourceDAO resourceDAO = new ResourceDAO();
            String resourceID = request.getParameter("resourceID");
            String resourceName = request.getParameter("resourceName");
            String userID = request.getParameter("userID");
            HttpSession session = request.getSession();
            AccountDTO dto = (AccountDTO) session.getAttribute("ACCOUNT");
            if (resourceDAO.changeNumberOfResource(resourceID, "-")) {
                if (dao.changeStatus("ST2", resourceID, userID,dto.getUserid())) {
                    request.setAttribute("ACTIVE_REQUEST ", "not null");
                    request.setAttribute("ADMIN_ALERT", "Allow " + dto.getFullname() + " to access resource " + resourceName);
                }
            } else {
                request.setAttribute("ADMIN_ALERT_WARNING", "Can't approve because the quantity is out of range");
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
