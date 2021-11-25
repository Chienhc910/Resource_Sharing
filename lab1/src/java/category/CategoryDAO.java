package category;

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
public class CategoryDAO {

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

    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> list = new ArrayList<>();

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT [ID],[CATEGORI_NAME] FROM [dbo].[TBL_CATEGORY]";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String name = rs.getString("CATEGORI_NAME");
                    CategoryDTO dto = new CategoryDTO(id, name);
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
