package dal;

import java.sql.*;
import model.Account;

public class AccountDAO {

    public Account checkLogin(String username, String password) {
        try {
            DBContext db = new DBContext();
            Connection connect = db.getConnection();

            // Join với AccountEmployee để lấy eid
            String sql = "SELECT a.aid, a.username, a.password, a.displayname, ae.eid " +
                         "FROM Account a " +
                         "LEFT JOIN AccountEmployee ae ON a.aid = ae.aid " +
                         "WHERE a.username = ? AND a.password = ?";

            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account(
                    rs.getInt("aid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("displayname")
                );

                int eid = rs.getInt("eid");
                if (!rs.wasNull()) {
                    acc.setEid(eid);
                } else {
                    acc.setEid(-1); // -1 nếu tài khoản không có eid (ví dụ: admin)
                }

                return acc;
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
