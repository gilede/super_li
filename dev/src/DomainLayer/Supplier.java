package DomainLayer;

import DataAccessLayer.SupplierAgreementDataDTO;
import DataAccessLayer.SupplierDTO;
import DataAccessLayer.SupplierProductCategoryDTO;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Supplier {
    private String name;
    private Contact contact;
    protected final String private_company_id;
    private String bank_account;
    private Payment_Method payment_method;
    private Delivering_Method delivering_Method;
    protected Supplying_Method supplying_method;
    public HashMap<Integer,Order> orders;
    protected Agreement agreement;
    protected ArrayList<Product_Category> products_Category;
    protected SupplierDTO supplierDTO;
    protected ArrayList<SupplierProductCategoryDTO> supplierProductCategoryDTOs;

    public Supplier(String name, String private_company_id, String contact_name, String contact_phone, String bank_account, String payment_method,String delivering_Method ,String supplying_method,ArrayList<ArrayList<String>> list_of_products,ArrayList<String> products_Category) {
        this.name = name;
        this.private_company_id = private_company_id;
        this.contact = new Contact(contact_name, contact_phone);
        this.bank_account = bank_account;
        this.payment_method = convertStringToEnumPayment(payment_method);
        this.delivering_Method = convertStringToEnumDelivering(delivering_Method);
        this.supplying_method = convertStringToEnum(supplying_method);
        this.orders = new HashMap<>();
        this.agreement = new Agreement(list_of_products, private_company_id, supplying_method);
        this.products_Category = makelistOfCategories(products_Category);
        this.supplierDTO = new SupplierDTO(name, private_company_id, contact_name, contact_phone, bank_account, payment_method, delivering_Method, supplying_method);
        this.supplierProductCategoryDTOs = new ArrayList<>();
        this.supplierDTO.Insert();
        for (String category : products_Category) {
            SupplierProductCategoryDTO supplierProductCategoryDTO = new SupplierProductCategoryDTO(private_company_id, category);
            supplierProductCategoryDTO.Insert();
            this.supplierProductCategoryDTOs.add(supplierProductCategoryDTO);
        }
    }
    public Supplier(SupplierDTO supplierDTO, ArrayList<SupplierAgreementDataDTO> supplierAgreementDataDTOs, ArrayList<SupplierProductCategoryDTO> supplierProductCategoryDTOs){
        this.name = supplierDTO.name;
        this.private_company_id = supplierDTO.private_company_id;
        this.contact = new Contact(supplierDTO.contact_name, supplierDTO.contact_phone);
        this.bank_account = supplierDTO.bank_account;
        this.payment_method = convertStringToEnumPayment(supplierDTO.payment_method);
        this.delivering_Method = convertStringToEnumDelivering(supplierDTO.delivering_Method);
        this.supplying_method = convertStringToEnum(supplierDTO.supplying_method);
        this.orders = new HashMap<>();
        this.agreement = new Agreement(supplierAgreementDataDTOs,supplierDTO.supplying_method);
        this.products_Category = makeListOfCategoriesDTO(supplierProductCategoryDTOs);
        this.supplierDTO = supplierDTO;
        this.supplierProductCategoryDTOs = supplierProductCategoryDTOs;
    }
    public void addOrder(Order order){
        orders.put(order.getOrderId(),order);
    }
    public void removeOrder(Integer orderID){
        orders.remove(orderID);
    }


    public double getPrice(Integer order_id){
        return agreement.getPrice(orders.get(order_id).getProducts_in_order());
    }
    public void SetPrice(Integer order_id, double price){
        orders.get(order_id).setTotalPrice(price);
    }

    public String getId() {
        return private_company_id;
    }

    public Supplying_Method getSupplying_method() {
        return supplying_method;
    }

    public void editContact(String contact_name, String contact_phone) {
        contact.set_Contact_Data(contact_name, contact_phone);
        supplierDTO.updateContact(contact_name, contact_phone);
    }
    public String toString(){

        String s= "";
        s+="Name: "+name+"\n";
        s+="Private BIN: "+private_company_id+"\n";
        s+="Contact Name: "+contact.get_Contact_Name()+"\n";
        s+="Contact Phone: "+contact.get_Contact_Phone()+"\n";
        s+="Bank Account: "+bank_account+"\n";
        s+="Payment Method: "+payment_method+"\n";
        s+="Delivering Method: "+replaceUnderscoreWithSpace(delivering_Method.toString()) +"\n";
        s+="Supplying Method: "+replaceUnderscoreWithSpace(supplying_method.toString()) +"\n";
        s+="Products Category: " + replaceUnderscoreWithSpacearray(products_Category) + "\n";
        return s;
    }
    public String getContact(){
        return "*************************************\n"+
                "Supplier Name: " + this.name+"\n"+contact.ToString();
    }
    private Supplying_Method convertStringToEnum(String supplying_method) {
        try {
            return Supplying_Method.valueOf(supplying_method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Invalid Supplying Method");
        }
    }
    private Payment_Method convertStringToEnumPayment(String payment_method) {
        try {
            return Payment_Method.valueOf(payment_method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Invalid Payment Method");
        }
    }
    private Delivering_Method convertStringToEnumDelivering(String delivering_method) {
        try {
            return Delivering_Method.valueOf(delivering_method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Invalid Delivering Method");
        }
    }
    private Product_Category convertStringToEnumCategory(String product_Category) {
        try {
            return Product_Category.valueOf(product_Category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Invalid Category");
        }
    }
    public String getLastOrder(){
        Order lastValue = null;

        // Iterate through the entries of the HashMap
        for (HashMap.Entry<Integer, Order> entry : orders.entrySet()) {
            lastValue = entry.getValue();
        }

            return lastValue.TOString();
    }
    public String allOrders(){
        if (orders.size()==0) {

        return "***********************************\n" +
                "No orders found for this supplier\n" +
                "***********************************\n\n";
        }

        String s="-----------------------------------------------------------------------------------------------\n" +
                "***********************************************************************************************\n";
        for (Order order : orders.values()){
            s+=order.TOString() ;
            s+="***********************************************************************************************\n";
        }
        s+= "-----------------------------------------------------------------------------------------------\n\n";
        return s;
    }
    public static String replaceUnderscoreWithSpace(String input) {
        if (input == null) {
            return null;
        }
        return input.replace('_', ' ');
    }
    public static String replaceUnderscoreWithSpacearray(ArrayList<Product_Category> input) {
        if (input == null) {
            return null;
        }
        String s = "";
        s+='[';
        for (Product_Category category : input) {
            s += replaceUnderscoreWithSpace(category.toString()) + ", ";
        }
        s = s.substring(0, s.length() - 2);
        s+=']';
        return s;

    }
    public Agreement getAgreement() {
        return agreement;
    }
    public boolean isProductExist(String product_id){
        return agreement.isProductExist(product_id);
    }
    public double getProductPrice(String product_id, int quantity){
        return agreement.getProductPrice(product_id, quantity);
    }
    public ArrayList<Product_Category> makeListOfCategoriesDTO(ArrayList<SupplierProductCategoryDTO> supplierProductCategoryDTOs){
        ArrayList<Product_Category> list_of_categories = new ArrayList<>();
        for (SupplierProductCategoryDTO supplierProductCategoryDTO : supplierProductCategoryDTOs) {
            list_of_categories.add(convertStringToEnumCategory(supplierProductCategoryDTO.product_category));
        }
        return list_of_categories;
    }
    public ArrayList<Product_Category> makelistOfCategories(ArrayList<String> products_Category){
        ArrayList<Product_Category> list_of_categories = new ArrayList<>();
        for (String category : products_Category) {
            list_of_categories.add(convertStringToEnumCategory(category));
        }
        return list_of_categories;
    }
    public Order has_open_order(){
        for (Order order : orders.values()){
            if(order.getOrderStatus().equals(Order_Status.RECEIVED) || order.getOrderStatus().equals(Order_Status.BEEN_PROCESSED))
                return order;
        }
        return null;
    }
    public Contact getContactInstance(){
        return contact;
    }
    public Order LastOrderInstance(){
        Order lastValue = null;

        // Iterate through the entries of the HashMap
        for (HashMap.Entry<Integer, Order> entry : orders.entrySet()) {
            lastValue = entry.getValue();
        }
        return lastValue;
    }
    public SupplierDTO getSupplierDTO() {
        return supplierDTO;
    }
    public Delivering_Method getDelivering_Method(){return delivering_Method;}
}
