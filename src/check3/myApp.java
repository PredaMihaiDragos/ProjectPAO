package check3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class myApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean exit = false;

        Service myService = new Service();
        try {
            myService.load();
        } catch (SQLException e) {
            System.out.println("Invalid database, creating a new Service.");
        }

        while(!exit) {
            System.out.println("Introdu comanda: ");
            String cmd = in.nextLine().toLowerCase(Locale.ROOT);
            try {
                switch (cmd) {
                    case "createshop":
                        myService.createShop();
                        break;
                    case "createproduct":
                        System.out.println("Shop id to create product in: ");
                        int shopId = in.nextInt();
                        in.nextLine();
                        myService.createProductForShop(shopId);
                        break;
                    case "buy":
                        System.out.println("Shop id to buy from: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        System.out.println("Product id to buy: ");
                        int productId = in.nextInt();
                        in.nextLine();
                        myService.buy(shopId, productId);
                        break;
                    case "richestshops":
                        myService.printRichestShops();
                        break;
                    case "mostpopularmaterial":
                        String material = myService.mostPopularMaterial();
                        System.out.println("Cel mai popular material este: " + material);
                        break;
                    case "shophasproduct":
                        System.out.println("Shop id: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        System.out.println("Product id: ");
                        productId = in.nextInt();
                        in.nextLine();
                        if (myService.shopHasProduct(shopId, productId)) {
                            System.out.println("Yes");
                        } else {
                            System.out.println("No");
                        }
                        break;
                    case "shophasproductname":
                        System.out.println("Shop id: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        System.out.println("Product name: ");
                        String productName = in.nextLine();
                        if (myService.shopHasProductName(shopId, productName)) {
                            System.out.println("Yes");
                        } else {
                            System.out.println("No");
                        }
                        break;
                    case "printproducts":
                        System.out.println("Shop id: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        myService.printProducts(shopId);
                        break;
                    case "sortproducts":
                        System.out.println("Shop id: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        myService.sortProductsByPrice(shopId);
                        System.out.println("Products sorted");
                        break;
                    case "reverseproducts":
                        System.out.println("Shop id: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        myService.reverseProducts(shopId);
                        System.out.println("Products reversed");
                        break;
                    case "emptyshop":
                        System.out.println("Shop id: ");
                        shopId = in.nextInt();
                        in.nextLine();
                        myService.emptyShop(shopId);
                        System.out.println("Shop emptied");
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        throw new InvalidCommand("Comanda invalida: " + cmd);
                }
            }
            catch(InvalidCommand | InputMismatchException | IOException error) {
                System.out.println(error.getMessage());
            }

            try {
                myService.save();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
