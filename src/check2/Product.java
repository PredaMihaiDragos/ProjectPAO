package check2;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Product implements CSV {
    private int id, shopId;

    abstract String getName();
    abstract int getPrice();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + getName();
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public int loadFromFile(String fileName, int offset) throws IOException {
        Reader reader = Reader.getInstance();
        String[] body = reader.readLine(fileName, offset);
        if (body == null) {
            return -1;
        }
        id = Integer.parseInt(body[0]);
        shopId = Integer.parseInt(body[1]);

        for (String s: body) {
            offset += s.length() + 1;
        }

        return offset + 1;
    }

    @Override
    public void saveInFile(String fileName) throws IOException {
        Writer writer = Writer.getInstance();
        String headers = "id,shopId,name,\n";
        String body = id + "," + shopId + "," + getName() + ",\n";
        if (writer.isEmpty(fileName)) {
            writer.write(fileName, headers + body);
        }
        else {
            writer.write(fileName, body);
        }
    }
}
