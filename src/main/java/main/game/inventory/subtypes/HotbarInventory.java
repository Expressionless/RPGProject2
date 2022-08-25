package main.game.inventory.subtypes;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.constants.InventoryConstants;
import main.game.RpgGame;
import main.game.inventory.Inventory;
import main.game.inventory.Slot;
import main.game.inventory.util.HotbarSelector;

public class HotbarInventory extends Inventory {

	private HotbarSelector selector;
	
	public HotbarInventory(RpgGame game, Vector2D screenPos) {
		super(game, screenPos, InventoryConstants.HOTBAR_WIDTH, InventoryConstants.HOTBAR_HEIGHT);
		selector = new HotbarSelector(game, screenPos.copy(), this);
		
		this.resetAllowedTypes();
	}

	@Override
	public Inventory copy() {
		return new HotbarInventory(this.getGame(), this.getPos().copy());
	}
	
	// Getters and Setters
	
	public Slot getSlot(int x) {
		return this.getSlot(0, x);
	}
	
	public HotbarSelector getSelector() {
		return selector;
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable parse(BinaryReader reader, int pos) {
		// TODO Auto-generated method stub
		return null;
	}
}
