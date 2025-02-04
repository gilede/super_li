package DataAccessLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderDTO extends DTO {
    public static final String OrderIdColumnName = "order_id";
    public static final String SupplierIdColumnName = "supplier_id";
    public static final String ShipmentDayColumnName = "shipment_day";
    public static final String SupplyingMethodColumnName = "supplying_method";
    public static final String OpeningDateColumnName = "opening_date";
    public static final String ShipmentDateColumnName = "shipment_date";
    public static final String OrderStatusColumnName = "order_status";
    public static final String TotalPriceColumnName = "total_price";
    public static final String tableName = "orders";

    public String order_id;
    public String supplier_id ;
    public String shipment_day;
    public String supplying_method;
    public LocalDate opening_date;
    public LocalDate shipment_date;
    public String order_status;
    public double total_price;

    public OrderDTO(String order_id, String supplier_id, String shipment_day, String supplying_method,
                    LocalDate opening_date, LocalDate shipment_date, String order_status, double total_price) {
        super(OrderControllerDTO.getInstance());
        this.order_id = order_id;
        this.supplier_id = supplier_id;
        this.shipment_day = shipment_day;
        this.supplying_method = supplying_method;
        this.opening_date = opening_date;
        this.shipment_date = shipment_date;
        this.order_status = order_status;
        this.total_price = total_price;
    }
    public void Insert() {
        try{
            ((OrderControllerDTO)_controller).Insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting SupplierDaysDTO: " + e.getMessage());
        }
    }
    public void UpdateTotalPrice(double total_price) {
        this.total_price = total_price;
        try{
            ((OrderControllerDTO)_controller).Update(this);
        } catch (Exception e) {
            throw new RuntimeException("Error updating SupplierDaysDTO: " + e.getMessage());
        }
    }
    public void UpdateOrderStatus(String order_status) {
        this.order_status = order_status;
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