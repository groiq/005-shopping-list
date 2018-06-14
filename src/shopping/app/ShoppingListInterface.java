package shopping.app;

import inout.In;
import inout.Out;
import shopping.list.ProductGroup;
import shopping.list.ShoppingList;
import shopping.list.Unit;

public class ShoppingListInterface {
	
	// Fields
	final static ShoppingList groceryList = new ShoppingList();
	// These fields will be used to look up nodes
	private static ProductGroup productGroup;
	private static ProductGroup[] productGroups;
	private static Unit unit;
	private static int quantity;
	private static int id;
	
	// Main method
	public static void main(String[] args) {
		// print a title
		Out.println("Shopping list App");
		separate();
		Out.println();
		// Generate test data from GroceryInit.txt
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
		// Print options
		printAvailable();
		// Enter interactive session
		interactionCycle();
	}

	// method for the main interaction cycle
	
	private static void interactionCycle() {
		// Interactive prompt
		Out.print("Enter one of (1,2,3,4,5,6,h,q): > ");
		char selection = In.readChar();
		// Parse input and perform action
		separate();
		if (selection != 'q') {
			switch (selection) {
			case '1':	// create a new item, prompting for parameters
				Out.println("adding a new item:");
				Out.println("------------------");
				Out.print("enter description: > ");
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
			case '3':	// print items selected by product group, unit and/or quantity
				Out.println("print item selection.");
				Out.println("Filter by product group, unit and/or quantity.");
				Out.println("----------------------------------------------");
				Out.println("Select one or mor product groups to filter by product group.");
				productGroups = selectMultipleProductGroups();
				unit = selectUnit(false);
				quantity = selectQuantity(false);
				Out.print("Selected product groups: ");
				for (ProductGroup productGroup : productGroups) {
					Out.print(productGroup + ". ");
				}
				Out.println("Unit: " + unit + ". Quantity: " + quantity + ".");
				printList(groceryList.getNodesBy(productGroups, unit, quantity));
				break;
			case '4':	// print a single item by ID
				Out.println("print a single item by ID:");
				Out.println("--------------------------");
				id = selectID();
				Out.println(groceryList.getNode(id));
				break;
			case '5':	// delete single
				Out.println("delete a single item by ID:");
				Out.println("--------------------------");
				id = selectID();
				groceryList.deleteNode(id);
				break;
			case '6':	// delete by group
				Out.println("delete all items in a specific product group:");
				Out.println("---------------------------------------------");
				Out.println("Select one or more product groups to delete items.");
				productGroups = selectMultipleProductGroups();
				// print number of deleted nodes
				int deletedNodes = groceryList.getNodesBy(productGroups, null, 0).length;
				Out.print("Selected product groups: ");
				for (ProductGroup productGroup : productGroups) {
					Out.print(productGroup + ". ");
				}
				Out.print("Deleting " + deletedNodes + " items...");
				groceryList.deleteByProductGroup(productGroups);
				Out.println(" done.");
				break;
			case 'h':	// print menu
				printAvailable();
				break;
			default:
				Out.print("Error: not a valid selection. ");
			}
			separate();
			interactionCycle();
		// if the user typed 'q'
		} else {
			Out.println("Exiting...");
		}
	}
	
	// Methods for prompting for parameters
	
	// Prompt for ID when looking up a single node
	private static int selectID() {
		Out.print("Enter id (as integer): > ");
		int id = In.readInt();
		return id;
	}

	// Prompt for a selection of product groups. This is used for reading and for deleting multiple nodes.
	private static ProductGroup[] selectMultipleProductGroups() {
		Out.println("For each product group, type 'y' to select or 'n' to deselect: ");
		// fetch list of all values of the ProductGroup enum
		ProductGroup[] pgValues = ProductGroup.values();
		// when looping through list, track list position, choice and number of chosen groups
		int listPos = 0;
		int counter = 0;
		char choice;
		// An interim list represents each group with a boolean to track whether it's selected.
		boolean[] selected = new boolean[pgValues.length];
		// loop through list
		while (listPos < pgValues.length) {
			// prompt for selection
			Out.print("Select this group? " + pgValues[listPos] + " > ");
			choice = In.readChar();
			if (choice == 'y') {
				counter += 1;
				selected[listPos] = true;
			} else if (choice == 'n') {
				selected[listPos] = false;
			} else {
				// if input invalid, go back in list and prompt again
				Out.println("No valid input. Please repeat.");
				listPos -= 1;
			}
			listPos += 1;
		}
		// create an array and fill it with all chosen groups
		ProductGroup[] result = new ProductGroup[counter];
		int j = 0;
		for (int i = 0; i < pgValues.length; i++) {
			if (selected[i] == true) {
				result[j] = pgValues[i];
				j += 1;
			}
		}
		return result;
	}
		
	// Prompt for a quantity. Obligatory when creating a new node, but optional as a selection criterium.
	private static int selectQuantity(boolean obligatory) {
		Out.print("Enter quantity (as integer)");
		if (obligatory == false) {
			Out.print(". Enter 0 to ignore quantity");
		}
		Out.print(": > ");
		int quantity = In.readInt();
		// if an invalid number is given, return 0 on a non-obligatory query
		if (quantity < 1) { quantity = 0; }
		// When creating a node, insist on a positive non-zero value
		while (obligatory == true && quantity < 1) {
			Out.println("Error: You cannot buy less than one product.");
			Out.print("Please enter a positive non-zero integer: > ");
			quantity = In.readInt();
		}
		return quantity;
	}

	// Prompt for unit of measurement. Obligatory when creating a new node, but optional as a selection criterium.
	private static Unit selectUnit(boolean obligatory) {
		Unit unit;
		Out.println("Available units of measurement:");
		// if optional, -1 denotes "none selected". (0 cannot denote "none" because this is an enum and 0 is a valid list index.)
		if (obligatory == false) {
			Out.println("-1: ignore unit");
		}
		// prompt for units by their ordinal number in the enum
		Unit[] unitValues = Unit.values();
		for (Unit value : unitValues) {
			Out.println(" " + value.ordinal() + ": " + value);
		}
		Out.print("Enter number of selected unit: > ");
		int unitNum = In.readInt();
		// if obligatory, repeat prompt until input is valid
		if (obligatory == true) {
			while (unitNum < 0 || unitNum >= unitValues.length) {
				Out.print("Error: Please enter a valid number: > ");
				unitNum = In.readInt();
			}
			unit = unitValues[unitNum];
		// if not obligatory, assign input if valid, otherwise null
		} else {
			if (unitNum > -1 && unitNum < unitValues.length) {
				unit = unitValues[unitNum];
			} else {
				unit = null;
			}
		}
		return unit;
	}

	// prompt for a single obligatory product group. Used for node creation.
	private static ProductGroup selectSingleProductGroup() {
		Out.println("Available product groups:");
		// fetch an array and print entries with their index numbers, using methods from the enum class
		ProductGroup[] pgValues = ProductGroup.values();
		for (ProductGroup value : pgValues) {
			Out.println(value.ordinal() + ": " + value);
		}
		// prompt for an index number
		Out.print("Enter number of selected product group: > ");
		int pgNum = In.readInt();
		// handle invalid numbers
		while (pgNum < 0 || pgNum >= pgValues.length) {
			Out.print("Error: please enter a valid number: > ");
			pgNum = In.readInt();
		}
		ProductGroup productGroup = pgValues[pgNum];
		return productGroup;
	}
	
	// other methods
	
	// print menu of available methods
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
	
	// Shorthand for printing item list
	private static void printList(String[] entries) {
		if (entries.length == 0) {
			Out.println("No items found.");
		} else {
			for (String entry : entries) {
				Out.println(entry);
			}
		}
	}
	
	// print a separator
	private static void separate() {
		Out.println("===========================================");
	}

}
