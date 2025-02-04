package DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDaysDTO extends DTO {
    public static final String SupplierIdColumnName = "supplier_id";
    public static final String DayColumnName = "day";
    public static final String tableName = "suppliersDays";

    public String supplier_id;
    public String day;

    public SupplierDaysDTO(String supplier_id, String day) {
        super(SupplierControllerDTO.getInstance());
        this.supplier_id = supplier_id;
        this.day = day;
    }
    public static DTO convertReaderToObjectDays(ResultSet resultSet){
        try {
            return new SupplierDaysDTO(
                    resultSet.getString(SupplierDaysDTO.SupplierIdColumnName),
                    resultSet.getString(SupplierDaysDTO.DayColumnName)
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
            throw new RuntimeException("Error inserting SupplierDaysDTO: " + e.getMessage());
        }
    }
}
