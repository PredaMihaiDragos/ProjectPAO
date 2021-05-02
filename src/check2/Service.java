package check2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Timestamp;
import java.util.*;

public class Service {
    public static final String SHOP_FACTORY_FILE_NAME = "shop_factory.csv";
    public static final String PRODUCT_FACTORY_FILE_NAME = "product_factory.csv";
    public static final String SHOPS_FILE_NAME = "shops.csv";
    public static final String PRODUCTS_FILE_NAME = "products.csv";
    public static final String AUDIT_FILE_NAME = "audit.csv";

    private final ShopFactory shopFactory = new ShopFactory();;
    private final ProductFactory productFactory = new ProductFactory();
    private final Set<Shop> shops = new TreeSet<>();

    // 1 op
    public void createShop() throws IOException {
        Shop s = shopFactory.createShop();
        shops.add(s);
        log("createshop");
    }

    // 2 op
    public void createProductForShop(int shopId) throws InvalidCommand, IOException {
        Shop s = getShopWithId(shopId);
        Product p = productFactory.createProduct();
        p.setShopId(shopId);
        s.addProduct(p);
        log("createproduct");
    }

    // 3 op
    public void buy(int shopId, int productId) throws InvalidCommand, IOException {
        Shop s = getShopWithId(shopId);
        s.buyProduct(productId);
        log("buy");
    }

    // 4 op
    public void printRichestShops() throws IOException {
        for(Shop s: shops) {
            System.out.println("Shop id: " + s.getId() + ", shop money: " + s.getMoney());
        }
        log("printrichestshops");
    }

    // 5 op
    public String mostPopularMaterial() throws IOException {
        Map<String, Integer> freq = new HashMap<>();
        Integer maxFreq = 0;
        String ret = "No materials";
        for (Shop s: shops) {
            for (Product p: s.getProducts()) {
                String material = p.getName();
                if (freq.containsKey(material)) {
                    freq.put(material, freq.get(material) + 1);
                } else {
                    freq.put(material, 1);
                }

                if (freq.get(material) > maxFreq) {
                    maxFreq = freq.get(material);
                    ret = material;
                }
            }
        }
        log("mostpopularmaterial");
        return ret;
    }

    // 6 op
    public boolean shopHasProduct(int shopId, int productId) throws InvalidCommand, IOException {
        log("shophasproduct");
        return getShopWithId(shopId).hasProduct(productId);
    }

    // 7 op
    public boolean shopHasProductName(int shopId, String productName) throws InvalidCommand, IOException {
        log("shophasproductname");
        return getShopWithId(shopId).hasProductWithName(productName);
    }

    // 8 op
    public void printProducts(int shopId) throws InvalidCommand, IOException {
        String products = getShopWithId(shopId).getProductsString();
        System.out.println(products);
        log("printproducts");
    }

    // 9 op
    public void sortProductsByPrice(int shopId) throws InvalidCommand, IOException {
        getShopWithId(shopId).sortProductsByPrice();
        log("sortproductsbyprice");
    }

    // 10 op
    public void reverseProducts(int shopId) throws InvalidCommand, IOException {
        getShopWithId(shopId).reverseProducts();
        log("reverseproducts");
    }

    // 11 op
    public void emptyShop(int shopId) throws InvalidCommand, IOException {
        getShopWithId(shopId).emptyShop();
        log("emptyshop");
    }

    private Shop getShopWithId(int shopId) throws InvalidCommand {
        for(Shop s: shops) {
            if (s.getId() == shopId)
                return s;
        }
        throw new InvalidCommand("There is no shop with id " + shopId);
    }

    public void load() throws IOException {
        shopFactory.loadFromFile(SHOP_FACTORY_FILE_NAME, 0);
        productFactory.loadFromFile(PRODUCT_FACTORY_FILE_NAME, 0);

        int offset = 0;
        Reader reader = Reader.getInstance();
        String[] headers = reader.readLine(SHOPS_FILE_NAME, offset);
        for (String s: headers) {
            offset += s.length() + 1;
        }
        offset++; // for the new line character

        while (offset != -1) {
            Shop s = new Shop();
            offset = s.loadFromFile(SHOPS_FILE_NAME, offset);
            if (offset != -1) {
                shops.add(s);
            }
        }

        ArrayList<Product> products = productFactory.readProductsFromFile(PRODUCTS_FILE_NAME);
        for (Product p: products) {
            try {
                Shop s = getShopWithId(p.getShopId());
                s.addProduct(p);
            } catch (InvalidCommand invalidCommand) {
                throw new IOException("Invalid file");
            }
        }
    }

    public void save() throws IOException {
        Writer writer = Writer.getInstance();

        // Clear the files content.
        writer.clear(SHOP_FACTORY_FILE_NAME);
        writer.clear(PRODUCT_FACTORY_FILE_NAME);
        writer.clear(SHOPS_FILE_NAME);
        writer.clear(PRODUCTS_FILE_NAME);

        // Save the new state.
        shopFactory.saveInFile(SHOP_FACTORY_FILE_NAME);
        productFactory.saveInFile(PRODUCT_FACTORY_FILE_NAME);
        for (Shop s: shops) {
            s.saveInFile(SHOPS_FILE_NAME);
            for (Product p: s.getProducts()) {
                p.saveInFile(PRODUCTS_FILE_NAME);
            }
        }
    }

    private void log(String action) throws IOException {
        Writer writer = Writer.getInstance();
        String timestamp = String.valueOf(System.currentTimeMillis());
        writer.write(AUDIT_FILE_NAME, action + ", " + timestamp + ",\n");
    }
}
