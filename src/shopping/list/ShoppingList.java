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

	void newItem(String description, Unit unit, int quantity, ProductGroup productGroup) {
		ListItem newItem = new ListItem(description, unit, quantity, productGroup);
//		Out.println(item);
		counter += 1;
		ListNode newNode = new ListNode(counter, newItem);
		ListNode prevNode = dummyNode;
//		Out.print(newNode.getQuantity() + ", " + newItem.getQuantity());
		while (prevNode.getNextNode() != null && newNode.getProductOrder() > prevNode.getNextNode().getProductOrder()) {
			prevNode = prevNode.getNextNode();
		}
		newNode.setNextNode(prevNode.getNextNode());
		prevNode.setNextNode(newNode);
	}
	
//	Read
	
	private String[] getNodesBy(ProductGroup productGroup, Unit unit, int quantity, int id) {
		boolean[] interimList = new boolean[counter];
		int listPosition = 0;
		int resultCounter = 0;
		ListNode currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			// insert check for conditions here
			interimList[listPosition] = true;
			resultCounter += 1;
			// end of check
			listPosition += 1;
		}
		String[] result = new String[resultCounter];
		listPosition = 0;
		resultCounter = 0;
		currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (interimList[listPosition] == true) {
				result[resultCounter] = currNode.getItem().toString();	// todo: make a short description
				resultCounter += 1;
			}
			listPosition += 1;
		}
		return result;
	}

	public String[] getAll() {
		return getNodesBy(null, null, -1, 0);
		
	}
	
//	Delete

}
