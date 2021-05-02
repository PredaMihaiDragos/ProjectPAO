package check3;
import java.sql.*;

public class Database {
    private static Database instance = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pao";
    private static final String USER = "root";
    private static final String PASS = "B7miPr34y";
    Connection conn = null;

    private Database() throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public ResultSet query(String query) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public void update(String query) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate(query);
    }
}
