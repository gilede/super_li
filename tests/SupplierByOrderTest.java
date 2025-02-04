import DomainLayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupplierByOrderTest {
    private Supplier_By_Order supplier;

    @BeforeEach
    public void setUp() {
        // Initialize a Supplier_By_Order object before each test method
        ArrayList<ArrayList<String>> list_of_products = new ArrayList<>();
        ArrayList<String> products = new ArrayList<>();
        products.add("0");
        products.add("100");
        products.add("10");
        products.add("0.5");
        list_of_products.add(products);

        ArrayList<String> products_Category = new ArrayList<>();
        products_Category.add("FRUITS_AND_VEGETABLES");
        products_Category.add("DRINKS");

        supplier = new Supplier_By_Order("tnuva", "0", "moshe", "052-1234567", "12345678", "CASH", "PICK_UP", list_of_products, products_Category);
    }

    @Test
    public void testGetId() {
        assertEquals("0", supplier.getId());
    }

    @Test
    public void testGetSupplyingMethod() {
        assertEquals(Supplying_Method.BY_ORDER, supplier.getSupplying_method());
    }

    @Test
    public void testEditContact() {
        assertEquals("Contact Name: " + "moshe" + "\n" + "Contact Phone: " + "052-1234567" + "\n", supplier.getContact());
        supplier.editContact("david", "052-7654321");
        assertEquals("Contact Name: " + "david" + "\n" + "Contact Phone: " + "052-7654321" + "\n", supplier.getContact());
    }

    @Test
    public void testAddOrder() {
        HashMap<String, Integer> products_in_order = new HashMap<>();
        products_in_order.put("0", 1);
        Order order = new Order(Day.SUNDAY, "0", products_in_order, Supplying_Method.BY_ORDER);
        supplier.addOrder(order);
        assertEquals(1, supplier.orders.size());
    }

    @Test
    public void testRemoveOrder() {
        HashMap<String, Integer> products_in_order = new HashMap<>();
        products_in_order.put("0", 1);
        Order order = new Order(Day.SUNDAY, "0", products_in_order, Supplying_Method.BY_ORDER);
        assertEquals(0, supplier.orders.size());
        supplier.addOrder(order);
        assertEquals(1, supplier.orders.size());
        supplier.removeOrder(order.getOrderId());
        assertEquals(0, supplier.orders.size());
    }
}
