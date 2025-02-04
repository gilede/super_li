import DataAccessLayer.*;
import DomainLayer.*;
import PresentationLayer.CLI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
/**
In this part we have to perform 3 types of tests:
1. Tests on the data loaded by the database
2. Tests on the data in the database that is updated in real time against the changes in the domain layer
3. Tests on the new requirement - automatic order due to shortage
*/

public class DataTest {
    private Supplier_Controller supplierController;
    private Product_Controller productController;
    private SupplierControllerDTO supplierControllerDTO;
    private ProductControllerDTO productControllerDTO;
    private OrderControllerDTO orderControllerDTO;
    static boolean Singleton = false;


    @BeforeEach
    void setUp() {
        if (!Singleton) {
            Singleton = true;
            CLI.loadData();
        }
        supplierController = Supplier_Controller.getInstance();
        productController = Product_Controller.getInstance();
        supplierControllerDTO = SupplierControllerDTO.getInstance();
        productControllerDTO = ProductControllerDTO.getInstance();
        orderControllerDTO = OrderControllerDTO.getInstance();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** Type 1 - Loaded Data Tests*/

    /**  Following preliminary use of the system, we know that there are about 350 items in the system numbered in ascending order.
        It should be expected that after loading, the domain will have an item with an ID in the range [0,350] (for example item 18)
        In addition, it should be expected that the system will not recognize items that are not within the range (for example item 1008)*/
    @Test
    public void LoadProductTest_ExistedProduct() {
        assertTrue(productController.Check_Product_Exist("18"));
    }

    @Test
    public void LoadProductTest_UnExistedProduct() {
        assertFalse(productController.Check_Product_Exist("1008"));
    }
    /*  Following preliminary use of the system, we know that there is a supplier in the system with a BIN value equal to 2,
        and there is no supplier in the system with a BIN value equal to 2000000
        It is to be expected that after loading provider 2 will exist in the domain and provider 2000000 will not exist */
    @Test
    public void LoadSupplierTest_ExistedSupplier() {
        assertNotNull(supplierController.FindSupplierBySupplierID("2"));
    }

    @Test
    public void LoadSupplierTest_UnExistedSupplier() {
        assertNull(supplierController.FindSupplierBySupplierID("2000000"));
    }
    /*  Following preliminary use of the system, we know that there is an ID number equal to 3 in the order system,
        and there is no doubt in the system with a BIN value equal to 3333333
        It should be expected that after the loading order 3 will be present in the domain And order 3333333 will not exist */
    @Test
    public void LoadOrderTest_ExistedOrder() {
        assertNotNull(supplierController.FindOrderByOrderID("3"));
    }

    @Test
    public void LoadOrderTest_UnExistedOrder() {
        assertNull(supplierController.FindOrderByOrderID("3333333"));
    }
    /* Following preliminary use of the system, we know that for book number 1, his contact phone information is - "0521234567".
    It should be expected that after loading an order, these contact details will be found with supplier number 1*/
    @Test
    public void LoadContactPhone() {
        assertEquals(supplierController.GetSupplierContactPhone("1"), "0521234567");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** Type 2 - DataUpdate Tests*/


    /* We will make an update at the domain level on the phone number details of the contact number of provider number 1.
    It is expected that the updated details will also be updated in the data layer*/
    @Test
    public void editContactPhone_Changing() {
        supplierController.editcontact("1", "Gil Eden", "0587979648");
        for (SupplierDTO  supplierDTO : supplierControllerDTO.selectAllSuppliers()) {
            if (supplierDTO.private_company_id.equals("1")) {
                assertTrue(supplierDTO.contact_phone.equals("0587979648"));
            }
        }
    }

    @Test
    public void editContactPhone_BackToOriginal() {
        supplierController.editcontact("1", "Gil Eden", "0521234567");
        for (SupplierDTO  supplierDTO : supplierControllerDTO.selectAllSuppliers()) {
            if (supplierDTO.private_company_id.equals("1")) {
                assertTrue(supplierDTO.contact_phone.equals("0521234567"));
            }
        }
    }

    /*  We will update the domain layer about adding a new item to the system
        It is expected that the new item will also appear in the data layer*/
    /*  After That We will make an update on the domain layer about the deletion of the new item we entered earlier,
        It will be observed that the new item will no longer appear in the data layer*/
    @Test
    public void AddProduct_Changing() {
        boolean t=false;
        productController.addProduct("Yellow-Spong", "Scrub-Daddy", "CLEANING_PRODUCTS");
        for(ProductDTO productDTO : productControllerDTO.selectAllProducts()){
            if(productDTO.product_name.equals("Yellow-Spong") && productDTO.company_name.equals("Scrub-Daddy")){
                t=true;
            }
        }
        if(!t){
            assertTrue(false);
        }
        // Part 2
        String newProductID = productController.GetProductIDByFullDescription("Yellow-Spong", "Scrub-Daddy");
        productController.removeProduct(newProductID);
        //check if the product no longer in the db
        for(ProductDTO productDTO : productControllerDTO.selectAllProducts()){
            if(productDTO.product_name.equals("Yellow-Spong") && productDTO.company_name.equals("Scrub-Daddy")){
                t=false;
            }
        }
        if(t){
            assertTrue(true);
        }
    }



    /* We will make an update on the domain layer about adding a new order to supplier 1000.
        It is expected that the new order will also appear in the data layer.
        After That In the same test, we will also delete the new order in the domain layer.
        It will be observed that the new order will no longer appear in the data layer.
        The test will be positive if the 2 tasks were performed successfully*/
    @Test
    public void AddOrder_Changing() {
        HashMap<String, Integer> products_in_order = new HashMap<>();
        products_in_order.put("81", 1);
        products_in_order.put("250", 2);
        supplierController.addOrder("SUNDAY", "1000", products_in_order);
        Order newOederInstance = supplierController.GetOrderInstanceOfLastOrder("1000");
        String newOrderID = String.valueOf(newOederInstance.getOrderId());
        boolean t1= false,t2=true;
        for(OrderDTO orderDTO : orderControllerDTO.selectAllOrders()){
            if(orderDTO.order_id.equals(newOrderID)){
                t1 = true;
            }
        }
        // Part 2
        supplierController.removeOrder(Integer.valueOf(newOrderID));
        for(OrderDTO orderDTO : orderControllerDTO.selectAllOrders()){
            if(orderDTO.order_id.equals(newOrderID)){
                t2 = false;
            }
        }
        assertTrue(t1 && t2);



    }
/**  This method is not a method that counts as a JunitTest
    We will use this method for personal needs after system reset in the state
    of manual tests
    For the benefit of the doubt - this method is deliberately in the note
  */
//    @Test
//    public void RemoveOrder_BackToOriginal() {
//        Order newOederInstance = supplierController.GetOrderInstanceOfLastOrder("1003");
//        int newOrderID = newOederInstance.getOrderId();
//        supplierController.removeOrder(newOrderID);
//
//    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** Type 3 - Automatic Order by Shortages Tests*/


    /**
     In this part of the tests, we examined different end cases against a situation of automatic ordering.
     We used providers with specific data defined for these tests in advance, providers number 1000,1001,1002,1003

     It is important to note - before and at the end of each test, all the above suppliers do not have an open order.
     This illustrates that the test was performed under correct conditions without trying to present a false result.
     */


    /*  This test was conducted as follows:
        1. We opened a manual order against supplier number 1003 of product 150
        2. We opened an order due to the lack of product 150
        3. Supplier 1000 supplies product 150 at the cheapest price
        4. It will be expected that you will open an automatic order against the cheap supplier [supplier 1000]
           and it will be expected that the manual order of supplier 1003 will not be updated*/
    @Test
    public void newShortageByLowerPrice() {
        Order LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
        Order LastOrder1003 = supplierController.GetOrderInstanceOfLastOrder("1003");
        if (LastOrder1003 == null && LastOrder1000 == null) {
            // There is no Opened Orders For Those Suppliers
            HashMap<String, Integer> products_in_order = new HashMap<>();
            HashMap<String, Integer> products_in_order1 = new HashMap<>();
            products_in_order.put("150", 10);
            products_in_order1.put("150", 10);
            supplierController.addOrder("SUNDAY", "1003", products_in_order);
            supplierController.addOrderByShortage(products_in_order1);
            LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
            LastOrder1003 = supplierController.GetOrderInstanceOfLastOrder("1003");
            if (LastOrder1003 != null && LastOrder1000 != null) {
                int Order1000Quantity = LastOrder1000.sumProductQuantities();
                int Order1003Quantity = LastOrder1003.sumProductQuantities();
                if (Order1003Quantity == 10 && Order1000Quantity == 10) {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1003 = LastOrder1003.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1003);
                    assertTrue(true);
                } else {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1003 = LastOrder1003.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1003);
                    assertTrue(false);
                }
            } else {
                if (LastOrder1003 != null) {
                    int OrderID1003 = LastOrder1003.getOrderId();
                    supplierController.removeOrder(OrderID1003);
                }
                if (LastOrder1000 != null) {
                    int OrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(OrderID1000);
                }
                assertTrue(false);
            }
        } else {
            assertTrue(false);
        }
    }

        /* This test was conducted as follows:
           1. We opened a manual order against supplier number 1000 of product 81
           2. We opened an order due to the lack of product 81
           3. Supplier 1000 is the only supplier that supplies product 150, and is therefore also considered the cheapest supplier
           4. It will be expected that you will not open a new order against supplier 1000,
            but that the quantity ordered in the original order will be updated accordingly*/

    @Test
    public void UpdateExistedOrderByShortage() {
        Order LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
        if (LastOrder1000 == null) {
            // There is no Opened Orders For Those Suppliers
            HashMap<String, Integer> products_in_order = new HashMap<>();
            HashMap<String, Integer> products_in_order1000 = new HashMap<>();
            products_in_order.put("81", 10);
            products_in_order1000.put("81", 10);
            supplierController.addOrder("SUNDAY", "1000", products_in_order1000);
            supplierController.addOrderByShortage(products_in_order);
            LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
            if (LastOrder1000 != null) {
                int Order1000Quantity = LastOrder1000.sumProductQuantities();
                if (Order1000Quantity == 20) {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    assertTrue(true);
                } else {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    assertTrue(false);
                }
            } else {
                int OrderID1000 = LastOrder1000.getOrderId();
                supplierController.removeOrder(OrderID1000);
                assertTrue(false);
            }
        } else {
            assertTrue(false);
        }
    }


    /*  This test was conducted as follows:
        1. We opened an order due to the lack of product 123
        2. Supplier 1002 is the supplier that supplies product 123 at the cheapest price, but is a FixedDay type supplier.
           Therefore, we would not like the order to be made against him
           [automatic orders due to shortages are made by ByOrder suppliers only - as stated in the basic assumptions]
        3. Supplier 1000 is the cheapest supplier among the ByOrder suppliers that markets product 123
        4. It will be observed that you will not open a new order against supplier 1002, but will open an order exclusively against supplier 1000
     */

    @Test
    public void ByOrderAboveLowPrice() {
        Order LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
        Order LastOrder1002 = supplierController.GetOrderInstanceOfLastOrder("1002");
        if (LastOrder1002 == null && LastOrder1000 == null) {
            // There is no Opened Orders For Those Suppliers
            HashMap<String, Integer> products_in_order = new HashMap<>();
            products_in_order.put("123", 10);
            supplierController.addOrderByShortage(products_in_order);
            LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
            LastOrder1002 = supplierController.GetOrderInstanceOfLastOrder("1002");
            if (LastOrder1002 == null && LastOrder1000 != null) {
                int Order1000Quantity = LastOrder1000.sumProductQuantities();
                if (Order1000Quantity == 10) {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    assertTrue(true);
                } else {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    assertTrue(false);
                }
            } else {
                assertTrue(false);
            }
        } else {
            assertTrue(false);
        }
    }


    /*  This test was conducted as follows:
        1. We opened a manual order against supplier number 1001 and against supplier 1000 of product 200
        2. We opened an order due to the absence of product 200
        3. Supplier 1000 and supplier 1001 deliver item 2000 at the same price (according to the ordered quantity specified in the test)
        4. It is expected that only one manual order will be updated according to the quantities listed in the automatic order
        - it is not critical which order will be updated.*/
    @Test
    public void UpdateOnlyOneTimeCheck() {
        Order LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
        Order LastOrder1001 = supplierController.GetOrderInstanceOfLastOrder("1001");
        if (LastOrder1001 == null && LastOrder1000 == null) {
            // There is no Opened Orders For Those Suppliers
            HashMap<String, Integer> products_in_order = new HashMap<>();
            HashMap<String, Integer> products_in_order1000 = new HashMap<>();
            HashMap<String, Integer> products_in_order1001 = new HashMap<>();
            products_in_order1000.put("200", 3);
            products_in_order1001.put("200", 3);
            products_in_order.put("200", 3);
            supplierController.addOrder("SUNDAY", "1000", products_in_order1000);
            supplierController.addOrder("SUNDAY", "1001", products_in_order1001);
            LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
            LastOrder1001 = supplierController.GetOrderInstanceOfLastOrder("1001");
            supplierController.addOrderByShortage(products_in_order);
            if (LastOrder1001 != null && LastOrder1000 != null) {
                int Order1000Quantity = LastOrder1000.sumProductQuantities();
                int Order1001Quantity = LastOrder1001.sumProductQuantities();
                if ((Order1001Quantity == 3 && Order1000Quantity == 6) || (Order1001Quantity == 6 && Order1000Quantity == 3)) {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1001 = LastOrder1001.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1001);
                    assertTrue(true);
                } else {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1001 = LastOrder1001.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1001);
                    assertTrue(false);
                }
            } else {
                if (LastOrder1001 != null) {
                    int OrderID1001 = LastOrder1001.getOrderId();
                    supplierController.removeOrder(OrderID1001);
                }
                if (LastOrder1000 != null) {
                    int OrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(OrderID1000);
                }
                assertTrue(false);
            }
        } else {
            assertTrue(false);
        }
    }


    /*  This test was conducted as follows:
        1. We opened a manual order against supplier number 1001 and against supplier 1000 of product 200
        2. We opened an order due to the absence of product 200
        3. In general, supplier 1000 and supplier 1001 supply item 2000 at the same price
           However, considering the quantity written in front of the suppliers, supplier number 1001 gives a discount (for the quantity found in this test)
            and therefore it is preferred in terms of economic viability
        4. It is expected that the order belonging to supplier 1001 will be updated according to the quantities listed in the automatic order
        - and that the quantities in the order of supplier 1000 will not change.
     */
    @Test
    public void UpdateByDiscountPrice(){
        Order LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
        Order LastOrder1001 = supplierController.GetOrderInstanceOfLastOrder("1001");
        if (LastOrder1001 == null && LastOrder1000 == null) {
            // There is no Opened Orders For Those Suppliers
            HashMap<String, Integer> products_in_order = new HashMap<>();
            HashMap<String, Integer> products_in_order1000 = new HashMap<>();
            HashMap<String, Integer> products_in_order1001 = new HashMap<>();
            products_in_order.put("200", 24);
            products_in_order1000.put("200", 24);
            products_in_order1001.put("200", 24);
            supplierController.addOrder("SUNDAY", "1000", products_in_order1000);
            supplierController.addOrder("SUNDAY", "1001", products_in_order1001);
            LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
            LastOrder1001 = supplierController.GetOrderInstanceOfLastOrder("1001");
            supplierController.addOrderByShortage(products_in_order);
            if (LastOrder1001 != null && LastOrder1000 != null) {
                int Order1000Quantity = LastOrder1000.sumProductQuantities();
                int Order1001Quantity = LastOrder1001.sumProductQuantities();
                if (Order1001Quantity == 48 && Order1000Quantity == 24) {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1001 = LastOrder1001.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1001);
                    assertTrue(true);
                } else {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1001 = LastOrder1001.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1001);
                    assertTrue(false);
                }
            } else {
                if (LastOrder1001 != null) {
                    int OrderID1001 = LastOrder1001.getOrderId();
                    supplierController.removeOrder(OrderID1001);
                }
                if (LastOrder1000 != null) {
                    int OrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(OrderID1000);
                }
                assertTrue(false);
            }
        } else {
            assertTrue(false);
        }

    }

    /*  This test was conducted as follows:
        1. We opened a manual order against supplier number 1003 and against supplier 1000 of product 150
        2. We opened an order due to the absence of product 150
        3. The quantities found in the manual orders are quantities which do not grant a discount,
           but an addition to the quantity found in the order due to the lack of an option for a discount for both orders.
        4. In general, supplier number 1000 provides the product at a lower price
           However, due to the effects of the discount, it would be worthwhile to update the order of supplier 1003 - thus we will save more costs.
        5. It is expected that the order belonging to supplier 1003 will be updated according to the quantities listed in the automatic order -
        and that the quantities in the order of supplier 1000 will not change. */

    @Test
    public void UpdateByTotalLowerPrice() {
        Order LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
        Order LastOrder1003 = supplierController.GetOrderInstanceOfLastOrder("1003");
        if (LastOrder1003 == null && LastOrder1000 == null) {
            // There is no Opened Orders For Those Suppliers
            HashMap<String, Integer> products_in_order = new HashMap<>();
            HashMap<String, Integer> products_in_order1000 = new HashMap<>();
            HashMap<String, Integer> products_in_order1003 = new HashMap<>();
            products_in_order.put("150", 18);
            products_in_order1000.put("150", 11);
            products_in_order1003.put("150", 120);
            supplierController.addOrder("SUNDAY", "1000", products_in_order1000);
            supplierController.addOrder("SUNDAY", "1003", products_in_order1003);
            LastOrder1000 = supplierController.GetOrderInstanceOfLastOrder("1000");
            LastOrder1003 = supplierController.GetOrderInstanceOfLastOrder("1003");
            supplierController.addOrderByShortage(products_in_order);
            if (LastOrder1003 != null && LastOrder1000 != null) {
                int Order1000Quantity = LastOrder1000.sumProductQuantities();
                int Order1003Quantity = LastOrder1003.sumProductQuantities();
                if (Order1003Quantity == 138 && Order1000Quantity == 11) {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1003 = LastOrder1003.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1003);
                    assertTrue(true);
                } else {
                    int newOrderID1000 = LastOrder1000.getOrderId();
                    int newOrderID1003 = LastOrder1003.getOrderId();
                    supplierController.removeOrder(newOrderID1000);
                    supplierController.removeOrder(newOrderID1003);
                    assertTrue(false);
                }
            } else {
                if (LastOrder1003 != null) {
                    int OrderID1003 = LastOrder1003.getOrderId();
                    supplierController.removeOrder(OrderID1003);
                }
                if (LastOrder1000 != null) {
                    int OrderID1000 = LastOrder1000.getOrderId();
                    supplierController.removeOrder(OrderID1000);
                }
                assertTrue(false);
            }
        } else {
            assertTrue(false);
        }

    }

}