package check1;

import java.util.Scanner;

public class ShopFactory {
    private static int BIGGEST_ID = 0;

    public Shop createShop() {
        Shop ret = new Shop();
        Scanner in = new Scanner(System.in);

        System.out.println("Numele magazinului: ");
        ret.setName(in.nextLine());
        ret.setMoney(0);
        ret.setId(BIGGEST_ID++);

        System.out.println("Magazin creat cu succes. Id: " + ret.getId());

        return ret;
    }
}
