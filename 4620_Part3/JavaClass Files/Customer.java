package CPSC4620.part3;


/**
 * Manning Graham
 * CPSC 4620
 * Project Part 3
 * 12-01-2022
 */

public class Customer 
{
	private int CustID;
	private String Name;
	private String Phone;
	private String Address;
	
	
	public Customer(int custID, String name, String phone) {
		CustID = custID;
		Name = name;
		Phone = phone;
	}
	public Customer(int custID, String address, String name, String phone) {
		CustID = custID;
		Address = address;
		Name = name;
		Phone = phone;
	}

	public int getCustID() { return CustID;}
	public String getName() { return Name; }
	public String getPhone() {
		return Phone;
	}
	public String getAddress(){return Address;}

	public void setCustID(int custID) {
		CustID = custID;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}
	public void setName(String name) { Name = name; }
	public void setAddress(String address) { Address = address;}

	@Override
	public String toString() {
		return "CustID = " + CustID + " | Name = " + Name + ", Phone = " + Phone;
	}

}
