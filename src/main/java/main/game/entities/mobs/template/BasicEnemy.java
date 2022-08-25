package main.game.entities.mobs.template;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public abstract class BasicEnemy extends Enemy {

	public BasicEnemy(RpgGame game, Vector2D pos) {
		super(game, pos);
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
}
