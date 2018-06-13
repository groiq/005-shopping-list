package shopping.app;

import inout.In;
import inout.Out;
import shopping.list.ProductGroup;
import shopping.list.ShoppingList;
import shopping.list.Unit;

public class ShoppingListInterface {
	
	public static void main(String[] args) {
		
		Out.println("Shopping list App");
		separate();
		Out.println();
		
		// shopping list object
		final ShoppingList groceryList = new ShoppingList();
		
		// Read test data from GroceryInit.txt
		In.open("GroceryInit.txt");
		if (!In.done()) {
			Out.println("Cannot open file GroceryInit.txt"); 
			return; 
		}
		Out.print("Creating nodes...");
		String descr = In.readWord();
		while (In.done()) {
			ProductGroup pg = ProductGroup.valueOf(In.readWord());
			Unit unit = Unit.valueOf(In.readWord());
			int quantity = In.readInt();
			// Comment out the next line to test an empty list
			groceryList.newItem(descr, unit, quantity, pg);
			descr = In.readWord();			
		}
		Out.println("done.");
		Out.println();
		
		// print options and read selection
		printAvailable();
		selectionCycle();


	}


	private static void separate() {
		Out.println("===========================================");
	}

	private static void printAvailable() {
		Out.println("Available options:");
		separate();
		Out.println("1: Create a new item");
		separate();
		Out.println("2: Print all items");
		Out.println("3: Print a selection of items");
		Out.println("4: Print a single item (by ID");
		separate();
		Out.println("4: Delete an item (by ID)");
		Out.println("5: Delete all items of one product group");
		separate();
		Out.println("h: show this list");
		Out.println("q: quit");
	}
	
	private static void selectionCycle() {
		
	}
	
}
