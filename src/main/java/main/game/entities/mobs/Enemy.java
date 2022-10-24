package main.game.entities.mobs;

import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;
import main.game.entities.Mob;

public abstract class Enemy extends Mob {

	private String type;
	
	public Enemy(EntityManager em, Vector2D pos) {
		super(em, pos); 
	}
	
	// Getters and Setters
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
