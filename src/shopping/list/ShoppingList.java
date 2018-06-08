package shopping.list;

import inout.Out;

public class ShoppingList {
	
	private ListNode firstNode;

	public ShoppingList() {
		this.firstNode = new ListNode(1, null);
	}

	void newItem(String description, Unit unit, int defaultQuantity, ProductGroup productGroup) {
		ListItem item = new ListItem(description, unit, defaultQuantity, productGroup);
		Out.println(item.getStats());
		Out.println(item);
		// TODO put item into list
		
		 
		
	}

}
