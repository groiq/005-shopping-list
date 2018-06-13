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
	private static int id;
	
	
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
		Out.println("4: Print a single item (by ID)");
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
				unit = selectUnit(true);
				quantity = selectQuantity(true);
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
				unit = selectUnit(false);
				quantity = selectQuantity(false);
				printList(groceryList.getNodesBy(productGroups, unit, quantity));
				break;
			case '4':	// print single
				Out.println("print a single item by ID");
				Out.println("-------------------------");
				id = selectID();
				Out.println(groceryList.getNode(id));
				break;
			case '5':	// delete single
				Out.println("delete a single item by ID");
				Out.println("--------------------------");
				id = selectID();
				groceryList.deleteNode(id);
				break;
			case '6':	// delete by group
				Out.println("delete all items in a specific product group");
				Out.println("--------------------------------------------");
				productGroups = selectMultipleProductGroups();
				groceryList.deleteByProductGroup(productGroups);
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
	
	private static int selectID() {
		Out.print("Enter id (as integer):");
		int id = In.readInt();
		return id;
	}

	private static ProductGroup[] selectMultipleProductGroups() {
		Out.println("If you select one or more product groups, all nodes with any of those groups are found.");
		Out.println("If you select no product groups, nodes of all product groups will be found.");
		Out.println("For each product group, type 'y' to select or 'n' to deselect: ");
		ProductGroup[] pgValues = ProductGroup.values();
		int listPos = 0;
		int counter = 0;
		char choice;
		boolean[] selected = new boolean[pgValues.length];
		while (listPos < pgValues.length) {
			Out.print("Select this group? " + pgValues[listPos] + " > ");
			choice = In.readChar();
			if (choice == 'y') {
//				Out.println("Selected.");
				counter += 1;
				selected[listPos] = true;
			} else if (choice == 'n') {
//				Out.println("Deselected.");
				selected[listPos] = false;
			} else {
				Out.println("No valid input. Please repeat.");
				listPos -= 1;
			}
			listPos += 1;
		}
		ProductGroup[] result;
		if (counter > 0) {
			result = new ProductGroup[counter];
			int j = 0;
			for (int i = 0; i < pgValues.length; i++) {
				if (selected[i] == true) {
					result[j] = pgValues[i];
					j += 1;
				}
			}
			Out.println("Your selection:");
			for (ProductGroup pg : result) {
				Out.println(pg);
			}
		} else {
			result = null;
		}
		return result;
	}
		
	private static int selectQuantity(boolean obligatory) {
		Out.print("Enter quantity (as integer)");
		if (obligatory == false) {
			Out.print(". Enter 0 to ignore quantity");
		}
		Out.print(": ");
		int quantity = In.readInt();
		return quantity;
	}

	private static Unit selectUnit(boolean obligatory) {
		Out.println("Available units:");
		if (obligatory == false) {
			Out.println("-1: ignore unit");
		}
		Unit[] unitValues = Unit.values();
		for (Unit value : unitValues) {
			Out.println(" " + value.ordinal() + ": " + value);
		}
		Out.print("Enter number of selected unit: ");
		int unitNum = In.readInt();
		Unit unit;
		if (unitNum > -1 && unitNum < unitValues.length) {
			unit = unitValues[unitNum];
		} else {
			unit = null;
		}
//		Unit unit = unitValues[unitNum];
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
