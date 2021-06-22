package main.game.entities;

import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;

public abstract class Doodad extends Entity {

	public Doodad(RpgGame game, Point pos) {
		super(game, pos);
	}
}
