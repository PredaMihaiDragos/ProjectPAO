package check3;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Product implements Storable {
    private int id, shopId;

    abstract String getName();
    abstract int getPrice();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + getName();
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public void loadFromDatabase(String tableName) throws SQLException {
        Database db = Database.getInstance();
        ResultSet rs = db.query("SELECT shopId FROM " + tableName);
        rs.next();
        shopId = rs.getInt("shopId");
    }

    @Override
    public void saveInDatabase(String tableName) throws SQLException {
        Database db = Database.getInstance();
        db.update("INSERT INTO " + tableName + "(id, name, shopId) VALUES " +
                  "(" + id + ",'" + getName() + "'," + shopId + ")");
    }
}
