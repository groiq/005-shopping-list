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
		// create the listItem object
		ListItem newItem = new ListItem(description, unit, quantity, productGroup);
		// create listNode object
		counter += 1;
		ListNode newNode = new ListNode(counter, newItem);
		// integrate new node into list
		ListNode prevNode = dummyNode;
		// Nodes are sorted by product group, so loop past all nodes that come "before" the new one
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
		// make an interim array that represents each node with a boolean.
		// This is to check whether or not a node is selected for output.
		boolean[] interimList = new boolean[counter];
		int listPosition = 0;
		int selectedNodesCounter = 0;
		ListNode currNode = dummyNode;
		// loop through list, marking nodes as selected or unselected in the interim array.
		// Selection criteria are ignored if set to null / 0.
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
			// check for the other criteria
			if (unit != null && currNode.getUnit() != unit) { itemSelected = false; }
			if (quantity != 0 && currNode.getQuantity() != quantity) { itemSelected = false; }
			// mark node and count selected nodes
			interimList[listPosition] = itemSelected;
			if (itemSelected == true) { selectedNodesCounter += 1; }
			listPosition += 1;
		}
		// make a new array with the size of the selected nodes. This array will be returned.
		ListNode[] result = new ListNode[selectedNodesCounter];
		// Loop through the linked list. If a node is marked in the boolean[], enter it into the result array.
		listPosition = 0;
		selectedNodesCounter = 0;
		currNode = dummyNode;
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (interimList[listPosition] == true) {
				result[selectedNodesCounter] = currNode;
				selectedNodesCounter += 1;
			}
			listPosition += 1;
		}
		return result;
	}

//	fetch nodes as an array of strings
	
	public String[] getNodesBy(ProductGroup[] productGroups, Unit unit, int quantity) {
		// fetch an array of node objects
		ListNode[] resultObjects = selectBy(productGroups, unit, quantity);
		// make a string array of equal size
		String[] resultStrings = new String[resultObjects.length];
		// write the toString() of each node into the string array
		for (int i = 0; i < resultObjects.length; i++) {
			resultStrings[i] = resultObjects[i].toString();
		}
		return resultStrings;
	}
	
	// overloaded method calls. Undefined parameters will be forwarded as null/0.
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
	
//	delete multiple nodes by product group
	
	public void deleteByProductGroup(ProductGroup[] productGroups) {
		ListNode currNode = dummyNode;
		ListNode prevNode = dummyNode;
		boolean toDelete;
		// loop through all nodes and check each for deletion
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			// for each node, loop through the given product groups and mark it for deletion if there's a match.
			toDelete = false;
			for(ProductGroup productGroup : productGroups) {
				if (currNode.getProductGroup() == productGroup) {
					toDelete = true;
					break;
				}
			}
			// If node is marked for deletion, unlink it. Otherwise it becomes the next prevNode.
			if (toDelete == true) {
				prevNode.setNextNode(currNode.getNextNode());
			} else {
				prevNode = currNode;
			}
		}
	}
	
//	delete a single node by id
	
	public void deleteNode(int id) {
		ListNode prevNode = dummyNode;
		ListNode currNode = dummyNode;
		// loop through list until node is identified
		while (currNode.getNextNode() != null) {
			currNode = currNode.getNextNode();
			if (currNode.getId() == id) {
				// once node is found, unlink it and return
				prevNode.setNextNode(currNode.getNextNode());
				return;
			}
			prevNode = currNode;
		}
		// if node isn't found, print an error message
		if (currNode.getNextNode() == null) {
			Out.println("Deleting node #" + id + ": Node not found.");
		}
	}
	
}
