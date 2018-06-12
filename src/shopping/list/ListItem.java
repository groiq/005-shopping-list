package shopping.list;

class ListItem {
	
	// Fields
	
	private final String description;
	private final int quantity;
	private final Unit unit;
	private final ProductGroup productGroup;

	//Constructor
	
	ListItem(String description, Unit unit, int quantity, ProductGroup productGroup) {
		this.description = description;
		this.quantity = quantity;
		this.unit = unit;
		this.productGroup = productGroup;
	}

	//Getters
	
	int getQuantity() {
		return quantity;
	}

	String getDescription() {
		return description;
	}

	Unit getUnit() {
		return unit;
	}

	ProductGroup getProductGroup() {
		return productGroup;
	}

//	toString()
	
	@Override
	public String toString() {
		return getQuantity() + " " + getUnit() + " of " + getDescription() + " needed from " + getProductGroup() + ".";
	}
	
}
