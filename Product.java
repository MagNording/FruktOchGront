// magnus.nording@iths.se
public class Product {
    private String name;
    private double price;
    private String productGroup;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price, String productGroup) {
        this.name = name;
        this.price = price;
        this.productGroup = productGroup;
    }

    @Override
    public String toString() {
        return "Namn: %s, pris: %.2f kr/kg, varugrupp: %s"
                .formatted(name, price, productGroup);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductGroup() {
        return this.productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }
}