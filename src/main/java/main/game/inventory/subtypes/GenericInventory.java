package main.game.inventory.subtypes;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.inventory.Inventory;

public class GenericInventory extends Inventory {

	public GenericInventory(RpgGame game, Point screenPos, int w, int h) {
		super(game, screenPos, w, h);
		this.resetAllowedTypes();
	}

	@Override
	public GenericInventory copy() {
		return new GenericInventory(this.getGame(), this.getPos().copy(), this.getWidth(), this.getHeight());
	}

}
