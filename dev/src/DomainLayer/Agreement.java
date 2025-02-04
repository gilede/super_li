package DomainLayer;

import DataAccessLayer.SupplierAgreementDataDTO;

import java.util.ArrayList;
import java.util.HashMap;

// created by Supplier controller
public class Agreement {
    private Supplying_Method supplying_method;
    private ArrayList<Product_Agreement_Data> products;
    private ArrayList<String> products_id;
    private int supplier_id;
    protected ArrayList<SupplierAgreementDataDTO> supplierAgreementDataDTOs;
    public Agreement(ArrayList<ArrayList<String>> list_of_products, String supplier_id, String supplying_method) {
        this.supplier_id = Integer.parseInt(supplier_id);
        this.supplying_method = convertStringToEnum(supplying_method);
        this.products = new ArrayList<>();
        this.products_id = new ArrayList<>();
        this.supplierAgreementDataDTOs = new ArrayList<>();
        for (ArrayList<String> product : list_of_products) {
            // i added new supplier id and connect it to the product id
            products.add(new Product_Agreement_Data(product, supplier_id));
            products_id.add(product.get(0));
            SupplierAgreementDataDTO supplierAgreementDataDTO = new SupplierAgreementDataDTO(supplier_id, product.get(0), product.get(1), product.get(2), product.get(3));
            supplierAgreementDataDTO.Insert();
            supplierAgreementDataDTOs.add(supplierAgreementDataDTO);
        }

    }
    public Agreement(ArrayList<SupplierAgreementDataDTO> supplierAgreementDataDTOs, String supplying_method) {
        this.supplierAgreementDataDTOs = supplierAgreementDataDTOs;
        this.supplier_id = Integer.parseInt(supplierAgreementDataDTOs.get(0).supplier_id);
        this.supplying_method = convertStringToEnum(supplying_method);
        this.products = new ArrayList<>();
        this.products_id = new ArrayList<>();
        makeListOfProducts(supplierAgreementDataDTOs);
    }
    public double getPrice(HashMap<String,Integer> products) {
        double price = 0;
        for (Product_Agreement_Data product : this.products) {
            if (products.containsKey(product.get_original_CatalogNumber())) {
                int quantity = products.get(product.get_original_CatalogNumber());
                price += product.getPrice() * quantity;
                if (quantity >= product.getQuantityForDiscount()) {
                    price -= product.getPrice() * quantity * product.getDiscountPrecentage();
                }
            }
        }
        return price;
    }
    public String BilOfQuantities(){
        String s="";
        s+="**************************************************************************************\n";
        s+="Supplier ID: "+supplier_id+"\n";
        s+="Supplying Method: "+supplying_method+"\n";
        for (Product_Agreement_Data product : products) {
            s+=product.ToString()+"\n";
        }
        s+="**************************************************************************************\n\n";
        return s;
    }
    private Supplying_Method convertStringToEnum(String supplying_method) {
        try {
            return Supplying_Method.valueOf(supplying_method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Invalid Supplying Method");
        }
    }
    public boolean isProductExist(String product_id){
        for (String product : products_id) {
            if (product.equals(product_id)) {
                return true;
            }
        }
        return false;
    }
    public void makeListOfProducts(ArrayList<SupplierAgreementDataDTO> supplierAgreementDataDTOs){
        for (SupplierAgreementDataDTO supplierAgreementDataDTO : supplierAgreementDataDTOs) {
            ArrayList<String> product = new ArrayList<>();
            product.add(supplierAgreementDataDTO.catalog_number);
            product.add(supplierAgreementDataDTO.price);
            product.add(supplierAgreementDataDTO.quantity_for_discount);
            product.add(supplierAgreementDataDTO.discount);
            products.add(new Product_Agreement_Data(product, supplierAgreementDataDTO.supplier_id));
            products_id.add(supplierAgreementDataDTO.catalog_number);
        }
    }
    public double getProductPrice(String product_id, int quantity){
        for (Product_Agreement_Data product : products) {
            if (product.get_original_CatalogNumber().equals(product_id)) {
                return product.getPrice(quantity);
            }
        }
        return -1;
    }
}
