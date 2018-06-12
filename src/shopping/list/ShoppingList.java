package shopping.list;

import inout.Out;

public class ShoppingList {

//	--------------------------------------------------------------
//	Fields, constructor, getters, setters
//	--------------------------------------------------------------

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
	
//	--------------------------------------------------------------
//	Create a new node
//	--------------------------------------------------------------

	void newItem(String description, Unit unit, int quantity, ProductGroup productGroup) {
		ListItem newItem = new ListItem(description, unit, quantity, productGroup);
		counter += 1;
		ListNode newNode = new ListNode(counter, newItem);
		ListNode prevNode = dummyNode;
		while (prevNode.getNextNode() != null && newNode.getProductOrder() > prevNode.getNextNode().getProductOrder()) {
			prevNode = prevNode.getNextNode();
		}
		newNode.setNextNode(prevNode.getNextNode());
		prevNode.setNextNode(newNode);
	}
	
//	--------------------------------------------------------------
//	Read list of nodes
//	--------------------------------------------------------------
	
//	Generate Array of nodes
	
	private ListNode[] selectBy(ProductGroup[] productGroups, Unit unit, int quantity) {
		boolean[] interimList = new boolean[counter];
		int listPosition = 0;
		int resultCounter = 0;
		ListNode currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			boolean itemSelected = true;
			// if itemSelected isn't already false, check for product groups
			if (itemSelected == true && productGroups != null) {
				itemSelected = false;
				for (ProductGroup productGroup : productGroups) {
					if (currNode.getProductGroup() == productGroup) { itemSelected = true; }
				}
			}
			if (unit != null && currNode.getUnit() != unit) { itemSelected = false; }
			if (quantity != 0 && currNode.getQuantity() != quantity) { itemSelected = false; }
			interimList[listPosition] = itemSelected;
			if (itemSelected == true) { resultCounter += 1; }
			listPosition += 1;
		}
		ListNode[] result = new ListNode[resultCounter];
		listPosition = 0;
		resultCounter = 0;
		currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (interimList[listPosition] == true) {
				result[resultCounter] = currNode;
				resultCounter += 1;
			}
			listPosition += 1;
		}
		return result;
	}

//	fetch nodes as an array of strings
	
	public String[] getNodesBy(ProductGroup[] productGroups, Unit unit, int quantity) {
		ListNode[] resultObjects = selectBy(productGroups, unit, quantity);
		String[] resultStrings = new String[resultObjects.length];
		for (int i = 0; i < resultObjects.length; i++) {
			resultStrings[i] = resultObjects[i].toString();
		}
		return resultStrings;
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups) {
		return getNodesBy(productGroups, null, 0);
	}
	
	public String[] getNodesBy(Unit unit) {
		return getNodesBy(null, unit, 0);
	}
	
	public String[] getNodesBy(int quantity) {
		return getNodesBy(null, null, quantity);
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups, Unit unit) {
		return getNodesBy(productGroups, unit, 0);
	}
	
	public String[] getNodesBy(ProductGroup[] productGroups, int quantity) {
		return getNodesBy(productGroups, null, quantity);
	}
	
	public String[] getNodesBy(Unit unit, int quantity) {
		return getNodesBy(null, unit, quantity);
	}
	
	public String[] getNodesBy() {
		return getNodesBy(null, null, 0);
	}
	
	public String[] getAll() {
		return getNodesBy(null, null, 0);
	}
	
//	get a single node by id
	
	public String getNode(int id) {
		String result = "Error: Node not found.";
		ListNode currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (currNode.getId() == id) {
				result = currNode.toString();
				break;
			}
		}
		return result;
	}
	
//	--------------------------------------------------------------
//	delete
//	--------------------------------------------------------------
	
	public void deleteByProductGroup(ProductGroup[] productGroups) {
		ListNode currNode = dummyNode;
		ListNode prevNode = dummyNode;
		boolean toDelete = false;
		while (currNode.getNextNode() != null) {
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
	

}
