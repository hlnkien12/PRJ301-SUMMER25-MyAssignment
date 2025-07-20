package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.RequestForLeave;

public class RequestForLeaveDAO extends DBContext {

    public boolean insert(RequestForLeave rfl) {
        Integer approverEid = findApproverEidByCreatedBy(rfl.getCreatedBy());

        String sql = "INSERT INTO RequestForLeave (title, [from], [to], reason, status, createdby, processedby) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, rfl.getTitle());
            ps.setDate(2, rfl.getFrom());
            ps.setDate(3, rfl.getTo());
            ps.setString(4, rfl.getReason());
            ps.setInt(5, 0); // 0 = chưa duyệt
            ps.setInt(6, rfl.getCreatedBy());

            if (approverEid != null) {
                ps.setInt(7, approverEid);
            } else {
                ps.setNull(7, Types.INTEGER); // Nếu là admin hoặc không có người duyệt
            }

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer findApproverEidByCreatedBy(int createdByEid) {
        String sql = """
            SELECT TOP 1 approver.eid
            FROM Employee creator
            JOIN Role creator_role ON creator.rid = creator_role.rid
            LEFT JOIN Department dept ON creator.did = dept.did

            LEFT JOIN Employee approver ON (
                (creator_role.rname = 'Employee' AND approver.rid = (SELECT rid FROM Role WHERE rname = 'Leader') AND approver.did = dept.did)
             OR (creator_role.rname = 'Leader' AND approver.rid = (SELECT rid FROM Role WHERE rname = 'head of department') AND approver.did = dept.did)
             OR (creator_role.rname = 'Head of Department' AND approver.rid = (SELECT rid FROM Role WHERE rname = 'Admin'))
            )
            WHERE creator.eid = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, createdByEid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("eid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Admin tạo đơn hoặc không tìm thấy người duyệt
    }

    public List<RequestForLeave> getRequestsByEid(int eid) {
    List<RequestForLeave> list = new ArrayList<>();
    String sql = "SELECT * FROM RequestForLeave WHERE createdby = ?";

    try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
        ps.setInt(1, eid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            RequestForLeave r = new RequestForLeave();
            r.setRid(rs.getInt("rid"));
            r.setTitle(rs.getString("title"));
            r.setFrom(rs.getDate("from"));
            r.setTo(rs.getDate("to"));
            r.setReason(rs.getString("reason"));
            r.setStatus(rs.getInt("status"));
            r.setCreatedBy(rs.getInt("createdby"));
            r.setProcessedBy(rs.getInt("processedby"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


}
