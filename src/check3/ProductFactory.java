package check3;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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

    public List<Product> readProductsFromDatabase(String tableName) throws SQLException {
        ArrayList<Product> ret = new ArrayList<>();
        Database db = Database.getInstance();
        ResultSet rs = db.query("SELECT id, name FROM " + tableName);
        while (rs.next()) {
            String name = rs.getString("name");
            Product prod = types.get(name.toLowerCase()).get();
            prod.setId(rs.getInt("id"));
            prod.loadFromDatabase(tableName);
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
