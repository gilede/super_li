import DomainLayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupplierFixedDayTest {
    private Supplier_Fixed_Days supplier;

    @BeforeEach
    public void setUp() {
        // Initialize a Supplier_Fixed_Days object before each test method
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

        ArrayList<String> days = new ArrayList<>();
        days.add("SUNDAY");
        days.add("MONDAY");

        supplier = new Supplier_Fixed_Days("tnuva", "0", "moshe", "052-1234567", "12345678", "CASH", "PICK_UP", days, list_of_products, products_Category);
    }

    @Test
    public void testGetId() {
        assertEquals("0", supplier.getId());
    }

    @Test
    public void testGetSupplyingMethod() {
        assertEquals(Supplying_Method.FIXED_DAYS, supplier.getSupplying_method());
    }

    @Test
    public void testEditContact() {
        assertEquals("Contact Name: " + "moshe" + "\n" + "Contact Phone: " + "052-1234567" + "\n", supplier.getContact());
        supplier.editContact("david", "052-7654321");
        assertEquals("Contact Name: " + "david" + "\n" + "Contact Phone: " + "052-7654321" + "\n", supplier.getContact());
    }

    @Test
    public void testCheckDay() {
        assertEquals(true, supplier.checkDay(Day.SUNDAY));
    }
}
