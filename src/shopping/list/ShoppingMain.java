package shopping.list;

import inout.In;
import inout.Out;

public class ShoppingMain {
	
	static ShoppingList groceryList;
	
	private static void printList(String[] entries, String comment) {
		Out.println(comment);
		for (String entry : entries) {
			Out.println(entry);
		}
		Out.println();
	}
	
	private static void getAll() {
		printList(groceryList.getAll(), "Showing all entries:");
	}

	public static void main(String[] args) {
		
		groceryList = new ShoppingList();
		
		In.open("GroceryInit.txt");
		if (!In.done()) {
			Out.println("Cannot open file GroceryInit.txt"); 
			return; 
		}
		Out.println("Creating nodes...");
		String descr = In.readWord();
		while (In.done()) {
//			String pgs = In.readWord();
//			ProductGroup pg = ProductGroup.valueOf(pgs);
			ProductGroup pg = ProductGroup.valueOf(In.readWord());
			Unit unit = Unit.valueOf(In.readWord());
			int defaultQuantity = In.readInt();
//			Out.print("Created: ");
			// comment this out to test for empty list
			groceryList.newItem(descr, unit, defaultQuantity, pg);
			descr = In.readWord();
			
		}
		
		
		getAll();
		
		
		// REad
		
		// Update
		
		// Delete
		
		
		

		

	}

}
