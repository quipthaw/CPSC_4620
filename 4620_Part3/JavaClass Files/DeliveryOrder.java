package CPSC4620.part3;

/**
 * Manning Graham
 * CPSC 4620
 * Project Part 3
 * 12-01-2022
 */


public class DeliveryOrder extends Order
{
	
	private String Address;
	
	public DeliveryOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isComplete, String address) 
	{
		super(orderID, custID, date, busPrice, custPrice, DBNinja.delivery, isComplete);
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String toString() {
		return super.toString() + " | Delivered to: " + Address;
	}
	
	
}
