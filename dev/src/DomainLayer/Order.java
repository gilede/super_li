package DomainLayer;


import DataAccessLayer.OrderDTO;
import DataAccessLayer.OrderProductDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class Order {

    /* Such use of static creates a logical order_ID sequence while providing a unique value for each instance   */
    private static AtomicInteger id_Counter = new AtomicInteger(1);
    /* Using the final attribute so that we cannot change this value after it is created*/
    private final int order_ID;
    private LocalDate openning_date;
    /* Rule number 1:  The shipping day is the next day that has the value shipmentDate ,
     *   which has at least a week open_date difference */
    private LocalDate shipment_date;
    private Order_Status order_status;
    private final String ID_of_supplier_in_charge;
    // String  for ProductNumber
    private HashMap<String, Integer> products_in_order;
    private double total_price;
    private Supplying_Method supplying_method;
    private int FixedDayIterationCheck;
    private Day dayshipmentday;
    private OrderDTO orderDTO;
    private ArrayList<OrderProductDTO> orderProudctDTOs;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    public Order(Day shipmentDateDay, String supplierID, HashMap<String, Integer> products_in_order, Supplying_Method supplying_method) {
        this.order_ID = id_Counter.getAndIncrement(); // Generate unique ID
        this.openning_date = LocalDate.now(); // Set opening date to the current date
        this.order_status = Order_Status.RECEIVED;
        this.ID_of_supplier_in_charge = supplierID;
        this.products_in_order = products_in_order;
        this.dayshipmentday = shipmentDateDay;
        this.supplying_method = supplying_method;
        this.shipment_date = calculation_for_ShipmentDay(shipmentDateDay);
        this.total_price = 0;
        this.FixedDayIterationCheck =0;
        OrderDTO orderDto = new OrderDTO(String.valueOf(order_ID),supplierID,shipmentDateDay.toString(),supplying_method.toString(),openning_date,shipment_date,order_status.toString(),total_price);
        orderDto.Insert();
        this.orderDTO = orderDto;
        this.orderProudctDTOs = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : products_in_order.entrySet()) {
            OrderProductDTO orderProudctDTO = new OrderProductDTO(String.valueOf(order_ID),entry.getKey(),entry.getValue());
            orderProudctDTO.Insert();
            orderProudctDTOs.add(orderProudctDTO);
        }
    }
    public Order DeepCopyOrder(){
        this.FixedDayIterationCheck =0;
        return new Order(dayshipmentday, ID_of_supplier_in_charge, products_in_order,supplying_method);
    }

    //DTO Constructor
    public Order(OrderDTO orderDTO,ArrayList<OrderProductDTO> orderProudctDTOS){
        this.order_ID = Integer.parseInt(orderDTO.order_id);
        id_Counter.getAndIncrement();
        this.openning_date = orderDTO.opening_date;
        this.order_status = Order_Status.valueOf(orderDTO.order_status);
        this.ID_of_supplier_in_charge = orderDTO.supplier_id;
        this.products_in_order = makeProductsInOrder(orderProudctDTOS);
        this.dayshipmentday = Day.valueOf(orderDTO.shipment_day);
        this.shipment_date = orderDTO.shipment_date;
        this.total_price = orderDTO.total_price;
        this.supplying_method = Supplying_Method.valueOf(orderDTO.supplying_method);
        this.FixedDayIterationCheck =0;
        this.orderDTO = orderDTO;
        this.orderProudctDTOs = orderProudctDTOS;
        this.order_status = getOrderStatus();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public LocalDate calculation_for_ShipmentDay(Day shipmentDateDay){
        LocalDate temp_shipment_date;
        /* Temporally Variable for ShipmentDate By the Rule number 1 :
         *   Rule number 1:  The shipping day is the next day that has the value shipmentDate ,
         *   which has at least a week open_date difference */
        if (this.supplying_method == Supplying_Method.FIXED_DAYS){
            temp_shipment_date = LocalDate.now().plusWeeks(3);
        }
        else {
            temp_shipment_date = LocalDate.now().plusWeeks(1);
        }
        // transfer Day enum Value to DayOfWeek for Specific function from DayOfWeek lib.
        DayOfWeek shipmentDayOfWeek = convertDayToDayOfWeek(shipmentDateDay);

        // Check if temp_shipment_date matches shipmentDate
        while (!temp_shipment_date.getDayOfWeek().equals(shipmentDayOfWeek)) {
            // Day Values are not Matched. check the followed day
            temp_shipment_date = temp_shipment_date.plusDays(1);
        }
        // the ShipmentDate Has Been Founded
        return temp_shipment_date;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* A helper method that converts the day enum value to a DayOfWeek value for further calculations */
    private DayOfWeek convertDayToDayOfWeek(Day day) {
        switch (day) {
            case SUNDAY:
                return DayOfWeek.SUNDAY;
            case MONDAY:
                return DayOfWeek.MONDAY;
            case TUESDAY:
                return DayOfWeek.TUESDAY;
            case WEDNESDAY:
                return DayOfWeek.WEDNESDAY;
            case THURSDAY:
                return DayOfWeek.THURSDAY;
            case FRIDAY:
                return DayOfWeek.FRIDAY;
            case SATURDAY:
                return DayOfWeek.SATURDAY;
            default:
                throw new IllegalArgumentException("Unknown day: " + day);
        }
    }

    public int GetCurrentQuantityOfProduct(String product_id){
        for (Map.Entry<String, Integer> entry : products_in_order.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            if (product.equals(product_id)) {
            return quantity;
            }
        }
        return 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters for the attributes
    public Integer getOrderId() {
        return order_ID;
    }
    public LocalDate getShipmentDate() {return shipment_date;}
    public Day getShipmentDay(){
        return dayshipmentday;
    }

    /*
            Updating and Get the Order Status By the followed Rules:
            Rule 1: Update status if more than 24 hours have passed since the order was opened
            Rule 2: Update status if the current day is the shipment day
            Rule 3: Update status if it's 17:00 and the current day is the shipment day

    */
    public Order_Status getOrderStatus() {
    /*
    demo for suppliers behavior
    Updating and Get the Order Status By the followed Rules:
    Rule 1: Update status to Been_Processed if at least One day passed since the order was opened
    Rule 2: Update status to Delivered if the current day is the shipment day
    Rule 3: Update status to Arrived if it's 17:00 and the current day is the shipment day
    */
    if(order_status.equals(Order_Status.CANCELLED)){
        return order_status;
    }
    while (this.supplying_method == Supplying_Method.FIXED_DAYS) {
        // advance the shipment date by weeks until it is after the current date
        if (LocalDate.now().isAfter(shipment_date)) {
            //At least one order has been placed.
            //Now, we would like to save the order history in case the order is changed / canceled.
            this.FixedDayIterationCheck =1;
            openning_date = shipment_date;
            Day tempShipDay = convertLocalDateToDay(openning_date);
            shipment_date=calculation_for_ShipmentDay(tempShipDay);
            order_status = Order_Status.RECEIVED;
            orderDTO.UpdateOrderStatus(order_status.toString());
        }
        else {
            break;
        }
    }
        /* Rule 1: If the difference between the current time and the time when we opened the order (openning_date) is more than 24 hours,
         order_status must be set to be equal to Been_Processed */
            if (ChronoUnit.HOURS.between(openning_date.atStartOfDay(), LocalDate.now().atStartOfDay()) > 24) {
                order_status = Order_Status.BEEN_PROCESSED;
                orderDTO.UpdateOrderStatus(order_status.toString());
            }

            /* Rule 2: If the current day is the day the order should arrive, change the value of order_status to Delivered */
            if (LocalDate.now().equals(shipment_date)) {
                order_status = Order_Status.DELIVERED;
                orderDTO.UpdateOrderStatus(order_status.toString());
            }

        /* Rule 3: If the time is 17:00 or later and the current day is the day when the order should arrive,
         change the value of order_status to Arrived */
            if (LocalDate.now().equals(shipment_date) && LocalTime.now().isAfter(LocalTime.of(16, 59))) {
                order_status = Order_Status.ARRIVED;
                orderDTO.UpdateOrderStatus(order_status.toString());
            }


            return order_status;
        }
    public boolean AtLeastOneOrderArrivedChecking(){
        getOrderStatus();
        if (this.FixedDayIterationCheck == 1){
            return true;
        }
        return false;
    }
    public Supplying_Method getSupplyingMethod(){
        return supplying_method;
    }
    public String EditOrder(String product_Number, Integer quantity) {
        if(this.order_status != Order_Status.DELIVERED && this.order_status != Order_Status.ARRIVED) {
            for (Map.Entry<String, Integer> entry : products_in_order.entrySet()) {
                // Scanning in HashMap By productNUmber Key
                if (entry.getKey().equals(product_Number)) {
                    // product_Number was founded !
                    // set his new quantity
                    entry.setValue(quantity);
                    break;
                }
                // there is no product_Number like this in this order
                // lets add him
            }
            //if the product_Number is not in the order
            if (!products_in_order.containsKey(product_Number)) {
                products_in_order.put(product_Number, quantity);
                OrderProductDTO orderProudctDTO = new OrderProductDTO(String.valueOf(order_ID),product_Number,quantity);
                orderProudctDTO.Insert();
                orderProudctDTOs.add(orderProudctDTO);
            }
            //if the product_Number is in the order
            else {
                orderProudctDTOs.forEach(orderProudctDTO -> {
                    if (orderProudctDTO.product_id.equals(product_Number) && orderProudctDTO.order_id.equals(String.valueOf(order_ID))) {
                        orderProudctDTO.quantity = quantity;
                        orderProudctDTO.Update();
                    }
                });
            }
            return "*******************************************\nThis order has been updated successfully!\n" +
                    "*******************************************\n\n";
        }
        else {
            return "**************************************************************************************************\nThis order has already arrived or already delivered." +
                    " It is not possible to edit it at this stage\n**************************************************************************************************\n\n";
        }
    }
    public String Remove_product_from_order(String product_Number) {
        if(this.order_status != Order_Status.DELIVERED && this.order_status != Order_Status.ARRIVED) {
            for (Map.Entry<String, Integer> entry : products_in_order.entrySet()) {
                // Scanning in HashMap By productNUmber Key
                if (entry.getKey().equals(product_Number)) {
                    // product_Number was founded !
                    // remove him
                    products_in_order.remove(product_Number);
                    orderProudctDTOs.forEach(orderProudctDTO -> {
                        if(orderProudctDTO.product_id.equals(product_Number) && orderProudctDTO.order_id.equals(String.valueOf(order_ID))){
                            orderProudctDTO.Delete();
                        }
                    });

                    return "**********************************************\nThis product has been removed successfully!" +
                            "\n***********************************************\n\n";
                }
            }
            return "****************************************************************\nThere is no product with this product number in this order" +
                    "\n****************************************************************\n\n";

        }
        else {
            return "**************************************************************************************************\nThis order has already arrived or already delivered." +
                    " It is not possible to edit it at this stage\n**************************************************************************************************\n\n";
        }
    }
    public HashMap<String, Integer> getProducts_in_order(){
        return products_in_order;
    }
    public void setTotalPrice(double total_price){
        this.total_price = total_price;
        this.orderDTO.UpdateTotalPrice(total_price);
    }
    public double getTotalPrice(){
        return  this.total_price;
    }
    public String getSupplierID(){
        return ID_of_supplier_in_charge;
    }
    public int get_products_in_order_size(){
        return products_in_order.size();
    }
    public void CancelOrder(Order_Status order_status){
        this.order_status = order_status;
        orderDTO.UpdateOrderStatus(order_status.toString());
        for (Map.Entry<String, Integer> entry : products_in_order.entrySet()) {
            String product = entry.getKey();
            orderProudctDTOs.forEach(orderProudctDTO -> {
                if(orderProudctDTO.product_id.equals(product) && orderProudctDTO.order_id.equals(String.valueOf(order_ID))){
                    orderProudctDTO.Delete();
                }
            });
        }
    }
    public boolean checkCancel(){
        return order_status == Order_Status.CANCELLED;
    }
    public void setCancel(){
        order_status = Order_Status.CANCELLED;
        orderDTO.UpdateOrderStatus(order_status.toString());
    }
    public String TOString() {
        getOrderStatus();
        return "Order ID: " + order_ID + "\n" +
                "Openning Date: " + openning_date + "\n" +
                "Shipment Date: " + shipment_date + "\n" +
                "Order Status: " + order_status + "\n" +
                "Supplier ID: " + ID_of_supplier_in_charge + "\n" +
                "Products in Order: " + products_in_order + "   (Product Number = Quantity in Order)\n" +
                "Total Price: " + total_price + "\n";

    }
    public Day convertLocalDateToDay(LocalDate localDate){
        int dayOfWeekValue = localDate.getDayOfWeek().getValue(); // Gets the day of the week as an integer (1 for Monday, 2 for Tuesday, etc.)
        switch (dayOfWeekValue) {
            case 1:
                return Day.MONDAY;
            case 2:
                return Day.TUESDAY;
            case 3:
                return Day.WEDNESDAY;
            case 4:
                return Day.THURSDAY;
            case 5:
                return Day.FRIDAY;
            case 6:
                return Day.SATURDAY;
            case 7:
                return Day.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeekValue);
        }
    }
    public HashMap<String, Integer> makeProductsInOrder(ArrayList<OrderProductDTO> orderProudctDTOS){
        HashMap<String, Integer> productsInOrder = new HashMap<>();
        for(OrderProductDTO orderProudctDTO : orderProudctDTOS){
            productsInOrder.put(orderProudctDTO.product_id,orderProudctDTO.quantity);
        }
        return productsInOrder;
    }
    public void delete(){
        orderDTO.Delete();
    }

    public int sumProductQuantities() {
        int sum = 0;
        for (int quantity : this.products_in_order.values()) {
            sum += quantity;
        }
        return sum;
    }


}


