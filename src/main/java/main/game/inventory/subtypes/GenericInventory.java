package main.game.inventory.subtypes;

import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;
import main.game.inventory.Inventory;

public class GenericInventory extends Inventory {

	public GenericInventory(RpgGame game, Vector2D screenPos, int w, int h) {
		super(game, screenPos, w, h);
		this.resetAllowedTypes();
	}

	@Override
	public GenericInventory copy() {
		return new GenericInventory(this.getGame(), this.getPos().copy(), this.getWidth(), this.getHeight());
	}

}