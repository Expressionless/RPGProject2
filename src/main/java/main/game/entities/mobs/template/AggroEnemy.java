package main.game.entities.mobs.template;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;

import main.game.entities.mobs.Enemy;

public abstract class AggroEnemy extends Enemy {

	public AggroEnemy(EntityManager em, Vector2D pos) {
		super(em, pos);
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
