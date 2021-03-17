package check1;

public abstract class Product {
    private int id;

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
}
