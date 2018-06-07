package shopping.list;

import inout.Out;

public class ShoppingList {

	public ShoppingList() {
		// TODO Auto-generated constructor stub
	}

	static void newItem(String description, Unit unit, int default_quantity, ProductGroup productGroup) {
		// TODO create a new item
		ListItem item = new ListItem(description, unit, default_quantity, productGroup);
		Out.println(item);
		// TODO put item into list
		
		 
		
	}

}
