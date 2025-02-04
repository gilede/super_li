package DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierProductCategoryDTO extends DTO {
    public static final String SupplierIdColumnName = "supplier_id";
    public static final String ProductCategoryColumnName = "product_category";
    public static final String tableName = "supplier_product_category";

    public String supplier_id;
    public String product_category;

    public SupplierProductCategoryDTO(String supplier_id, String product_category) {
        super(SupplierControllerDTO.getInstance());
        this.supplier_id = supplier_id;
        this.product_category = product_category;
    }
    public static DTO convertReaderToObjectPC(ResultSet resultSet){
        try {
            return new SupplierProductCategoryDTO(
                    resultSet.getString(SupplierProductCategoryDTO.SupplierIdColumnName),
                    resultSet.getString(SupplierProductCategoryDTO.ProductCategoryColumnName)
            );
        }
        catch (SQLException e) {
            throw new RuntimeException("Error converting ResultSet to SupplierAgreementDataDTO: " + e.getMessage());
        }
    }
    public void Insert() {
        try{
            ((SupplierControllerDTO)_controller).Insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting SupplierProductCategoryDTO: " + e.getMessage());
        }
    }
}
