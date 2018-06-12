package shopping.list;

class ListNode {

	// Fields
	
	private final int id;
	private ListNode nextNode;
	private final ListItem item;

	// Constructor
	
	ListNode(int id, ListItem item) {
		this.id = id;
		this.item = item;
	}

	// Getters and setters
	
	ListNode getNextNode() {
		return nextNode;
	}

	void setNextNode(ListNode nextNode) {
		this.nextNode = nextNode;
	}

	int getId() {
		return id;
	}

	ListItem getItem() {
		return item;
	}
	
	int getQuantity() {
		return item != null ? item.getQuantity() : -1;
	}
	
	String getDescription() {
		return item != null ? item.getDescription() : "Error: Dummy node. No description available";
	}
	
	Unit getUnit() {
		return item != null ? item.getUnit() : null;
	}
	
	ProductGroup getProductGroup() {
		return item != null ? item.getProductGroup() : null;
	}
	
	int getProductOrder() {
		return item != null ? item.getProductGroup().ordinal() : -1;
	}

	// toString()
	
	@Override
	public String toString() {
		return "#" + this.getId() + ": " +getItem().toString();
	}

}
