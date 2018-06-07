package shopping.list;

import inout.Out;

public class ShoppingMain {

	public static void main(String[] args) {
		Out.println("first, call shopping list statically");
		ShoppingList.newItem(null, null, 0, null);

	}

}
