package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.RequestForLeave;

public class RequestForLeaveDAO extends DBContext {

    /**
     * Chèn một đơn xin nghỉ phép mới vào cơ sở dữ liệu.
     *
     * @param rfl Đối tượng RequestForLeave chứa thông tin đơn xin nghỉ.
     * @return true nếu thêm thành công, false nếu có lỗi.
     */
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

    /**
     * Tìm `eid` của người duyệt đơn phù hợp với người tạo đơn. Dựa theo vai
     * trò: - Nếu là nhân viên → tìm Leader cùng phòng ban. - Nếu là Leader →
     * tìm Head of Department cùng phòng ban. - Nếu là Head of Department → tìm
     * Admin.
     *
     * @param createdByEid `eid` của người tạo đơn xin nghỉ.
     * @return `eid` của người duyệt nếu có, hoặc null nếu không tìm thấy.
     */
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

    /**
     * Lấy danh sách đơn xin nghỉ phép mà một nhân viên đã tạo.
     *
     * @param eid `eid` của người tạo đơn.
     * @return Danh sách các đơn xin nghỉ tương ứng.
     */
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

    /**
     * Lấy danh sách các đơn xin nghỉ của cấp dưới, dựa vào vai trò: - Nếu là
     * Leader → lấy đơn của nhân viên trong cùng group. - Nếu là Head of
     * Department → lấy đơn của tất cả nhân viên và Leader trong cùng phòng ban.
     * - Nếu là Admin → lấy tất cả đơn trong hệ thống.
     *
     * @param eid `eid` của người đang đăng nhập (Leader/HOD/Admin).
     * @param role Vai trò của người đăng nhập.
     * @return Danh sách đơn xin nghỉ cần được xét duyệt.
     */
    public List<RequestForLeave> getRequestsOfSubordinates(int eid, String role) {
        List<RequestForLeave> list = new ArrayList<>();
        String sql = "";

        if ("Leader".equalsIgnoreCase(role)) {
            sql = """
            SELECT r.* FROM RequestForLeave r
            JOIN Employee e ON r.createdby = e.eid
            WHERE e.groupid = (
                SELECT groupid FROM Employee WHERE eid = ?
            )
            AND r.createdby != ?
            """;
        } else if ("Head of Department".equalsIgnoreCase(role)) {
            sql = """
            SELECT r.* FROM RequestForLeave r
            JOIN Employee e ON r.createdby = e.eid
            WHERE e.did = (
                SELECT did FROM Employee WHERE eid = ?
            )
            AND r.createdby != ?
            """;
        } else if ("Admin".equalsIgnoreCase(role)) {
            sql = "SELECT * FROM RequestForLeave";
        }

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            if (!"Admin".equalsIgnoreCase(role)) {
                ps.setInt(1, eid);
                ps.setInt(2, eid);
            }

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

    /**
     * Cập nhật trạng thái của một đơn xin nghỉ phép.
     *
     * @param rid ID của đơn nghỉ phép.
     * @param status Trạng thái cập nhật: 1 = Approved, 2 = Rejected.
     * @param approverEid ID của người xét duyệt (Leader, HOD, Admin).
     * @return true nếu cập nhật thành công, false nếu có lỗi.
     */
    public boolean updateStatus(int rid, int status, int approverEid) {
        String sql = "UPDATE RequestForLeave SET status = ?, processedby = ? WHERE rid = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, status);           // Trạng thái: 1 (duyệt), 2 (từ chối)
            ps.setInt(2, approverEid);      // Người xử lý đơn
            ps.setInt(3, rid);              // ID của đơn nghỉ phép
            return ps.executeUpdate() > 0;  // Trả về true nếu có ít nhất 1 dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false;                   // Trả về false nếu xảy ra lỗi
        }
    }

}
