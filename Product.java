// magnus.nording@iths.se
public class Product {
    private String name;
    private double price;
    private String[] productGroup;
    private boolean isWeightPrice;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price, String[] productGroup, boolean isWeightPrice) {
        this.name = name;
        this.price = price;
        this.productGroup = productGroup;
        this.isWeightPrice = isWeightPrice;

    }

    @Override
    public String toString() {
        String priceType = isWeightPrice ? "Pris/kg" : "Pris/st";

        String productGroupStr = "";
        if (productGroup != null && productGroup.length > 0) {
            productGroupStr = String.join(", ", productGroup);
        }

        return String.format("Produkt:%s %s: %.2f Varugrupp: %s",
                name, priceType, price, productGroupStr);
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

    public String[] getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String[] productGroup) {
        this.productGroup = productGroup;
    }

    public boolean isWeightPrice() {
        return isWeightPrice;
    }

    public void setWeightPrice(boolean weightPrice) {
        isWeightPrice = weightPrice;
    }

    public boolean getIsWeightPrice() {
        return this.isWeightPrice;
    }

    public void setIsWeightPrice(boolean weightPrice) {
        this.isWeightPrice = weightPrice;
    }
}
