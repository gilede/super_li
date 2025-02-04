package DomainLayer;

import DataAccessLayer.SupplierAgreementDataDTO;
import DataAccessLayer.SupplierDTO;
import DataAccessLayer.SupplierDaysDTO;
import DataAccessLayer.SupplierProductCategoryDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class Supplier_By_Order extends Supplier{
    private ArrayList<Order> active_orders;
    public Supplier_By_Order(String supplier_Name, String private_company_id ,String contact_name ,String contact_phone, String bankAccount, String paymentMethod,String delivering_method,ArrayList<ArrayList<String>> list_of_products,ArrayList<String> products_Category){
        super(supplier_Name,private_company_id , contact_name ,contact_phone , bankAccount, paymentMethod, delivering_method, "BY_ORDER",list_of_products,products_Category);
        active_orders = new ArrayList<>();
    }
    public Supplier_By_Order(SupplierDTO supplierDTO, ArrayList< SupplierAgreementDataDTO > supplierAgreementDataDTOs, ArrayList< SupplierProductCategoryDTO > supplierProductCategoryDTOs){
            super(supplierDTO,supplierAgreementDataDTOs,supplierProductCategoryDTOs);
            active_orders = new ArrayList<>();
        }
    public void addOrder(Order order){
        super.addOrder(order);
        active_orders.add(order);
    }
    public void removeOrder(Integer orderID){
        super.removeOrder(orderID);
        for (Order order : active_orders) {
            if(order.getOrderId() == orderID){
                active_orders.remove(order);
                break;
            }
        }
    }

}
