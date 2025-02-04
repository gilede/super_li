package DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderProductDTO extends DTO {
    public static final String OrderIdColumnName = "order_id";
    public static final String ProductIdColumnName = "product_id";
    public static final String QuantityColumnName = "quantity";

    public static final String tableName = "order_products";

    public String order_id;
    public String product_id;
    public Integer quantity;

    public OrderProductDTO(String order_id, String product_id, Integer quantity) {
        super(OrderControllerDTO.getInstance());
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }
    public static DTO convertReaderToObjectOrderProduct(ResultSet resultSet){
        try {
            return new OrderProductDTO(
                    resultSet.getString(OrderProductDTO.OrderIdColumnName),
                    resultSet.getString(OrderProductDTO.ProductIdColumnName),
                    resultSet.getInt(OrderProductDTO.QuantityColumnName)
            );
        }
        catch (SQLException e) {
            throw new RuntimeException("Error converting ResultSet to OrderProductDTO: " + e.getMessage());
        }
    }
    public void Insert() {
        try{
            ((OrderControllerDTO)_controller).Insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting SupplierDaysDTO: " + e.getMessage());
        }
    }
    public void Update(){
        try{
            ((OrderControllerDTO)_controller).Update(this);
        } catch (Exception e) {
            throw new RuntimeException("Error updating SupplierDaysDTO: " + e.getMessage());
        }
    }
    public void Delete(){
        try{
            ((OrderControllerDTO)_controller).Delete(this);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting SupplierDaysDTO: " + e.getMessage());
        }
    }
}
