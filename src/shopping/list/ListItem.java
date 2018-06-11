package shopping.list;

class ListItem {
	
	private final String description;
	private final int quantity;
	private final Unit unit;
	private final ProductGroup productGroup;

	ListItem(String description, Unit unit, int quantity, ProductGroup productGroup) {
		this.description = description;
		this.quantity = quantity;
		this.unit = unit;
		this.productGroup = productGroup;
	}

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
	
	String getStats() {
		return "ListItem [description: " + getDescription() + ", product group: " 
				+ getProductGroup() + ", needed: " + getQuantity()
				+ " " + getUnit() + "]";
	}
	// scrap if not needed

	@Override
	public String toString() {
		return getQuantity() + " " + getUnit() + " of " + getDescription() + " needed from " + getProductGroup() + ".";
		
	}
	
}
