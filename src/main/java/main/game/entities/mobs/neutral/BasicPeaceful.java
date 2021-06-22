package main.game.entities.mobs.neutral;

import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;

public abstract class BasicPeaceful extends Mob {

	public BasicPeaceful(RpgGame game, Point pos) {
		super(game, pos);
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		return false;
	}


	@Override
	public void step(float delta) {
		
	}

}
