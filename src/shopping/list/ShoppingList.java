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
	
	private String[] getNodesBy(ProductGroup[] productGroups, Unit unit, int quantity, int id) {
		boolean[] interimList = new boolean[counter];
		int listPosition = 0;
		int resultCounter = 0;
		ListNode currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			// insert check for conditions here
			boolean itemSelected = true;
			// !!! This might run into trouble after deleting some nodes!
//			if (productGroup != null && currNode.getProductGroup() != productGroup) { itemSelected = false; }
			if (productGroups != null) {
				itemSelected = false;
				for (ProductGroup productGroup : productGroups) {
					if (currNode.getProductGroup() == productGroup) { itemSelected = true; }
				}
			}
			// If I have another parameter before this, I'll need a return statement, otherwise itemSelected could be already false 
			// and then be set to true by this piece of code.
			if (unit != null && currNode.getUnit() != unit) { itemSelected = false; }
			if (quantity != 0 && currNode.getQuantity() != quantity) { itemSelected = false; }
			if (id != 0 && currNode.getId() != id) { itemSelected = false; }
			interimList[listPosition] = itemSelected;
			if (itemSelected == true) { resultCounter += 1; }
			// end of check
//			Out.println(interimList[listPosition] + ": " + currNode.getItem());
			listPosition += 1;
		}
		while (listPosition < counter) {	// maybe necessary after deleting nodes. Check.
			interimList[listPosition] = false;
			listPosition += 1;
		}
//		Out.println(interimList.toString());
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
		return getNodesBy(null, null, 0, 0);
		
	}
	
	public String[] getByProductGroup(ProductGroup[] productGroups) {
		return getNodesBy(productGroups, null, 0, 0);
	}
	
	public String[] getByUnit(Unit unit) {
		return getNodesBy(null, unit, 0, 0);
	}
	
	public String[] getByQuantity(int quantity) {
		return getNodesBy(null, null, quantity, 0);
	}
	
	public String[] getByID(int id) {
		return getNodesBy(null, null, 0, id);
	}
	
//	Delete

}
