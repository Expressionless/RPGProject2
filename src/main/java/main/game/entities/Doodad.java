package main.game.entities;

import io.sly.helix.utils.math.Vector2D;
import main.game.Entity;
import main.game.EntityManager;

public abstract class Doodad extends Entity {

	public Doodad(EntityManager em, Vector2D pos) {
		super(em, pos);
	}
}
