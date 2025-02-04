package ServiceLayer;


import java.util.ArrayList;
import java.util.HashMap;

public class Service_Controller {
    private static Service_Controller instance;
    private Supplier_Service supplierService;
    private Product_Service productService;

    private Service_Controller() {
        this.supplierService = Supplier_Service.getInstance();
        this.productService = Product_Service.getInstance();
    }

    public static Service_Controller getInstance() {
        if (instance == null) {
            instance = new Service_Controller();
        }
        return instance;
    }
    public String addProduct(String product_Name, String company_name, String product_Category) {
        try{
            return productService.addProduct(product_Name, company_name, product_Category);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String findProductById(String id) {
        try {
            return productService.findProductById(id);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getProducts() {
        try{
            return productService.getProducts();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String removeProduct(String ProductID) {
        try {
            return productService.removeProduct(ProductID);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String AddFixedDaySupplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<String> days, ArrayList<ArrayList<String>> list_of_products) {
        try {
            ArrayList<String> products_Category = productService.getProductsCategory(list_of_products);
            return supplierService.addFixedDaySupplier(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, days, list_of_products,products_Category);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String addByOrderSupplier(String supplier_Name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method, String delivering_method, ArrayList<ArrayList<String>> list_of_products) {
        try {
            ArrayList<String> products_Category1 = productService.getProductsCategory(list_of_products);
            return supplierService.addByOrderSupplier(supplier_Name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_method, list_of_products, products_Category1);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getSuppliers() {
        try {
            return supplierService.getSuppliers();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String supplier_card(String id) {
        try {
            return supplierService.supplier_card(id);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String editcontact(String supplier_id, String contact_name, String contact_phone) {
        try {
            return supplierService.editcontact(supplier_id, contact_name, contact_phone);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getcontact(String supplier_id) {
        try {
            return supplierService.getcontact(supplier_id);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String editOrder(String orderID, String product_Number, String quantity) {
        try {
            return supplierService.EditOrder(orderID, product_Number, quantity);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addOrder(String shipmentDate, String supplierID, HashMap<String, Integer> products_in_order) {
        try {
            //if fixrs day
            return supplierService.addOrder(shipmentDate, supplierID, products_in_order);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String addOrderByShortage(HashMap<String, Integer> products_in_order) {
        try {
            return supplierService.addOrderByShortage(products_in_order);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String Remove_product_from_order(String orderID, String product_Number) {
        try {
            return supplierService.Remove_product_from_order(orderID, product_Number);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String removeOrder(Integer orderID) {
        try {
            return supplierService.removeOrder(orderID);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String allSupplierOrders(String supplierID) {
        try {
            return supplierService.allSupplierOrders(supplierID);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String LastOrderSupplier(String supplierID) {
        try {
            return supplierService.LastOrderSupplier(supplierID);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public boolean Check_Phone(String phone){return supplierService.Check_Phone(phone);}
    public boolean Check_Product_Price(String product_Price){return productService.Check_Product_Price(product_Price);}
    public boolean Check_product_Quantity(String product_Quantity){return productService.Check_Product_Quantity(product_Quantity);}
    public boolean Check_product_Discount(String product_Discount){return productService.Check_product_Discount(product_Discount);}
    public String BilOfQuantities(String supplier_id){
        return supplierService.BilOfQuantities(supplier_id);
    }
    public boolean IsFixedDay(String Supplying_Method){return supplierService.IsFixedDay(Supplying_Method);}
    public Integer Check_String_Greater_than_zero(String number){return productService.Check_String_Greater_than_0(number);}
    public boolean ExistedOrderChecking(String orderID){return supplierService.ExistedOrderChecking(orderID);}
    public boolean Check_Product_ID(ArrayList<ArrayList<String>> list_of_products,String product_ID){return productService.Check_Product_ID(list_of_products,product_ID);}
    public boolean Contains_product(HashMap<String, Integer> products_in_order, String product_Number){
        return productService.Check_Product_Exist(product_Number) && supplierService.Contains_product(products_in_order,product_Number);}
    public boolean check_if_there_is_more_products(int size){return productService.check_if_there_is_more_products(size);}
    public String loadData(){
        try {
            return productService.loadProducts() + supplierService.loadSuppliers() + supplierService.loadOrders();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String ExitData(){
        try {
            return productService.ExitProducts() + supplierService.ExitSuppliers() + supplierService.ExitOrders();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
}
