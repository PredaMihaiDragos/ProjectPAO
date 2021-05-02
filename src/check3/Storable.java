package check3;

import java.sql.SQLException;

public interface Storable {
    void loadFromDatabase(String tableName) throws SQLException;
    void saveInDatabase(String tableName) throws SQLException;
}
