package main.game.inventory.subtypes;

import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;
import main.game.inventory.Inventory;

public class ArmourInventory extends Inventory {

	public ArmourInventory(RpgGame game, Vector2D screenPos) {
		super(game, screenPos, 1, 4);
		this.clearAllowedTypes();
		this.addAllowedTypes("ARMOUR");
	}

	@Override
	public Inventory copy() {
		return new ArmourInventory(this.getGame(), this.getPos().copy());
	}

}
