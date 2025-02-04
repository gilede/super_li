package DomainLayer;

import DataAccessLayer.*;
import ServiceLayer.Supplier_Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Supplier_Controller {
    private static Supplier_Controller instance;
    private ArrayList<Supplier> suppliers;
    private Order_Controller order_controller;
    private SupplierControllerDTO supplierControllerDTO;
    public Supplier_Controller() {
        this.order_controller = Order_Controller.getInstance();
        suppliers = new ArrayList<>();
        supplierControllerDTO = SupplierControllerDTO.getInstance();
    }

    public static Supplier_Controller getInstance() {
        if (instance == null) {
            instance = new Supplier_Controller();
        }
        return instance;
    }
    public String add_Fixed_day_Supplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<String> days, ArrayList<ArrayList<String>> list_of_products, ArrayList<String> products_Category) {
        try {
            ValidId(private_company_id);
            suppliers.add(new Supplier_Fixed_Days(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, days, list_of_products, products_Category));

            return "***********************************************\nThe new supplier has been added successfully.\n***********************************************\n\n";
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public String add_By_Order_Supplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<ArrayList<String>> list_of_products, ArrayList<String> products_Category) {
        try {
            ValidId(private_company_id);
            suppliers.add(new Supplier_By_Order(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, list_of_products, products_Category));
            return "***********************************************\nThe new supplier has been added successfully.\n***********************************************\n\n";
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public String addOrder(String shipmentDate, String supplierID, HashMap<String, Integer> products_in_order) {
        try {
            isSupplierExist(supplierID);
            Supplier supplier = findSupplier(supplierID);
            Check_Products(products_in_order, supplier);
            Order order = this.order_controller.addOrder(shipmentDate, supplierID, products_in_order, supplier.getSupplying_method());
            supplier.addOrder(order);
            supplier.SetPrice(order.getOrderId(), supplier.getPrice(order.getOrderId()));
            if(supplier.getDelivering_Method().equals(Delivering_Method.PICK_UP)){
                return "order_date:" + order.getShipmentDate() + ":supplier_id:"+supplier.getId() + ":order_id:"+order.getOrderId()+":contact_phone:"+supplier.getContactInstance().get_Contact_Phone();
            }
            return "*********************************************\nOrder number " + order.getOrderId() + " has been opened successfully. \n" +
                    "*********************************************\n\n";
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }
    public String addOrderByShortage(HashMap<String, Integer> products_in_order) {
        try {
            for (Map.Entry<String, Integer> entry : products_in_order.entrySet()) {
                String product_id = entry.getKey();
                int quantity = entry.getValue();
                Supplier supplier = find_cheapest_supplier(product_id,quantity);
                Order order = supplier.has_open_order();
                //if there is an open order for this supplier
                if(order!= null) {
                    int new_quantity = order.GetCurrentQuantityOfProduct(product_id) + quantity;
                    EditOrder(order.getOrderId().toString(), product_id, String.valueOf(new_quantity));
                }
                //open new order
                else{
                    String shipmentDate = WeekFromNow();
                    HashMap<String, Integer> products_in_order1 = new HashMap<>();
                    products_in_order1.put(product_id, products_in_order.get(product_id));
                    addOrder(shipmentDate, supplier.getId(), products_in_order1);
                }
            }
            return "********************************************************\nThe orders by shortage has been processed successfully\n" +
                    "********************************************************\n\n";
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }
    public String WeekFromNow() {
        // Get the current date
        LocalDate today = LocalDate.now();
        // Add 7 days to the current date to get the date one week from now
        LocalDate weekFromNow = today.plusDays(7);
        // Get the day of the week for the date one week from now
        DayOfWeek dayOfWeek = weekFromNow.getDayOfWeek();
        // Return the name of the day of the week in uppercase
        return dayOfWeek.toString();
    }
    public String EditOrder(String orderID, String product_Number, String quantity) {
        try {
            Integer order_id = Integer.parseInt(orderID);
            Integer quantity_order = Check_String_Greater_than_0(quantity);
            Supplier supplier = findSupplier(order_controller.ExistedOrder(order_id).getSupplierID());
            // need to check if this product number is existed in the agreement
            if (!supplier.isProductExist(product_Number)) {

                return "*********************************************************************************************\n" +
                        "This product cannot be added because it is not included in the agreement with the supplier\n" +
                        "*********************************************************************************************\n\n";
            }
            // create new order and cancel it (so will be in the history) we change the original order
            if(AtLeastOneOrderArrivedChecking(order_id) && supplier.getSupplying_method().equals(Supplying_Method.FIXED_DAYS)){
                Order new_Order = DeepCopyOrder(order_id);
                supplier.addOrder(new_Order);
                setCancel(new_Order);

            }
            String s = order_controller.EditOrder(order_id, product_Number, quantity_order);
            supplier.SetPrice(order_id, supplier.getPrice(order_id));
            return s;
        }
        catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public String Remove_product_from_order(String orderID, String product_Number) {
        try {
            Integer order_id = Integer.parseInt(orderID);
            Supplier supplier = findSupplier(order_controller.ExistedOrder(order_id).getSupplierID());
            // create new order and cancel it (so will be in the history) we change the original order
            if(AtLeastOneOrderArrivedChecking(order_id)){
                Order new_Order = DeepCopyOrder(order_id);
                supplier.addOrder(new_Order);
                setCancel(new_Order);
            }
            String s = order_controller.Remove_product_from_order(order_id, product_Number);
            supplier.SetPrice(order_id, supplier.getPrice(order_id));
            return s;
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public String removeOrder(Integer orderID) {
        try {
            Order order =order_controller.ExistedOrder(orderID);
            if (order==null) {
            return "**************************************************************************\n" +
                    "There is no order in the system that belongs to the order number - " + orderID + "" +
                    "\n**************************************************************************\n\n";
            }
            Supplier supplier = findSupplier(order.getSupplierID());
            return order_controller.removeOrder(orderID, supplier);
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public String getSuppliers() {
        String s = "-----------------------------------------------------------------------------------\n***********************************************************************************\n";
        for (Supplier supplier : suppliers) {
            s += supplier.toString() ;
            s += "***********************************************************************************\n";
        }
        s+="-----------------------------------------------------------------------------------\n\n";
        return  s;
    }

    public String supplier_card(String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                return "********************************************************\n" + supplier.toString() +
                        "********************************************************\n\n";
            }
        }

        return "*********************\nSupplier not found\n*********************\n\n"; // Supplier not found
    }

    private boolean isSupplierExist(String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                return true;
            }
        }
        return false; // Supplier not found
    }

    private Supplier findSupplier(String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                return supplier;
            }
        }
        return null; // Supplier not found
    }

    public String LastOrderSupplier(String supplierID) {
        isSupplierExist(supplierID);
        Supplier supplier = findSupplier(supplierID);
        if (supplier.orders.size()==0) {
            return "***********************************\n" +
                    "No orders found for this supplier\n" +
                    "***********************************\n\n";
        }
        return "***********************************************************************************************\n" + supplier.getLastOrder() + "***********************************************************************************************\n\n";
    }

    public String allSupplierOrders(String supplierID) {
        isSupplierExist(supplierID);
        Supplier supplier = findSupplier(supplierID);
        return supplier.allOrders();
    }


    public String editcontact(String supplier_id, String contact_name, String contact_phone) {
        try {
            isSupplierExist(supplier_id);
            Supplier supplier = findSupplier(supplier_id);
            supplier.editContact(contact_name, contact_phone);

            return "*********************************************\nContact data has been updated successfully\n*********************************************\n\n";
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public String getcontact(String supplier_id) {
        try {
            isSupplierExist(supplier_id);
            Supplier supplier = findSupplier(supplier_id);
            return supplier.getContact();
        } catch (AssertionError e) {
            return e.getMessage();
        }

    }

    public boolean ValidId(String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                throw new AssertionError("The system already has a Supplier with such a BIN. It is not possible to add another supplier with the same BIN");
            }
        }
        return true;
    }

    public boolean AtLeastOneOrderArrivedChecking(Integer orderID) {

            return order_controller.AtLeastOneOrderArrivedChecking(orderID);
    }

    public Order DeepCopyOrder(Integer orderID) {
        return order_controller.DeepCopyOrder(orderID);
    }

    public Order GetOrderByOrderID(Integer orderID) {
        return order_controller.ExistedOrder(orderID);
    }

    public void setCancel(Order coppyOrder) {
        order_controller.setCancel(coppyOrder);
    }

    public String BilOfQuantities(String supplier_id) {
        try {
            isSupplierExist(supplier_id);
            Supplier supplier = findSupplier(supplier_id);
            return supplier.getAgreement().BilOfQuantities();
        } catch (AssertionError e) {
            return e.getMessage();
        }
    }

    public boolean Check_Phone(String phone) {
        return phone.matches("^05\\d{8}$");
    }

    public boolean IsFixedDay(String Supplying_Method) {
        return Supplying_Method.equals("FIXED_DAYS");
    }
    public boolean ExistedOrderChecking(String orderID) {
        try {
            Integer order_id = Integer.parseInt(orderID);
            return order_controller.ExistedOrderChecking(order_id);
        } catch (AssertionError e) {
            return false;
        }
    }

    public boolean ExistedSupplierIDChecking(String SupplierID) {
        try {
            for (Supplier supplier : suppliers) {
                if (supplier.getId().equals(SupplierID)) {
                    return true;
                }
            }
            return false;
        } catch (AssertionError e) {
            return false;
        }
    }

    public Integer Check_String_Greater_than_0(String number) {
        Integer quantity = Integer.parseInt(number);
        if(quantity > 0)
            return quantity;
        else
            throw new AssertionError("*************************************\n+" +
                    "The quantity must be greater than 0\n" +
                    "*************************************\n\n");
    }
    public boolean Contains_product(HashMap<String, Integer> products_in_order, String product_Number){
        return !products_in_order.containsKey(product_Number);}
    public String loadSuppliers() {
        try {
            ArrayList<SupplierDTO> SupplierDTOs = supplierControllerDTO.selectAllSuppliers();
            for (SupplierDTO supplierDTO : SupplierDTOs) {
                ArrayList<SupplierAgreementDataDTO> SupplierAgreementDataDTOs = supplierControllerDTO.selectSupplierAgreementData(supplierDTO.private_company_id);
                ArrayList<SupplierProductCategoryDTO> SupplierProductCategoryDTOs = supplierControllerDTO.selectSupplierProductCategory(supplierDTO.private_company_id);
                if (supplierDTO.supplying_method.equals("FIXED_DAYS")) {
                    ArrayList<SupplierDaysDTO> SupplierDaysDTOs = supplierControllerDTO.selectSupplierDays(supplierDTO.private_company_id);
                    suppliers.add(new Supplier_Fixed_Days(supplierDTO, SupplierAgreementDataDTOs,SupplierProductCategoryDTOs, SupplierDaysDTOs));
                } else {
                    suppliers.add(new Supplier_By_Order(supplierDTO, SupplierAgreementDataDTOs, SupplierProductCategoryDTOs));
                }
            }
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    public String loadOrders() {
        try {
            HashMap<Integer, Order> orders_of_suppliers = order_controller.loadOrders();
            for (Supplier supplier : suppliers) {
                for (Order order : orders_of_suppliers.values()) {
                    if (order.getSupplierID().equals(supplier.getId())) {
                        supplier.addOrder(order);
                    }
                }
            }
            return "";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }
    public Supplier FindSupplierBySupplierID(String supplierID){
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(supplierID)) {
                return supplier;
            }
        }
        return null;
    }
    public Order FindOrderByOrderID(String orderID){
        return order_controller.FindOrderByOrderID(orderID);
    }
    public boolean Check_Products(HashMap<String, Integer> products_in_order, Supplier supplier) {
        for (String product_Number : products_in_order.keySet()) {
            if (!supplier.isProductExist(product_Number)) {
                throw new AssertionError("*********************************************************************************************\n" +
                        "This product cannot be added because it is not included in the agreement with the supplier\n" +
                        "*********************************************************************************************\n\n");
            }
        }
        return true;
    }
    public Supplier find_cheapest_supplier(String productId, int quantity) {
        Supplier cheapest_supplier = null;
        double min_price = Double.MAX_VALUE;
        Order order;
        int old_quantity;
        int newQuantity;
        double newPrice;
        double OldPrice;
        double ExtraPrice;

        HashMap<String, Integer> productsInOrder;
        for (Supplier supplier : suppliers) {
            if (supplier.isProductExist(productId) && supplier.getSupplying_method().equals(Supplying_Method.BY_ORDER)) {
                order = supplier.has_open_order();
                if (order != null) {
                    productsInOrder = order.getProducts_in_order();
                    for (String product_Number : productsInOrder.keySet()) {
                        if (product_Number.equals(productId)) {
                            old_quantity = order.GetCurrentQuantityOfProduct(product_Number);
                            newQuantity = old_quantity+quantity;
                            OldPrice = supplier.getProductPrice(productId, old_quantity)*old_quantity;
                            newPrice = supplier.getProductPrice(productId, newQuantity)*newQuantity;
                            ExtraPrice = (newPrice-OldPrice);
                            if (ExtraPrice < min_price) {
                                min_price = ExtraPrice;
                                cheapest_supplier = supplier;
                            }
                            // No need to check the price outside the loop if we find the product in an open order
                            break;
                        }
                    }
                    // If product not found in open orders, check the price directly
                    ExtraPrice = supplier.getProductPrice(productId, quantity) * quantity;
                    if (ExtraPrice < min_price) {
                        min_price = ExtraPrice;
                        cheapest_supplier = supplier;
                    }
                } else {
                    // No open orders, check the price directly
                    ExtraPrice = supplier.getProductPrice(productId, quantity) * quantity;
                    if (ExtraPrice < min_price) {
                        min_price = ExtraPrice;
                        cheapest_supplier = supplier;
                    }
                }
            } else {
                // Continue if product doesn't exist or the supplying method is not BY_ORDER
                continue;
            }
        }
        if(cheapest_supplier == null)
            throw new AssertionError("*************************************************************\n" +
                    "There is no supplier in the system that supplies the product\n" +
                    "*************************************************************\n\n");
        return cheapest_supplier;
    }

    public String GetSupplierContactPhone(String supplierID){
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(supplierID)) {
                return supplier.getContactInstance().get_Contact_Phone();
            }
        }
        return null;
    }
    public Order GetOrderInstanceOfLastOrder(String supplierID){
        for (Supplier supplier : suppliers){
            if (supplier.getId().equals(supplierID)){

                return supplier.LastOrderInstance();
            }
        }
        return null;
    }
    public String ExitSuppliers(){
        supplierControllerDTO.closeConnection();
        return "The connection to the database has been closed successfully";
    }
    public String ExitOrders(){
        order_controller.ExitOrders();
        return "The connection to the database has been closed successfully";
    }

}