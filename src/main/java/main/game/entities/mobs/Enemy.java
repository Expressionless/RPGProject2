package main.game.entities.mobs;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;

public abstract class Enemy extends Mob {

	private String type;
	
	public Enemy(RpgGame game, Point pos) {
		super(game, pos); 
	}
	
	// Getters and Setters
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
