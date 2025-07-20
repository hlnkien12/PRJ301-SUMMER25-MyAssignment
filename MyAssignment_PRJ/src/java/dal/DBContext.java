package dal;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    /**
     * // Cho phép class con sử dụng
     */
    protected Connection connection;

    private final String serverName = "localhost";
    private final String dbName = "LeaveManagement";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "sa";

    public DBContext() {
        try {
            String url = "jdbc:sqlserver://" + serverName + ":" + portNumber
                    + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, userID, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Optional nếu muốn sử dụng riêng
     * @return connection 
     */
    public Connection getConnection() {
        return connection;
    }
}
