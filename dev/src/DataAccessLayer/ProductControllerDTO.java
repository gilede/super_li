package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductControllerDTO extends DbController {
    private static ProductControllerDTO instance;
    public ProductControllerDTO()
    {
        super("products");
    }

    public static ProductControllerDTO getInstance() {
        if (instance == null) {
            instance = new ProductControllerDTO() {};
        }
        return instance;
    }
    // Methods for handling Product-specific database operations
    @Override
    protected DTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        return new ProductDTO(
                resultSet.getString(ProductDTO.ProductIdColumnName),
                resultSet.getString(ProductDTO.ProductNameColumnName),
                resultSet.getString(ProductDTO.CompanyNameColumnName),
                resultSet.getString(ProductDTO.ProductCategoryColumnName)
        );
    }

    public ArrayList<ProductDTO> selectAllProducts() {
        ArrayList<ProductDTO> products = new ArrayList<>();
        ArrayList<DTO> dtos = selectAll();
        for (DTO dto : dtos) {
            if (dto instanceof ProductDTO) {
                products.add((ProductDTO) dto);
            }
        }

        return products;
    }
    public void Insert(ProductDTO product) {
        // SQL INSERT statement
        String sql = "INSERT INTO " + tableName + " (" +
                ProductDTO.ProductIdColumnName + ", " +
                ProductDTO.ProductNameColumnName + ", " +
                ProductDTO.CompanyNameColumnName + ", " +
                ProductDTO.ProductCategoryColumnName +
                ") VALUES (?, ?, ?, ?)";

        // Establish database connection
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, product.productId);
            pstmt.setString(2, product.product_name);
            pstmt.setString(3, product.company_name);
            pstmt.setString(4, product.productCategory);

            // Execute the insert statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting product: " + e.getMessage());
        }
    }
    public void Delete(ProductDTO product) {
        // SQL DELETE statement
        String sql = "DELETE FROM " + tableName + " WHERE " + ProductDTO.ProductIdColumnName + " = ?";

        // Establish database connection
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, product.productId);

            // Execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product: " + e.getMessage());
        }
    }
    public void deleteAllProducts() {
        deleteAll("Products"); // Assume table name is "Products"
    }



}
