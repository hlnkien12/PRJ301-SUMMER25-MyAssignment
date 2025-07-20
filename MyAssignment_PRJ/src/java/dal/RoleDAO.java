/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
//dùng để lấy danh sách role của 1 Account từ database
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Role;
/**
 *
 * @author Admin
 */
public class RoleDAO extends DBContext{
     /**
      * Lấy tất cả các Role trong hệ thống
      * @return list of role
      */
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT rid, rname FROM Role";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Role r = new Role();
                r.setRid(rs.getInt("rid"));
                r.setRname(rs.getString("rname"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Lấy danh sách Role theo aid (account id)
     * @param aid
     * @return 
     */
    public List<Role> getRolesByAccountId(int aid) {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT r.rid, r.rname FROM Role r " +
                     "JOIN AccountRole ar ON r.rid = ar.rid " +
                     "WHERE ar.aid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Role r = new Role();
                r.setRid(rs.getInt("rid"));
                r.setRname(rs.getString("rname"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
