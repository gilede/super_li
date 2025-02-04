package DomainLayer;

public class Contact {
    private String contact_name;
    private String contact_phone;

    public Contact (String contact_name, String contact_phone) {
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
    }
    public String get_Contact_Name() {
        return contact_name;
    }
    public String get_Contact_Phone() {
        return contact_phone;
    }
    public void set_Contact_Data(String new_contact_name, String new_contact_phone) {
        this.contact_name = new_contact_name;
        this.contact_phone = new_contact_phone;
    }
    public String ToString(){

        return "Contact Name: " + contact_name + "\n" +
                "Contact Phone: " + contact_phone + "\n" +
                "****************************************\n\n";
    }
}
