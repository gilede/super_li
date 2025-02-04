import DomainLayer.Product;
import DomainLayer.Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private Product product;

    @BeforeEach
    public void setUp() {
        // Initialize a Product object before each test method
        product = new Product("0", "Banana", "tnuva", "FRUITS_AND_VEGETABLES");
    }

    @Test
    public void testGetId() {
        assertEquals("0", product.getId());
    }

    @Test
    public void testGetCategory() {
        assertEquals(Product_Category.FRUITS_AND_VEGETABLES.toString(), product.getCategory());
    }
}
