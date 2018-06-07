package shopping.list;

import inout.In;
import inout.Out;

public class ShoppingMain {

	public static void main(String[] args) {
		
		Out.println("read text file");
		
		In.open("GroceryInit.txt");
		if (!In.done()) {
			Out.println("Cannot open file GroceryInit.txt"); 
			return; 
		}
		String descr = In.readWord();
		while (In.done()) {
//			String pgs = In.readWord();
//			ProductGroup pg = ProductGroup.valueOf(pgs);
			ProductGroup pg = ProductGroup.valueOf(In.readWord());
			Unit unit = Unit.valueOf(In.readWord());
			int defaultQuantity = In.readInt();
			Out.print("Created: ");
			// comment this out to test for empty list
			ShoppingList.newItem(descr, unit, defaultQuantity, pg);
			descr = In.readWord();
			
		}
		
		
		

		

	}

}
