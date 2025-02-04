package DomainLayer;

import DataAccessLayer.SupplierAgreementDataDTO;
import DataAccessLayer.SupplierDTO;
import DataAccessLayer.SupplierDaysDTO;
import DataAccessLayer.SupplierProductCategoryDTO;

import java.util.ArrayList;

public class Supplier_Fixed_Days extends Supplier {
    private Day[] days;
    private ArrayList<SupplierDaysDTO> supplierDaysDTOs;
    public Supplier_Fixed_Days(String supplier_Name, String private_company_id ,String contact_name ,String contact_phone, String bank_account, String payment_method,String delivering_method ,ArrayList<String> days,ArrayList<ArrayList<String>> list_of_products,ArrayList<String> products_Category) {
        super(supplier_Name,private_company_id , contact_name ,contact_phone , bank_account, payment_method, delivering_method, "FIXED_DAYS",list_of_products,products_Category);
        this.supplierDaysDTOs = new ArrayList<>();
        for(int i = 0; i < days.size(); i++){
            SupplierDaysDTO supplierDaysDTO = new SupplierDaysDTO(private_company_id,days.get(i));
            supplierDaysDTOs.add(supplierDaysDTO);
            supplierDaysDTO.Insert();
        }
        this.days = convertDays(days);
    }
    public Supplier_Fixed_Days(SupplierDTO supplierDTO, ArrayList<SupplierAgreementDataDTO> supplierAgreementDataDTOs, ArrayList<SupplierProductCategoryDTO> supplierProductCategoryDTOs, ArrayList<SupplierDaysDTO> supplierDaysDTOs){
        super(supplierDTO,supplierAgreementDataDTOs,supplierProductCategoryDTOs);
        this.days = convertDaysDTO(supplierDaysDTOs);
    }
    public ArrayList<String> getDaysString() {
        ArrayList<String> days1 = new ArrayList<>();
        for (int i = 0; i < days.length; i++) {
            days1.add(days[i].toString());
        }
        return days1;
    }
    public Day[] convertDays(ArrayList<String> days){
        Day[] days1 = new Day[days.size()];
        for (int i = 0; i < days.size(); i++) {
            days1[i] = Day.valueOf(days.get(i).toUpperCase());
        }
        return days1;
    }
    public Day[] convertDaysDTO(ArrayList<SupplierDaysDTO> supplierDaysDTOs){
        Day[] days1 = new Day[supplierDaysDTOs.size()];
        for (int i = 0; i < supplierDaysDTOs.size(); i++) {
            days1[i] = Day.valueOf(supplierDaysDTOs.get(i).day.toUpperCase());
        }
        return days1;
    }
    public void addOrder(Order order){
        checkDay(order.getShipmentDay());
        orders.put(order.getOrderId(),order);
    }
    public boolean checkDay(Day day){
        for (int i = 0; i < days.length; i++) {
            if (days[i].equals(day)) {
                return true;
            }
        }
        throw new AssertionError("The supplier does not supply on this day");
    }
    public String toString(){
        return super.toString() + "Days: " + getDaysString() + "\n";
    }

}
