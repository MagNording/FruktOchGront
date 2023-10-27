import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Banan");
    }

    @Test
    void testGetName() {
        String expectedName = "Banan";
        String actualName = product.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    void TestSetName() {
        String newName = "Chiquita";
        product.setName(newName);
        String actualName = product.getName();
        assertEquals(newName, actualName);
    }

    @Test
    void testGetPrice() {
        Product product = new Product("Banan", 14.95);
        double expectedPrice = 14.95;
        double actualPrice = product.getPrice();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testSetPrice() {
        Product product = new Product("Banan", 3.99);
        double newPrice = 4.99;
        product.setPrice(newPrice);
        double actualPrice = product.getPrice();
        assertEquals(newPrice, actualPrice);
    }

}
