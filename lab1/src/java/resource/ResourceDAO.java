package resource;

import utils.DBUtil;
import booking.BookingDAO;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class ResourceDAO {

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

    public int countAll(String name, String category, String dateFrom, String dateTo) {
        int count = 0;
        String sqlDate = "";
        if (dateFrom != null && dateTo != null) {
            if (!dateFrom.equals("") && !dateTo.equals("")) {
                sqlDate = "AND(A.CREATED_DATE BETWEEN ? AND ?)";
            }
        }
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT a.ID,[NAME] ,[COLOR],b.[CATEGORI_NAME],[CREATED_DATE],QUANTITY FROM [dbo].[TBL_RESOURCE] A JOIN [dbo].[TBL_CATEGORY] B ON A.CATEGORY_ID = B.ID WHERE [NAME] like ? AND B.CATEGORI_NAME like ? " + sqlDate;
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + name + "%");
                pst.setString(2, "%" + category + "%");
                if (!sqlDate.equals("")) {
                    pst.setString(4, dateFrom);
                    pst.setString(5, dateTo);
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return count;
    }

    public List<ResourceDTO> search(String name, String category, String dateFrom, String dateTo, int page, int pageSize) {
        List<ResourceDTO> list = new ArrayList<>();
        String sqlDate = "";
        if (dateFrom != null && dateTo != null) {
            if (!dateFrom.equals("") && !dateTo.equals("")) {
                sqlDate = "AND(A.CREATED_DATE BETWEEN ? AND ?)";
            }
        }
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "with x as(select ROW_NUMBER() over (order by [CREATED_DATE] desc)\n"
                        + "as r, a.ID,[NAME] ,[COLOR],b.[CATEGORI_NAME],[CREATED_DATE],QUANTITY FROM [dbo].[TBL_RESOURCE] A JOIN [dbo].[TBL_CATEGORY] B ON A.CATEGORY_ID = B.ID WHERE [NAME] like ? AND B.CATEGORI_NAME like ? )\n"
                        + "select ID,[NAME] ,[COLOR],[CATEGORI_NAME],[CREATED_DATE],QUANTITY from x where r between ? and ? " + sqlDate;
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + name + "%");
                pst.setString(2, "%" + category + "%");
                if (!sqlDate.equals("")) {
                    pst.setString(5, dateFrom);
                    pst.setString(6, dateTo);
                }
                int start = 1;

                if (page != 1) {
                    start += (page - 1) * pageSize;
                }

                int end = start + pageSize;
                pst.setInt(3, start);
                pst.setInt(4, end);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String title = rs.getString("NAME");
                    String color = rs.getString("COLOR");
                    String categoryName = rs.getString("CATEGORI_NAME");
                    String date = rs.getString("CREATED_DATE");
                    int quantiry = rs.getInt("quantity");
                    ResourceDTO dto = new ResourceDTO(id, color, categoryName, title, date, quantiry);
                    if (quantiry > 0) {
                        list.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return list;
    }

    public List<ResourceDTO> getResourceBtStatus(String statusID, String name) {
        List<ResourceDTO> list = new ArrayList<>();

        String sqlStatus = "";
        if (statusID != null) {
            sqlStatus = "and C.ID = ?";
        } else {
            statusID = "";
        }

        if (name == null) {
            name = "";
        }

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select a.ID,d.FULLNAME ,d.USERID,b.BOOKING_DATE, [COLOR],E.CATEGORI_NAME,[QUANTITY],[NAME],[CREATED_DATE], b.ADMIN_APPROVE FROM TBL_RESOURCE A JOIN TBL_BOOKING B ON A.ID = B.RESOURCE_ID JOIN TBL_BOOKING_STATUS C ON B.STATUS_BOOKING = C.ID JOIN TBL_ACCOUNT D ON D.USERID = B.USERID JOIN TBL_CATEGORY E ON E.ID = A.CATEGORY_ID WHERE NAME like ?  " + sqlStatus + " ORDER BY BOOKING_DATE DESC";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + name + "%");
                if (!sqlStatus.equals("")) {
                    pst.setString(2, statusID);
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    String resourceID = rs.getString("ID");
                    String color = rs.getString("COLOR");
                    String cateName = rs.getString("CATEGORI_NAME");
                    int quantity = rs.getInt("QUANTITY");
                    String resourceName = rs.getString("NAME");
                    String date = rs.getString("BOOKING_DATE");
                    String adminApproved = rs.getString("ADMIN_APPROVE");
                    String userFullname = rs.getString("FULLNAME");
                    String userID = rs.getString("USERID");
                    if (adminApproved == null) {
                        adminApproved = "";
                    }
                    ResourceDTO resource = new ResourceDTO(resourceID, color, cateName, resourceName, date, quantity);
                    resource.setAdminApproved(adminApproved);
                    resource.setUserRequest(userFullname);
                    resource.setBookingDate(date);
                    resource.setUserID(userID);
                    list.add(resource);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }

        return list;
    }

    public boolean checkQuantity(String resourceID) {
        boolean res = true;
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select [QUANTITY] from [dbo].[TBL_RESOURCE] where [ID] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, resourceID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (rs.getInt("QUANTITY") <= 0) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return res;
    }

    public boolean changeNumberOfResource(String resourceID, String operator) {
        boolean res = false;
        try {
            boolean checkQuantity = checkQuantity(resourceID);
            if (!checkQuantity && operator.equals("-")) {
                return false;
            }
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "update [dbo].[TBL_RESOURCE] set [QUANTITY] = [QUANTITY] " + operator + " 1 where [ID] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, resourceID);
                res = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return res;
    }

    public ResourceDTO getDetail(String id) {
        ResourceDTO dto = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "select [NAME],[Detail] from [dbo].[TBL_RESOURCE] where [ID] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    String detail = rs.getString("Detail");
                    dto = new ResourceDTO();
                    dto.setName(name);
                    dto.setDetail(detail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }

        return dto;
    }
}
