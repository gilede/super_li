package DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierAgreementDataDTO extends DTO {
    public static final String SupplierIdColumnName = "supplier_id";
    public static final String CatalogNumberColumnName = "catalog_number";
    public static final String PriceColumnName = "price";
    public static final String QuantityForDiscountColumnName = "quantity_for_discount";
    public static final String DiscountColumnName = "discount";
    public static final String tableName = "supplier_agreement_data";

    public String supplier_id;
    public String catalog_number;
    public String price;
    public String quantity_for_discount;
    public String discount;

    public SupplierAgreementDataDTO(String supplier_id, String catalog_number, String price, String quantity_for_discount, String discount) {
        super(SupplierControllerDTO.getInstance());
        this.supplier_id = supplier_id;
        this.catalog_number = catalog_number;
        this.price = price;
        this.quantity_for_discount = quantity_for_discount;
        this.discount = discount;
    }
    public static DTO convertReaderToObjectAgreement(ResultSet resultSet){
        try {
            return new SupplierAgreementDataDTO(
                    resultSet.getString(SupplierAgreementDataDTO.SupplierIdColumnName),
                    resultSet.getString(SupplierAgreementDataDTO.CatalogNumberColumnName),
                    resultSet.getString(SupplierAgreementDataDTO.PriceColumnName),
                    resultSet.getString(SupplierAgreementDataDTO.QuantityForDiscountColumnName),
                    resultSet.getString(SupplierAgreementDataDTO.DiscountColumnName)
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
            throw new RuntimeException("Error inserting SupplierAgreementDataDTO: " + e.getMessage());
        }
    }
}