// magnus.nording@iths.se
public class Product {
    private String name;
    private double price;
    private String productGroup;
    private boolean isWeightPrice;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price, String productGroup, boolean isWeightPrice) {
        this.name = name;
        this.price = price;
        this.productGroup = productGroup;
        this.isWeightPrice = isWeightPrice;
    }

    @Override
    public String toString() { 
        if (isWeightPrice) {
            return "Produkt: Vara: %s Pris: %.2f Varugrupp: %s".
                    formatted(name, price, productGroup);
        } return "Produkt: Vara: %s Pris: %.2f Varugrupp: %s".
                formatted(name, price, productGroup);
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

    public boolean isWeightPrice() {
        return isWeightPrice;
    }

    public void setWeightPrice(boolean weightPrice) {
        isWeightPrice = weightPrice;
    }
}
