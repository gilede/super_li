package DataAccessLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderControllerDTO extends DbController {
    private static OrderControllerDTO instance;
    public OrderControllerDTO()
    {
        super("orders");
    }

    public static OrderControllerDTO getInstance() {
        if (instance == null) {
            instance = new OrderControllerDTO() {};
        }
        return instance;
    }
    // Methods for handling Product-specific database operations
    @Override
    protected DTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        return new OrderDTO(
                resultSet.getString(OrderDTO.OrderIdColumnName),
                resultSet.getString(OrderDTO.SupplierIdColumnName),
                resultSet.getString(OrderDTO.ShipmentDayColumnName),
                resultSet.getString(OrderDTO.SupplyingMethodColumnName),
                LocalDate.parse(resultSet.getString(OrderDTO.OpeningDateColumnName)),
                LocalDate.parse(resultSet.getString(OrderDTO.ShipmentDateColumnName)),
                resultSet.getString(OrderDTO.OrderStatusColumnName),
                resultSet.getDouble(OrderDTO.TotalPriceColumnName)
        );
    }

    public ArrayList<OrderDTO> selectAllOrders() {
        ArrayList<OrderDTO> orders = new ArrayList<>();
        ArrayList<DTO> dtos = selectAll();
        for (DTO dto : dtos) {
            if (dto instanceof OrderDTO) {
                orders.add((OrderDTO) dto);
            }
        }
        return orders;
    }
    public ArrayList<OrderProductDTO> selectOrderProducts(String order_id) {
        ArrayList<OrderProductDTO> order_products = new ArrayList<>();
        ArrayList<DTO> dtos = select_by_id(order_id, OrderProductDTO.tableName, OrderProductDTO.OrderIdColumnName, OrderProductDTO::convertReaderToObjectOrderProduct);
        for (DTO dto : dtos) {
            if (dto instanceof OrderProductDTO) {
                order_products.add((OrderProductDTO) dto);
            }
        }
        return order_products;
    }

    public void Insert(OrderDTO order) {
        String sql = "INSERT INTO " + OrderDTO.tableName + " (" +
                OrderDTO.OrderIdColumnName + ", " +
                OrderDTO.SupplierIdColumnName + ", " +
                OrderDTO.ShipmentDayColumnName + ", " +
                OrderDTO.SupplyingMethodColumnName + ", " +
                OrderDTO.OpeningDateColumnName + ", " +
                OrderDTO.ShipmentDateColumnName + ", " +
                OrderDTO.OrderStatusColumnName + ", " +
                OrderDTO.TotalPriceColumnName +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, order.order_id);
            pstmt.setString(2, order.supplier_id);
            pstmt.setString(3, order.shipment_day);
            pstmt.setString(4, order.supplying_method);
            pstmt.setString(5, order.opening_date.toString());
            pstmt.setString(6, order.shipment_date.toString());
            pstmt.setString(7, order.order_status);
            pstmt.setDouble(8, order.total_price);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting order: " + e.getMessage());
        }
    }
    public void Insert(OrderProductDTO orderProduct) {
        String sql = "INSERT INTO " + OrderProductDTO.tableName + " (" +
                OrderProductDTO.OrderIdColumnName + ", " +
                OrderProductDTO.ProductIdColumnName + ", " +
                OrderProductDTO.QuantityColumnName +
                ") VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orderProduct.order_id);
            pstmt.setString(2, orderProduct.product_id);
            pstmt.setInt(3, orderProduct.quantity);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting order product: " + e.getMessage());
        }
    }
    public void Update(OrderDTO order) {
        String sql = "UPDATE " + OrderDTO.tableName + " SET " +
                OrderDTO.SupplierIdColumnName + " = ?, " +
                OrderDTO.ShipmentDayColumnName + " = ?, " +
                OrderDTO.SupplyingMethodColumnName + " = ?, " +
                OrderDTO.OpeningDateColumnName + " = ?, " +
                OrderDTO.ShipmentDateColumnName + " = ?, " +
                OrderDTO.OrderStatusColumnName + " = ?, " +
                OrderDTO.TotalPriceColumnName + " = ? " +
                "WHERE " + OrderDTO.OrderIdColumnName + " = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, order.supplier_id);
            pstmt.setString(2, order.shipment_day);
            pstmt.setString(3, order.supplying_method);
            pstmt.setString(4, order.opening_date.toString());
            pstmt.setString(5, order.shipment_date.toString());
            pstmt.setString(6, order.order_status);
            pstmt.setDouble(7, order.total_price);
            pstmt.setString(8, order.order_id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order: " + e.getMessage());
        }
    }
    public void Update(OrderProductDTO orderProduct) {
        String sql = "UPDATE " + OrderProductDTO.tableName + " SET " +
                OrderProductDTO.QuantityColumnName + " = ? " +
                "WHERE " + OrderProductDTO.OrderIdColumnName + " = ? AND " +
                OrderProductDTO.ProductIdColumnName + " = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orderProduct.quantity);
            pstmt.setString(2, orderProduct.order_id);
            pstmt.setString(3, orderProduct.product_id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order product: " + e.getMessage());
        }
    }
    public void Delete(OrderDTO order) {
        String sql = "DELETE FROM " + OrderDTO.tableName + " WHERE " +
                OrderDTO.OrderIdColumnName + " = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, order.order_id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order: " + e.getMessage());
        }
    }
    public void Delete(OrderProductDTO orderProduct) {
        String sql = "DELETE FROM " + OrderProductDTO.tableName + " WHERE " +
                OrderProductDTO.OrderIdColumnName + " = ? AND " +
                OrderProductDTO.ProductIdColumnName + " = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orderProduct.order_id);
            pstmt.setString(2, orderProduct.product_id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order product: " + e.getMessage());
        }
    }
}
