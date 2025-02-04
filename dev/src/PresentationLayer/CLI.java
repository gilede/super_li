package PresentationLayer;
import ServiceLayer.Service_Controller;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI {
    // Creating a private static variable prevents the option to create multiple scanner type variables
    private static Scanner scanner = new Scanner(System.in);
    // Singleton of Service Controller.
    public static Service_Controller serviceController = Service_Controller.getInstance();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  Mains */
    public static void main() {
        System.out.println("Welcome !\nThis is the supplier module which was created by Gil Eden and Lidor Mashiach.\n");
        boolean ExitProgram = false;
        loadData();

        System.out.println("What would you like to accomplish today?");
        while (!ExitProgram) {
            System.out.println("_________________________________________________ ");
            System.out.println("1. Actions related to Suppliers.");
            System.out.println("2. Actions related to Orders.");
            System.out.println("3. Actions related to Products.");
            System.out.println("4. Exit From The System.");
            System.out.println("_________________________________________________ ");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            System.out.println();

            System.out.println();
            switch (choice) {
                case 1:
                    SuppliersMain();
                    System.out.println("Is there anything else you would like to accomplish?");

                    break;
                case 2:
                    OrdersMain();
                    System.out.println("Is there anything else you would like to accomplish?");

                    break;
                case 3:
                    ProductsMain();
                    System.out.println("Is there anything else you would like to accomplish?");

                    break;
                case 4:
                    ExitProgram = true;
                    System.out.println("It was a pleasure working with you today.\nHave a nice day!");
                    exitdata();

                    break;
                default:
                    System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                    System.out.println("Let's Try again.");

                    break;
            }

        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void SuppliersMain() {
        boolean ExitSuppliersMain = false;
        System.out.println("\n\n~You are now in the Suppliers Menu~\n");
        while (!ExitSuppliersMain) {

            System.out.println("What action would you like to take?");
            while (!ExitSuppliersMain) {
                System.out.println("________________________________________________________ ");
                System.out.println("1. Add a new supplier to the system.");
                System.out.println("2. Edit the contact details of a specific supplier.");
                System.out.println("3. View the contact information of a specific supplier.");
                System.out.println("4. Print supplier card of specific supplier.");
                System.out.println("5. Print all the data you have on all supplies.");
                System.out.println("6. Print the last order of a specific supplier.");
                System.out.println("7. Print the entire order history of a specific supplier");
                System.out.println("8. Please print the Bill of Quantities of a specific supplier");
                System.out.println("9. Exit From Suppliers Menu.");
                System.out.println("________________________________________________________ ");
                System.out.print("Please enter the number of your choice here: ");
                int choice = scanner.nextInt();
                System.out.println();
                switch (choice) {
                    case 1:
                        System.out.println();
                        addSupplier();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 2:
                        System.out.println();
                        editContact();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 3:
                        System.out.println();
                        getContact();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 4:
                        System.out.println();
                        supplier_card();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 5:
                        System.out.println();
                        getSuppliers();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 6:
                        System.out.println();
                        LastOrderSupplier();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 7:
                        System.out.println();
                        allSupplierOrders();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                        case 8:
                            System.out.println();
                            BilOfQuantities();
                            System.out.println("\nIs there anything else you would like to accomplish?");
                            break;
                    case 9:
                        ExitSuppliersMain = true;
                        System.out.println("\n\n\n~You have chosen to exit from the Suppliers menu~\n");
                        break;
                    default:
                        System.out.println("\n\n\nOops... It's seems like you entered an invalid choice number.");
                        System.out.println("Let's Try again.");
                        break;
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void OrdersMain() {
        boolean ExitOrderMain = false;
        System.out.println("\n\n~You are now in the Orders Menu~\n");
        while (!ExitOrderMain) {
            System.out.println("What action would you like to take?");
            while (!ExitOrderMain) {
                System.out.println("______________________________________________________________ ");
                System.out.println("1. Create a new order.");
                System.out.println("2. Create a new order due to shortages.");
                System.out.println("3. Edit an existing order.");
                System.out.println("4. Cancel the order of some products from an existing order.");
                System.out.println("5. Cancel an existing order.");
                System.out.println("6. Print the last order of a specific supplier.");
                System.out.println("7. Print the entire order history of a specific supplier.");
                System.out.println("8. Exit From Orders Menu.");
                System.out.println("______________________________________________________________ ");
                System.out.print("Please enter the number of your choice here: ");
                int choice = scanner.nextInt();
                System.out.println();
                switch (choice) {
                    case 1:
                        System.out.println();
                        addOrder();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 2:
                        System.out.println();
                        orderByShortage();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 3:
                        System.out.println();
                        editOrder();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 4:
                        System.out.println();
                        Remove_product_from_order();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 5:
                        System.out.println();
                        removeOrder();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 6:
                        System.out.println();
                        LastOrderSupplier();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 7:
                        System.out.println();
                        allSupplierOrders();
                        System.out.println("\nIs there anything else you would like to accomplish?");
                        break;
                    case 8:
                        ExitOrderMain = true;
                        System.out.println("\n\n\n~You have chosen to exit from the Orders menu~\n");
                        break;
                    default:
                        System.out.println("\n\n\nOops... It's seems like you entered an invalid choice number.");
                        System.out.println("Let's Try again.");
                        break;
                }
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void ProductsMain() {
        boolean ExitProductsMain = false;
        System.out.println("\n\n~You are now in the Products Menu~\n");

        while (!ExitProductsMain) {
            System.out.println("What action would you like to take?");
            while (!ExitProductsMain) {
                System.out.println("___________________________________________________________________________ ");
                System.out.println("1. Add a new product to the system.");
                System.out.println("2. Print all the data you have about a specific product.");
                System.out.println("3. Print all the information you have on all the products in the system.");
                /*
                We will not want to delete items from our system, so as not to delete optional data for future use.
                However, it is important to give an option to delete an item in situations where an item has been entered incorrectly, i.e. takes up space in memory unnecessarily
                 */
                System.out.println("4. Remove a product from the system.");
                System.out.println("5. Exit From Products Menu.");
                System.out.println("___________________________________________________________________________ ");

                System.out.print("Please enter the number of your choice here: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println();
                        addProduct();
                        System.out.println("Is there anything else you would like to accomplish?");
                        break;
                    case 2:
                        System.out.println();
                        printProduct();
                        System.out.println("Is there anything else you would like to accomplish?");
                        break;
                    case 3:
                        System.out.println();
                        printAllProduct();
                        System.out.println("Is there anything else you would like to accomplish?");
                        break;
                    case 4:
                        System.out.println();
                        removeProduct();
                        System.out.println("Is there anything else you would like to accomplish?");
                        break;
                    case 5:
                        ExitProductsMain = true;
                        System.out.println("\n\n\n~You have chosen to exit from the Products menu~\n");
                        break;
                    default:
                        System.out.println("\n\n\nOops... It's seems like you entered an invalid choice number.");
                        System.out.println("Let's Try again.");
                        break;
                }


            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Products Methods*/

    public static void addProduct() {
        System.out.print("Enter the product name: ");
        String product_Name = scanner.next();
        System.out.print("Enter the company name: ");
        String company_name = scanner.next();
        String product_Category = productCategoryChecking();
        System.out.println(serviceController.addProduct(product_Name, company_name, product_Category));
    }

    public static String productCategoryChecking() {
        System.out.println("Please select specify the product family to which the product belongs:");
        while (true) {
            System.out.println("1. DAIRY PRODUCTS.");
            System.out.println("2. FROZEN PRODUCTS.");
            System.out.println("3. FRUITS AND VEGETABLES.");
            System.out.println("4. CLEANING PRODUCTS.");
            System.out.println("5. DRIED PRODUCTS.");
            System.out.println("6. MEAT PRODUCTS.");
            System.out.println("7. DRINKS.");
            System.out.println("8. MISCELLANEOUS.");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    return "DAIRY_PRODUCTS";
                case 2:
                    return "FROZEN_PRODUCTS";
                case 3:
                    return "FRUITS_AND_VEGETABLES";
                case 4:
                    return "CLEANING_PRODUCTS";
                case 5:
                    return "DRIED_PRODUCTS";
                case 6:
                    return "MEAT_PRODUCTS";
                case 7:
                    return "DRINKS";
                case 8:
                    return "MISCELLANEOUS";
                default:
                    System.out.println("\n\nOops... It seems like you entered an invalid choice number.");
                    System.out.println("Let's try again.");
                    break;
            }
        }
    }

    public static void removeProduct() {
        System.out.print("Please enter a product ID that you would like to remove from the system: ");
        String choiceProductID = scanner.next();
        System.out.print(serviceController.removeProduct(choiceProductID));
    }

    public static void printProduct() {
        System.out.print("Please enter a product ID that you would like to print: ");
        String choiceProductID = scanner.next();
        System.out.println(serviceController.findProductById(choiceProductID));
        System.out.println("\n");
    }

    public static void printAllProduct() {
        System.out.println(serviceController.getProducts());
        System.out.println("\n");
    }
    public static void loadData() {
        System.out.print(serviceController.loadData());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Suppliers Methods*/

    public static void addSupplier() {
        System.out.print("Enter the supplier name: ");
        String supplier_Name = scanner.next();
        System.out.print("Enter the BIN (Business Identification Number): ");
        String supplier_ID = scanner.next();
        System.out.print("Enter the supplier's contact name: ");
        String contact_name = scanner.next();
        String contact_phone = Phone_Number_Validation_Checking();
        System.out.print("Enter the supplier's bank account number: ");
        String supplier_Bank_Account = scanner.next();
        String payment_method = payment_method();
        System.out.println("To complete entering the supplier's details in the system, add the details of the agreement with him.");
        ArrayList<ArrayList<String>> list_of_products = products_of_agreements();
        String supplying_method = Supplying_method();
        if (serviceController.IsFixedDay(supplying_method)) {
            String delivering_method = Delivering_Method();
            ArrayList<String> days = enterDays();
            System.out.println(serviceController.AddFixedDaySupplier(supplier_Name, supplier_ID, contact_name, contact_phone, supplier_Bank_Account, payment_method, delivering_method, days, list_of_products));
        } else {
            System.out.println(serviceController.addByOrderSupplier(supplier_Name, supplier_ID, contact_name, contact_phone, supplier_Bank_Account, payment_method,"DELIVERING_METHOD", list_of_products));
        }
    }

    public static void supplier_card() {
        System.out.print("Please enter the BIN (Business Identification Number): ");
        String supplier_id = scanner.next();
        System.out.println(serviceController.supplier_card(supplier_id));
    }

    public static void getSuppliers() {
        System.out.println(serviceController.getSuppliers());
    }

    public static void editContact() {
        System.out.print("Please enter the BIN (Business Identification Number): ");
        String supplier_id = scanner.next();
        System.out.print("Enter the supplier's contact name: ");
        String contact_name = scanner.next();
        String contact_phone = Phone_Number_Validation_Checking();
        System.out.println(serviceController.editcontact(supplier_id, contact_name, contact_phone));
    }

    public static void getContact() {
        System.out.print("Please enter the BIN (Business Identification Number): ");
        String supplier_id = scanner.next();
        System.out.println(serviceController.getcontact(supplier_id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String Phone_Number_Validation_Checking() {
        while (true) {
            System.out.print("Please enter the contact's phone number: ");
            String contact_phone = scanner.next();
            if (serviceController.Check_Phone(contact_phone)) {
                return contact_phone;
            }
            System.out.println("\nYou entered an incorrect phone number.\nA valid phone number starts with \"05\" and contains exactly 10 digits.");
            System.out.println("Let's Try again.\n");

        }
    }
    public static void BilOfQuantities(){
        System.out.println("Please enter the BIN number of the supplier whose bill of quantities you would like to see");
        System.out.print("Please enter the number of your choice here: ");
        String supplier_id = scanner.next();
        System.out.println(serviceController.BilOfQuantities(supplier_id));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String payment_method() {
        System.out.println("Please select a payment method that the supplier would like to accept:");
        while (true) {
            System.out.println("1. Payment by checks.");
            System.out.println("2. Cash payment.");
            System.out.println("3. Payment by bank transfer.");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    return "CHECK";
                case 2:
                    return "CASH";
                case 3:
                    return "BANK_TRANSACTION";
                default:
                    System.out.println("\n\nOops... It seems like you entered an invalid choice number.");
                    System.out.println("Let's try again.");
                    break;
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String Delivering_Method() {
        System.out.println("Please select a delivering method that has been signed in front of the Supplier");
        while (true) {
            System.out.println("1. Picking Up Method - The collection of shipments is the responsibility of \"Super-Lee\"");
            System.out.println("2. Delivering Method - The responsibility for the delivery of the orders is the responsibility of the supplier");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    return "PICK_UP";
                case 2:
                    return "DELIVERING_METHOD";
                default:
                    System.out.println("\n\nOops... It seems like you entered an invalid choice number.");
                    System.out.println("Try again.");
                    break;
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String Supplying_method() {
        System.out.println("Please select an order method that has been signed in front of the Supplier");
        while (true) {
            System.out.println("1. Fixed Day Orders - The supplier will deliver standing orders on pre-arranged days");
            System.out.println("2. By Order - The supplier will only deliver if a new order is opened, and each order is for a single shipment.");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    return "FIXED_DAYS";
                case 2:
                    return "BY_ORDER";
                default:
                    System.out.println("\n\nOops... It seems like you entered an invalid choice number.");
                    System.out.println("Try again.");
                    break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ArrayList<String>> products_of_agreements() {
        ArrayList<ArrayList<String>> list_of_products = new ArrayList<>();
        String product_ID, product_Quantity, product_Price, product_Discount;
        System.out.println("The agreement must contain details of at least one item that the supplier can provide for \"Super-Lee\"");
        while (true) {
            while (true) {
                System.out.print("Enter product ID: ");
                product_ID = scanner.next();
                if (serviceController.Check_Product_ID(list_of_products, product_ID)) {
                    break;
                }
                System.out.println("Invalid productID or ProductID already been insert  0\n Try again");
            }
            while (true) {
                System.out.print("Enter product price: ");
                product_Price = scanner.next();
                if (serviceController.Check_Product_Price(product_Price)) {
                    break;
                }
                System.out.println("Price should be above 0\n Try again");
            }
            while (true) {
                System.out.print("Enter a minimum quantity that qualifies for a discount: ");
                product_Quantity = scanner.next();
                if (serviceController.Check_product_Quantity(product_Quantity)) {
                    break;
                }
                System.out.println("Quantity should be above 1\nLet's Try again");
            }
            while (true) {
                System.out.print("Enter product discount: ");
                product_Discount = scanner.next();
                if (serviceController.Check_product_Discount(product_Discount)) {
                    break;
                }
                System.out.println("Discount should be between 0.01-0.99\n Try again");
            }
            ArrayList<String> product = new ArrayList<>();
            product.add(product_ID);
            product.add(product_Price);
            product.add(product_Quantity);
            product.add(product_Discount);
            list_of_products.add(product);
            int choice;
            while (true) {
                System.out.println("Do you want to add another product to the agreement?\n1. Yes.\n2. No. This was the last Product.");
                System.out.print("Please enter the number of your choice here: ");
                choice = scanner.nextInt();
                if (choice != 2 && choice != 1) {
                    System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                    System.out.println("Let's Try again.");
                } else {
                    break;
                }
            }
            if (choice == 2) {
                break;
            }
        }
        return list_of_products;
    }

    public static ArrayList<String> enterDays() {
        ArrayList<String> days = new ArrayList<>();
        System.out.println("Please select a fixed day on which the supplier will be able to place the fixed orders");
        while (true) {
            System.out.println("1. Sunday");
            System.out.println("2. Monday");
            System.out.println("3. Tuesday");
            System.out.println("4. Wednesday");
            System.out.println("5. Thursday");
            System.out.println("6. Friday");
            System.out.println("7. Saturday");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            if (choice >= 1 && choice <= 7) {
                String dayToAdd = getDayName(choice);
                if (!isDayAlreadyAdded(days, dayToAdd)) {
                    days.add(dayToAdd);
                } else {
                    System.out.println("The selected day is already in the list.");
                }
                int choice2;
                while (true) {
                    System.out.println("Do you want to add another fixed-day to the agreement?\n1. Yes.\n2. No. This was the last fixed-day.");
                    System.out.print("Please enter the number of your choice here: ");
                    choice2 = scanner.nextInt();
                    if (choice2 != 2 && choice2 != 1) {
                        System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                        System.out.println("Let's Try again.");
                    } else {
                        break;
                    }
                }
                if (choice2 == 2) {
                    break;
                }
            }
        }
        return days;
    }

    public static String getDayName(int dayNumber) {
        switch (dayNumber) {
            case 1:
                return "SUNDAY";
            case 2:
                return "MONDAY";
            case 3:
                return "TUESDAY";
            case 4:
                return "WEDNESDAY";
            case 5:
                return "THURSDAY";
            case 6:
                return "FRIDAY";
            case 7:
                return "SATURDAY";
            default:
                throw new IllegalArgumentException("Invalid day number: " + dayNumber);
        }
    }

    public static boolean isDayAlreadyAdded(ArrayList<String> days, String day) {
        return days.contains(day);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Orders Methods*/
    public static void addOrder() {
        System.out.print("Please enter the BIN of the supplier from which you would like to place an order: ");
        String SupplierID = scanner.next();
        String ShipmentDay = selectshipmentday();
        HashMap<String, Integer> products_in_order = Products_in_order();
        System.out.println(serviceController.addOrder(ShipmentDay, SupplierID, products_in_order));
    }
    public static void orderByShortage() {
        HashMap<String, Integer> products_in_order = Products_in_order();
        //open orders in the cheapest supplier
        System.out.println(serviceController.addOrderByShortage(products_in_order));
    }

    public static String selectshipmentday() {
        while (true) {
            System.out.println("Please choose a desired delivery day - the delivery day you choose will be at least a week away from today's date");
            System.out.println("1. Sunday");
            System.out.println("2. Monday");
            System.out.println("3. Tuesday");
            System.out.println("4. Wednesday");
            System.out.println("5. Thursday");
            System.out.println("6. Friday");
            System.out.println("7. Saturday");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice < 8) {
                return getDayName(choice);

            } else {
                System.out.println("\n\nOops... It seems like you entered an invalid choice number.");
                System.out.println("Try again.");
            }
        }
    }

    public static HashMap<String, Integer> Products_in_order() {
        HashMap<String, Integer> products_in_order = new HashMap<>();
        Integer quantity;
        String ProductID;
        while (true) {
            while (true) {
                System.out.println("Please enter an ID number of the product you would like to add to the order");
                System.out.print("Please enter your choice here: ");
                ProductID = scanner.next();
                // Check if the product ID is valid
                if (!serviceController.Contains_product(products_in_order, ProductID)) {
                    System.out.println("\nYou have already entered this item number in this order.\n" +
                            "or the product does not exist in the system.\n" +
                            "Please enter a different item number\n");
                } else
                    break;
            }
            while (true) {
                System.out.println("Please enter the order quantity for this product");
                System.out.print("Please enter your choice here: ");
                quantity = serviceController.Check_String_Greater_than_zero(scanner.next());
                if (quantity != null)
                    break;
                else
                    System.out.println("Quantity should be above 0\n Try again");
            }
            products_in_order.put(ProductID, quantity);
            if (!serviceController.check_if_there_is_more_products(products_in_order.size())){
                System.out.println("you already put all the products that is possible to put in the order");
                break;
            }
            int choice;
            while (true) {
                System.out.println("Do you want to add another product to the order?\n1. Yes.\n2. No. This was the last Product.");
                System.out.print("Please enter the number of your choice here: ");
                choice = scanner.nextInt();
                if (choice != 2 && choice != 1) {
                    System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                    System.out.println("Let's Try again.");
                } else {
                    break;
                }
            }
            if (choice == 2) {
                break;
            }
        }
        return products_in_order;
    }

    public static void LastOrderSupplier(){
        System.out.println("Please enter the BIN of the supplier on which you would like to perform the operation");
        System.out.print("Please enter the number of your choice here: ");
        String choice = scanner.next();
        System.out.println(serviceController.LastOrderSupplier(choice));
    }

    public static void allSupplierOrders(){
        System.out.println("Please enter the BIN of the supplier on which you would like to perform the operation");
        System.out.print("Please enter the number of your choice here: ");
        String choice = scanner.next();
        System.out.println(serviceController.allSupplierOrders(choice));
    }


    public static void editOrder() {
        System.out.println("Please enter the order number you would like to edit");
        System.out.print("Please enter the number of your choice here: ");
        String ordernumber = scanner.next();
        if(!serviceController.ExistedOrderChecking(ordernumber)){

            System.out.println("*****************************************************************************\nThere is no order in the system that belongs to the order number - " + ordernumber +
                    "\n*****************************************************************************\n\n");
            return;
        }
        while (true) {
            System.out.println("Please enter the number of the product you want to change the ordered quantity of");
            System.out.print("Please enter the number of your choice here: ");
            String productID = scanner.next();
            System.out.println("Please enter the updated quantity");
            System.out.print("Please enter the number of your choice here: ");
            String Updatedquantity = scanner.next();
            System.out.println(serviceController.editOrder(ordernumber, productID, Updatedquantity));
            System.out.println();
            int choice;
            while (true) {
                System.out.println("Do you want edit quantity of another product in this order?\n1. Yes.\n2. No. This was the last Product.");
                System.out.print("Please enter the number of your choice here: ");
                choice = scanner.nextInt();
                if (choice != 2 && choice != 1) {
                    System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                    System.out.println("Let's Try again.");
                } else {
                    break;
                }
            }
            if (choice == 2) {
                break;
            }
        }
    }

    public static void removeOrder(){
        System.out.println("Please enter the order number you wish to Cancel");
        System.out.print("Please enter the number of your choice here: ");
        Integer choice = Integer.valueOf(scanner.next());
        System.out.println(serviceController.removeOrder(choice));
    }


    public static void Remove_product_from_order(){
        System.out.println("Please enter the order number you would like to edit");
        System.out.print("Please enter the number of your choice here: ");
        String ordernumber = scanner.next();
        if(!serviceController.ExistedOrderChecking(ordernumber)){
            System.out.println("*****************************************************************************\nThere is no order in the system that belongs to the order number - " + ordernumber +
                    "\n*****************************************************************************\n\n");
            return;
        }
        while (true) {
            System.out.println("Please enter the number of the product you want to remove from the order");
            System.out.print("Please enter the number of your choice here: ");
            String productID = scanner.next();
            System.out.println(serviceController.Remove_product_from_order(ordernumber, productID));
            System.out.println();
            int choice;
            while (true) {
                System.out.println("Do you want remove another product from this order?\n1. Yes.\n2. No. This was the last Product.");
                System.out.print("Please enter the number of your choice here: ");
                choice = scanner.nextInt();
                if (choice != 2 && choice != 1) {
                    System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                    System.out.println("Let's Try again.");
                } else {
                    break;
                }
            }
            if (choice == 2) {
                break;
            }
        }
    }

    public static void exitdata() {
        try {
            serviceController.ExitData();
            // Get the desktop path
            String desktopPath = System.getProperty("user.home") + "/Desktop";
            File desktopFolder = new File(desktopPath);

            // Create a File object for superli.db
            File dbFile = new File(desktopFolder, "superli.db");

            // Check if the file exists and delete it
            if (dbFile.exists()) {
                if (dbFile.delete()) {
                }
            } else {
            }
        } catch (Exception e) {
            System.err.println("Error deleting the database file: " + e.getMessage());
        }
    }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
