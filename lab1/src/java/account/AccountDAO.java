package account;

import booking.BookingDTO;
import resource.ResourceDTO;
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
public class AccountDAO {

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

    public boolean setActiveCode(String username, String code) {
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[TBL_ACCOUNT]  SET [ACTIVE_CODE] = ? WHERE USERID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, code);
                pst.setString(2, username);
                return pst.executeUpdate() > 0;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return false;
    }

    public boolean activeAccount(String username, String code) {
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[TBL_ACCOUNT]  SET [STATUS_ID] = '2' WHERE USERID = ? and [ACTIVE_CODE] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, code);
                return pst.executeUpdate() > 0;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return false;
    }

    public AccountDTO checkLogin(String username, String password) {
        AccountDTO res = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT USERID,PHONE,FULLNAME,ADDRESS,CREAT_DATE,B.ROLE_NAME,C.STATUS FROM TBL_ACCOUNT A JOIN TBL_ACCOUNT_ROLE B ON A.ROLE_ID = B.ID  JOIN TBL_ACCOUNT_STATUS C ON A.STATUS_ID = C.ID  WHERE USERID = ? AND PASSWORD = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String userid = rs.getString("USERID");
                    String phone = rs.getString("PHONE");
                    String fullname = rs.getString("FULLNAME");
                    String address = rs.getString("ADDRESS");
                    String createdDate = rs.getString("CREAT_DATE");
                    String role = rs.getString("ROLE_NAME");
                    String status = rs.getString("STATUS");
                    res = new AccountDTO(userid, phone, fullname, address, createdDate, role, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return res;
    }

    public boolean signUp(AccountDTO dto, String status) throws Exception {
        boolean res = false;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO [dbo].[TBL_ACCOUNT](USERID,FULLNAME,ADDRESS,PHONE,PASSWORD,CREAT_DATE,ROLE_ID,STATUS_ID) values (?,?,?,?,?,GETDATE(),?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, dto.getUserid());
                pst.setString(2, dto.getFullname());
                pst.setString(3, dto.getAddress());
                pst.setString(4, dto.getPhone());
                pst.setString(5, dto.getPassword());
                pst.setString(6, dto.getRole());
                pst.setString(7, status);
                res = pst.executeUpdate() > 0;
            }
        }finally {
            CloseConnection();
        }

        return res;
    }

    public AccountDTO checkExist(String username) {
        AccountDTO dto = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT USERID,PHONE,FULLNAME,ADDRESS,CREAT_DATE,B.ROLE_NAME,C.STATUS FROM TBL_ACCOUNT A JOIN TBL_ACCOUNT_ROLE B ON A.ROLE_ID = B.ID  JOIN TBL_ACCOUNT_STATUS C ON A.STATUS_ID = C.ID  WHERE USERID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, username);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String userid = rs.getString("USERID");
                    String phone = rs.getString("PHONE");
                    String fullname = rs.getString("FULLNAME");
                    String address = rs.getString("ADDRESS");
                    String createdDate = rs.getString("CREAT_DATE");
                    String role = rs.getString("ROLE_NAME");
                    String status = rs.getString("STATUS");
                    dto = new AccountDTO(userid, phone, fullname, address, createdDate, role, status);
                }
            }
        } catch (Exception e) {
        } finally {
            CloseConnection();
        }

        return dto;
    }

    public List<BookingDTO> getListHistory(String userID, String name, String dateSearch) {
        List<BookingDTO> history = new ArrayList<>();
        String sqlDate = "";
        if (dateSearch != null) {
            if (!dateSearch.equals("")) {
                sqlDate = "and (cast (BOOKING_DATE as date) = ?)";
            }
        }

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT [RESOURCE_ID],b.NAME,[BOOKING_DATE],a.ADMIN_APPROVE,c.[STATUS_NAME] FROM [dbo].[TBL_BOOKING] a join [dbo].[TBL_RESOURCE] b on a.RESOURCE_ID = b.ID join [TBL_BOOKING_STATUS] c on a.[STATUS_BOOKING] = c.[ID] WHERE USERID = ? AND NAME like ? and c.ID != 'ST4'" + sqlDate + " ORDER BY BOOKING_DATE DESC";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, "%" + name + "%");
                if (!sqlDate.equals("")) {
                    pst.setString(3, dateSearch);
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    String resourceID = rs.getString("RESOURCE_ID");
                    String resourceName = rs.getString("NAME");
                    String date = rs.getString("BOOKING_DATE");
                    String status = rs.getString("STATUS_NAME");
                    String admin = rs.getString("ADMIN_APPROVE");

                    BookingDTO h = new BookingDTO(userID, resourceName, resourceID, date, status, admin);
                    history.add(h);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }

        return history;
    }

}
