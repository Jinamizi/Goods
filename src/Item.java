public class Item {
    private String name;
    private double price;
    private int totalGoods;

    public Item(String name, double price, int totalGoods) {
        this.name = name;
        this.price = price;
        this.totalGoods = totalGoods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalGoods() {
        return totalGoods;
    }

    public void setTotalGoods(int totalGoods) {
        this.totalGoods = totalGoods;
    }
}
