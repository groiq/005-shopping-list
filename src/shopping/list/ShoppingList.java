package shopping.list;

import inout.Out;

public class ShoppingList {
	
//	Fields, constructor, getters, setters
//	-------------------------------------
	
	private final ListNode dummyNode;
	private int counter;

	public ShoppingList() {
		this.dummyNode = new ListNode(0, null);
		counter = 0;
	}

	int getCounter() {
		return counter;
	}

	void setCounter(int counter) {
		this.counter = counter;
	}

	ListNode getDummyNode() {
		return dummyNode;
	}
	
//	Create
//	-----

	void newItem(String description, Unit unit, int defaultQuantity, ProductGroup productGroup) {
		ListItem newItem = new ListItem(description, unit, defaultQuantity, productGroup);
		Out.println(newItem.getStats());
//		Out.println(item);
		counter += 1;
		ListNode newNode = new ListNode(counter, newItem);
		ListNode prevNode = dummyNode;
		Out.print(newNode.getQuantity() + ", " + newItem.getQuantity());
		while (newNode.getProductOrder() > prevNode.getProductOrder() && prevNode.getNextNode() != null) {
//		while (item.getProductGroup().compareTo(currNode.getProductGroup()) > 0) {
			prevNode = prevNode.getNextNode();
		}
		newNode.setNextNode(prevNode.getNextNode());
		prevNode.setNextNode(newNode);
			

		// TODO put item into list
		
		 
		
	}
	
//	Read
	
//	Update
	
//	Delete

}
