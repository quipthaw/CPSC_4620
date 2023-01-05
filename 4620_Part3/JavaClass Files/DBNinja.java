package CPSC4620.part3;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Manning Graham
 * CPSC 4620
 * Project Part 3
 * 12-01-2022
 */


/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery,
	// and sizes and
	// crusts
	public final static String pickup = "pickup";
	public final static String delivery = "delivery";
	public final static String dine_in = "dinein";

	public final static String size_s = "small";
	public final static String size_m = "medium";
	public final static String size_l = "large";
	public final static String size_xl = "x-large";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";

	/**
	 * This function will handle the connection to the database
	 * 
	 * @return true if the connection was successfully made
	 * @throws SQLException
	 * @throws IOException
	 */
	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}

	/**
	 *
	 * @param o order that needs to be saved to the database
	 * @throws SQLException
	 * @throws IOException
	 * @requires o is not NULL. o's ID is -1, as it has not been assigned yet. The
	 *           pizzas do not exist in the database yet, and the topping inventory
	 *           will allow for these pizzas to be made
	 * @ensures o will be assigned an id and added to the database, along with all
	 *          of it's pizzas. Inventory levels will be updated appropriately
	 */
	public static void addOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		String q = "insert into orders(Order_ID, Order_Cust_ID, Order_DateTime, Order_Cost, Order_Price, Order_Type) " + "values(?, ?, now(), ?, ?, ?);";
		PreparedStatement pStmt = conn.prepareStatement(q);
		pStmt.setInt(1, o.getOrderID());
		pStmt.setInt(2, o.getCustID());
		pStmt.setDouble(3, o.getBusPrice());
		pStmt.setDouble(4, o.getCustPrice());
		pStmt.setString(5, o.getOrderType());
		pStmt.executeUpdate();
		conn.close();
	}

	public static void addPizza(Pizza p) throws SQLException, IOException {
		connect_to_db();
		String query = "insert into pizza(Pizza_ID, Pizza_Order_ID, Pizza_Crust_Type, Pizza_Size, Pizza_Cust_Price, Pizza_Bus_Cost,Pizza_State) " +
				"values(?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setInt(1,  p.getPizzaID());
		pStmt.setInt(2, p.getOrderID());
		pStmt.setString(3, p.getCrustType());
		pStmt.setString(4, p.getSize());
		pStmt.setDouble(5, p.getCustPrice());
		pStmt.setDouble(6, p.getBusPrice());
		pStmt.setString(7, "DONE");
		pStmt.executeUpdate();
		conn.close();
	}

	public static int getMaxPizzaID() throws SQLException, IOException
	{
		connect_to_db();
		String ret = "";
		String query = "select max(Pizza_ID) from pizza;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()) { ret = rset.getString(1); }
		conn.close();
		if (ret == null) return 0;
		return Integer.parseInt(ret);
	}

	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException
	//this function will update toppings inventory in SQL and add entities to the Pizza table. Pass in the p pizza that is using t topping
	{
		connect_to_db();
		double tnum = t.getPerAMT();
		if(p.getSize().equals("medium")) tnum = t.getMedAMT();
		if(p.getSize().equals("large")) tnum = t.getLgAMT();
		if(p.getSize().equals("x-large")) tnum = t.getXLAMT();
		String query = "insert into pizza_topping values (?, ?, ?);";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setInt(1, p.getPizzaID());
		pStmt.setString(2, t.getTopName());
		pStmt.setInt(3, 0);

		pStmt.executeUpdate();

		query = "update topping set Topping_Cur_Inventory = Topping_Cur_Inventory - ? where Topping_Name = ?;";
		pStmt = conn.prepareStatement(query);
		double x = tnum;
		if(isDoubled == true) { x = tnum * 2; }
		pStmt.setDouble(1,  x);
		pStmt.setString(2,  t.getTopName());
		pStmt.executeUpdate();
		conn.close();
	}

	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		String query = "insert into pizza_discount values (?, ?);";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setInt(1, d.getDiscountID());
		pStmt.setInt(2, p.getPizzaID());
		pStmt.executeUpdate();
		conn.close();
	}

	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		String query = "insert into order_discount values (?, ?);";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setInt(2, o.getOrderID());
		pStmt.setInt(1, d.getDiscountID());
		pStmt.executeUpdate();
		conn.close();
		return;
		}

	public static void addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		String query = "INSERT INTO customer VALUES (?, ?, ?, ?);";
		String query2 = "SELECT MAX(Customer_ID) FROM customer;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		PreparedStatement pStmt2 = conn.prepareStatement(query2);

		ResultSet rset2 = pStmt2.executeQuery();
		rset2.next();
		c.setCustID(rset2.getInt(1)+1);

		pStmt.setInt(1,c.getCustID());
		pStmt.setString(2,c.getAddress());
		pStmt.setString(3, c.getName());
		pStmt.setString(4, c.getPhone());
		pStmt.executeUpdate();
		pStmt.close();
		conn.close();
	}
	
	public static void CompleteOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		String query = "UPDATE pizza SET Pizza_State = 'DONE' WHERE Pizza_Order_ID = ?;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setInt(1, o.getOrderID());
		pStmt.executeUpdate();
		pStmt.close();
		conn.close();
	}


	public static void AddToInventory(Topping t, double toAdd) throws SQLException, IOException {
		connect_to_db();
		String query = "update topping set Topping_Cur_Inventory = Topping_Cur_Inventory + ? where Topping_Name = ?;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setDouble(1, toAdd);
		pStmt.setString(2, t.getTopName());
		pStmt.executeUpdate();
		conn.close();
	}

	public static void printInventory() throws SQLException, IOException {
		connect_to_db();
		ArrayList<Topping> inv = new ArrayList<Topping>();
		String query = "SELECT Topping_Name, Topping_Cur_Inventory FROM topping ORDER BY Topping_Name;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();
		System.out.print(rset);
		conn.close();
	}

	public static void printCustomers() throws SQLException, IOException {
		connect_to_db();
		System.out.println("");
		String query = "SELECT Customer_Name, Customer_ID FROM customer ORDER BY Customer_ID;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rset.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(",  ");
				String columnValue = rset.getString(i);
				System.out.print(rsmd.getColumnName(i)+ " : " +   columnValue);
			}
			System.out.println("");
		}
		System.out.println("");
		pStmt.close();
		conn.close();
	}

	public static ArrayList<Topping> getInventory() throws SQLException, IOException {
		connect_to_db();
		ArrayList<Topping> inv = new ArrayList<Topping>();
		String query = "SELECT * FROM topping ORDER BY Topping_Name;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();
		while(rset.next()){
			Topping top = new Topping(rset.getString(1),
					rset.getDouble(2),
					rset.getDouble(3),
					rset.getInt(4),
					rset.getInt(5),
					rset.getDouble(6),
					rset.getDouble(7),
					rset.getInt(8),
					rset.getInt(9));
			inv.add(top);
		}
		pStmt.close();
		conn.close();
		return inv;
	}

	public static ArrayList<Order> getCurrentOrders() throws SQLException, IOException {
		connect_to_db();
		ArrayList<Order> os = new ArrayList<Order>();
		String query = "SELECT * FROM orders ORDER BY Order_DateTime ASC;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();

		while(rset.next()){
			Order listOrder = new Order(rset.getInt(1),
					rset.getInt(2),
					rset.getString(3),
					rset.getDouble(4),
					rset.getDouble(5),
					rset.getString(6),
					1);
			os.add(listOrder);
		}
		pStmt.close();
		conn.close();
		return os;
	}

	public static ArrayList<Order> sortOrders(ArrayList<Order> list)
	{
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
		
	}

	public static boolean checkDate(int year, int month, int day, String dateOfOrder)
	{
		//Helper function I used to help sort my dates. You likely wont need these
		return false;
	}

	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}
	
	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		String query = "SELECT Pizza_Lookup_Base_Price FROM pizza_lookup WHERE Pizza_Lookup_Size = ? AND Pizza_Lookup_Crust_Type = ?;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setString(1, size);
		pStmt.setString(2, crust);
		ResultSet rset = pStmt.executeQuery();
		rset.next();
		bp = rset.getDouble(1);
		pStmt.close();
		conn.close();
		return bp;
	}

	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
		connect_to_db();
		String foo = "";
		String query = "Select Customer_Name From customer WHERE Customer_ID=" + CustID + ";";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			foo = rset.getString(1);
		}
		conn.close();
		return foo;
	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		String query = "SELECT Pizza_Lookup_Base_Cost FROM pizza_lookup WHERE Pizza_Lookup_Size = ? AND Pizza_Lookup_Crust_Type = ?;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		pStmt.setString(1, size);
		pStmt.setString(2, crust);
		ResultSet rset = pStmt.executeQuery();
		rset.next();
		bp = rset.getDouble(1);
		pStmt.close();
		conn.close();
		return bp;
	}

	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		ArrayList<Discount> discs = new ArrayList<Discount>();
		connect_to_db();
		String query = "SELECT * FROM discount;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()){
			if(rset.getInt(4) == 0){
				discs.add(new Discount(rset.getInt(1), rset.getString(2), rset.getDouble(3), false));
			}else discs.add(new Discount(rset.getInt(1), rset.getString(2), rset.getDouble(4), true));
		}
		conn.close();
		return discs;
	}

	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
		ArrayList<Customer> custs = new ArrayList<Customer>();
		connect_to_db();
		String query = "SELECT * FROM customer ORDER BY Customer_Name;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			custs.add(new Customer(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4)));
		}
		conn.close();
		return custs;
	}

	public static int getNextOrderID() throws SQLException, IOException
	{
		connect_to_db();
		String s = "";
		String query = "select max(Order_ID) from orders;";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		while(r.next()) { s = r.getString(1); }
		conn.close();
		if (s == null) return 0;
		return Integer.parseInt(s);
	}

	public static void printToppingPopReport() throws SQLException, IOException
	{
		connect_to_db();
		System.out.println(" - Topping Popularity - ");
		String query = "SELECT * FROM ToppingPopularity;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rset.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(",  ");
				String columnValue = rset.getString(i);
				System.out.print(rsmd.getColumnName(i)+ " : " +   columnValue);
			}
			System.out.println("");
		}
		System.out.println("\n");
		pStmt.close();
		conn.close();
	}

	public static void printProfitByPizzaReport() throws SQLException, IOException
	{
		connect_to_db();
		System.out.println(" - Profit By Pizza - ");
		String query = "SELECT * FROM ProfitByPizza;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rset.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(",  ");
				String columnValue = rset.getString(i);
				System.out.print(rsmd.getColumnName(i)+ " : " +   columnValue);
			}
			System.out.println("");
		}
		System.out.println("\n");
		pStmt.close();
		conn.close();
	}

	public static void printProfitByOrderType() throws SQLException, IOException
	{
		connect_to_db();
		System.out.println(" - Profit By Order Type - ");
		String query = "SELECT * FROM ProfitByOrderType;";
		PreparedStatement pStmt = conn.prepareStatement(query);
		ResultSet rset = pStmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rset.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(",  ");
				String columnValue = rset.getString(i);
				System.out.print(rsmd.getColumnName(i)+ " : " +   columnValue);
			}
			System.out.println("");
		}
		System.out.println("\n");
		pStmt.close();
		conn.close();
	}
}
