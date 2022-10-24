package main.game.inventory.subtypes;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;

import main.game.inventory.Inventory;

public class GenericInventory extends Inventory {

	public GenericInventory(EntityManager em, Vector2D screenPos, int w, int h) {
		super(em, screenPos, w, h);
		this.resetAllowedTypes();
	}

	@Override
	public GenericInventory copy() {
		return new GenericInventory(entityManager, this.getPos().copy(), this.getInventoryWidth(), this.getInventoryHeight());
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