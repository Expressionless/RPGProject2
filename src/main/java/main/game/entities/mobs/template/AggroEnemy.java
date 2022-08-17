package main.game.entities.mobs.template;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public abstract class AggroEnemy extends Enemy {

	public AggroEnemy(RpgGame game, Vector2D pos) {
		super(game, pos);
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
