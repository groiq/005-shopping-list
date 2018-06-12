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
	
	private ListNode[] selectBy(ProductGroup[] productGroups, Unit unit, int quantity, int id) {
		boolean[] interimList = new boolean[counter];
		int listPosition = 0;
		int resultCounter = 0;
		ListNode currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
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
		ListNode[] result = new ListNode[resultCounter];
		listPosition = 0;
		resultCounter = 0;
		currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (interimList[listPosition] == true) {
				result[resultCounter] = currNode;	// todo: make a short description
				resultCounter += 1;
			}
			listPosition += 1;
		}
		return result;
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups, Unit unit, int quantity, int id) {
		ListNode[] resultObjects = selectBy(productGroups, unit, quantity, id);
		String[] resultStrings = new String[resultObjects.length];
		for (int i = 0; i < resultObjects.length; i++) {
			resultStrings[i] = resultObjects[i].toString();
		}
		return resultStrings;
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups) {
		return getNodesBy(productGroups, null, 0, 0);
	}
	
	public String[] getNodesBy(Unit unit) {
		return getNodesBy(null, unit, 0, 0);
	}
	
	public String[] getNodesBy(int quantity) {
		return getNodesBy(null, null, quantity, 0);
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups, Unit unit) {
		return getNodesBy(productGroups, unit, 0, 0);
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups, int quantity) {
		return getNodesBy(productGroups, null, quantity, 0);
	}
	
	public String[] getNodesBy(Unit unit, int quantity) {
		return getNodesBy(null, unit, quantity, 0);
	}
	
	public String[] getNodesBy() {
		return getNodesBy(null, null, 0, 0);
	}
	
	public String[] getAll() {
		return getNodesBy(null, null, 0, 0);
	}
	
	public String getNode(int id) {
		ListNode[] result = selectBy(null, null, 0, id);
		if (result.length > 0) {
			return result[0].toString();
		} else {
			return "Error: Node not found.";
		}
	}
	
	public void deleteByProductGroup(ProductGroup[] productGroups) {
		ListNode[] deletionList = selectBy(productGroups, null, 0, 0);
		int listPosition = 0;
		ListNode currNode = dummyNode;
		ListNode prevNode = dummyNode;
		boolean toDelete = false;
		while (currNode.getNextNode() != null) {
//			prevNode = currNode;
			toDelete = false;
			currNode = currNode.getNextNode();
			for(ProductGroup productGroup : productGroups) {
				if (currNode.getProductGroup() == productGroup) {
					toDelete = true;
					break;
				}
			}
			if (toDelete == true) {
				prevNode.setNextNode(currNode.getNextNode());
			} else {
				prevNode = currNode;
			}
		}
	}
	
	public void deleteNode(int id) {
		ListNode prevNode = dummyNode;
		ListNode currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (currNode.getId() == id) {
				prevNode.setNextNode(currNode.getNextNode());
				return;
			}
			prevNode = currNode;
		}
		if (currNode.getNextNode() == null) {
			Out.println("Deleting node #" + id + ": Node not found.");
		}
	}
	
//	private ListNode[] getNodesBy() {
//		return getNodesBy(null, null, 0, 0);
//	}
//	
//	private ListNode[] getNodesBy(ProductGroup[] productGroups) {
//		return getNodesBy(productGroups, null, 0, 0);
//	}
//	
//	private ListNode[] getNodesBy(Unit unit) {
//		return getNodesBy(null, unit, 0, 0);
//	}
//	
//	private ListNode[] getNodesBy(int quantity) {
//		return getNodesBy(null, null, quantity, 0);
//	}
//	
//	private ListNode[] getNodesBy(ProductGroup[] productGroups, Unit unit) {
//		return getNodesBy(productGroups, unit, 0, 0);
//	}
//	
//	private ListNode[] getNodesBy(ProductGroup[] productGroups, int quantity) {
//		return getNodesBy(productGroups, null, quantity, 0);
//	}
//	
//	private ListNode[] getNodesBy(Unit unit, int quantity) {
//		return getNodesBy(null, unit, quantity, 0);
//	}
//	
//	private ListNode[] getNodesBy(ProductGroup[] productGroups, Unit unit, int quantity) {
//		return getNodesBy(productGroups, unit, quantity, 0);
//	}
		
	
	
//	Delete

}
