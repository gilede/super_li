import DomainLayer.Day;
import DomainLayer.Order;
import DomainLayer.Supplying_Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private Order order;

    @BeforeEach
    public void setUp() {
        // Initialize an Order object before each test method
        HashMap<String, Integer> products_in_order = new HashMap<>();
        products_in_order.put("0", 1);
        products_in_order.put("1", 2);
        order = new Order(Day.SUNDAY, "0", products_in_order, Supplying_Method.BY_ORDER);
    }

    @Test
    public void testGetId() {
        assertEquals("2", order.getOrderId().toString());
    }

    @Test
    public void testGetSupplierId() {
        assertEquals("0", order.getSupplierID());
    }

    @Test
    public void testCancelOrder() {
        order.setCancel();
        assertTrue(order.checkCancel());
    }

    @Test
    public void testRemoveProductFromOrder() {
        assertEquals(2, order.get_products_in_order_size());
        order.Remove_product_from_order("0");
        assertEquals(1, order.get_products_in_order_size());
    }

    @Test
    public void testEditOrder() {
        assertEquals(1, order.getProducts_in_order().get("0").intValue());
        order.EditOrder("0", 3);
        assertEquals(3, order.getProducts_in_order().get("0").intValue());
    }

    @Test
    public void testgetShipmentDay() {
        assertEquals(Day.SUNDAY, order.getShipmentDay());
    }

    @Test
    public void testsetTotalPrice() {
        order.setTotalPrice(100);
        assertEquals(100, order.getTotalPrice(), 0.01);
    }

    //
}
