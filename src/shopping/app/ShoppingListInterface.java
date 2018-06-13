package shopping.app;

import inout.In;
import inout.Out;
import shopping.list.ProductGroup;
import shopping.list.ShoppingList;
import shopping.list.Unit;

public class ShoppingListInterface {
	
	final static ShoppingList groceryList = new ShoppingList();
	private static ProductGroup productGroup;
	private static ProductGroup[] productGroups;
	private static Unit unit;
	private static int quantity;
	
	
	public static void main(String[] args) {
		
		Out.println("Shopping list App");
		separate();
		Out.println();
		
		// shopping list object
//		groceryList = new ShoppingList();
		
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
		In.close();
		Out.println("done.");
		Out.println();
		
		// print options and read selection
		printAvailable();
		interactionCycle();


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
		Out.println("5: Delete an item (by ID)");
		Out.println("6: Delete all items of one product group");
		separate();
		Out.println("h: show this list");
		Out.println("q: quit");
		separate();
	}
	
	private static void interactionCycle() {
		Out.print("Enter one of (1,2,3,4,5,6,h,q): ");
		char selection = In.readChar();
//		char selection = parseInput();
		separate();
		if (selection != 'q') {
			switch (selection) {
			case '1':	// create a new item
				Out.println("adding a new item...");
				Out.println("--------------------");
				Out.print("enter description: ");
				String description = In.readWord();
				productGroup = selectSingleProductGroup();
				unit = selectUnit();
				quantity = selectQuantity();
				groceryList.newItem(description, unit, quantity, productGroup);
				Out.println("Created entry: " + quantity + " " + unit + " of " + description + " needed from " + productGroup + ".");
				break;
			case '2':	// print all
				Out.println("all items:");
				Out.println("----------");
				printList(groceryList.getAll());
				break;
			case '3':	// print selected
				Out.println("print selected items:");
				Out.println("---------------------");
				productGroups = selectMultipleProductGroups();
				unit = selectUnit();
				quantity = selectQuantity();
				printList(groceryList.getNodesBy(productGroups, unit, quantity));
				break;
			case '4':	// print single
				break;
			case '5':	// delete single 
				break;
			case '6':	// delete by group
				break;
			case 'h':	// print menu
				printAvailable();
				break;
			default:
				Out.print("Error: not a valid selection. ");
			}
			separate();
			interactionCycle();
		} else {
			Out.println("Exiting...");
		}
	}

	private static ProductGroup[] selectMultipleProductGroups() {
		Out.println("Available product groups:");
		ProductGroup[] pgValues = ProductGroup.values();
		boolean[] pgSelected = new boolean[pgValues.length];
		for (ProductGroup value : pgValues) {
			Out.println(value.ordinal() + ": " + value);
		}
		Out.println("selected: []");
		Out.print("To add or remove a product group, type its number. To finish selecting, press enter: ");
		int pgNum = In.readInt();
		while (In.done() && pgNum > -1 && pgNum < pgValues.length) {
			pgSelected[pgNum] = !pgSelected[pgNum];
			Out.print("currently selected: [");
			for (int i = 0; i < pgValues.length; i++) {
				if (pgSelected[i] == true) Out.print(pgValues[i] + ", ");
			}
			Out.println("]");
			Out.print("To add or remove a product group, type its number. To finish selecting, press enter: ");
			pgNum = In.readInt();
		}
		Out.println("done");
		
		
		

		return null;
	}

	private static int selectQuantity() {
		Out.print("Enter quantity (as integer): ");
		int quantity = In.readInt();
		return quantity;
	}

	private static Unit selectUnit() {
		Out.println("Available units:");
		Unit[] unitValues = Unit.values();
		for (Unit value : unitValues) {
			Out.println(value.ordinal() + ": " + value);
		}
		Out.print("Enter number of selected unit: ");
		int unitNum = In.readInt();
		Unit unit = unitValues[unitNum];
		return unit;
	}

	private static ProductGroup selectSingleProductGroup() {
		Out.println("Available product groups:");
		ProductGroup[] pgValues = ProductGroup.values();
		for (ProductGroup value : pgValues) {
			Out.println(value.ordinal() + ": " + value);
		}
		Out.print("Enter number of selected product group: ");
		int pgNum = In.readInt();
		ProductGroup productGroup = pgValues[pgNum];
		return productGroup;
	}

	private static char parseInput() {
		Out.println("Enter one of (1,2,3,4,5,h,q): ");
		char selection = In.readChar();
		return selection;
	}
	
	private static void printList(String[] entries) {
		if (entries.length == 0) {
			Out.println("No items found.");
		} else {
			for (String entry : entries) {
				Out.println(entry);
			}
		}
	}
	
}
