package main.game.entities.mobs.neutral;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;
import main.game.entities.Mob;

public abstract class BasicPeacefulMob extends Mob {

	public BasicPeacefulMob(RpgGame game, Vector2D pos) {
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
