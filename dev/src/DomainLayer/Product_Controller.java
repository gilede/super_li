package DomainLayer;

import DataAccessLayer.DTO;
import DataAccessLayer.ProductControllerDTO;
import DataAccessLayer.ProductDTO;

import java.util.ArrayList;

public class Product_Controller {
    private static Product_Controller instance;
    private ArrayList<Product> products;
    private static int product_id = 0;
    private ProductControllerDTO productControllerDTO;
    public Product_Controller() {
        products = new ArrayList<>();
        productControllerDTO = ProductControllerDTO.getInstance();
    }
    public static Product_Controller getInstance() {
        if (instance == null) {
            instance = new Product_Controller();
        }
        return instance;
    }
    public String addProduct(String product_Name, String company_name, String product_Category) {
        try {
            products.add(new Product(String.valueOf(product_id), product_Name, company_name, product_Category));
            product_id++;
            return "*****************************************************************************************\nThe new product has been added successfully. The product number in the system is - " + (product_id - 1) + "\n*****************************************************************************************\n\n\n";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeProduct(String ProductID) {
        Product product = findProduct(ProductID);
        if (product == null) {

            return "*****************************************************************************************\nThere is no product in the system that belongs to the entered product number.\n*****************************************************************************************\n\n\n\n";
        } else {
            products.remove(product);
            product.getProductDTO().Delete();
            product_id--;
            return "*****************************************************************************************\nThe product was successfully removed from the system.\n*****************************************************************************************\n\n\n\n";
        }
    }

    public String getProducts() {

        String productsString = "-----------------------------------------------------------\n***********************************************************\n";
        for (Product product : products) {
            productsString += product.ToString() + "\n";
        }
        productsString +="-----------------------------------------------------------\n";
        return productsString;
    }

    public String findProductById(String id) {
        String productsString ="***********************************************************\n";
        for (Product product : products) {
            if (product.getId().equals(id)) {
                productsString+= product.ToString()+"\n";
                return productsString;
            }
        }
        return "******************************************************************************\nThere is no product in the system that belongs to the entered product number.\n******************************************************************************\n";
    }

    private Product findProduct(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null; // Product not found
    }

    public ArrayList<String> getProductsCategory(ArrayList<ArrayList<String>> list_of_products) {
        ArrayList<String> products_Category = new ArrayList<>();
        for (ArrayList<String> product : list_of_products) {
            Product product1 = findProduct(product.get(0));
            if (product1 == null) {
                throw new AssertionError("Product number " + product.get(0) + "does not exist in the system.\n");
            }
            if (!products_Category.contains(product1.getCategory()))
                products_Category.add(product1.getCategory());
        }
        return products_Category;
    }


    public boolean Check_Product_Price(String product_Price) {
        return product_Price.matches("^0*[1-9]\\d*(\\.\\d+)?$");
    }

    public boolean Check_Product_Quantity(String product_Quantity) {
        return product_Quantity.matches("^[1-9]\\d*$");
    }

    public boolean Check_product_Discount(String product_Discount) {
///////// check if the string is a decimal number between 0.01 to 0.99
        //////// it can handle with any number you entered as sting
            // Check if the input matches the pattern
            if (product_Discount.matches("^0\\.(\\d+)$")) {
                // Convert the string after the decimal point to a double
                double value = Double.parseDouble(product_Discount);
                // Check if the value is in the range [0.01, 0.99]
                if (value >= 0.01 && value <= 0.99) {
                    return true;
                }
            }
            return false;
        }

    public Integer Check_String_Greater_than_0(String number) {
        Integer quantity = Integer.parseInt(number);
        if(quantity > 0)
            return quantity;
        else
            return null;
    }
    public boolean Check_Product_ID(ArrayList<ArrayList<String>> list_of_products,String ProductID) {
        boolean t= true;
        for (ArrayList<String> product : list_of_products) {
            if (product.get(0).equals(ProductID)) {
                t = false;
            }
        }
        if(!Check_Product_Exist(ProductID))
            t=false;
        return t;
    }
    public boolean check_if_there_is_more_products(int size){
        return products.size()>=size;
    }
    public String loadProducts() {
        try {
            ArrayList<ProductDTO> ProductDTOs = productControllerDTO.selectAllProducts();
            for (ProductDTO productDTO : ProductDTOs) {
                Product product = new Product(productDTO);
                products.add(product);
                product_id++;
            }
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    public boolean Check_Product_Exist(String ProductID) {
        return findProduct(ProductID) != null;
    }
    public String GetProductIDByFullDescription(String ProductName, String ProductCompany){
        for (Product product : products){
            if (product.getProduct_name().equals(ProductName) && product.getCompanyName().equals(ProductCompany)){
                return product.getId();
            }
        }
        return null;
    }
    public String ExitProducts(){
        productControllerDTO.closeConnection();
        return "product connecion closed";
    }
}

