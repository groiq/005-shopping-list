package shopping.list;

class ListItem {
	
	private final String description;
	private int quantity;
	private final int defaultQuantity;
	private final Unit unit;
	private final ProductGroup productGroup;

	ListItem(String description, Unit unit, int defaultQuantity, ProductGroup productGroup) {
		this.description = description;
		this.defaultQuantity = defaultQuantity;
		this.quantity = 0;
		this.unit = unit;
		this.productGroup = productGroup;
	}

	int getQuantity() {
		return quantity;
	}

	void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	String getDescription() {
		return description;
	}

	int getDefaultQuantity() {
		return defaultQuantity;
	}

	Unit getUnit() {
		return unit;
	}

	ProductGroup getProductGroup() {
		return productGroup;
	}
	
	
	boolean getNeeded() {
		boolean needed = getQuantity() == 0 ? false : true ;
		return needed;
	}
	
	void setNeeded(boolean needed) {
		int quantity = needed ? getDefaultQuantity() : 0;
		setQuantity(quantity);
	}
	
	String getStats() {
		return "ListItem [description: " + getDescription() + ", product group: " 
				+ getProductGroup() + ", currently needed: " + getQuantity()
				+ " (default: " + getDefaultQuantity() + ") " + getUnit() + "]";
	}

	@Override
	public String toString() {
		String result;
		if (getQuantity() == 0) {
			result = "No " + getDescription() + " currently needed.";
		} else {
			result = getQuantity() + " " + unit + " of " + description + " are currently needed.";
		}
//		result =  "ListItem [getQuantity()=" + getQuantity() + ", getDescription()=" + getDescription()
//				+ ", getDefaultQuantity()=" + getDefaultQuantity() + ", getUnit()=" + getUnit() + ", getProductGroup()="
//				+ getProductGroup() + ", getNeeded()=" + getNeeded() + "]";
		
		return result;
	}
	
	

}
