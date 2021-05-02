package check3;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Shop implements Comparable<Shop>, Storable {
    private int id;
    private String name;
    private final ArrayList<Product> products = new ArrayList<>();
    private int money;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasProduct(int productId) {
        return getProductWithId(productId) != null;
    }

    public boolean hasProductWithName(String productName) {
        return getProductWithName(productName) != null;
    }

    public void addProduct(Product p) {
        this.products.add(p);
    }

    public void buyProduct(int id) throws InvalidCommand {
        Product p = getProductWithId(id);
        if (p == null)
            throw new InvalidCommand("The shop doesn't have this product.");
        money += p.getPrice();
        products.remove(p);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getProductsString() {
        return products.toString();
    }

    public void sortProductsByPrice() {
        products.sort(Comparator.comparing(Product::getPrice));
    }

    public void reverseProducts() {
        Collections.reverse(products);
    }

    public void emptyShop() {
        products.clear();
    }

    @Override
    public int compareTo(Shop o) {
        if (o.money == money) {
            return id-o.id;
        }
        return o.money-money;
    }

    private Product getProductWithId(int productId) {
        for(Product p: products) {
            if(p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    private Product getProductWithName(String productName) {
        for(Product p: products) {
            if(p.getName().equals(productName)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void loadFromDatabase(String tableName) throws SQLException {
        Database db = Database.getInstance();
        ResultSet rs = db.query("SELECT name, money FROM " + tableName + " WHERE id = " + id);
        rs.next();
        name = rs.getString("name");
        money = rs.getInt("money");
    }

    @Override
    public void saveInDatabase(String tableName) throws SQLException {
        Database db = Database.getInstance();
        db.update("INSERT INTO " + tableName + "(id, name, money) VALUES " +
                  "(" + id + ",'" + name + "'," + money + ")");
    }
}
