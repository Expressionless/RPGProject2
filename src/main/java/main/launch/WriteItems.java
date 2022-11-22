package main.launch;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import main.constants.InventoryConstants;
import main.game.item.ItemInfo;

public class WriteItems {

	private static boolean[] STACKABLE = {true, false, false, false};
	private static boolean[] NONSTACKABLE = {false, false, false, false};
	
	public static void main(String[] args) {
		BinaryWriter writer = new BinaryWriter("/data/item");
		new ItemInfo(0, "material", "grass", InventoryConstants.MAX_STACK, STACKABLE).write(writer);
		new ItemInfo(1, "material", "wood", InventoryConstants.MAX_STACK, STACKABLE).write(writer);
		new ItemInfo(2, "weapon"  , "sword", 1, NONSTACKABLE).write(writer);
		new ItemInfo(3, "weapon"  , "bow", 1, NONSTACKABLE).write(writer);
		new ItemInfo(4, "weapon"  , "boomerang", 1, NONSTACKABLE).write(writer);
		new ItemInfo(5, "tool"    , "basic", 1, NONSTACKABLE).write(writer);
		new ItemInfo(6, "tool"    , "axe", 1, NONSTACKABLE).write(writer);
		new ItemInfo(7, "crafting", "shaft", 1, STACKABLE).write(writer);
		new ItemInfo(8, "tool"    , "pickaxe", 1, NONSTACKABLE).write(writer);
		new ItemInfo(9, "weapon"    , "bottle", 64, STACKABLE).write(writer);
		
		BinaryReader reader = new BinaryReader("/data/item");
		for(int i = 0; i < 10; i++) {
			ItemInfo item = new ItemInfo();
			item.parse(reader, i);
			System.out.println(item);
		}
	}

}
