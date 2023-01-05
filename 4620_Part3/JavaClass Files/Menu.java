package CPSC4620.part3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Formatter;
import java.text.SimpleDateFormat;

/**
 * Manning Graham
 * CPSC 4620
 * Project Part 3
 * 12-01-2022
 */


public class Menu {
	public static void main(String[] args) throws SQLException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to Taylor's Pizzeria!");
		
		int menu_option = 0;

		// present a menu of options and take their selection
		PrintMenu();
		String option = reader.readLine();
		menu_option = Integer.parseInt(option);

		while (menu_option != 9) {
			switch (menu_option) {
			case 1:// enter order
				EnterOrder();
				break;
			case 2:// view customers
				viewCustomers();
				break;
			case 3:// enter customer
				EnterCustomer();
				break;
			case 4:// view order
				// open/closed/date
				ViewOrders();
				break;
			case 5:// mark order as complete
				MarkOrderAsComplete();
				break;
			case 6:// view inventory levels
				ViewInventoryLevels();
				break;
			case 7:// add to inventory
				AddInventory();
				break;
			case 8:// view reports
				PrintReports();
				break;
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
		}
	}

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}


	public static void EnterOrder() throws SQLException, IOException 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int custID = -1;
		int oType = 0;
		int orderID = DBNinja.getNextOrderID() + 1;
		Date date = new Date();
		String orderDate =  date.toString();

		// get order type
		String[] o = {"delivery", "pickup", "dine-in"};
		while(oType > 4 || oType< 1 ){
			System.out.println(" 1.delivery   2.pickup   3.dine-in\n");
			try{
				oType = Integer.parseInt(reader.readLine());
			} catch (Exception e){
				System.out.println("Wrong format, choose between 1, 2, or 3");
			}
		}

		// Create Customer
		if(oType == 1 || oType == 2){
			int foo = 0;
			while(foo != 1 && foo != 2){
				System.out.println("1.Existing customer   2.Non existing customer\n");
				try {
					foo = Integer.parseInt(reader.readLine());
				} catch (Exception e){
					System.out.println("Wrong format, choose between 1 or 2");
				}
			}
			//if existing customer, get id
			if(foo == 1){
				DBNinja.printCustomers();
				while (custID  <= 0){
					System.out.println("PLease enter Customer ID: ");
					try{
						custID = Integer.parseInt(reader.readLine());
					} catch (Exception e){
						System.out.println("Wrong format, must be larger than 0, try again");
					}
					if(custID > DBNinja.getCustomerList().size()){
						custID = -1;
						System.out.println("Invalid customer information, try again.");
					}
				}
			} else {
				custID = DBNinja.getCustomerList().size() + 1;
				EnterCustomer();
			}
		}

		//build and store pizzas locally
		ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
		Double Ordprice = 0.0;
		Double Ordcost= 0.0;
		int s = 5;
		while(s != 1){
			Pizza a = buildPizza((orderID));
			Ordprice += a.getCustPrice();
			Ordcost += a.getBusPrice();
			pizzas.add(a);
			System.out.println(" Press 1 to finish my order. Press any other key to add another pizza:");
			s = Integer.parseInt(reader.readLine());
		}

		// Get Discounts
		String moreDiscount = "";
		System.out.println("Press 1 if no discounts. Press any other key to add Discounts");
		ArrayList<Discount> discountList = DBNinja.getDiscountList();
		int num_discount = discountList.size();
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		moreDiscount = reader.readLine();
		while (!moreDiscount.equals("1")){

			System.out.println("Enter your discount for order\n 1.Employee	2.Lunch special Medium	" +
					"3.Lunch special large	4.Specialty Pizza	5. Gameday Special\n");
			int discountID = -1;
			while (discountID < 1 || discountID > num_discount){
				try	{
					discountID = Integer.parseInt(reader.readLine());
				} catch (Exception e){
					System.out.println("Wrong Format");
				}
				if(discountID < 1 || discountID > num_discount)
					System.out.println("Discount ID must be in the list.");
			}
			Discount curDiscount =  discountList.get(discountID - 1);
			discounts.add(curDiscount);
			System.out.println("Press to 1 finish adding discounts. Press any other key to continue adding more:");
			moreDiscount = reader.readLine();
		}

		//Pickup Order
		if(oType == 2){
			Order O = new PickupOrder(orderID,custID,orderDate,Ordprice, Ordcost, 1, 0);
			DBNinja.addOrder(O);
			for(int i = 0; i < discounts.size(); ++i){
				System.out.println(discounts.get(i));
				O.addDiscount(discounts.get(i)); }
			for(int i = 0; i < discounts.size(); ++i){ DBNinja.useOrderDiscount(O, discounts.get(i)); }
		}
		//Delivery Order
		else if(oType == 1){
			System.out.println("Enter delivery address");
			String address = reader.readLine();
			Order O = new DeliveryOrder(orderID, custID, orderDate, Ordcost, Ordprice, 0, address);
			DBNinja.addOrder(O);
			for(int i = 0; i < discounts.size(); ++i){
				System.out.println(discounts.get(i));
				O.addDiscount(discounts.get(i)); }
			for(int i = 0; i < discounts.size(); ++i){ DBNinja.useOrderDiscount(O, discounts.get(i)); }
		}
		//Dine-In Order
		else {
			System.out.println("Enter Table Number:");
			int table = -1;
			while (table < 0){
				try {
					table = Integer.parseInt(reader.readLine());
				}catch (Exception e){
					System.out.println("Wrong format");
				}
				if( table < 0) System.out.println("table number must be > 0");
			}
			Order O = new DineinOrder(orderID, custID, orderDate, Ordprice, Ordcost, 0, table);
			DBNinja.addOrder(O);
			for(int i = 0; i < discounts.size(); ++i){ O.addDiscount(discounts.get(i)); }
			for(int i = 0; i < discounts.size(); ++i){ DBNinja.useOrderDiscount(O, discounts.get(i)); }
		}

		//Add Pizza Info into DB
		for(int i = 0; i < pizzas.size(); ++i){
			// add into pizza table
			Pizza curPizza = pizzas.get(i);
			curPizza.setPizzaID(DBNinja.getMaxPizzaID() + 1);
			DBNinja.addPizza(curPizza);
			ArrayList<Topping> pt = curPizza.getToppings();
			for(int j = 0;j < pt.size(); ++j){ DBNinja.useTopping(curPizza, pt.get(j), false); }
			ArrayList<Discount> curDiscounts = curPizza.getDiscounts();
			for(int k  = 0; k < curDiscounts.size(); ++k){ DBNinja.usePizzaDiscount(curPizza, curDiscounts.get(k)); }
		}
		System.out.println("Order Complete.");
	}


	public static void viewCustomers() throws IOException, SQLException {

		System.out.println("CUSTOMER LIST: ");
		ArrayList<Customer> customers = DBNinja.getCustomerList();
		for(int i = 0; i < customers.size(); i++){
			System.out.println(customers.get(i));
		}
	}


	public static void EnterCustomer() throws SQLException, IOException 
	{
		System.out.println("Please Enter the Customers name (First <space> Last) : ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String name = reader.readLine();
		System.out.println("Need Address? (Delivery Customer) Enter Y/N");
		String option = reader.readLine();
		String address = "";
		if (option.equals("y") || option.equals("Y")) {
			System.out.println("Please enter the address: ");
			address = reader.readLine();
		}else{ address = null;}

		System.out.println("Please Enter the Customer phone number (XXX-XXX-XXXX): ");
 		String phone = reader.readLine();
 		Customer tmp = new Customer( -1, address, name, phone);
 		DBNinja.addCustomer(tmp);
 	}


	public static void ViewOrders() throws SQLException, IOException 
	{
		ArrayList<Order> currOrders = DBNinja.getCurrentOrders();
		// Print off high level information about the order
		int o_count = 1;
		for (Order o : currOrders) {
			System.out.println(Integer.toString(o_count) + ": " + o.toSimplePrint());
			o_count++;
		}
		// User can now select an order and get the full detail
		System.out.println("Which order would you like to see in detail? Enter the number: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chosen_order = Integer.parseInt(reader.readLine());
		if (chosen_order <= currOrders.size()) {
			System.out.println(currOrders.get(chosen_order - 1).toString());
		} else {
			System.out.println("Incorrect entry, not an option");
		}
	}

	public static void MarkOrderAsComplete() throws SQLException, IOException 
	{
		ArrayList<Order> currOrders = DBNinja.getCurrentOrders();
		int o_count = 1;
		// see all open orders
		for (Order o : currOrders) {
			System.out.println(Integer.toString(o_count) + ": " + o.toSimplePrint());
			o_count++;
		}
		// pick the order to mark as completed
		System.out.println("Which order would you like mark as complete? Enter the number: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chosen_order = Integer.parseInt(reader.readLine());
		if (chosen_order <= currOrders.size()) {
			DBNinja.CompleteOrder(currOrders.get(chosen_order - 1));
		} else {
			System.out.println("Incorrect entry, not an option");
		}
	}

	public static void ViewInventoryLevels() throws SQLException, IOException 
	{
		ArrayList<Topping> curInventory = DBNinja.getInventory();
		int t_count = 1;
		for (Topping t : curInventory) {
			System.out.println(Integer.toString(t_count) + ": " + t.getTopName() + ", INV Level: "+ Double.toString(t.getCurINVT()));
			t_count++;
		}
	}

	public static void AddInventory() throws SQLException, IOException {
		ArrayList<Topping> curInventory = DBNinja.getInventory();
		int t_count = 1;
		for (Topping t : curInventory)
		{
			System.out.println(Integer.toString(t_count) + " : " + t.getTopName() + ", INV Level: "	+ Double.toString(t.getCurINVT()));
			t_count++;
		}
		System.out.println("Which topping do you want to add inventory to? Enter the number: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chosen_t = Integer.parseInt(reader.readLine());
		if (chosen_t <= curInventory.size()) {
			System.out.println("How many units would you like to add? ");
			double add = Double.parseDouble(reader.readLine());
			DBNinja.AddToInventory(curInventory.get(chosen_t - 1), add);
		} else {
			System.out.println("Incorrect entry, not an option");
		}
	}

	public static Pizza buildPizza(int orderID) throws SQLException, IOException 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean input = false;
		String tmp = "";
		String size = "";
		String crust = "";
		double custPrice = 0;
		double busPrice = 0;

		ArrayList<Topping> toppings = new ArrayList<Topping>(DBNinja.getInventory());
		ArrayList<Topping> customerToppings = new ArrayList<Topping>();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = formatter.format(date);
		System.out.println("What size is the pizza?");
		System.out.println("1.) Small \n2.) Medium \n3.) Large \n4.) X-Large \nEnter the Size number: ");
		while(!input){
			tmp = reader.readLine();
			if(tmp.equals("1")){
				input = true;
				size = "small";
			}
			else if(tmp.equals("2")){
				input = true;
				size = "medium";
			}
			else if(tmp.equals("3")){
				input = true;
				size = "large";
			}
			else if(tmp.equals("4")){
				input = true;
				size = "x-large";
			}
			else System.out.println("Please select Size ID. Example: 1");
		}
		System.out.println("What crust for this pizza?");
		System.out.println("1.) Thin \n2.) Original \n3.) Pan \n4.)Gluten-Free \nEnter the Crust Number: ");
		input = false;
		while(!input){
			tmp = reader.readLine();
			if(tmp.equals("1")){
				input = true;
				crust = "Thin";
			}
			else if(tmp.equals("2")){
				input = true;
				crust = "Original";
			}
			else if(tmp.equals("3")){
				input = true;
				crust = "Pan";
			}
			else if(tmp.equals("4")){
				input = true;
				crust = "Gluten-Free";
			}
			else System.out.println("Please select crust ID. Example: 1");
		}
		while(!(tmp.equals("-1"))){
			ViewInventoryLevels();
			System.out.println("Please select toppings by entering the Topping ID. Enter -1 when done.");
			tmp = reader.readLine();

			int t = Integer.parseInt(tmp) -1;
			if(!tmp.equals("-1")) {customerToppings.add(toppings.get(t));}
			}

			int u = 0;
			while(u == 0){
				System.out.println("Extra Toppings? Enter y/n");
				tmp = reader.readLine();

				if(tmp.equals("y")){
					u = 1;
				}
				else if(tmp.equals("n")){
					u = 1;
				}
				else System.out.println("Please enter y/n.");
			}
		custPrice = DBNinja.getBaseCustPrice(size, crust);
		busPrice = DBNinja.getBaseBusPrice(size, crust);
		for(int i = 0; i < customerToppings.size(); i++){
			custPrice += customerToppings.get(i).getCustPrice();
			busPrice += customerToppings.get(i).getBusPrice();
		}
		Pizza customerPizza = new Pizza(-1, size, crust, orderID, "WAIT", today, custPrice, busPrice);
		customerPizza.setToppings(customerToppings);
		return customerPizza;
	}

	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		DBNinja.printToppingPopReport();
		DBNinja.printProfitByOrderType();
		DBNinja.printProfitByPizzaReport();
	}
}
