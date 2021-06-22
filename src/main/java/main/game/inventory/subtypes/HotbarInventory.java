package main.game.inventory.subtypes;

import helix.utils.math.Point;
import main.constants.InventoryConstants;
import main.game.RpgGame;
import main.game.inventory.Inventory;
import main.game.inventory.Slot;
import main.game.inventory.util.HotbarSelector;

public class HotbarInventory extends Inventory {

	private HotbarSelector selector;
	
	public HotbarInventory(RpgGame game, Point screenPos) {
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
}
