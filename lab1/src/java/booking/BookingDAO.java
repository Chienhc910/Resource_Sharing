package booking;

import utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class BookingDAO {

    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private void CloseConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }

        } catch (Exception e) {
        }
    }

    public String checkBooking(String userid, String resourceID) {
        String status = null;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT B.STATUS_NAME FROM [dbo].[TBL_BOOKING] A JOIN [dbo].[TBL_BOOKING_STATUS] B ON A.STATUS_BOOKING = B.ID WHERE USERID = ? AND RESOURCE_ID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userid);
                pst.setString(2, resourceID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    status = rs.getString("STATUS_NAME");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return status;
    }

    public boolean createBooking(String userID, String resourceID) {
        boolean res = false;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "INSERT [dbo].[TBL_BOOKING](USERID,RESOURCE_ID,BOOKING_DATE) VALUES(?,?,GETDATE())";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, resourceID);
                res = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return res;
    }

    public boolean handleBooking(String userID, String resourceID) {
        boolean res = false;
        String statusBooking = checkBooking(userID, resourceID);
        if (statusBooking == null) {
            createBooking(userID, resourceID);
        }
        res = changeStatus("ST1", resourceID, userID, "");

        return res;
    }

    public boolean changeStatus(String statusID, String resourceID, String userID, String admin) {
        boolean res = false;
        if (admin == null) {
            admin = "";
        }
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[TBL_BOOKING] SET [STATUS_BOOKING] = ?,[ADMIN_APPROVE]=? WHERE USERID = ? AND [RESOURCE_ID] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, statusID);
                pst.setString(2, admin);
                pst.setString(3, userID);
                pst.setString(4, resourceID);
                res = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return res;
    }

    public boolean deleteBooking(String resourceID, String userID) {
        boolean res = false;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "delete from [dbo].[TBL_BOOKING] where [USERID] = ? and [RESOURCE_ID] =?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, resourceID);
                res = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return res;
    }

    public List<BookingDTO> getListBooking(String statusSearch, String name) {
        List<BookingDTO> list = new ArrayList<>();
        String sqlStatus = "";
        if (!statusSearch.equals("")) {
            sqlStatus = "and B.[ID] = ?";
        }
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT A.[ID],[USERID],[RESOURCE_ID],C.NAME, B.STATUS_NAME, A.BOOKING_DATE, c.QUANTITY  FROM [dbo].[TBL_BOOKING] A JOIN [dbo].[TBL_BOOKING_STATUS] B ON A.[STATUS_BOOKING] = B.[ID] JOIN [dbo].[TBL_RESOURCE] C ON  A.RESOURCE_ID = C.ID where C.NAME like ? " + sqlStatus;
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + name + "%");
                if (!sqlStatus.equals("")) {
                    pst.setString(2, statusSearch);
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String userID = rs.getString("USERID");
                    String resourceID = rs.getString("RESOURCE_ID");
                    String resourceName = rs.getString("NAME");
                    String status = rs.getString("STATUS_NAME");
                    String date = rs.getString("BOOKING_DATE");
                    int quantity = rs.getInt("QUANTITY");
                    BookingDTO dto = new BookingDTO(userID, resourceName, resourceID, date, status, "");
                    dto.setQuantity(quantity);
                    dto.setBookingID(id);
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }

        return list;
    }
}
