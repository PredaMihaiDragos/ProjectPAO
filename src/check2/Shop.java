package check2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Shop implements Comparable<Shop>, CSV {
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
    public int loadFromFile(String fileName, int offset) throws IOException {
        Reader reader = Reader.getInstance();
        String[] body = reader.readLine(fileName, offset);
        if (body == null) {
            return -1;
        }
        id = Integer.parseInt(body[0]);
        name = body[1];
        money = Integer.parseInt(body[2]);

        for (String s: body) {
            offset += s.length() + 1;
        }

        return offset + 1;
    }

    @Override
    public void saveInFile(String fileName) throws IOException {
        Writer writer = Writer.getInstance();
        String headers = "id,name,money,\n";
        String body = id + "," + name + "," + money + ",\n";
        if (writer.isEmpty(fileName)) {
            writer.write(fileName, headers + body);
        }
        else {
            writer.write(fileName, body);
        }
    }
}
