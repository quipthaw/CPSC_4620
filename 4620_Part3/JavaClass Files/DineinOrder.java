package CPSC4620.part3;

/**
 * Manning Graham
 * CPSC 4620
 * Project Part 3
 * 12-01-2022
 */


public class DineinOrder extends Order{

	private int TableNum;
	
	public DineinOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isComplete, int tablenum) {
		super(orderID, custID, date, busPrice, custPrice, DBNinja.dine_in, isComplete);
		this.TableNum = tablenum;
	}

	public int getTableNum() {
		return TableNum;
	}

	public void setTableNum(int tableNum) {
		TableNum = tableNum;
	}
	
	@Override
	public String toString() {
		return super.toString() + " | Customer was sat at table number " + TableNum;
	}
}
