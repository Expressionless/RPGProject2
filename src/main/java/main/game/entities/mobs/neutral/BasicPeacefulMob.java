package main.game.entities.mobs.neutral;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;

import main.game.entities.Mob;

public abstract class BasicPeacefulMob extends Mob {

	public BasicPeacefulMob(EntityManager em, Vector2D pos) {
		super(em, pos);
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public Serializable parse(BinaryReader reader, int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void step(float delta) {
		
	}

}
