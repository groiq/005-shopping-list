package shopping.list;

class ListItem {
	
	private String description;
	private int quantity;
	private int default_quantity;
	private Unit unit;
	private ProductGroup productGroup;
	// enum unit
	// enum product group

	ListItem(String description, Unit unit, int default_quantity, ProductGroup productGroup) {
		this.description = description;
		this.default_quantity = quantity;
		this.quantity = 0;
		this.unit = unit;
		this.productGroup = productGroup;
	}
	
	

}
