package dal;

import java.sql.*;
import model.Account;

public class AccountDAO {

    public Account checkLogin(String username, String password) {
        try {
            DBContext db = new DBContext();
            Connection connect = db.getConnection();

            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getInt("aid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("displayname")
                );
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
