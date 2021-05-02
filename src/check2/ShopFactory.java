package check2;

import java.util.Scanner;

public class ShopFactory extends Factory {
    private static int BIGGEST_ID = 0;

    public Shop createShop() {
        Shop ret = new Shop();
        Scanner in = new Scanner(System.in);

        System.out.println("Numele magazinului: ");
        String name = in.nextLine();
        addToHistory(BIGGEST_ID);
        ret.setName(name);
        ret.setMoney(0);
        ret.setId(BIGGEST_ID++);

        System.out.println("Magazin creat cu succes. Id: " + ret.getId());

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
