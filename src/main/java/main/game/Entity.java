package main.game;

import helix.utils.math.Point;
import main.GameData;
import main.game.entities.mobs.neutral.Player;

public abstract class Entity extends helix.game.objects.Entity {

	private final RpgGame game;
	
	public Entity(RpgGame game, Point pos) {
		super(game.getData(), pos);
		this.game = game;
	}
	
	public RpgGame getGame() {
		return game;
	}
	
	public GameData getGameData() {
		return this.game.getGameData();
	}
	
	public Player getPlayer() {
		return this.getGameData().getPlayer();
	}

}
