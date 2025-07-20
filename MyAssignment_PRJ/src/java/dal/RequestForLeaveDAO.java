package dal;

import java.sql.*;
import model.RequestForLeave;

public class RequestForLeaveDAO extends DBContext {

    public boolean insert(RequestForLeave rfl) {
        String sql = "INSERT INTO RequestForLeave (title, [from], [to], reason, status, createdby, processedby) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NULL)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, rfl.getTitle());
            ps.setDate(2, rfl.getFrom());
            ps.setDate(3, rfl.getTo());
            ps.setString(4, rfl.getReason());
            ps.setInt(5, 0); // trạng thái khởi tạo: 0 (Chưa duyệt)
            ps.setInt(6, rfl.getCreatedBy());

            return ps.executeUpdate() > 0;
        } catch (SQLException | Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
