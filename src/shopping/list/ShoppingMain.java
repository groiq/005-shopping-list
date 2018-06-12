package shopping.list;

import inout.In;
import inout.Out;
import static shopping.list.ProductGroup.*;
import static shopping.list.Unit.*;

public class ShoppingMain {
	
	// list object for testing
	static ShoppingList groceryList;
	
	// Shorthand for retrieving and printing a list of nodes
	private static void printList(String[] entries, String comment) {
		Out.println(comment);
		if (entries.length == 0) {
			Out.println("No entries found.");
		} else {
			for (String entry : entries) {
				Out.println(entry);
			}
		}
		Out.println();
	}
	
	// Shorthand for printing all nodes
	private static void getAll() {
		printList(groceryList.getAll(), "Showing all entries:");
	}

	public static void main(String[] args) {
		
		groceryList = new ShoppingList();
		
		// Read test data from GroceryInit.txt
		In.open("GroceryInit.txt");
		if (!In.done()) {
			Out.println("Cannot open file GroceryInit.txt"); 
			return; 
		}
		Out.println("Creating nodes...");
		String descr = In.readWord();
		while (In.done()) {
			ProductGroup pg = ProductGroup.valueOf(In.readWord());
			Unit unit = Unit.valueOf(In.readWord());
			int quantity = In.readInt();
			// Comment out the next line to test an empty list
			groceryList.newItem(descr, unit, quantity, pg);
			descr = In.readWord();			
		}
		getAll();
		
		// test reading nodes
		ProductGroup[] singleProductGroup = {aaa};
		ProductGroup[] twoProductGroups = {bbb,ccc};
		printList(groceryList.getNodesBy(singleProductGroup),"Showing group aaa:");
		printList(groceryList.getNodesBy(twoProductGroups),"Showing two product groups:");
		printList(groceryList.getNodesBy(packs),"Showing all packs:");
		printList(groceryList.getNodesBy(4),"Showing by quantity 4:");
		printList(groceryList.getNodesBy(twoProductGroups, kilos), "kilos from two groups:");
		printList(groceryList.getNodesBy(twoProductGroups, 4), "quantity 4 from two groups:");
		printList(groceryList.getNodesBy(liters, 4), "liters with quantity 4:");
		Out.println("Showing a single node:");
		Out.println(groceryList.getNode(6));
		Out.println(groceryList.getNode(3));
		Out.println();
		Out.println("a non-existent node:");
		Out.println(groceryList.getNode(100));
		Out.println();
		
		
		// Delete nodes
		Out.println("Deleting node #3...");
		groceryList.deleteNode(3);
		getAll();
		Out.println("deleting a non-existent node...");
		groceryList.deleteNode(100);
		getAll();
		Out.println("Deleting two entire product groups...");
		groceryList.deleteByProductGroup(twoProductGroups);
		getAll();

	}

}
