package check1;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;

public class ProductFactory {
    private static int BIGGEST_ID = 0;

    public Product createProduct() {
        Product ret;
        Scanner in = new Scanner(System.in);

        HashMap<String, Supplier<Product>> types = new HashMap<>() {{
            put("copper", Copper::new);
            put("diamond", Diamond::new);
            put("gold", Gold::new);
            put("metal", Metal::new);
            put("plastic", Plastic::new);
            put("platinum", Platinum::new);
            put("silver", Silver::new);
            put("wood", Wood::new);
        }};

        System.out.println("Tipul produsului: ");
        while(true) {
            String inType = in.nextLine();
            if(types.containsKey(inType)) {
                ret = types.get(inType).get();
                break;
            }
            System.out.print("Tip produs invalid. Tipuri produse: " );
            types.forEach((type, value) -> System.out.print(type + " "));
            System.out.print("\n");
        }
        ret.setId(BIGGEST_ID++);

        System.out.println("Produs creat cu succes. Id: " + ret.getId());

        return ret;
    }
}
