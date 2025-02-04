package DataAccessLayer;
import java.util.ArrayList;
import java.util.List;

public class SupplierDTO extends DTO {
    public static final String SupplierNameColumnName = "supplier_name";
    public static final String PrivateCompanyIdColumnName = "private_company_id";
    public static final String ContactNameColumnName = "contact_name";
    public static final String ContactPhoneColumnName = "contact_phone";
    public static final String BankAccountColumnName = "bank_account";
    public static final String PaymentMethodColumnName = "payment_method";
    public static final String DeliveringMethodColumnName = "delivering_method";
    public static final String TypeColumnName = "supplying_method";

    public String name;
    public String private_company_id;
    public String contact_name;
    public String contact_phone;
    public String bank_account;
    public String payment_method;
    public String delivering_Method;
    public String supplying_method;


    public SupplierDTO(String name, String private_company_id, String contact_name, String contact_phone,
                       String bank_account, String payment_method, String delivering_Method, String supplying_method) {
        super(SupplierControllerDTO.getInstance());
        this.name = name;
        this.private_company_id = private_company_id;
        this.contact_name = contact_name;
        this.contact_phone = "0"+contact_phone;
        this.bank_account = bank_account;
        this.payment_method = payment_method;
        this.delivering_Method = delivering_Method;
        this.supplying_method = supplying_method;
    }
    // Additional methods can be implemented as needed, such as insert(), delete(), update(), etc.
    public void Insert() {
        try{
            ((SupplierControllerDTO)_controller).Insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting SupplierDaysDTO: " + e.getMessage());
        }
    }
    public void updateContact(String contact_name, String contact_phone) {
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        try{
            ((SupplierControllerDTO)_controller).Update(this);
        } catch (Exception e) {
            throw new RuntimeException("Error updating SupplierDaysDTO: " + e.getMessage());
        }
    }
}
