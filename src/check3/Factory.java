package check3;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Factory implements Storable {
    private final ArrayList<Integer> history = new ArrayList<>();
    abstract int getBiggestId();
    abstract void setBiggestId(int id);

    @Override
    public void loadFromDatabase(String tableName) throws SQLException {
        Database db = Database.getInstance();
        ResultSet rs = db.query("SELECT biggestId FROM " + tableName);
        rs.next();
        setBiggestId(rs.getInt("biggestId"));
    }

    @Override
    public void saveInDatabase(String tableName) throws SQLException {
        Database db = Database.getInstance();
        db.update("INSERT INTO " + tableName + "(biggestId) VALUES (" + getBiggestId() + ")");
    }

    protected void addToHistory(Integer id) {
        history.add(id);
    }

    protected ArrayList<Integer> getHistory() {
        return history;
    }
}
