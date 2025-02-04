package DomainLayer;

import DataAccessLayer.ProductDTO;

public class Product {
    private String product_id;
    private String product_name;
    private String company_name;
    private Product_Category productCategory;
    private ProductDTO productDTO;

    public Product (String ProductID, String product_Name, String company_name, String product_Category){
        if(!validcategory(product_Category));
        this.product_id=ProductID;
        this.product_name=product_Name;
        this.company_name=company_name;
        this.productCategory = convertStringToEnum(product_Category);
        ProductDTO produtDTO = new ProductDTO(ProductID, product_Name, company_name, product_Category);
        this.productDTO = produtDTO;
        productDTO.Insert();
    }
    public Product(ProductDTO productDTO){
        this.product_id=productDTO.productId;
        this.product_name=productDTO.product_name;
        this.company_name=productDTO.company_name;
        this.productCategory = convertStringToEnum(productDTO.productCategory);
        this.productDTO = productDTO;
    }
    public String getId() {
        return product_id;
    }

    public String ToString(){
        return "Product ID: "+product_id+"\nProduct Name: "+product_name+"\nCompany Name: "+company_name+"\nProduct Category: "+productCategory+"\n***********************************************************";
    }
    public String getCategory() {
        return productCategory.toString();
    }

    public String getCompanyName() {
        return company_name;
    }

    public String getProduct_name(){
        return product_name;
    }
    private Product_Category convertStringToEnum(String product_Category) {
        try {
            return Product_Category.valueOf(product_Category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Or handle the exception as needed
        }
    }

    private boolean validcategory(String product_Category) {
        for (Product_Category category : Product_Category.values()) {
            if (category.name().equals(product_Category.toUpperCase())) {
                return true;
            }
        }
        throw new AssertionError("The product category is not valid");
    }
    public ProductDTO getProductDTO() {
        return productDTO;
    }

}
