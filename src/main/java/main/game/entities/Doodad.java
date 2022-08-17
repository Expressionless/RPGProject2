package main.game.entities;

import io.sly.helix.utils.math.Vector2D;
import main.game.Entity;
import main.game.RpgGame;

public abstract class Doodad extends Entity {

	public Doodad(RpgGame game, Vector2D pos) {
		super(game, pos);
	}
}
