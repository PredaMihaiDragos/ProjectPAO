package check1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Service {
    private final Set<Shop> shops = new TreeSet<>();

    // 1 op
    public void createShop() {
        ShopFactory shopFactory = new ShopFactory();
        Shop s = shopFactory.createShop();
        shops.add(s);
    }

    // 2 op
    public void createProductForShop(int shopId) throws InvalidCommand {
        Shop s = getShopWithId(shopId);
        ProductFactory productFactory = new ProductFactory();
        Product p = productFactory.createProduct();

        s.addProduct(p);
    }

    // 3 op
    public void buy(int shopId, int productId) throws InvalidCommand {
        Shop s = getShopWithId(shopId);
        s.buyProduct(productId);
    }

    // 4 op
    public void printRichestShops() {
        for(Shop s: shops) {
            System.out.println("Shop id: " + s.getId() + ", shop money: " + s.getMoney());
        }
    }

    // 5 op
    public String mostPopularMaterial() {
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
        return ret;
    }

    // 6 op
    public boolean shopHasProduct(int shopId, int productId) throws InvalidCommand {
        return getShopWithId(shopId).hasProduct(productId);
    }

    // 7 op
    public boolean shopHasProductName(int shopId, String productName) throws InvalidCommand {
        return getShopWithId(shopId).hasProductWithName(productName);
    }

    // 8 op
    public void printProducts(int shopId) throws InvalidCommand {
        String products = getShopWithId(shopId).getProductsString();
        System.out.println(products);
    }

    // 9 op
    public void sortProductsByPrice(int shopId) throws InvalidCommand {
        getShopWithId(shopId).sortProductsByPrice();
    }

    // 10 op
    public void reverseProducts(int shopId) throws InvalidCommand {
        getShopWithId(shopId).reverseProducts();
    }

    // 11 op
    public void emptyShop(int shopId) throws InvalidCommand {
        getShopWithId(shopId).emptyShop();
    }

    private Shop getShopWithId(int shopId) throws InvalidCommand {
        for(Shop s: shops) {
            if (s.getId() == shopId)
                return s;
        }
        throw new InvalidCommand("There is no shop with id " + shopId);
    }
}
