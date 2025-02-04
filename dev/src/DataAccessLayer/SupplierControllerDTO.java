package DataAccessLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SupplierControllerDTO extends DbController {
    private static SupplierControllerDTO instance;

    public SupplierControllerDTO() {
        super("suppliers");
    }

    public static SupplierControllerDTO getInstance() {
        if (instance == null) {
            instance = new SupplierControllerDTO() {
            };
        }
        return instance;
    }

    // Methods for handling Supplier-specific database operations
    @Override
    protected DTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        // Parse Days from a single string to ArrayList<String>

        return new SupplierDTO(
                resultSet.getString(SupplierDTO.SupplierNameColumnName),
                resultSet.getString(SupplierDTO.PrivateCompanyIdColumnName),
                resultSet.getString(SupplierDTO.ContactNameColumnName),
                resultSet.getString(SupplierDTO.ContactPhoneColumnName),
                resultSet.getString(SupplierDTO.BankAccountColumnName),
                resultSet.getString(SupplierDTO.PaymentMethodColumnName),
                resultSet.getString(SupplierDTO.DeliveringMethodColumnName),
                resultSet.getString(SupplierDTO.TypeColumnName)
        );
    }

    public ArrayList<SupplierDTO> selectAllSuppliers() {
        ArrayList<SupplierDTO> suppliers = new ArrayList<>();
        ArrayList<DTO> dtos = selectAll();
        for (DTO dto : dtos) {
            if (dto instanceof SupplierDTO) {
                suppliers.add((SupplierDTO) dto);
            }
        }

        return suppliers;
    }
    public ArrayList<SupplierAgreementDataDTO> selectSupplierAgreementData(String supplierId) {
            ArrayList<SupplierAgreementDataDTO> Sup_agr_data = new ArrayList<>();
            ArrayList<DTO> dtos = select_by_id(supplierId, SupplierAgreementDataDTO.tableName, SupplierAgreementDataDTO.SupplierIdColumnName, SupplierAgreementDataDTO::convertReaderToObjectAgreement);
            for (DTO dto : dtos) {
                if (dto instanceof SupplierAgreementDataDTO) {
                    Sup_agr_data.add((SupplierAgreementDataDTO) dto);
                }
            }
            return Sup_agr_data;
        }
    public ArrayList<SupplierDaysDTO> selectSupplierDays(String supplierId) {
        ArrayList<SupplierDaysDTO> Sup_days = new ArrayList<>();
        ArrayList<DTO> dtos = select_by_id(supplierId, SupplierDaysDTO.tableName, SupplierDaysDTO.SupplierIdColumnName, SupplierDaysDTO::convertReaderToObjectDays);
        for (DTO dto : dtos) {
            if (dto instanceof SupplierDaysDTO) {
                Sup_days.add((SupplierDaysDTO) dto);
            }
        }
        return Sup_days;
    }
    public ArrayList<SupplierProductCategoryDTO> selectSupplierProductCategory(String supplierId) {
        ArrayList<SupplierProductCategoryDTO> Sup_prod_cat = new ArrayList<>();
        ArrayList<DTO> dtos = select_by_id(supplierId, SupplierProductCategoryDTO.tableName, SupplierProductCategoryDTO.SupplierIdColumnName, SupplierProductCategoryDTO::convertReaderToObjectPC);
        for (DTO dto : dtos) {
            if (dto instanceof SupplierProductCategoryDTO) {
                Sup_prod_cat.add((SupplierProductCategoryDTO) dto);
            }
        }
        return Sup_prod_cat;
    }
    public void Insert(SupplierDTO supplier) {
        // SQL INSERT statement
        String sql = "INSERT INTO suppliers (" +
                SupplierDTO.SupplierNameColumnName + ", " +
                SupplierDTO.PrivateCompanyIdColumnName + ", " +
                SupplierDTO.ContactNameColumnName + ", " +
                SupplierDTO.ContactPhoneColumnName + ", " +
                SupplierDTO.BankAccountColumnName + ", " +
                SupplierDTO.PaymentMethodColumnName + ", " +
                SupplierDTO.DeliveringMethodColumnName + ", " +
                SupplierDTO.TypeColumnName +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Establish database connection
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, supplier.name);
            pstmt.setString(2, supplier.private_company_id);
            pstmt.setString(3, supplier.contact_name);
            pstmt.setString(4, supplier.contact_phone);
            pstmt.setString(5, supplier.bank_account);
            pstmt.setString(6, supplier.payment_method);
            pstmt.setString(7, supplier.delivering_Method);
            pstmt.setString(8, supplier.supplying_method);

            // Execute the insert statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting supplier: " + e.getMessage());
        }
    }
    // Method to insert SupplierAgreementDataDTO into the database
    public void Insert(SupplierAgreementDataDTO agreementData) {
        // SQL INSERT statement
        String sql = "INSERT INTO " + SupplierAgreementDataDTO.tableName + " (" +
                SupplierAgreementDataDTO.SupplierIdColumnName + ", " +
                SupplierAgreementDataDTO.CatalogNumberColumnName + ", " +
                SupplierAgreementDataDTO.PriceColumnName + ", " +
                SupplierAgreementDataDTO.QuantityForDiscountColumnName + ", " +
                SupplierAgreementDataDTO.DiscountColumnName +
                ") VALUES (?, ?, ?, ?, ?)";

        // Establish database connection
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, agreementData.supplier_id);
            pstmt.setString(2, agreementData.catalog_number);
            pstmt.setString(3, agreementData.price);
            pstmt.setString(4, agreementData.quantity_for_discount);
            pstmt.setString(5, agreementData.discount);

            // Execute the insert statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting supplier agreement data: " + e.getMessage());
        }
    }
    // Method to insert SupplierProductCategoryDTO into the database
    public void Insert(SupplierProductCategoryDTO productCategory) {
        // SQL INSERT statement
        String sql = "INSERT INTO " + SupplierProductCategoryDTO.tableName + " (" +
                SupplierProductCategoryDTO.SupplierIdColumnName + ", " +
                SupplierProductCategoryDTO.ProductCategoryColumnName +
                ") VALUES (?, ?)";

        // Establish database connection
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, productCategory.supplier_id);
            pstmt.setString(2, productCategory.product_category);

            // Execute the insert statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting supplier product category: " + e.getMessage());
        }
    }
    // Method to insert SupplierDaysDTO into the database
    public void Insert(SupplierDaysDTO supplierDays) {
        // SQL INSERT statement
        String sql = "INSERT INTO " + SupplierDaysDTO.tableName + " (" +
                SupplierDaysDTO.SupplierIdColumnName + ", " +
                SupplierDaysDTO.DayColumnName +
                ") VALUES (?, ?)";

        // Establish database connection
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, supplierDays.supplier_id);
            pstmt.setString(2, supplierDays.day);

            // Execute the insert statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting supplier days: " + e.getMessage());
        }
    }
    public void Update(SupplierDTO supplier){
        String sql = "UPDATE suppliers SET " +
                SupplierDTO.SupplierNameColumnName + " = ?, " +
                SupplierDTO.PrivateCompanyIdColumnName + " = ?, " +
                SupplierDTO.ContactNameColumnName + " = ?, " +
                SupplierDTO.ContactPhoneColumnName + " = ?, " +
                SupplierDTO.BankAccountColumnName + " = ?, " +
                SupplierDTO.PaymentMethodColumnName + " = ?, " +
                SupplierDTO.DeliveringMethodColumnName + " = ?, " +
                SupplierDTO.TypeColumnName + " = ? " +
                "WHERE " + SupplierDTO.SupplierNameColumnName + " = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, supplier.name);
            pstmt.setString(2, supplier.private_company_id);
            pstmt.setString(3, supplier.contact_name);
            pstmt.setString(4, supplier.contact_phone);
            pstmt.setString(5, supplier.bank_account);
            pstmt.setString(6, supplier.payment_method);
            pstmt.setString(7, supplier.delivering_Method);
            pstmt.setString(8, supplier.supplying_method);
            pstmt.setString(9, supplier.name);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating supplier: " + e.getMessage());
        }
    }
}
