package main.game.entities.mobs.template;

import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public abstract class AggroEnemy extends Enemy {

	public AggroEnemy(RpgGame game, Point pos) {
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
