package DataAccessLayer;

public class ProductDTO extends DTO {
    public static final String ProductIdColumnName = "product_id";
    public static final String ProductNameColumnName = "product_name";
    public static final String CompanyNameColumnName = "company_name";
    public static final String ProductCategoryColumnName = "product_category";

    public String productId;
    public String product_name;
    public String company_name;
    public String productCategory;

    public ProductDTO(String productId, String product_name, String company_name, String productCategory) {
        super(ProductControllerDTO.getInstance());
        this.productId = productId;
        this.product_name = product_name;
        this.company_name = company_name;
        this.productCategory = productCategory;
    }
    public void Insert() {
        try{
            ((ProductControllerDTO)_controller).Insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting SupplierDaysDTO: " + e.getMessage());
        }
    }
    public void Delete(){
        try{
            ((ProductControllerDTO)_controller).Delete(this);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting SupplierDaysDTO: " + e.getMessage());
        }
    }

}

