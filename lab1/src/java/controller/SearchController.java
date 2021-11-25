package controller;

import account.AccountDAO;
import account.AccountDTO;
import resource.ResourceDAO;
import resource.ResourceDTO;
import booking.BookingDAO;
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
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private static final String USER = "user.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final int PAGESIZE = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = USER;
        try {
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String dateFrom = request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo");
            String page = request.getParameter("page");
            int pageInt;
            if (page != null) {
                pageInt = Integer.parseInt(page);
            } else {
                pageInt = 1;
            }
            if (category == null) {
                category = "";
            }
            if (name == null) {
                name = "";
            }
            ResourceDAO dao = new ResourceDAO();
            HttpSession session = request.getSession();
            AccountDTO dto = (AccountDTO) session.getAttribute("ACCOUNT");
            List<ResourceDTO> list = dao.search(name, category, dateFrom, dateTo, pageInt, 5);
            int countPage = dao.countAll(name, category, dateFrom, dateTo);
            BookingDAO bookingDAO = new BookingDAO();

            for (ResourceDTO a : list) {
                String status = bookingDAO.checkBooking(dto.getUserid(), a.getId());
                if (status == null) {
                    status = "";
                }
                a.setStatusBooking(status);
            }
            request.setAttribute("LIST", list);
            int countPageIndex = countPage / PAGESIZE;
            if (countPage % PAGESIZE != 0) {
                countPageIndex++;
            }
            request.setAttribute("PAGE_INDEX", countPageIndex);
            String size = list.size() + "";
            request.setAttribute("LIST_SIZE", size);
            if (dto.getRole().equals("MANAGER")) {
                url = ADMIN;
            }
            AccountDAO accDAO = new AccountDAO();
            dto.setHistory(accDAO.getListHistory(dto.getUserid(), "", ""));
        } catch (Exception e) {
            e.printStackTrace();
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
