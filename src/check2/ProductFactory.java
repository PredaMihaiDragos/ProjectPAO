package check2;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class ProductFactory extends Factory {
    private static int BIGGEST_ID = 0;
    private static final HashMap<String, Supplier<Product>> types = new HashMap<>() {{
        put("copper", Copper::new);
        put("diamond", Diamond::new);
        put("gold", Gold::new);
        put("metal", Metal::new);
        put("plastic", Plastic::new);
        put("platinum", Platinum::new);
        put("silver", Silver::new);
        put("wood", Wood::new);
    }};

    public Product createProduct() {
        Product ret;
        Scanner in = new Scanner(System.in);

        System.out.println("Tipul produsului: ");
        while(true) {
            String inType = in.nextLine().toLowerCase();
            if(types.containsKey(inType)) {
                ret = types.get(inType).get();
                break;
            }
            System.out.print("Tip produs invalid. Tipuri produse: " );
            types.forEach((type, value) -> System.out.print(type + " "));
            System.out.print("\n");
        }
        addToHistory(BIGGEST_ID);
        ret.setId(BIGGEST_ID++);

        System.out.println("Produs creat cu succes. Id: " + ret.getId());

        return ret;
    }

    public ArrayList<Product> readProductsFromFile(String fileName) throws IOException {
        ArrayList<Product> ret = new ArrayList<Product>();
        Reader reader = Reader.getInstance();
        int offset = 0;
        String[] headers = reader.readLine(fileName, offset);
        for (String s: headers) {
            offset += s.length() + 1;
        }
        offset++;

        while (true) {
            String[] productInfo = reader.readLine(fileName, offset);
            if (productInfo == null) {
                break;
            }
            Product prod = types.get(productInfo[2].toLowerCase()).get();
            offset = prod.loadFromFile(fileName, offset);
            ret.add(prod);
        }

        return ret;
    }

    @Override
    protected int getBiggestId() {
        return BIGGEST_ID;
    }

    @Override
    protected void setBiggestId(int id) {
        BIGGEST_ID = id;
    }
}
