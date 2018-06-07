package shopping.list;

import inout.Out;

public class ShoppingList {

	public ShoppingList() {
		// TODO Auto-generated constructor stub
	}

	static void newItem(String description, Unit unit, int defaultQuantity, ProductGroup productGroup) {
		ListItem item = new ListItem(description, unit, defaultQuantity, productGroup);
		Out.println(item.getStats());
		// TODO put item into list
		
		 
		
	}

}
