package CPSC4620.part3;

/**
 * Manning Graham
 * CPSC 4620
 * Project Part 3
 * 12-01-2022
 */


public class PickupOrder extends Order
{

	private int isPickedUp;
	
	public PickupOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isPickedUp, int isComplete) {
		super(orderID, custID, date, busPrice, custPrice, DBNinja.pickup, isComplete);
		this.isPickedUp = isPickedUp;
	}

	public int getIsPickedUp() {
		return isPickedUp;
	}

	public void setIsPickedUp(int isPickedUp) {
		this.isPickedUp = isPickedUp;
	}
	
	@Override
	public String toString() {
		return super.toString() + " | is the order picked up? (yes=1): " + isPickedUp;
	}
}
