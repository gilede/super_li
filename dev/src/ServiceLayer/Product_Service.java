package ServiceLayer;
import DomainLayer.Product_Controller;

import java.util.ArrayList;

public class Product_Service {
    private static Product_Service instance;
    private Product_Controller productController;

    private Product_Service() {
        this.productController = Product_Controller.getInstance();
    }

    public static Product_Service getInstance() {
        if (instance == null) {
            instance = new Product_Service();
        }
        return instance;
    }
    public String addProduct(String product_Name, String company_name, String product_Category) {
        return productController.addProduct(product_Name, company_name, product_Category);
    }
    public String removeProduct(String ProductID) {
        return productController.removeProduct(ProductID);
    }
    public String getProducts() {
          return productController.getProducts();
    }
    public String findProductById(String id) {
        return productController.findProductById(id);
    }
    public ArrayList<String> getProductsCategory(ArrayList<ArrayList<String>> list_of_products) {
        return productController.getProductsCategory(list_of_products);
    }
    public boolean Check_Product_Price(String Price) {
        return productController.Check_Product_Price(Price);
    }
    public boolean Check_Product_Quantity(String Quantity) {
        return productController.Check_Product_Quantity(Quantity);
    }
    public boolean Check_product_Discount(String Quantity) {
        return productController.Check_product_Discount(Quantity);
    }
    public Integer Check_String_Greater_than_0(String number) {
        try {
            return productController.Check_String_Greater_than_0(number);
        }
        catch (Exception e) {
            return null;
        }
    }
    public boolean Check_Product_ID(ArrayList<ArrayList<String>> list_of_products,String ProductID) {
        return productController.Check_Product_ID(list_of_products,ProductID);
    }
    public boolean Check_Product_Exist(String ProductID) {
        return productController.Check_Product_Exist(ProductID);
    }
    public boolean check_if_there_is_more_products(int size){
        return productController.check_if_there_is_more_products(size);
    }
    public String loadProducts() {
        return productController.loadProducts();
    }
    public String ExitProducts(){
        return productController.ExitProducts();
    }

}
