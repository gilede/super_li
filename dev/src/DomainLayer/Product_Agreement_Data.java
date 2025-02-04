package DomainLayer;

import java.util.ArrayList;

public class Product_Agreement_Data {
    private String catalog_number;
    private double price;
    private int quantity_for_discount;
    private double discount_precentage;

    public Product_Agreement_Data(ArrayList<String> list_of_product, String supplier_id) {
        // i added new supplier id and i connect it to the product id
        this.catalog_number = list_of_product.get(0)+"."+supplier_id;
        this.price = Double.parseDouble(list_of_product.get(1));
        this.quantity_for_discount = Integer.parseInt(list_of_product.get(2));
        this.discount_precentage = Double.parseDouble(list_of_product.get(3));
    }

    public String get_original_CatalogNumber() {
        return catalog_number.split("\\.")[0];
    }
    public double getPrice() {
        return price;
    }
    public double getPrice(int quantity) {
        if (quantity >= quantity_for_discount) {
            return price * (1 - discount_precentage);
        }
        return price;
    }
    public int getQuantityForDiscount() {
        return quantity_for_discount;
    }
    public double getDiscountPrecentage() {
        return discount_precentage;
    }
    public String ToString(){
        return "Catalog Number: "+catalog_number+" Price: "+price+" Quantity for Discount: "+quantity_for_discount+" Discount Precentage: "+discount_precentage;
    }

}
