package main.game.inventory.subtypes;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;

import main.game.inventory.Inventory;

public class ArmourInventory extends Inventory {

	public ArmourInventory(EntityManager em, Vector2D screenPos) {
		super(em, screenPos, 1, 4);
		this.clearAllowedTypes();
		this.addAllowedTypes("ARMOUR");
	}

	@Override
	public Inventory copy() {
		return new ArmourInventory(this.entityManager, this.getPos().copy());
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
