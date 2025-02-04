package DomainLayer;
import DataAccessLayer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order_Controller {
    private static Order_Controller instance;
    private HashMap<Integer, Order> orders;
    private OrderControllerDTO orderControllerDTO ;
    public Order_Controller() {
        this.orders = new HashMap<Integer, Order>();
        this.orderControllerDTO = OrderControllerDTO.getInstance();
    }
    public static Order_Controller getInstance() {
        if (instance == null) {
            instance = new Order_Controller();
        }
        return instance;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Order addOrder(String shipmentDate, String supplierID, HashMap<String, Integer> products_in_order, Supplying_Method supplying_method) {
        Order newOrder = new Order(convertStringToEnum(shipmentDate), supplierID, products_in_order, supplying_method);
        orders.put(newOrder.getOrderId(), newOrder);
        return  newOrder;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Order ExistedOrder (Integer orderID) {
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            // Scanning in HashMap By orderID Key
            if (entry.getKey().equals(orderID)) {
                return entry.getValue();
            }
        }
        // If the order is not found, throw an AssertionError
        return null;
    }
    public boolean ExistedOrderChecking(Integer orderID){
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            // Scanning in HashMap By orderID Key
            if (entry.getKey().equals(orderID)) {
                return true;
            }
        }
        throw new AssertionError("There is no order in the system that belongs to the order number - " + orderID);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Check if it is fixed day supplier and at least one order has arrived
    public boolean AtLeastOneOrderArrivedChecking(Integer orderID){
        return ExistedOrder(orderID).AtLeastOneOrderArrivedChecking();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String EditOrder(Integer orderID,String product_Number, Integer quantity){
        if(ExistedOrder(orderID).checkCancel())
            throw new AssertionError("*************************************************************\nThis order has already been cancelled. It cannot be edited.\n" +
                    "*************************************************************\n\n");
        return ExistedOrder(orderID).EditOrder(product_Number,quantity);
    }
    public String Remove_product_from_order(Integer orderID, String product_Number){
        if(ExistedOrder(orderID).get_products_in_order_size() == 1)

            throw new AssertionError("****************************************************************************************\nThis order has only one item, it is not possible to leave an active order without items.\nInstead, you can try to cancel the order.\n" +
                    "****************************************************************************************\n\n");
        if(ExistedOrder(orderID).checkCancel())
            throw new AssertionError("****************************************************************************************\nThis order has already been cancelled. It cannot be edited." +
                    "\n****************************************************************************************\n\n");
        return ExistedOrder(orderID).Remove_product_from_order(product_Number);
    }

    public String removeOrder(Integer orderID,  Supplier supplier){
        Order order = ExistedOrder(orderID);
        if ( order.getOrderStatus().equals(Order_Status.ARRIVED) ||order.getOrderStatus().equals(Order_Status.DELIVERED)){

           return  "***************************************************************************************************\n" +
                   "This order has already arrived or already delivered. It is not possible to cancel it at this stage" +
                   "\n***************************************************************************************************\n\n";
        }
        order.CancelOrder(Order_Status.CANCELLED);
        if (!AtLeastOneOrderArrivedChecking(orderID)) {
            // if By Order - We don't need to save this order data at all
            //if Fixed day But not even one order was delivered - We don't need to save this order data at all

            supplier.removeOrder(orderID);
            orders.remove(orderID);
            order.delete();
        }
        else {
            // the supplier is fixed day And at least one order has already been delivered
            // we want to save the order on Supplier's order history, but not in orders
            orders.remove(orderID);
        }
        return "************************************\nThe order was successfully removed\n" +
                "************************************\n\n";
    }

    public Order FindOrderByOrderID(String orderID){
        Integer orderIDAsInteger = Integer.valueOf(orderID);
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            if (entry.getKey().equals(orderIDAsInteger)) {
                return  entry.getValue();
            }
        }
        return null;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Day convertStringToEnum(String shipmentDate) {
        try {
            return Day.valueOf(shipmentDate.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Or handle the exception as needed
        }
    }
    public Order DeepCopyOrder(Integer orderID){
        return ExistedOrder(orderID).DeepCopyOrder();
    }
    public void setCancel(Order coppyOrder){
        ExistedOrder(coppyOrder.getOrderId()).setCancel();
        orders.remove(coppyOrder.getOrderId());
    }
    public HashMap<Integer, Order> loadOrders(){
        try {
            ArrayList<OrderDTO> OrderDTOs = orderControllerDTO.selectAllOrders();
            for (OrderDTO orderDTO : OrderDTOs) {
                ArrayList<OrderProductDTO> OrderProudctDTOs = orderControllerDTO.selectOrderProducts(orderDTO.order_id);
                Order newOrder = new Order(orderDTO, OrderProudctDTOs);
                orders.put(newOrder.getOrderId(), newOrder);
            }
            return orders;

        } catch (Exception ex) {
            throw new AssertionError("Error loading orders from the database: " + ex.getMessage());
        }
    }
    public void ExitOrders(){
        orderControllerDTO.closeConnection();
    }
}


