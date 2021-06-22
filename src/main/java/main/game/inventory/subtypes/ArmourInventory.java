package main.game.inventory.subtypes;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.inventory.Inventory;

public class ArmourInventory extends Inventory {

	public ArmourInventory(RpgGame game, Point screenPos) {
		super(game, screenPos, 1, 4);
		this.clearAllowedTypes();
		this.addAllowedTypes("ARMOUR");
	}

	@Override
	public Inventory copy() {
		return new ArmourInventory(this.getGame(), this.getPos().copy());
	}

}
